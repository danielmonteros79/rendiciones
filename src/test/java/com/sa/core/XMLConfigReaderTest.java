package com.sa.core;

import com.sa.exceptions.ImposibleLeerXMLException;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.w3c.dom.Document;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class XMLConfigReaderTest {
    @Mock
    DocumentBuilderFactory documentBuilderFactory;
    @Mock
    DocumentBuilder documentBuilder;
    @Mock
    Document document;
    @Mock
    XPathFactory xPathFactory;
    @Mock
    XPath xPath;
    @Mock
    ServletContext servletContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando constructor con request")
    void testConstructorRequest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        session.setServletContext(servletContext);
        request.setHttpSession(session);

        when(servletContext.getRealPath(anyString())).thenReturn("realPath");


        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                try (MockedConstruction<File> fileMC = Mockito.mockConstruction(File.class, (mockFile, context) -> {
                    when(mockFile.getParentFile()).thenReturn(mockFile);
                })) {


                    XMLConfigReader result = new XMLConfigReader(request);
                    assertNotNull(result);
                }
            }
        }
    }

    @Test
    @DisplayName("Testeando constructor con request exception")
    void testConstructorRequestException() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenThrow(new ParserConfigurationException());

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenThrow(new XPathExpressionException("Exception"));


                    assertThrows(ImposibleLeerXMLException.class, () -> {
                        XMLConfigReader result = new XMLConfigReader(request);
                    });
            }
        }
    }

    @Test
    @DisplayName("Testeando constructor exception")
    void testConstructorException() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenThrow(new ParserConfigurationException());

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenThrow(new XPathExpressionException("Exception"));


                assertThrows(ImposibleLeerXMLException.class, () -> {
                    XMLConfigReader result = new XMLConfigReader();
                });
            }
        }
    }

    @Test
    @DisplayName("Testeando getArchivoConfNombre")
    void getArchivoConfNombre() throws Exception {
        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                String result = xmlConfigReader.getArchivoConfNombre();
                assertEquals("applicationConfig.xml", result);
            }
        }
    }

    @Test
    @DisplayName("Testeando getters")
    void getters() throws Exception {
        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(DocumentBuilderFactory::newInstance).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse((InputStream) any())).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(XPathFactory::newInstance).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any(Object.class))).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                assertAll(
                        () -> assertEquals("evaluate", xmlConfigReader.getApplicationLogFileName()),
                        () -> assertEquals("evaluate", xmlConfigReader.getCantidadLineas()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbDriver()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbPassword()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbURL()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbUser()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbDriverOracle()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbPasswordOracle()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbURLOracle()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDbUserOracle()),
                        () -> assertEquals("evaluate", xmlConfigReader.getDnsIp()),
                        () -> assertEquals("evaluate", xmlConfigReader.getExceptionsLogFileName()),
                        () -> assertEquals("evaluate", xmlConfigReader.getLogsPath()),
                        () -> assertEquals("evaluate", xmlConfigReader.getPool()),
                        () -> assertEquals("evaluate", xmlConfigReader.getPuertodns())
                );
            }
        }
    }

    @Test
    @DisplayName("Testeando getXml")
    void getXml() throws Exception {
        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                XMLConfigReader result = xmlConfigReader.getXml();
                assertNotNull(result);
            }
        }
    }

    @Test
    @DisplayName("Testeando getArchivoConfigWAS")
    void getArchivoConfigWAS() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();

        session.setServletContext(servletContext);
        request.setHttpSession(session);

        when(servletContext.getRealPath(anyString())).thenReturn("realPath");


        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                try (MockedConstruction<File> fileMC = Mockito.mockConstruction(File.class, (mockFile, context) -> {
                    when(mockFile.getParentFile()).thenReturn(mockFile);
                })) {


                    File result = xmlConfigReader.getArchivoConfigWAS(request);
                    assertNotNull(result);
                }
            }
        }
    }

    @Test
    @DisplayName("Testeando getDoc")
    void getDoc() throws Exception {
        try (MockedStatic<DocumentBuilderFactory> documentBuilderFactoryMockedStatic = mockStatic(DocumentBuilderFactory.class)) {
            documentBuilderFactoryMockedStatic.when(() -> DocumentBuilderFactory.newInstance()).thenReturn(documentBuilderFactory);

            when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);

            when(documentBuilder.parse(any(InputStream.class))).thenReturn(document);

            try (MockedStatic<XPathFactory> xPathFactoryMockedStatic = mockStatic(XPathFactory.class)) {
                xPathFactoryMockedStatic.when(() -> XPathFactory.newInstance()).thenReturn(xPathFactory);

                when(xPathFactory.newXPath()).thenReturn(xPath);

                when(xPath.evaluate(anyString(),any())).thenReturn("evaluate");

                XMLConfigReader xmlConfigReader = new XMLConfigReader();

                Document result = xmlConfigReader.getDoc();
                assertNull(result);
            }
        }
    }
}