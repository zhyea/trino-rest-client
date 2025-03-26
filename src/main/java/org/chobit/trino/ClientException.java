package org.chobit.trino;

/**
 * 客户端请求异常
 *
 * @author zhangrui
 * @since 2025/3/24
 */
public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

}
