package org.chobit.trino;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * @author zhangrui
 * @since 2025/3/25
 */
public record StatementStats(String state,
                             boolean queued,
                             boolean scheduled,
                             int nodes,
                             int totalSplits,
                             int queuedSplits,
                             int runningSplits,
                             int completedSplits,
                             long cpuTimeMillis,
                             long wallTimeMillis,
                             long queuedTimeMillis,
                             long elapsedTimeMillis,
                             long processedRows,
                             long processedBytes,
                             long physicalInputBytes,
                             long peakMemoryBytes,
                             long spilledBytes,
                             StageStats rootStage) {


    @JsonCreator
    public StatementStats(
            @JsonProperty("state") String state,
            @JsonProperty("queued") boolean queued,
            @JsonProperty("scheduled") boolean scheduled,
            @JsonProperty("nodes") int nodes,
            @JsonProperty("totalSplits") int totalSplits,
            @JsonProperty("queuedSplits") int queuedSplits,
            @JsonProperty("runningSplits") int runningSplits,
            @JsonProperty("completedSplits") int completedSplits,
            @JsonProperty("cpuTimeMillis") long cpuTimeMillis,
            @JsonProperty("wallTimeMillis") long wallTimeMillis,
            @JsonProperty("queuedTimeMillis") long queuedTimeMillis,
            @JsonProperty("elapsedTimeMillis") long elapsedTimeMillis,
            @JsonProperty("processedRows") long processedRows,
            @JsonProperty("processedBytes") long processedBytes,
            @JsonProperty("physicalInputBytes") long physicalInputBytes,
            @JsonProperty("peakMemoryBytes") long peakMemoryBytes,
            @JsonProperty("spilledBytes") long spilledBytes,
            @JsonProperty("rootStage") StageStats rootStage) {
        this.state = requireNonNull(state, "state is null");
        this.queued = queued;
        this.scheduled = scheduled;
        this.nodes = nodes;
        this.totalSplits = totalSplits;
        this.queuedSplits = queuedSplits;
        this.runningSplits = runningSplits;
        this.completedSplits = completedSplits;
        this.cpuTimeMillis = cpuTimeMillis;
        this.wallTimeMillis = wallTimeMillis;
        this.queuedTimeMillis = queuedTimeMillis;
        this.elapsedTimeMillis = elapsedTimeMillis;
        this.processedRows = processedRows;
        this.processedBytes = processedBytes;
        this.physicalInputBytes = physicalInputBytes;
        this.peakMemoryBytes = peakMemoryBytes;
        this.spilledBytes = spilledBytes;
        this.rootStage = rootStage;
    }
}
