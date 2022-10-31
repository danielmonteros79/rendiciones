package com.sa.util;

import java.text.SimpleDateFormat;
import javax.sql.DataSource;

public final class GlobalConfig {

    private static GlobalConfig instance;

    private DataSource dataSource;
    private DataSource vtosDataSource;
    private DataSource notifDataSource;
    private DataSource fdosDataSource;

    private int hitsPerPage;

    private String dateFormatPattern;
    private SimpleDateFormat dateFormatter;

    private int logMailErorPageSize;

    private GlobalConfig() {
        //set here all default values
        hitsPerPage = 20;
        logMailErorPageSize = 10;
    }

    public static GlobalConfig getInstance() {
        if (instance == null) {
            instance = new GlobalConfig();
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getVencimientosDataSource() {
        return vtosDataSource;
    }

    public void setVencimientosDataSource(DataSource vtosDataSource) {
        this.vtosDataSource = vtosDataSource;
    }

    public DataSource getNotificacionesDataSource() {
        return notifDataSource;
    }

    public void setNotificacionesDataSource(DataSource notifDataSource) {
        this.notifDataSource = notifDataSource;
    }

    public DataSource getFeriadosDataSource() {
        return fdosDataSource;
    }

    public void setFeriadosDataSource(DataSource fdosDataSource) {
        this.fdosDataSource = fdosDataSource;
    }

    public int getHitsPerPage() {
        return getHitsPerPage(false);
    }

    public int getHitsPerPage(boolean isRia) {
        if (isRia) {
            return 50;
        } else {
            return hitsPerPage;
        }
    }

    public void setHitsPerPage(int hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public String getDateFormatPattern() {
        return dateFormatPattern;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public SimpleDateFormat getDateFormatter() {
        if (dateFormatter == null) {
            dateFormatter = new SimpleDateFormat(dateFormatPattern);
        }
        return dateFormatter;
    }

    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public int getLogMailErorPageSize() {
        return logMailErorPageSize;
    }

    public void setLogMailErorPageSize(int logMailErorPageSize) {
        this.logMailErorPageSize = logMailErorPageSize;
    }

}
