package org.chobit.trino;

import org.chobit.trino.models.ExecuteResults;
import org.chobit.trino.models.QueryStatusInfo;

/**
 * Trino客户端接口
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public interface TrinoClient {

    /**
     * 取消当前正在执行的查询任务
     *
     * @param queryId 查询ID
     * @param context 客户端上下文
     * @return 是否执行成功
     */
    boolean kill(String queryId, ClientSession context);


    /**
     * 获取查询任务的状态
     *
     * @param queryId 查询ID
     * @param context 客户端上下文
     * @return 对应查询任务的状态
     */
    QueryStatusInfo queryStatus(String queryId, ClientSession context);


    /**
     * 执行查询SQL，直到结束
     * <p>
     * 该方法并不能得到查询结果，只能查询任务结束时得到查询任务的最终状态
     *
     * @param sql     查询SQL
     * @param context 客户端上下文
     * @return 查询任务的最终状态
     */
    ExecuteStatusInfo execute(String sql, ClientSession context);


    /**
     * 执行查询SQL，直到结束
     * <p>
     * 该方法的返回值不是sql的查询结果，但可以通过callback方法从中间结果集中得到查询结果
     *
     * @param sql      查询SQL
     * @param context  客户端上下文
     * @param callback 回调函数，通过该函数可以获取到任务的中间结果集
     * @return 查询任务的最终状态
     */
    ExecuteStatusInfo execute(String sql,
                              ClientSession context,
                              StageCallback<JsonResponse<ExecuteResults>> callback);


    /**
     * 执行查询SQL，直到结束并获取查询的结果集
     * <p>
     * 该方法的返回值不是sql的查询结果，但可以通过callback方法从中间结果集中得到查询结果
     *
     * @param sql     查询SQL
     * @param context 客户端上下文
     * @return 查询任务执行结果
     */
    ExecuteResults executeAndQuery(String sql, ClientSession context);

}
