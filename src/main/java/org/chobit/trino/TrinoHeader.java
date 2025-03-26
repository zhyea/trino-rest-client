package org.chobit.trino;

import okhttp3.Headers;
import org.chobit.commons.utils.StrKit;

import java.util.HashMap;
import java.util.Map;

import static org.chobit.commons.utils.StrKit.isNotBlank;

/**
 * header信息
 *
 * @author zhangrui
 * @since 2025/3/23
 */
class TrinoHeader {


    private static final String USER = "X-Presto-User";
    private static final String SOURCE = "X-Presto-Source";
    private static final String CATALOG = "X-Presto-Catalog";
    private static final String SCHEMA = "X-Presto-Schema";
    private static final String TIME_ZONE = "X-Presto-Time-Zone";
    private static final String CURRENT_STATE = "X-Presto-Current-State";
    private static final String MAX_WAIT = "X-Presto-Max-Wait";
    private static final String MAX_SIZE = "X-Presto-Max-Size";
    private static final String PAGE_SEQUENCE_ID = "X-Presto-Page-Sequence-Id";
    private static final String SESSION = "X-Presto-Session";
    private static final String USER_AGENT = "User-Agent";
    private static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "X-Presto-Transaction-Id";


    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String USER_AGENT_VALUE = TrinoRestClient.class.getSimpleName() +
            "/" + StrKit.coalesce(TrinoRestClient.class.getPackage().getImplementationVersion(), "unknown");


    static Builder builder() {
        return new Builder();
    }


    static final class Builder {


        private final Map<String, String> headers = new HashMap<>(16);

        private Builder() {
        }

        Builder user(String user) {
            if (isNotBlank(user)) {
                headers.put(USER, user);
            }
            return this;
        }


        Builder source(String source) {
            if (isNotBlank(source)) {
                headers.put(SOURCE, source);
            }
            return this;
        }


        Builder catalog(String catalog) {
            if (isNotBlank(catalog)) {
                headers.put(CATALOG, catalog);
            }
            return this;
        }


        Builder schema(String schema) {
            if (isNotBlank(schema)) {
                headers.put(SCHEMA, schema);
            }
            return this;
        }


        Builder timeZone(String timeZone) {
            if (isNotBlank(timeZone)) {
                headers.put(TIME_ZONE, timeZone);
            }
            return this;
        }


        Builder currentState(String currentState) {
            if (isNotBlank(currentState)) {
                headers.put(CURRENT_STATE, currentState);
            }
            return this;
        }


        Builder maxWait(Integer maxWait) {
            if (null != maxWait && maxWait != 0) {
                headers.put(MAX_WAIT, maxWait.toString());
            }
            return this;
        }


        Builder maxSize(Integer maxSize) {
            if (null != maxSize && maxSize != 0) {
                headers.put(MAX_SIZE, maxSize.toString());
            }
            return this;
        }


        Builder pageSequenceId(String pageSequenceId) {
            if (isNotBlank(pageSequenceId)) {
                headers.put(PAGE_SEQUENCE_ID, pageSequenceId);
            }
            return this;
        }


        Builder session(String session) {
            if (isNotBlank(session)) {
                headers.put(SESSION, session);
            }
            return this;
        }


        Builder userAgent(String userAgent) {
            if (isNotBlank(userAgent)) {
                headers.put(USER_AGENT, userAgent);
            }
            return this;
        }


        Builder authorization(String authorization) {
            headers.put(AUTHORIZATION, authorization);
            return this;
        }


        Builder transactionId(String transactionId) {
            if (isNotBlank(transactionId)) {
                headers.put(TRANSACTION_ID, transactionId);
            }

            return this;
        }


        Builder acceptEncoding(boolean compressionDisabled) {
            if (compressionDisabled) {
                headers.put(ACCEPT_ENCODING, "identity");
            }

            return this;
        }


        Headers build() {
            if (!headers.containsKey(USER_AGENT)) {
                headers.put(USER_AGENT, USER_AGENT_VALUE);
            }

            return Headers.of(headers);
        }

    }


}
