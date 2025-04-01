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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public record SessionRepresentation(
        @JsonProperty("queryId") String queryId,
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("clientTransactionSupport") boolean clientTransactionSupport,
        @JsonProperty("user") String user,
        @JsonProperty("groups") Set<String> groups,
        @JsonProperty("principal") String principal,
        @JsonProperty("enabledRoles") Set<String> enabledRoles,
        @JsonProperty("source") String source,
        @JsonProperty("catalog") String catalog,
        @JsonProperty("schema") String schema,
        @JsonProperty("path") Object path,
        @JsonProperty("traceToken") String traceToken,
        @JsonProperty("timeZoneKey") Integer timeZoneKey,
        @JsonProperty("locale") Locale locale,
        @JsonProperty("remoteUserAddress") String remoteUserAddress,
        @JsonProperty("userAgent") String userAgent,
        @JsonProperty("clientInfo") String clientInfo,
        @JsonProperty("clientTags") Set<String> clientTags,
        @JsonProperty("clientCapabilities") Set<String> clientCapabilities,
        @JsonProperty("resourceEstimates") Object resourceEstimates,
        @JsonProperty("start") Instant start,
        @JsonProperty("systemProperties") Map<String, String> systemProperties,
        @JsonProperty("catalogProperties") Map<String, Map<String, String>> catalogProperties,
        @JsonProperty("catalogRoles") Map<String, SelectedRole> catalogRoles,
        @JsonProperty("preparedStatements") Map<String, String> preparedStatements,
        @JsonProperty("protocolName") String protocolName) {
}
