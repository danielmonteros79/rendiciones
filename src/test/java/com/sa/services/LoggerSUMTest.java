package com.sa.services;

import com.sa.core.XMLConfigReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;

class LoggerSUMTest {

    @InjectMocks
    LoggerSUM loggerSUM;
    
    @Mock
    XMLConfigReader xml;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loggerSUM = new LoggerSUM(xml);
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
    void Exception() {
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
    void ExceptionStackTrace() {
        XMLConfigReader xml = mock(XMLConfigReader.class);
        when(xml.getExceptionsLogFileName()).thenReturn("logExceptionStackTrace.txt");
        when(xml.getLogsPath()).thenReturn("src/test/java/resources/");
        LoggerSUM loggerSUM = new LoggerSUM(xml);
        loggerSUM.logExceptionStackTrace(new Exception("exception"));
        verify(xml).getExceptionsLogFileName();
        verify(xml).getLogsPath();
    }
    
    @Test
    @DisplayName("Debe generar el nombre de archivo correctamente cuando xml NO es null")
    void createFileName_XmlNotNull() throws Exception {
        when(xml.getLogsPath()).thenReturn("src/test/resources/");
        String fileName = loggerSUM.getClass().getDeclaredMethod("createFileName", String.class)
                .invoke(loggerSUM, "testFile").toString();
        
        assertTrue(fileName.contains("testFile"));
        assertTrue(fileName.endsWith(".txt"));
    }

    @Test
    @DisplayName("Debe generar el nombre de archivo correctamente cuando xml ES null")
    void createFileName_XmlNull() throws Exception {
        LoggerSUM loggerSUMWithoutXml = new LoggerSUM(null);
        String fileName = loggerSUMWithoutXml.getClass().getDeclaredMethod("createFileName", String.class)
                .invoke(loggerSUMWithoutXml, "testFile").toString();
        
        assertTrue(fileName.contains("testFile"));
        assertTrue(fileName.endsWith(".txt"));
    }

    @Test
    @DisplayName("Debe crear el path correcto para APPLICATION_FILE")
    void createPath_ApplicationFile() throws Exception {
        when(xml.getApplicationLogFileName()).thenReturn("appLog");
        String path = loggerSUM.getClass().getDeclaredMethod("createPath", int.class)
                .invoke(loggerSUM, LoggerSUM.APPLICATION_FILE).toString();
        
        assertTrue(path.contains("appLog"));
    }

    @Test
    @DisplayName("Debe crear el path correcto para EXCEPTIONS_FILE")
    void createPath_ExceptionsFile() throws Exception {
        when(xml.getExceptionsLogFileName()).thenReturn("exceptionsLog");
        String path = loggerSUM.getClass().getDeclaredMethod("createPath", int.class)
                .invoke(loggerSUM, LoggerSUM.EXCEPTIONS_FILE).toString();
        
        assertTrue(path.contains("exceptionsLog"));
    }


    @Test
    @DisplayName("Debe escribir en el log sin XMLConfigReader")
    void log_WithoutXml() {
        LoggerSUM loggerWithoutXml = new LoggerSUM(null);
        assertDoesNotThrow(() -> loggerWithoutXml.log("Test message", LoggerSUM.EXCEPTIONS_FILE));
    }

    @Test
    @DisplayName("Debe escribir en el log cuando el XMLConfigReader está presente")
    void log_WithXml() {
        when(xml.getLogsPath()).thenReturn("src/test/resources/");
        when(xml.getExceptionsLogFileName()).thenReturn("testLog");

        assertDoesNotThrow(() -> loggerSUM.log("Test message", LoggerSUM.EXCEPTIONS_FILE));
    }

    @Test
    @DisplayName("Debe manejar error de IOException en log()")
    void log_IOExceptionHandling() throws Exception {
        XMLConfigReader xmlMock = mock(XMLConfigReader.class);
        LoggerSUM loggerSUM = new LoggerSUM(xmlMock);
        
        LoggerSUM loggerSpy = spy(loggerSUM);
        
        doThrow(new IOException("Error de escritura")).when(loggerSpy).createPath(anyInt());

        assertDoesNotThrow(() -> loggerSpy.log("Mensaje de prueba", LoggerSUM.EXCEPTIONS_FILE));
    }

    @Test
    @DisplayName("Debe loguear una excepción correctamente")
    void logException() {
        when(xml.getExceptionsLogFileName()).thenReturn("logException.txt");
        when(xml.getLogsPath()).thenReturn("src/test/resources/");

        assertDoesNotThrow(() -> loggerSUM.logException(new Exception("Error de prueba")));
    }

    @Test
    @DisplayName("Debe manejar IOException en logException()")
    void logException_Error() throws Exception {
        XMLConfigReader xmlMock = mock(XMLConfigReader.class);
        LoggerSUM loggerSUM = new LoggerSUM(xmlMock);
        
        LoggerSUM loggerSpy = spy(loggerSUM);
        
        doThrow(new IOException("Error")).when(loggerSpy).createPath(anyInt());

        assertDoesNotThrow(() -> loggerSpy.logException(new Exception("Prueba de error")));
    }


    @Test
    @DisplayName("Debe loguear una excepción con stack trace correctamente")
    void logExceptionStackTrace() {
        when(xml.getExceptionsLogFileName()).thenReturn("logExceptionStackTrace.txt");
        when(xml.getLogsPath()).thenReturn("src/test/resources/");

        assertDoesNotThrow(() -> loggerSUM.logExceptionStackTrace(new Exception("StackTrace Error")));
    }

    @Test
    @DisplayName("Debe manejar error en logExceptionStackTrace")
    void logExceptionStackTrace_Error() throws Exception {
        XMLConfigReader xmlMock = mock(XMLConfigReader.class);
        LoggerSUM loggerSUM = new LoggerSUM(xmlMock);

        LoggerSUM loggerSpy = spy(loggerSUM);
        doThrow(new IOException("Error en escritura")).when(loggerSpy).createPath(anyInt());

        assertDoesNotThrow(() -> loggerSpy.logExceptionStackTrace(new Exception("StackTrace Error")));
    }


}