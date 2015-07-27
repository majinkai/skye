package com.handu.skye.mysql;

import com.handu.skye.*;
import com.handu.skye.Tracer;
import com.mysql.jdbc.*;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Jinkai.Ma
 */
public class SkyeMySQLInterceptor implements StatementInterceptorV2 {

    public ResultSetInternalMethods preProcess(String sql, Statement interceptedStatement, Connection connection) throws SQLException {
        if (interceptedStatement instanceof PreparedStatement) {
            Span span = Tracer.begin("MySQL");
            span.addEvent(Event.CLIENT_SEND, this.getEndpoint(connection));
            span.addBinaryEvent("executed.query", ((PreparedStatement) interceptedStatement).getPreparedSql());
        }
        return null;
    }

    public ResultSetInternalMethods postProcess(String sql, Statement interceptedStatement, ResultSetInternalMethods originalResultSet, Connection connection, int warningCount, boolean noIndexUsed, boolean noGoodIndexUsed, SQLException statementException) throws SQLException {
        if (interceptedStatement instanceof PreparedStatement) {
            Span span = Tracer.lastSpan();
            try {
                if (span != null) {
                    span.addEvent(Event.CLIENT_RECV, this.getEndpoint(connection));
                    if (warningCount > 0) {
                        span.addBinaryEvent("warning.count", String.valueOf(warningCount));
                    }
                    if (statementException != null) {
                        span.addBinaryEvent("error.code", String.valueOf(statementException.getErrorCode()));
                    }
                }
            } finally {
                Tracer.commit(span);
            }
        }
        return null;
    }

    private Endpoint getEndpoint(Connection connection) {
        Endpoint endpoint = null;
        Properties connectionProperties = connection.getProperties();
        String host = connectionProperties.getProperty("HOST");
        String port = connectionProperties.getProperty("PORT");
        if (host != null && host.length() > 0 && port != null && port.length() > 0) {
            try {
                endpoint = new Endpoint(host, Integer.parseInt(port));
            } catch (NumberFormatException ignored) {

            }
        }
        return endpoint;
    }

    public void init(Connection conn, Properties props) throws SQLException {

    }

    public boolean executeTopLevelOnly() {
        return true;
    }

    public void destroy() {

    }
}
