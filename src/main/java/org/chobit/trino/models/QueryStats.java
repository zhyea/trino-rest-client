/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;

import static java.lang.Math.min;


public record QueryStats(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonProperty("createTime")
        LocalDateTime createTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonProperty("executionStartTime")
        LocalDateTime executionStartTime,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonProperty("lastHeartbeat")
        LocalDateTime lastHeartbeat,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonProperty("endTime")
        LocalDateTime endTime,

        @JsonProperty("elapsedTime") String elapsedTime,
        @JsonProperty("queuedTime") String queuedTime,
        @JsonProperty("resourceWaitingTime") String resourceWaitingTime,
        @JsonProperty("dispatchingTime") String dispatchingTime,
        @JsonProperty("executionTime") String executionTime,
        @JsonProperty("analysisTime") String analysisTime,
        @JsonProperty("planningTime") String planningTime,
        @JsonProperty("finishingTime") String finishingTime,

        @JsonProperty("totalTasks") int totalTasks,
        @JsonProperty("runningTasks") int runningTasks,
        @JsonProperty("completedTasks") int completedTasks,
        @JsonProperty("failedTasks") int failedTasks,

        @JsonProperty("totalDrivers") int totalDrivers,
        @JsonProperty("queuedDrivers") int queuedDrivers,
        @JsonProperty("runningDrivers") int runningDrivers,
        @JsonProperty("blockedDrivers") int blockedDrivers,
        @JsonProperty("completedDrivers") int completedDrivers,

        @JsonProperty("cumulativeUserMemory") double cumulativeUserMemory,
        @JsonProperty("failedCumulativeUserMemory") double failedCumulativeUserMemory,
        @JsonProperty("userMemoryReservation") String userMemoryReservation,
        @JsonProperty("revocableMemoryReservation") String revocableMemoryReservation,
        @JsonProperty("totalMemoryReservation") String totalMemoryReservation,
        @JsonProperty("peakUserMemoryReservation") String peakUserMemoryReservation,
        @JsonProperty("peakRevocableMemoryReservation") String peakRevocableMemoryReservation,
        @JsonProperty("peakTotalMemoryReservation") String peakTotalMemoryReservation,
        @JsonProperty("peakTaskUserMemory") String peakTaskUserMemory,
        @JsonProperty("peakTaskRevocableMemory") String peakTaskRevocableMemory,
        @JsonProperty("peakTaskTotalMemory") String peakTaskTotalMemory,

        @JsonProperty("scheduled") boolean scheduled,
        @JsonProperty("totalScheduledTime") String totalScheduledTime,
        @JsonProperty("failedScheduledTime") String failedScheduledTime,
        @JsonProperty("totalCpuTime") String totalCpuTime,
        @JsonProperty("failedCpuTime") String failedCpuTime,
        @JsonProperty("totalBlockedTime") String totalBlockedTime,
        @JsonProperty("fullyBlocked") boolean fullyBlocked,
        @JsonProperty("blockedReasons") Set<String> blockedReasons,

        @JsonProperty("physicalInputDataSize") String physicalInputDataSize,
        @JsonProperty("failedPhysicalInputDataSize") String failedPhysicalInputDataSize,
        @JsonProperty("physicalInputPositions") long physicalInputPositions,
        @JsonProperty("failedPhysicalInputPositions") long failedPhysicalInputPositions,
        @JsonProperty("physicalInputReadTime") String physicalInputReadTime,
        @JsonProperty("failedPhysicalInputReadTime") String failedPhysicalInputReadTime,

        @JsonProperty("internalNetworkInputDataSize") String internalNetworkInputDataSize,
        @JsonProperty("failedInternalNetworkInputDataSize") String failedInternalNetworkInputDataSize,
        @JsonProperty("internalNetworkInputPositions") long internalNetworkInputPositions,
        @JsonProperty("failedInternalNetworkInputPositions") long failedInternalNetworkInputPositions,

        @JsonProperty("rawInputDataSize") String rawInputDataSize,
        @JsonProperty("failedRawInputDataSize") String failedRawInputDataSize,
        @JsonProperty("rawInputPositions") long rawInputPositions,
        @JsonProperty("failedRawInputPositions") long failedRawInputPositions,

        @JsonProperty("processedInputDataSize") String processedInputDataSize,
        @JsonProperty("failedProcessedInputDataSize") String failedProcessedInputDataSize,
        @JsonProperty("processedInputPositions") long processedInputPositions,
        @JsonProperty("failedProcessedInputPositions") long failedProcessedInputPositions,

        @JsonProperty("inputBlockedTime") String inputBlockedTime,
        @JsonProperty("failedInputBlockedTime") String failedInputBlockedTime,

        @JsonProperty("outputDataSize") String outputDataSize,
        @JsonProperty("failedOutputDataSize") String failedOutputDataSize,
        @JsonProperty("outputPositions") long outputPositions,
        @JsonProperty("failedOutputPositions") long failedOutputPositions,

        @JsonProperty("outputBlockedTime") String outputBlockedTime,
        @JsonProperty("failedOutputBlockedTime") String failedOutputBlockedTime,

        @JsonProperty("physicalWrittenDataSize") String physicalWrittenDataSize,
        @JsonProperty("failedPhysicalWrittenDataSize") String failedPhysicalWrittenDataSize) {


    @JsonProperty
    public Double getProgressPercentage() {
        if (!scheduled || totalDrivers == 0) {
            return Double.NaN;
        }
        return min(100, (completedDrivers * 100.0) / totalDrivers);
    }

}
