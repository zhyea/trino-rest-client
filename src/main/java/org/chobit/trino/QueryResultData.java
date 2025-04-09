package org.chobit.trino;

import java.util.List;

/**
 * 查询结果数据接口
 *
 * @author zhangrui
 * @since 2025/4/9
 */
public interface QueryResultData {

    Iterable<List<Object>> getData();

}
