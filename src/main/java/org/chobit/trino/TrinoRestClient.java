package org.chobit.trino;

import okhttp3.*;
import org.chobit.commons.utils.Collections2;
import org.chobit.trino.models.ExecuteResults;
import org.chobit.trino.models.QueryStatusInfo;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;
import static java.net.HttpURLConnection.*;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.chobit.commons.utils.StrKit.isNotBlank;
import static org.chobit.trino.JsonCodec.jsonCodec;
import static org.chobit.trino.Paths.QUERY;
import static org.chobit.trino.Paths.STATEMENT;


/**
 * trino rest 请求客户端
 *
 * @author zhangrui
 * @since 2025/3/24
 */
public class TrinoRestClient implements TrinoClient {


    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain; charset=utf-8");

    private static final JsonCodec<ExecuteResults> QUERY_RESULT_CODEC = jsonCodec(ExecuteResults.class);
    private static final JsonCodec<QueryStatusInfo> QUERY_INFO_CODEC = jsonCodec(QueryStatusInfo.class);

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public boolean kill(String queryId, ClientSession session) {

        URI uri = URI.create(session.getServer() + QUERY.path + queryId);

        Request request = prepareRequest(HttpUrl.get(uri), session)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != HTTP_OK && response.code() != HTTP_NO_CONTENT) {
                String responseBody = (null != response.body() ? response.body().string() : "none");
                String message = format("kill query failed, response code:[%d], detail:[%s]",
                        response.code(), responseBody);
                throw new ClientException(message);
            }
        } catch (ClientException e) {
            throw e;
        } catch (IOException e) {
            throw new ClientException("kill query error", e);
        }

        return true;
    }


    @Override
    public QueryStatusInfo queryStatus(String queryId, ClientSession session) {
        URI uri = URI.create(session.getServer() + QUERY.path + queryId);

        Request request = prepareRequest(HttpUrl.get(uri), session)
                .get()
                .build();

        JsonResponse<QueryStatusInfo> response = JsonResponse.execute(QUERY_INFO_CODEC, client, request);
        if (null != response.getException()) {
            throw new ClientException("query trino error.", response.getException());
        }

        if (null == response.getValue()) {
            throw new ClientException(format("query trino error. response code:[%d], message:[%s]",
                    response.getStatusCode(), response.getStatusMessage()));
        }

        return response.getValue();
    }


    @Override
    public ExecuteStatusInfo execute(String sql, ClientSession session) {
        return this.execute(sql, session, null);
    }


    @Override
    public ExecuteResults executeAndQuery(String sql, ClientSession session) {
        final List<List<Object>> resultData = new LinkedList<>();
        ExecuteStatusInfo r = this.execute(sql, session, info -> {
            if (null != info && null != info.getValue() && Collections2.isNotEmpty(info.getValue().getData())) {
                resultData.addAll(info.getValue().getData());
            }
        });

        if (null == r) {
            return null;
        }

        ExecuteResults result = (ExecuteResults) r;
        result.setData(resultData);

        return result;
    }


    @Override
    public ExecuteStatusInfo execute(String sql,
                                     ClientSession session,
                                     StageCallback<JsonResponse<ExecuteResults>> callback) {
        try (QueryRunner queryRunner = new QueryRunner(session, client, sql, callback)) {
            return queryRunner.run();
        }
    }


    private Request buildQueryRequest(ClientSession session, String query, String path) {
        HttpUrl url = HttpUrl.get(session.getServer());
        if (url == null) {
            throw new ClientException("Invalid server URL: " + session.getServer());
        }
        url = url.newBuilder().encodedPath(path).build();

        Request.Builder builder = prepareRequest(url, session)
                .post(RequestBody.Companion.create(query, MEDIA_TYPE_TEXT));

        return builder.build();
    }


    private Request.Builder prepareRequest(HttpUrl url, ClientSession session) {
        Request.Builder builder = new Request.Builder()
                .url(url);

        Headers headers = TrinoHeader.builder()
                .user(session.getUser())
                .source(session.getSource())
                .timeZone(session.getTimeZone().getId())
                .transactionId(session.getTransactionId())
                .acceptEncoding(session.isCompressionDisabled())
                .catalog(session.getCatalog())
                .schema(session.getSchema())
                .session(session.getHeaderSession())
                .build();

        builder.headers(headers);
        return builder;
    }


    /**
     * Trino查询执行器
     *
     * @author zhangrui
     * @since 2025/3/25
     */
    private class QueryRunner implements Closeable {


        private final AtomicReference<State> state = new AtomicReference<>(State.RUNNING);

        private final ClientSession session;
        private final OkHttpClient client;
        private final long requestTimeoutMillis;
        private final String query;
        private final StageCallback<JsonResponse<ExecuteResults>> callback;

        private final AtomicReference<ExecuteResults> currentResults = new AtomicReference<>();


        public QueryRunner(ClientSession session,
                           OkHttpClient client,
                           String query,
                           StageCallback<JsonResponse<ExecuteResults>> callback) {
            this.session = requireNonNull(session);
            this.client = requireNonNull(client);
            this.requestTimeoutMillis = (session.getClientRequestTimeout() > 0 ?
                    session.getClientRequestTimeout() : TimeUnit.MINUTES.toMillis(1L));
            this.query = query;
            this.callback = callback;
        }


        public ExecuteResults run() {

            long start = System.currentTimeMillis();

            Request request = buildQueryRequest(session, query, STATEMENT.path);
            JsonResponse<ExecuteResults> response = JsonResponse.execute(QUERY_RESULT_CODEC, client, request);
            currentResults.set(response.getValue());

            if (null != callback) {
                callback.onStage(response);
            }

            boolean advanced = advance(start);
            while (advanced) {
                advanced = advance(start);
            }

            return currentStatusInfo();
        }


        private boolean advance(long startTime) {
            if (!isRunning()) {
                return false;
            }

            URI nextUri = currentStatusInfo().getNextUri();
            if (null == nextUri) {
                state.compareAndSet(State.RUNNING, State.FINISHED);
                return false;
            }

            Request request = prepareRequest(HttpUrl.get(nextUri), session).build();
            Exception cause = null;
            long attempts = 0;
            while (true) {
                if (isClientAborted()) {
                    return false;
                }

                long elapsed = System.currentTimeMillis() - startTime;
                if ((System.currentTimeMillis() - startTime) > this.requestTimeoutMillis) {
                    throw new RuntimeException(format("Error fetching next (attempts: %s, elapsed: %s)", attempts, elapsed));
                }

                if (attempts > 0) {
                    try {
                        MILLISECONDS.sleep(attempts * 100);
                    } catch (InterruptedException e) {
                        try {
                            this.close();
                        } finally {
                            Thread.currentThread().interrupt();
                        }
                        throw new RuntimeException("trino query client thread was interrupted.", cause);
                    }
                }

                attempts++;

                JsonResponse<ExecuteResults> response;
                try {
                    response = JsonResponse.execute(QUERY_RESULT_CODEC, client, request);
                } catch (Exception e) {
                    cause = e;
                    continue;
                }

                if (null != callback) {
                    callback.onStage(response);
                }

                if (response.getStatusCode() == HTTP_OK && response.isHasValue()) {
                    currentResults.set(response.getValue());
                    return true;
                }

                if (response.getStatusCode() != HTTP_UNAUTHORIZED) {
                    state.compareAndSet(State.RUNNING, State.CLIENT_ERROR);
                    throw requestFailedException(request, response);
                }
            }
        }


        private RuntimeException requestFailedException(Request request, JsonResponse<ExecuteResults> response) {
            if (response.isHasValue()) {
                return new RuntimeException(format("Error fetching next at %s returned HTTP %s",
                        request.url(), response.getStatusCode()));
            }

            if (response.getStatusCode() == HTTP_UNAUTHORIZED) {
                return new ClientException("Authentication failed" +
                        Optional.ofNullable(response.getStatusMessage())
                                .map(message -> ": " + message)
                                .orElse(""));
            }
            String msg;
            if (isNotBlank(response.getResponseBody())) {
                msg = format("Error fetching next at %s returned an invalid response: %s [Error: %s]",
                        request.url(), response, response.getResponseBody());
            } else {
                msg = "<Response Too Large>";
            }
            return new RuntimeException(msg, response.getException());
        }


        public boolean isRunning() {
            return state.get() == State.RUNNING;
        }

        public boolean isClientAborted() {
            return state.get() == State.CLIENT_ABORTED;
        }

        public ExecuteResults currentStatusInfo() {
            return currentResults.get();
        }

        @Override
        public void close() {
            if (state.compareAndSet(State.RUNNING, State.CLIENT_ABORTED)) {
                URI uri = currentResults.get().getNextUri();
                if (null != uri) {
                    kill(currentStatusInfo().getId(), session);
                }
            }
        }


        /**
         * trino 指令执行状态
         *
         * @author zhangrui
         * @since 2025/3/25
         */
        enum State {

            /**
             * submitted to server, not in terminal state (including planning, queued, running, etc)
             */
            RUNNING,

            CLIENT_ERROR,

            CLIENT_ABORTED,

            /**
             * finished on remote Trino server (including failed and successfully completed)
             */
            FINISHED,
        }
    }
}
