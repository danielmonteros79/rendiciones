package com.sa.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.SQLNestedException;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

class DBConnectionTest {

    @InjectMocks
    DBConnection dBConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled("Terminar de implementar")
    @DisplayName("Testeando getConnection")
    void testGetConnection() throws Exception {

    }


    @Test
    @DisplayName("Testeando printSQLException")
    void testPrintSQLException() {
        SQLException ex = new SQLException(new Throwable("Error"));
        DBConnection.printSQLException(ex);

        assertNotNull(ex);
    }
}

