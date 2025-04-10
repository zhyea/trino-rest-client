package org.chobit.trino;

import org.chobit.commons.utils.JsonKit;
import org.chobit.trino.models.QueryStatusInfo;
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

    private static final String sql = """
            select * from app1196.track_log_view as log
                order by log."$time" desc  limit 1000
            """;

    @Test
    public void execute() {
        ExecuteStatusInfo executeStatusInfo = new TrinoRestClient().execute(sql, createSession(), info -> {
            if (null != info.getValue() && null != info.getValue().getData()) {
                int size = info.getValue().getData().size();
                System.out.println(size);
            }
            System.out.println(JsonKit.toJson(info.getValue()));
        });
        System.out.println("------------------------------------------");
        System.out.println(JsonKit.toJson(executeStatusInfo));
    }


    @Test
    public void kill() {
        TrinoClient client = new TrinoRestClient();
        ClientSession session = createSession();
        String queryId = "20250409_062952_00163_dbwmj";
        boolean success = client.kill(queryId, session);
        System.out.println(success);
    }


    @Test
    public void queryStatus() {
        TrinoClient client = new TrinoRestClient();
        ClientSession session = createSession();
        String queryId = "20250409_063109_00167_dbwmj";
        QueryStatusInfo result = client.queryStatus(queryId, session);
        System.out.println(JsonKit.toJson(result));
    }


    private ClientSession createSession() {
        return ClientSession.builder()
                .server(URI.create("http://127.0.0.1:8889"))
                .user("hadoop")
                .source("zhy")
                .catalog("hive")
                .schema("default")
                .principal("zhy")
                .timeZone(ZoneId.of("Asia/Shanghai"))
                .clientRequestTimeout(TimeUnit.MINUTES.toMillis(5L))
                .compressionDisabled(true)
                .build();
    }


}
