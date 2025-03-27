package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trino查询错误
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public record QueryError(String message,
                         String sqlState,
                         int errorCode,
                         String errorName,
                         String errorType,
                         ErrorLocation errorLocation,
                         FailureInfo failureInfo) {

    @JsonCreator
    public QueryError(
            @JsonProperty("message") String message,
            @JsonProperty("sqlState") String sqlState,
            @JsonProperty("errorCode") int errorCode,
            @JsonProperty("errorName") String errorName,
            @JsonProperty("errorType") String errorType,
            @JsonProperty("errorLocation") ErrorLocation errorLocation,
            @JsonProperty("failureInfo") FailureInfo failureInfo) {
        this.message = message;
        this.sqlState = sqlState;
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorType = errorType;
        this.errorLocation = errorLocation;
        this.failureInfo = failureInfo;
    }

    @Override
    public String toString() {
        return "QueryError{" +
                "message='" + message + '\'' +
                ", sqlState='" + sqlState + '\'' +
                ", errorCode=" + errorCode +
                ", errorName='" + errorName + '\'' +
                ", errorType='" + errorType + '\'' +
                ", errorLocation=" + errorLocation +
                ", failureInfo=" + failureInfo +
                '}';
    }
}
