package org.chobit.trino;

/**
 * Trino Rest API path信息
 *
 * @author zhangrui
 * @since 2025/3/26
 */
public enum Paths {


    STATEMENT("/v1/statement"),

    QUERY("/v1/query/"),
    ;


    public final String path;


    Paths(String path) {
        this.path = path;
    }

}
