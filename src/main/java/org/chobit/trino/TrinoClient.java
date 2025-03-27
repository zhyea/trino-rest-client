package org.chobit.trino;

import org.chobit.trino.models.ExecuteResults;
import org.chobit.trino.models.QueryResults;

/**
 * Trino客户端接口
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public interface TrinoClient {


    boolean kill(String queryId, ClientSession session);


    QueryResults query(String queryId, ClientSession session);


    ExecuteStatusInfo execute(String sql, ClientSession session);


    ExecuteStatusInfo execute(String sql,
                              ClientSession session,
                              StageCallback<JsonResponse<ExecuteResults>> callback);

}
