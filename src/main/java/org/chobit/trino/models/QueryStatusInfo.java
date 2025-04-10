package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.chobit.trino.constants.QueryState;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询状态信息
 *
 * @author zhangrui
 * @since 2025/3/27
 */
public record QueryStatusInfo(@JsonProperty("queryId") String queryId,
                              @JsonProperty("session") SessionRepresentation session,
                              @JsonProperty("state") QueryState state,
                              @JsonProperty("self") URI self,
                              @JsonProperty("fieldNames") List<String> fieldNames,
                              @JsonProperty("query") String query,
                              @JsonProperty("preparedQuery") String preparedQuery,
                              @JsonProperty("queryStats") QueryStats queryStats,
                              @JsonProperty("setCatalog") String setCatalog,
                              @JsonProperty("setSchema") String setSchema,
                              @JsonProperty("setPath") String setPath,
                              @JsonProperty("setSessionProperties") Map<String, String> setSessionProperties,
                              @JsonProperty("resetSessionProperties") Set<String> resetSessionProperties,
                              @JsonProperty("setRoles") Map<String, SelectedRole> setRoles,
                              @JsonProperty("addedPreparedStatements") Map<String, String> addedPreparedStatements,
                              @JsonProperty("deallocatedPreparedStatements") Set<String> deallocatedPreparedStatements,
                              @JsonProperty("startedTransactionId") String startedTransactionId,
                              @JsonProperty("clearTransactionId") boolean clearTransactionId,
                              @JsonProperty("updateType") String updateType,
                              @JsonProperty("failureInfo") FailureInfo failureInfo,
                              @JsonProperty("errorCode") ErrorCode errorCode,
                              @JsonProperty("referencedTables") List<TableInfo> referencedTables,
                              @JsonProperty("finalQueryInfo") boolean finalQueryInfo,
                              @JsonProperty("resourceGroupId") List<String> resourceGroupId,
                              @JsonProperty("queryType") String queryType,
                              @JsonProperty("retryPolicy") String retryPolicy) {
}
