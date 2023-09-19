package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mchange.v2.c3p0.impl.NewProxyCallableStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.pool.impl.GenericObjectPool;

import org.junit.jupiter.api.Test;

class DBUtilTest {

    @Test
    void testCloseResultSet() {
        assertTrue(DBUtil.closeResultSet(null));
    }

    @Test
    void testCloseResultSet2() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        doNothing().when(rs).close();
        assertTrue(DBUtil.closeResultSet(rs));
        verify(rs).close();
    }


    @Test
    void testClosePreparedStatement() {
        assertTrue(DBUtil.closePreparedStatement(null));
        assertTrue(DBUtil.closePreparedStatement(new NewProxyCallableStatement(mock(CallableStatement.class))));
    }


    @Test
    void testClosePreparedStatement2() throws SQLException {
        PreparedStatement stmt = mock(PreparedStatement.class);
        doNothing().when(stmt).close();
        assertTrue(DBUtil.closePreparedStatement(stmt));
        verify(stmt).close();
    }


    @Test
    void testCloseConnection() {
        assertTrue(DBUtil.closeConnection(null));
    }


    @Test
    void testCloseConnection2() throws SQLException {
        Connection conn = mock(Connection.class);
        doNothing().when(conn).close();
        assertTrue(DBUtil.closeConnection(conn));
        verify(conn).close();
    }


    @Test
    void testCloseConnection3() throws SQLException {
        Connection conn = mock(Connection.class);
        when(conn.isClosed()).thenReturn(true);
        assertFalse(DBUtil.closeConnection(new PoolableConnection(conn, new GenericObjectPool())));
        verify(conn).isClosed();
    }


    @Test
    void testCloseConnection4() throws SQLException {
        Connection conn = mock(Connection.class);
        when(conn.isClosed()).thenReturn(false);
        assertTrue(DBUtil.closeConnection(new PoolableConnection(conn, new GenericObjectPool())));
        verify(conn).isClosed();
    }
}

