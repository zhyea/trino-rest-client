package org.chobit.trino;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * sql错误位置信息
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public record ErrorLocation(int lineNumber, int columnNumber) {

    @JsonCreator
    public ErrorLocation(@JsonProperty("lineNumber") int lineNumber,
                         @JsonProperty("columnNumber") int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }


    @Override
    public String toString() {
        return "ErrorLocation{" +
                "lineNumber=" + lineNumber +
                ", columnNumber=" + columnNumber +
                '}';
    }
}
