package org.chobit.trino;

/**
 * Trino客户端接口
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public interface TrinoClient {


    boolean kill(String queryId, ClientSession session);


    QueryStatusInfo query(String queryId, ClientSession session);


    JsonResponse<QueryResults> execute(String sql, ClientSession session);


    QueryStatusInfo executeWithAdvance(String query, ClientSession session);
}
