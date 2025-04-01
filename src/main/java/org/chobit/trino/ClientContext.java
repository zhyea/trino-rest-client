package org.chobit.trino;

import java.net.URI;
import java.time.ZoneId;

import static java.util.Objects.requireNonNull;

/**
 * 会话信息
 *
 * @author robin
 * @since 2025/3/23
 */
public class ClientContext {

    private final URI server;
    private final String principal;
    private final String user;
    private final String source;
    private final String path;
    private final String catalog;
    private final String schema;
    private final ZoneId timeZone;
    private final String transactionId;
    private final String session;
    private final long clientRequestTimeout;
    private final boolean compressionDisabled;


    private ClientContext(URI server,
                          String principal,
                          String user,
                          String source,
                          String path,
                          String catalog,
                          String schema,
                          ZoneId timeZone,
                          String transactionId,
                          String session,
                          long clientRequestTimeout,
                          boolean compressionDisabled) {
        this.server = requireNonNull(server, "trino server is null");
        this.principal = principal;
        this.user = user;
        this.source = source;
        this.path = path;
        this.catalog = catalog;
        this.schema = schema;
        this.timeZone = requireNonNull(timeZone, "timeZone is null");
        this.transactionId = transactionId;
        this.session = session;

        this.clientRequestTimeout = clientRequestTimeout;
        this.compressionDisabled = compressionDisabled;
    }

    public URI getServer() {
        return server;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getUser() {
        return user;
    }

    public String getSource() {
        return source;
    }

    public String getPath() {
        return path;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getSession() {
        return session;
    }

    public long getClientRequestTimeout() {
        return clientRequestTimeout;
    }

    public boolean isCompressionDisabled() {
        return compressionDisabled;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private URI server;
        private String principal;
        private String user;
        private String source;
        private String path;
        private String catalog;
        private String schema;
        private ZoneId timeZone;
        private String transactionId;
        private String session;
        private long clientRequestTimeout;
        private boolean compressionDisabled;

        private Builder() {
        }

        private Builder(ClientContext clientContext) {
            server = clientContext.getServer();
            principal = clientContext.getPrincipal();
            user = clientContext.getUser();
            source = clientContext.getSource();
            path = clientContext.getPath();
            catalog = clientContext.getCatalog();
            schema = clientContext.getSchema();
            timeZone = clientContext.getTimeZone();
            transactionId = clientContext.getTransactionId();
            session = clientContext.getSession();
            clientRequestTimeout = clientContext.getClientRequestTimeout();
            compressionDisabled = clientContext.isCompressionDisabled();
        }


        public Builder server(URI server) {
            this.server = server;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder principal(String principal) {
            this.principal = principal;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder catalog(String catalog) {
            this.catalog = catalog;
            return this;
        }

        public Builder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public Builder timeZone(ZoneId timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        public Builder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder session(String session) {
            this.session = session;
            return this;
        }

        public Builder clientRequestTimeout(long clientRequestTimeout) {
            this.clientRequestTimeout = clientRequestTimeout;
            return this;
        }

        public Builder compressionDisabled(boolean compressionDisabled) {
            this.compressionDisabled = compressionDisabled;
            return this;
        }

        public ClientContext build() {
            return new ClientContext(
                    server,
                    principal,
                    user,
                    source,
                    path,
                    catalog,
                    schema,
                    timeZone,
                    transactionId,
                    session,
                    clientRequestTimeout,
                    compressionDisabled);
        }
    }
}
