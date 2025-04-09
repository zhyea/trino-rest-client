package org.chobit.trino;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;

import java.io.IOException;
import java.io.UncheckedIOException;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.chobit.commons.utils.StrKit.isBlank;

/**
 * 处理json结构的返回值
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public final class JsonResponse<T> {


    public static final String LOCATION = "Location";

    private final int statusCode;
    private final String statusMessage;
    private final Headers headers;
    private final String responseBody;

    private final boolean hasValue;
    private final T value;
    private final IllegalArgumentException exception;


    private JsonResponse(int statusCode,
                         String statusMessage,
                         Headers headers,
                         String responseBody) {

        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = requireNonNull(headers, "headers is null");
        this.responseBody = requireNonNull(responseBody, "responseBody is null");

        this.hasValue = false;
        this.value = null;
        this.exception = null;
    }


    private JsonResponse(int statusCode,
                         String statusMessage,
                         Headers headers,
                         String responseBody,
                         T value,
                         IllegalArgumentException exception) {

        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.headers = requireNonNull(headers, "headers is null");
        this.responseBody = responseBody;
        this.value = value;
        this.exception = exception;
        this.hasValue = (exception == null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public boolean isHasValue() {
        return hasValue;
    }

    public T getValue() {
        return value;
    }

    public IllegalArgumentException getException() {
        return exception;
    }

    public static <T> JsonResponse<T> execute(JsonCodec<T> codec, OkHttpClient client, Request request) {
        try (Response response = client.newCall(request).execute()) {
            if ((response.code() == 307) || (response.code() == 308)) {
                String location = response.header(LOCATION);
                if (location != null) {
                    request = request.newBuilder().url(location).build();
                    return execute(codec, client, request);
                }
            }

            ResponseBody responseBody = requireNonNull(response.body());

            if (!isJson(responseBody.contentType())) {
                return new JsonResponse<>(response.code(), response.message(), response.headers(), responseBody.string());
            }

            T value = null;
            String body = null;
            IllegalArgumentException exception = null;

            try {
                if (responseBody.contentLength() < 0) {
                    value = codec.fromJson(responseBody.byteStream());
                } else {
                    body = responseBody.string();
                    value = codec.fromJson(body);
                }
            } catch (JsonProcessingException e) {
                String message;
                if (isBlank(body)) {
                    message = format("Unable to create %s from JSON response:\n[%s]", codec.getType(), body);
                } else {
                    message = format("Unable to create %s from JSON response", codec.getType());
                }
                exception = new IllegalArgumentException(message, e);
            }
            return new JsonResponse<>(response.code(), response.message(), response.headers(), body, value, exception);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    /**
     * 判断http请求体或返回值是否是json
     *
     * @param type http请求或响应信息的MediaType
     * @return http请求体或返回值是否是json
     */
    private static boolean isJson(MediaType type) {
        return (type != null) && "application".equals(type.type()) && "json".equals(type.subtype());
    }

}
