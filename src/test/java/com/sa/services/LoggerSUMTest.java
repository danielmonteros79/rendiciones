package com.sa.services;

import com.sa.core.XMLConfigReader;
import com.sa.exceptions.ImposibleLeerXMLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoggerSUMTest {

    @InjectMocks
    LoggerSUM loggerSUM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Testeando log")
    void Log() {
        XMLConfigReader xml = mock(XMLConfigReader.class);
        when(xml.getExceptionsLogFileName()).thenReturn("test.txt");
        when(xml.getLogsPath()).thenReturn("src/test/java/resources/");
        (new LoggerSUM(xml)).log("Msg", 1);
        verify(xml).getExceptionsLogFileName();
        verify(xml).getLogsPath();
    }

    @Test
    @DisplayName("Testeando logException")
    void logException() {
        XMLConfigReader xml = mock(XMLConfigReader.class);
        when(xml.getExceptionsLogFileName()).thenReturn("logException.txt");
        when(xml.getLogsPath()).thenReturn("src/test/java/resources/");
        LoggerSUM loggerSUM = new LoggerSUM(xml);
        loggerSUM.logException(new Exception("foo"));
        verify(xml).getExceptionsLogFileName();
        verify(xml).getLogsPath();
    }


    @Test
    @DisplayName("Testeando logExceptionStackTrace")
    void logExceptionStackTrace() {
        XMLConfigReader xml = mock(XMLConfigReader.class);
        when(xml.getExceptionsLogFileName()).thenReturn("logExceptionStackTrace.txt");
        when(xml.getLogsPath()).thenReturn("src/test/java/resources/");
        LoggerSUM loggerSUM = new LoggerSUM(xml);
        loggerSUM.logExceptionStackTrace(new Exception("exception"));
        verify(xml).getExceptionsLogFileName();
        verify(xml).getLogsPath();
    }

}