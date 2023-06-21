package com.sa.dao;

import com.sa.core.XMLConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DaoTest {

    @Mock
    Statement stmt;
    @Mock
    ResultSet rs;
    @Mock
    XMLConfigReader xml;
    @Mock
    Logger log;
    @InjectMocks
    Dao dao;

    @BeforeEach
    void setUp() {
        dao = mock(Dao.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void close() {
        doNothing().when(dao).close(stmt,rs);
        dao.close(stmt,rs);
        verify(dao,times(1)).close(stmt,rs);
    }

    @Test
    void doException() throws DAOException {
        DAOException e = new DAOException("");
        dao.doException(e);
        doNothing().when(dao).doException(e);
        verify(dao,times(1)).doException(e);
    }

    @Test
    void getXml() {
    }
}