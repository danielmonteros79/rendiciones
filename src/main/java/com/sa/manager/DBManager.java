package com.sa.manager;

import com.sa.util.GlobalConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    public static Connection getConnection() throws SQLException {

        Connection conn = GlobalConfig.getInstance().getDataSource().getConnection();

        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        return conn;
    }

}
