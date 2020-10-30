package com.multi.datasource.proxy;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 代理datasource，做事务的统一提交或回滚
 */
public class DatasourceProxy implements DataSource {

    private DataSource dataSource;

    public DatasourceProxy(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        // 必须设置事务不自动提交
        connection.setAutoCommit(false);
        ConnectionProxy connectionProxy = new ConnectionProxy();
        connectionProxy.setConnection(connection);
        return connectionProxy;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSource.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface == null) {
            return null;
        }

        if (iface.isInstance(this)) {
            return (T) this;
        }

        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface != null && iface.isInstance(this);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        dataSourceCheck();
        return dataSource.getLogWriter();
    }

    protected void dataSourceCheck() {
        if (dataSource == null) {
            throw new UnsupportedOperationException("dataSource CAN NOT be null");
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSourceCheck();
        dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSourceCheck();
        dataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        dataSourceCheck();
        return dataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        dataSourceCheck();
        return dataSource.getParentLogger();
    }
}
