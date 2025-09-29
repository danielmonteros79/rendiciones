package com.sa.util;

import com.sa.util.GlobalConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class GlobalConfigTest {

    @Spy
    @InjectMocks
    private GlobalConfig globalConfig;

    private DataSource dataSource = Mockito.mock(DataSource.class);

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        globalConfig = GlobalConfig.getInstance();
    }

    @Test
    @DisplayName("Testeando set y get DataSource")
    void getDataSource() throws SQLException {
        globalConfig.setDataSource(dataSource);
        DataSource resultTest = globalConfig.getDataSource();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,dataSource)
        );
    }

    @Test
    @DisplayName("Testeando set y get VencimientosDataSource")
    void getVencimientosDataSource() {
        globalConfig.setVencimientosDataSource(dataSource);
        DataSource resultTest = globalConfig.getVencimientosDataSource();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,dataSource)
        );
    }

    @Test
    @DisplayName("Testeando set y get NotificacionesDataSource")
    void getNotificacionesDataSource() {
        globalConfig.setNotificacionesDataSource(dataSource);
        DataSource resultTest = globalConfig.getNotificacionesDataSource();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,dataSource)
        );
    }

    @Test
    @DisplayName("Testeando set y get FeriadosDataSource")
    void getFeriadosDataSource() {
        globalConfig.setFeriadosDataSource(dataSource);
        DataSource resultTest = globalConfig.getFeriadosDataSource();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,dataSource)
        );
    }

    @Test
    @DisplayName("Testeando set y getHitsPerPage")
    void getHitsPerPage() {
        globalConfig.setHitsPerPage(50);
        int getHits = globalConfig.getHitsPerPage();
        int getTrueHits = globalConfig.getHitsPerPage(true);
        int getFalseHits = globalConfig.getHitsPerPage(false);
        int resultTest = globalConfig.getHitsPerPage(false);
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertNotNull(getHits),
                ()->assertNotNull(getFalseHits),
                ()->assertEquals(getFalseHits, resultTest, getTrueHits)
        );
    }

    @Test
    @DisplayName("Testeando set y get DateFormatPattern")
    void getDateFormatPattern() {
        globalConfig.setDateFormatPattern("");
        String resultTest = globalConfig.getDateFormatPattern();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest, "")
        );
    }

    @Test
    @DisplayName("Testeando set y get DateFormatter")
    void getDateFormatter() {
        SimpleDateFormat newDate = new SimpleDateFormat();
        globalConfig.setDateFormatter(newDate);
        SimpleDateFormat resultTest = globalConfig.getDateFormatter();
        assertAll(
                ()->assertNotNull(resultTest)
        );

        SimpleDateFormat resultTestNull = globalConfig.getDateFormatter();
        assertAll(
                ()->assertNotNull(resultTestNull)
        );
    }

    @Test
    @DisplayName("Testeando set y get LogMailErorPageSize")
    void getLogMailErorPageSize() {
        globalConfig.setLogMailErorPageSize(1);
        int resultTest = globalConfig.getLogMailErorPageSize();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest, 1)
        );
    }

}