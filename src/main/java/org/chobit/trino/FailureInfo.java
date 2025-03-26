package org.chobit.trino;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * 执行失败信息描述
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public record FailureInfo(String type,
                          String message,
                          FailureInfo cause,
                          List<FailureInfo> suppressed,
                          List<String> stack,
                          ErrorLocation errorLocation) {


    @JsonCreator
    public FailureInfo(
            @JsonProperty("type") String type,
            @JsonProperty("message") String message,
            @JsonProperty("cause") FailureInfo cause,
            @JsonProperty("suppressed") List<FailureInfo> suppressed,
            @JsonProperty("stack") List<String> stack,
            @JsonProperty("errorLocation") ErrorLocation errorLocation) {

        this.type = type;
        this.message = message;
        this.cause = cause;
        this.suppressed = Collections.unmodifiableList(suppressed);
        this.stack = Collections.unmodifiableList(stack);
        this.errorLocation = errorLocation;
    }


    @Override
    public String toString() {
        return "FailureInfo{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", cause=" + cause +
                ", suppressed=" + suppressed +
                ", stack=" + stack +
                ", errorLocation=" + errorLocation +
                '}';
    }
}
