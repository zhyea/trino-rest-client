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


    boolean kill(String queryId, ClientContext context);


    QueryResults query(String queryId, ClientContext context);


    ExecuteStatusInfo execute(String sql, ClientContext context);


    ExecuteStatusInfo execute(String sql,
                              ClientContext context,
                              StageCallback<JsonResponse<ExecuteResults>> callback);

}
