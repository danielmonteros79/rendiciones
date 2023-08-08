package com.sa.services;

import com.sa.core.XMLConfigReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LoggerSUMTest {
    /**
     * Method under test: {@link LoggerSUM#LoggerSUM(XMLConfigReader)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConstructor() {
        // TODO: Complete this test.
        //   Reason: R081 Exception in arrange section.
        //   Diffblue Cover was unable to construct an instance of the class under test using
        //   com.sa.services.LoggerSUM.<init>(XMLConfigReader).
        //   The arrange section threw
        //   java.lang.IllegalArgumentException: InputStream cannot be null
        //       at javax.xml.parsers.DocumentBuilder.parse(DocumentBuilder.java:117)
        //       at com.sa.core.XMLConfigReader.inicilizarXMLDesarrollo(XMLConfigReader.java:192)
        //       at com.sa.core.XMLConfigReader.<init>(XMLConfigReader.java:67)
        //       at com.sa.core.XMLConfigReader.getXml(XMLConfigReader.java:98)
        //   See https://diff.blue/R081 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        XMLConfigReader xml = null;

        // Act
        LoggerSUM actualLoggerSUM = new LoggerSUM(xml);

        // Assert
        // TODO: Add assertions on result
    }
}

