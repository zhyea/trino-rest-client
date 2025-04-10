package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * stage stats
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public record StageStats(String stageId,
                         String state,
                         boolean done,
                         int nodes,
                         int totalSplits,
                         int queuedSplits,
                         int runningSplits,
                         int completedSplits,
                         long cpuTimeMillis,
                         long wallTimeMillis,
                         long processedRows,
                         long processedBytes,
                         long physicalInputBytes,
                         int failedTasks,
                         boolean coordinatorOnly,
                         List<StageStats> subStages) {


	@JsonCreator
	public StageStats(
			@JsonProperty("stageId") String stageId,
			@JsonProperty("state") String state,
			@JsonProperty("done") boolean done,
			@JsonProperty("nodes") int nodes,
			@JsonProperty("totalSplits") int totalSplits,
			@JsonProperty("queuedSplits") int queuedSplits,
			@JsonProperty("runningSplits") int runningSplits,
			@JsonProperty("completedSplits") int completedSplits,
			@JsonProperty("cpuTimeMillis") long cpuTimeMillis,
			@JsonProperty("wallTimeMillis") long wallTimeMillis,
			@JsonProperty("processedRows") long processedRows,
			@JsonProperty("processedBytes") long processedBytes,
			@JsonProperty("physicalInputBytes") long physicalInputBytes,
			@JsonProperty("failedTasks") int failedTasks,
			@JsonProperty("coordinatorOnly") boolean coordinatorOnly,
			@JsonProperty("subStages") List<StageStats> subStages) {
		this.stageId = stageId;
		this.state = requireNonNull(state, "state is null");
		this.done = done;
		this.nodes = nodes;
		this.totalSplits = totalSplits;
		this.queuedSplits = queuedSplits;
		this.runningSplits = runningSplits;
		this.completedSplits = completedSplits;
		this.cpuTimeMillis = cpuTimeMillis;
		this.wallTimeMillis = wallTimeMillis;
		this.processedRows = processedRows;
		this.processedBytes = processedBytes;
		this.physicalInputBytes = physicalInputBytes;
		this.failedTasks = failedTasks;
		this.coordinatorOnly = coordinatorOnly;
		this.subStages = Collections.unmodifiableList(requireNonNull(subStages, "subStages is null"));
	}
}
