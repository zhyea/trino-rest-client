package org.chobit.trino;

import java.io.Closeable;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Trino客户端接口
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public interface TrinoClient extends Closeable {


    boolean kill(String queryId, ClientSession session);


    QueryResult query(String sql, ClientSession session);


    void execute(String sql, ClientSession session);


    boolean advance();


}
