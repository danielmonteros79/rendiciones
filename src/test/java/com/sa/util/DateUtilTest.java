package com.sa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DateUtilTest {

    @InjectMocks
    DateUtil dateUtil;


    @Test
    void testDaysBetween() {
        Date d1 = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        assertEquals(0, DateUtil.daysBetween(d1,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /*@Test
    void testConstructor() {
        DateUtil actualDateUtil = new DateUtil();
        SimpleDateFormat dfDDMMYYYY = new SimpleDateFormat();
        actualDateUtil.setDfDDMMYYYY(dfDDMMYYYY);
        SimpleDateFormat dfDDMMYYYYGuion = new SimpleDateFormat();
        actualDateUtil.setDfDDMMYYYYGuion(dfDDMMYYYYGuion);
        SimpleDateFormat dfYYYYMMDD = new SimpleDateFormat();
        actualDateUtil.setDfYYYYMMDD(dfYYYYMMDD);
        DateFormat dfDDMMYYYY2 = actualDateUtil.getDfDDMMYYYY();
        assertSame(dfDDMMYYYY, dfDDMMYYYY2);
        assertEquals(dfDDMMYYYYGuion, dfDDMMYYYY2);
        assertEquals(dfYYYYMMDD, dfDDMMYYYY2);
        DateFormat dfDDMMYYYYGuion2 = actualDateUtil.getDfDDMMYYYYGuion();
        assertSame(dfDDMMYYYYGuion, dfDDMMYYYYGuion2);
        assertEquals(dfDDMMYYYY2, dfDDMMYYYYGuion2);
        DateFormat dfYYYYMMDD2 = actualDateUtil.getDfYYYYMMDD();
        assertEquals(dfYYYYMMDD2, dfDDMMYYYYGuion2);
        assertSame(dfYYYYMMDD, dfYYYYMMDD2);
        assertEquals(dfDDMMYYYYGuion, dfYYYYMMDD2);
        assertEquals(dfDDMMYYYY2, dfYYYYMMDD2);
    }*/
}