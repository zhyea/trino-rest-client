package org.chobit.trino;

import java.io.Closeable;

/**
 * Trino客户端接口
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public interface TrinoClient {


    boolean kill(String queryId, ClientSession session);


    QueryStatusInfo query(String queryId, ClientSession session);


    QueryStatusInfo execute(String sql, ClientSession session);


    QueryStatusInfo executeWithAdvance(String query, ClientSession session);
}
