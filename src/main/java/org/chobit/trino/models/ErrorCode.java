package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonProperty;


public record ErrorCode(
        @JsonProperty("code") int code,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type) {

}
