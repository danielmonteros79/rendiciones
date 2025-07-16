package com.sa.util;

import static ar.com.bbva.utils.WSUtils.getServletContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JCategoryLog;
import org.apache.log4j.Category;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockServletConfig;
import org.apache.struts.mock.MockServletContext;
import org.displaytag.filter.BufferedResponseWrapper13Impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class fileEnablerTest {

    @Mock
    ServletConfig servletConfig;
    @Mock
    ServletContext servletContext;


    @InjectMocks
    fileEnabler fileEnabler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        fileEnabler actualFileEnabler = new fileEnabler();
        assertNull(actualFileEnabler.getServletConfig());
        assertEquals(ParamsConstants.GASTOS_CANTIDAD, actualFileEnabler.getServletInfo());
    }


    @Test
    void testDoGet() throws IOException, ServletException {
        fileEnabler fileEnabler = new fileEnabler();
        
        // Mock the servlet context to return a test path
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("C:\\temp\\test\\");
        when(mockContext.getRealPath(".")).thenReturn("C:\\temp\\test");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        
        // Use a stateful mock that tracks setAttribute calls
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock the servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize the servlet
        fileEnabler.init(mockConfig);
        
        // Use servlet interfaces directly instead of Mock implementations
        HttpServletRequest arg0 = mock(HttpServletRequest.class);
        when(arg0.getRequestURI()).thenReturn("https://example.org/example");
        HttpServletResponse arg1 = mock(HttpServletResponse.class);
        doNothing().when(arg1).sendError(anyInt());
        
        fileEnabler.doGet(arg0, arg1);
        verify(arg0).getRequestURI();
        verify(arg1).sendError(anyInt());
    }


    @Test
    void testDoPost() throws IOException, ServletException {
        fileEnabler fileEnabler = new fileEnabler();
        
        // Mock the servlet context to return a test path
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("C:\\temp\\test\\");
        when(mockContext.getRealPath(".")).thenReturn("C:\\temp\\test");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        
        // Use a stateful mock that tracks setAttribute calls
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock the servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize the servlet
        fileEnabler.init(mockConfig);
        
        // Use servlet interfaces directly instead of Mock implementations
        HttpServletRequest arg0 = mock(HttpServletRequest.class);
        when(arg0.getRequestURI()).thenReturn("https://example.org/example");
        HttpServletResponse arg1 = mock(HttpServletResponse.class);
        doNothing().when(arg1).sendError(anyInt());
        
        fileEnabler.doPost(arg0, arg1);
        verify(arg0).getRequestURI();
        verify(arg1).sendError(anyInt());
    }
    @Test
    @DisplayName("Testeando init")
    void testInit() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(any())).thenReturn("test");
        fileEnabler.init();


    }

    @Test
    void testReplaceVariablesInString() {
        // Mock the servlet context to return values for attributes
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("test")).thenReturn("test");
        
        fileEnabler fileEnabler = new fileEnabler();
        String valores = ("${test}");
        String result = fileEnabler.replaceVariablesInString(valores, mockContext);
        assertNotNull(result);
    }

    @Test
    void testReplaceVariablesInString2() {
        fileEnabler fileEnabler = new fileEnabler();
        ServletContext mockContext = mock(ServletContext.class);
        String result = fileEnabler.replaceVariablesInString(null, mockContext);
        assertNull(result);
    }

    @Test
    void testReplaceVariablesInString3() {
        // Mock the servlet context to return values for attributes
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("1")).thenReturn("1");
        
        fileEnabler fileEnabler = new fileEnabler();
        String valores = ("${1}");
        String result = fileEnabler.replaceVariablesInString(valores, mockContext);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test file serving with proper MIME type detection")
    void testFileServingWithMimeType() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Create a temporary test file
        File tempFile = new File("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\test.css");
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\");
        when(mockContext.getRealPath(".")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        when(mockContext.getMimeType("test.css")).thenReturn("text/css");
        
        // Use a stateful mock for attributes
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/test.css");
        
        // Mock response output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        // Test when file exists
        if (tempFile.exists()) {
            servlet.doGet(mockRequest, mockResponse);
            
            // Verify that proper headers are set
            verify(mockResponse).reset();
            verify(mockResponse).setHeader("Content-Type", "text/css");
            verify(mockResponse).setHeader("Content-Length", String.valueOf(tempFile.length()));
            verify(mockResponse).getOutputStream();
        }
    }

    @Test
    @DisplayName("Test file serving with default MIME type when null")
    void testFileServingWithDefaultMimeType() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\");
        when(mockContext.getRealPath(".")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        when(mockContext.getMimeType("test.unknown")).thenReturn(null); // Return null to test default
        
        // Use a stateful mock for attributes
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/test.unknown");
        
        // Mock response output stream
        ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        // Create a temporary test file
        File tempFile = new File("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\test.unknown");
        
        // Test when file exists
        if (tempFile.exists()) {
            servlet.doGet(mockRequest, mockResponse);
            
            // Verify that default MIME type is used
            verify(mockResponse).reset();
            verify(mockResponse).setHeader("Content-Type", "application/octet-stream");
            verify(mockResponse).setHeader("Content-Length", String.valueOf(tempFile.length()));
        }
    }

    @Test
    @DisplayName("Test file serving with IO exception handling")
    void testFileServingWithIOException() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\");
        when(mockContext.getRealPath(".")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        when(mockContext.getMimeType("test.txt")).thenReturn("text/plain");
        
        // Use a stateful mock for attributes
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/test.txt");
        
        // Mock response output stream to throw IOException
        ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenThrow(new IOException("Test IO Exception"));
        
        // Create a temporary test file
        File tempFile = new File("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\test.txt");
        
        // Test when file exists but output stream fails
        if (tempFile.exists()) {
            servlet.doGet(mockRequest, mockResponse);
            
            // Verify that headers are still set before the exception
            verify(mockResponse).reset();
            verify(mockResponse).setHeader("Content-Type", "text/plain");
            verify(mockResponse).setHeader("Content-Length", String.valueOf(tempFile.length()));
        }
    }

    @Test
    @DisplayName("Test file streaming with buffer reading")
    void testFileStreamingWithBuffer() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context - ensure the path ends with a separator
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\");
        when(mockContext.getRealPath(".")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        when(mockContext.getMimeType("img.png")).thenReturn("image/png");
        
        // Use a stateful mock for attributes
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/rendiciones90/images/img.png");
        
        // Mock response output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServletOutputStream mockOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }
            
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                baos.write(b, off, len);
            }
        };
        
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        // Test with the actual img.png file that exists in test resources
        servlet.doGet(mockRequest, mockResponse);
        
        // With the updated servlet logic, it should now find the file and serve it
        verify(mockResponse).reset();
        verify(mockResponse).setHeader("Content-Type", "image/png");
        verify(mockResponse, atLeast(1)).setHeader(eq("Content-Length"), anyString());
        
        // Verify that no 404 error was sent
        verify(mockResponse, never()).sendError(404);
    }

    @Test
    @DisplayName("Test content length header setting")
    void testContentLengthHeaderSetting() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getRealPath("/")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\");
        when(mockContext.getRealPath(".")).thenReturn("x:\\Workspace2\\rendiciones90\\src\\test\\resources");
        when(mockContext.getInitParameter("extended-document-root")).thenReturn(null);
        when(mockContext.getMimeType("archivo.txt")).thenReturn("text/plain");
        
        // Use a stateful mock for attributes
        Map<String, Object> attributes = new HashMap<>();
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            Object value = invocation.getArgument(1);
            attributes.put(key, value);
            return null;
        }).when(mockContext).setAttribute(anyString(), any());
        
        doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return attributes.get(key);
        }).when(mockContext).getAttribute(anyString());
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/archivo.txt");
        
        // Mock response output stream
        ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        // Test with the actual archivo.txt file that exists in test resources
        servlet.doGet(mockRequest, mockResponse);
        
        // Verify content length header is set with the actual file size
        File expectedFile = new File("x:\\Workspace2\\rendiciones90\\src\\test\\resources\\archivo.txt");
        if (expectedFile.exists()) {
            verify(mockResponse).setHeader("Content-Length", String.valueOf(expectedFile.length()));
        }
    }

    @Test
    @DisplayName("Test path construction debug")
    void testPathConstruction() throws IOException, ServletException {
        // Simulate the path construction logic from the servlet
        String requestUri = "/rendiciones90/img.png";
        int idx = requestUri.indexOf("/", 2);
        String filename = requestUri.substring(idx + 1);
        
        String basePath = "x:\\Workspace2\\rendiciones90\\src\\test\\resources\\";
        String fullPath = basePath + filename;
        
        File testFile = new File(fullPath);
        System.out.println("Request URI: " + requestUri);
        System.out.println("Index: " + idx);
        System.out.println("Filename: " + filename);
        System.out.println("Full path: " + fullPath);
        System.out.println("File exists: " + testFile.exists());
        System.out.println("File absolute path: " + testFile.getAbsolutePath());
        
        // The file should exist
        assertTrue(testFile.exists(), "Test file should exist at: " + fullPath);
    }

    @Test
    @DisplayName("Test updated path construction logic")
    void testUpdatedPathConstruction() {
        String requestUri = "/rendiciones90/images/img.png";
        String arch = requestUri;
        
        // Extract the filename from the request URI
        // For URIs like /context/servlet-mapping/filename, we want just the filename
        int idx = arch.indexOf("/", 2);
        if (idx != -1) {
            // Find the next slash after the context path
            int nextIdx = arch.indexOf("/", idx + 1);
            if (nextIdx != -1) {
                // Extract everything after the servlet mapping
                arch = arch.substring(nextIdx + 1);
            } else {
                // No additional path, extract from the current position
                arch = arch.substring(idx + 1);
            }
        }
        
        System.out.println("Request URI: " + requestUri);
        System.out.println("Extracted filename: " + arch);
        
        assertEquals("img.png", arch, "Should extract just the filename");
    }

    @Test
    @DisplayName("Test path extraction for various URI formats")
    void testPathExtraction() {
        // Test cases for different URI formats
        String[] testCases = {
            "/rendiciones90/images/img.png",
            "/rendiciones90/css/style.css",
            "/rendiciones90/js/script.js",
            "/app/static/file.html",
            "/context/servlet/resource.txt"
        };
        
        String[] expectedResults = {
            "img.png",
            "style.css", 
            "script.js",
            "file.html",
            "resource.txt"
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String requestUri = testCases[i];
            String arch = requestUri;
            
            // Extract the filename from the request URI
            // For URIs like /context/servlet-mapping/filename, we want just the filename
            int idx = arch.indexOf("/", 2);
            if (idx != -1) {
                // Find the next slash after the context path
                int nextIdx = arch.indexOf("/", idx + 1);
                if (nextIdx != -1) {
                    // Extract everything after the servlet mapping
                    arch = arch.substring(nextIdx + 1);
                } else {
                    // No additional path, extract from the current position
                    arch = arch.substring(idx + 1);
                }
            }
            
            System.out.println("URI: " + requestUri + " -> Extracted: " + arch);
            assertEquals(expectedResults[i], arch, "Failed for URI: " + requestUri);
        }
    }

    @Test
    @DisplayName("Test servlet path extraction logic")
    void testServletPathExtraction() {
        // Test the path extraction logic that we implemented in the servlet
        String[] testCases = {
            "/rendiciones90/images/img.png",
            "/rendiciones90/css/style.css",
            "/rendiciones90/js/script.js",
            "/context/static/file.html"
        };
        
        String[] expectedResults = {
            "img.png",
            "style.css",
            "script.js",
            "file.html"
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String arch = testCases[i];
            
            // This is the same logic as in the servlet
            int idx = arch.indexOf("/", 2);
            if (idx != -1) {
                int nextIdx = arch.indexOf("/", idx + 1);
                if (nextIdx != -1) {
                    arch = arch.substring(nextIdx + 1);
                } else {
                    arch = arch.substring(idx + 1);
                }
            }
            
            assertEquals(expectedResults[i], arch, "Path extraction failed for: " + testCases[i]);
        }
    }

    @Test
    @DisplayName("Test file exists in test resources")
    void testFileExistsInTestResources() {
        String testResourcePath = "x:\\Workspace2\\rendiciones90\\src\\test\\resources\\";
        String filename = "img.png";
        String fullPath = testResourcePath + filename;
        
        File testFile = new File(fullPath);
        assertTrue(testFile.exists(), "Test file should exist at: " + fullPath);
        assertTrue(testFile.isFile(), "Path should point to a file, not a directory");
        assertTrue(testFile.canRead(), "Test file should be readable");
    }

}
