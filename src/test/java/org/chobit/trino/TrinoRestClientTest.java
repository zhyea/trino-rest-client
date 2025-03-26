package org.chobit.trino;

import org.chobit.commons.concurrent.Threads;
import org.chobit.commons.utils.JsonKit;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Trino Rest Client Test.
 *
 * @author zhangrui
 * @since 2025/3/25
 */
public class TrinoRestClientTest {

    private static final String sql = "/* Owner: zhangrui,Executor: zhangrui,Scheduled: False,Query Hash: 3729cd625d0272b8c078948334f75d28,Query ID: 435606,Query Source: oasis,Service: funnel-analytics,Related ID:  */ create table hive.test1019.oasis_funnel_1019_435606_20250326145458_8960 as with _inner as (\n select uid,\n MT_funnel(\n 2, 1742889599, 86400, -8*3600, 1,\n step, time, group_property, filter_conditions, true, associatedProperty\n ) as funnel\n from (\n select\n log.uid, 0 as step, log.time, null as group_property, null as filter_conditions, null as associatedProperty\nfrom hive.app1019.track_log_view log\n \nwhere action='install'\nand datesub >= '2025-03-22' and datesub <= '2025-03-25' \n union all\n select\n log.uid, 1 as step, log.time, null as group_property, null as filter_conditions, null as associatedProperty\nfrom hive.app1019.track_log_view log\n \nwhere action='login'\nand datesub >= '2025-03-22' and datesub <= '2025-03-25' \n )\n as track_log(uid, step, time, group_property, filter_conditions, associatedProperty)\n group by 1\n )\n select t.group_property, t.trend_interval, t.fulfilled_step, count(*) as elapsed_count, slice(array_agg(uid),1,1000000) as elapsed_uids\n from _inner, unnest(_inner.funnel)\n as t(trend_interval, fulfilled_step, group_property)\n group by 1, 2, 3\n order by 1, 2, 3";


    @Test
    public void execute() {
        JsonResponse<QueryResults> queryStatusInfo = new TrinoRestClient().execute(sql, createSession());
        System.out.println(queryStatusInfo);
    }


    @Test
    public void executeWithAdvance() {
        QueryStatusInfo queryStatusInfo = new TrinoRestClient().executeWithAdvance(sql, createSession());
        System.out.println(JsonKit.toJson(queryStatusInfo));
    }


    @Test
    public void kill() {
        TrinoClient client = new TrinoRestClient();
        ClientSession session = createSession();
        JsonResponse<QueryResults> queryStatusInfo = client.execute(sql, createSession());
        Threads.sleep(TimeUnit.MILLISECONDS, 30000);
        boolean success = client.kill(queryStatusInfo.getValue().getId(), session);
        System.out.println(success);
    }


    private ClientSession createSession() {
        return ClientSession.builder()
                .server(URI.create("http://trino-stg.mte.io"))
                .user("hadoop")
                .source("zhy")
                .catalog("hive")
                .schema("default")
                .principal("hadoop")
                .timeZone(ZoneId.of("Asia/Shanghai"))
                .clientRequestTimeout(0L)
                .compressionDisabled(true)
                .build();
    }


}
