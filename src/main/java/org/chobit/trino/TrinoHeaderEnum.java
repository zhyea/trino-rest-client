package org.chobit.trino;

/**
 * header信息枚举
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public enum TrinoHeaderEnum {


    USER("X-Presto-User"),

    SOURCE("X-Presto-Source"),

    CATALOG("X-Presto-Catalog"),

    SCHEMA("X-Presto-Schema"),

    TIME_ZONE("X-Presto-Time-Zone"),

    CURRENT_STATE("X-Presto-Current-State"),

    MAX_WAIT("X-Presto-Max-Wait"),

    MAX_SIZE("X-Presto-Max-Size"),

    PAGE_SEQUENCE_ID("X-Presto-Page-Sequence-Id"),

    SESSION("X-Presto-Session"),

    USER_AGENT("User-Agent"),

    AUTHORIZATION("Authorization"),
    ;

    private final String header;


    TrinoHeaderEnum(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

}
