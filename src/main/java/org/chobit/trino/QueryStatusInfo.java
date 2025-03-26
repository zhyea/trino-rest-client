package org.chobit.trino;

import java.net.URI;
import java.util.List;

/**
 * 查询结果描述接口
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public interface QueryStatusInfo {

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
