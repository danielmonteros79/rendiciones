package com.sa.manager;

import java.sql.Connection;
import java.sql.SQLException;

import com.sa.util.GlobalConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class DBManagerTest {

    @Mock
    private GlobalConfig globalConfig;

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getConnection")
    void testGetConnection() throws SQLException {
        try (MockedStatic<GlobalConfig> globalConfigMockedStatic = mockStatic(GlobalConfig.class)) {
            globalConfigMockedStatic.when(() -> GlobalConfig.getInstance()).thenReturn(globalConfig);

            when(globalConfig.getDataSource()).thenReturn(dataSource);
            when(dataSource.getConnection()).thenReturn(connection);

            Connection conn = DBManager.getConnection();

            assertEquals(connection, conn);
        }
    }
}

