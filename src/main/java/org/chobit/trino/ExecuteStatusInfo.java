package org.chobit.trino;

import org.chobit.trino.models.Column;
import org.chobit.trino.models.QueryError;
import org.chobit.trino.models.StatementStats;

import java.net.URI;
import java.util.List;

/**
 * 查询结果描述接口
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public interface ExecuteStatusInfo {

    String getId();

    URI getInfoUri();

    URI getPartialCancelUri();

    URI getNextUri();

    List<Column> getColumns();

    StatementStats getStats();

    QueryError getError();

    String getUpdateType();

    Long getUpdateCount();
}
