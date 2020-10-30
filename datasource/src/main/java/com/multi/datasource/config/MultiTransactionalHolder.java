package com.multi.datasource.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MultiTransactionalHolder {

    private static final ThreadLocal<List<Connection>> CONTEXT_CONNECTION = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new ArrayList<Connection>();
        }
    };

    public static void add(Connection connection) throws SQLException {
        CONTEXT_CONNECTION.get().add(connection);
    }

    public static List<Connection> get() {
        return CONTEXT_CONNECTION.get();
    }
}
