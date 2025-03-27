package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * 列信息
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public record Column(String name,
                     String type) {

    @JsonCreator
    public Column(@JsonProperty("name") String name,
                  @JsonProperty("type") String type) {

        this.name = requireNonNull(name, "name is null");
        this.type = requireNonNull(type, "type is null");
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
