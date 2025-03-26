package org.chobit.trino;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * 查询结果
 *
 * @author zhangrui
 * @since 2025/3/23
 */
public class QueryResults implements QueryStatusInfo {

    private final String id;
    private final URI infoUri;
    private final URI partialCancelUri;
    private final URI nextUri;
    private final List<Column> columns;
    private final Iterable<List<Object>> data;
    private final QueryError error;
    private final String updateType;
    private final Long updateCount;
    private final StatementStats stats;


    @JsonCreator
    public QueryResults(@JsonProperty("id") String id,
                        @JsonProperty("infoUri") URI infoUri,
                        @JsonProperty("partialCancelUri") URI partialCancelUri,
                        @JsonProperty("nextUri") URI nextUri,
                        @JsonProperty("columns") List<Column> columns,
                        @JsonProperty("data") List<List<Object>> data,
                        @JsonProperty("error") QueryError error,
                        @JsonProperty("updateType") String updateType,
                        @JsonProperty("updateCount") Long updateCount,
                        @JsonProperty("stats") StatementStats stats) {

        this.id = requireNonNull(id, "id is null");
        this.infoUri = requireNonNull(infoUri, "infoUri is null");
        this.partialCancelUri = partialCancelUri;
        this.nextUri = nextUri;
        this.columns = (columns != null) ? Collections.unmodifiableList(columns) : null;
        this.data = (data != null) ? Collections.unmodifiableList(data) : null;
        this.error = error;
        this.updateType = updateType;
        this.updateCount = updateCount;
        this.stats = stats;
    }


    public String getId() {
        return id;
    }

    public URI getInfoUri() {
        return infoUri;
    }

    public URI getPartialCancelUri() {
        return partialCancelUri;
    }

    public URI getNextUri() {
        return nextUri;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Iterable<List<Object>> getData() {
        return data;
    }

    public QueryError getError() {
        return error;
    }

    public String getUpdateType() {
        return updateType;
    }

    public Long getUpdateCount() {
        return updateCount;
    }

    public StatementStats getStats() {
        return stats;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "id='" + id + '\'' +
                ", infoUri=" + infoUri +
                ", partialCancelUri=" + partialCancelUri +
                ", nextUri=" + nextUri +
                ", columns=" + columns +
                ", data=" + data +
                ", error=" + error +
                ", updateType='" + updateType + '\'' +
                ", updateCount=" + updateCount +
                '}';
    }
}
