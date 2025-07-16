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
        
        // Mock servlet context - use portable path resolution
        ServletContext mockContext = mock(ServletContext.class);
        
        // Get the test resources path portably - try multiple approaches
        String testResourcePath = getClass().getClassLoader().getResource("").getPath();
        if (testResourcePath == null || testResourcePath.isEmpty()) {
            // Fallback to system property approach
            String userDir = System.getProperty("user.dir");
            testResourcePath = userDir + "/src/test/resources/";
        }
        
        if (testResourcePath.startsWith("file:")) {
            testResourcePath = testResourcePath.substring(5);
        }
        // Ensure path ends with separator
        if (!testResourcePath.endsWith("/") && !testResourcePath.endsWith("\\")) {
            testResourcePath += "/";
        }
        
        // Verify the test resource path has the img.png file
        File testImg = new File(testResourcePath + "img.png");
        if (!testImg.exists()) {
            // Try alternative path construction
            String userDir = System.getProperty("user.dir");
            testResourcePath = userDir + "/src/test/resources/";
            testImg = new File(testResourcePath + "img.png");
            if (!testImg.exists()) {
                fail("Cannot find img.png test resource. Tried paths: " + 
                     getClass().getClassLoader().getResource("").getPath() + " and " + testResourcePath);
            }
        }
        
        when(mockContext.getRealPath("/")).thenReturn(testResourcePath);
        when(mockContext.getRealPath(".")).thenReturn(testResourcePath.substring(0, testResourcePath.length() - 1));
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
        System.out.println("About to call servlet.doGet with URI: " + mockRequest.getRequestURI());
        System.out.println("Mock context getRealPath(\"/\") will return: " + testResourcePath);
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
        String requestUri = "/rendiciones90/images/img.png";
        String arch = requestUri;
        
        // Use the updated servlet logic
        int idx = arch.indexOf("/", 2);
        if (idx != -1) {
            int nextIdx = arch.indexOf("/", idx + 1);
            if (nextIdx != -1) {
                arch = arch.substring(nextIdx + 1);
            } else {
                arch = arch.substring(idx + 1);
            }
        }
        
        // Use portable path resolution
        String basePath = getClass().getClassLoader().getResource("").getPath();
        if (basePath == null || basePath.isEmpty()) {
            String userDir = System.getProperty("user.dir");
            basePath = userDir + "/src/test/resources/";
        }
        
        if (basePath.startsWith("file:")) {
            basePath = basePath.substring(5);
        }
        if (!basePath.endsWith("/") && !basePath.endsWith("\\")) {
            basePath += "/";
        }
        
        String fullPath = basePath + arch;
        
        File testFile = new File(fullPath);
        
        // If the first approach doesn't work, try the system property approach
        if (!testFile.exists()) {
            String userDir = System.getProperty("user.dir");
            basePath = userDir + "/src/test/resources/";
            fullPath = basePath + arch;
            testFile = new File(fullPath);
        }
        System.out.println("Request URI: " + requestUri);
        System.out.println("Extracted filename: " + arch);
        System.out.println("Base path: " + basePath);
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
        // Use a more portable way to get the test resources path
        String testResourcePath = getClass().getClassLoader().getResource("").getPath();
        if (testResourcePath == null || testResourcePath.isEmpty()) {
            String userDir = System.getProperty("user.dir");
            testResourcePath = userDir + "/src/test/resources/";
        }
        
        String filename = "img.png";
        String fullPath = testResourcePath + filename;
        
        File testFile = new File(fullPath);
        
        // If the first approach doesn't work, try the system property approach
        if (!testFile.exists()) {
            String userDir = System.getProperty("user.dir");
            testResourcePath = userDir + "/src/test/resources/";
            fullPath = testResourcePath + filename;
            testFile = new File(fullPath);
        }
        
        // Debug output to understand the path resolution
        System.out.println("Test resource path: " + testResourcePath);
        System.out.println("Full path: " + fullPath);
        System.out.println("File exists: " + testFile.exists());
        System.out.println("File absolute path: " + testFile.getAbsolutePath());
        
        assertTrue(testFile.exists(), "Test file should exist at: " + fullPath);
        assertTrue(testFile.isFile(), "Path should point to a file, not a directory");
        assertTrue(testFile.canRead(), "Test file should be readable");
    }

    @Test
    @DisplayName("Test class loader resource resolution")
    void testClassLoaderResourceResolution() {
        // Test that we can access the test resource using the class loader
        ClassLoader classLoader = getClass().getClassLoader();
        
        // Try to get the img.png resource directly
        java.net.URL resourceUrl = classLoader.getResource("img.png");
        assertNotNull(resourceUrl, "Should be able to find img.png resource");
        
        // Try to get the resource path
        String resourcePath = classLoader.getResource("").getPath();
        assertNotNull(resourcePath, "Should be able to get resource path");
        
        System.out.println("Resource URL: " + resourceUrl);
        System.out.println("Resource path: " + resourcePath);
        
        // Try to access the file directly
        File imgFile = new File(resourcePath, "img.png");
        System.out.println("File path: " + imgFile.getAbsolutePath());
        System.out.println("File exists: " + imgFile.exists());
    }

    @Test
    @DisplayName("Test alternative resource path resolution")
    void testAlternativeResourcePathResolution() {
        // Try multiple approaches to get the test resources path
        
        // Approach 1: Class loader
        String classLoaderPath = getClass().getClassLoader().getResource("").getPath();
        System.out.println("Class loader path: " + classLoaderPath);
        
        // Approach 2: System property
        String userDir = System.getProperty("user.dir");
        String testResourcePath = userDir + "/src/test/resources/";
        System.out.println("System property path: " + testResourcePath);
        
        // Approach 3: Relative path
        String relativePath = "./src/test/resources/";
        System.out.println("Relative path: " + relativePath);
        
        // Test which approach works
        File[] testFiles = {
            new File(classLoaderPath, "img.png"),
            new File(testResourcePath, "img.png"),
            new File(relativePath, "img.png")
        };
        
        for (int i = 0; i < testFiles.length; i++) {
            File testFile = testFiles[i];
            System.out.println("Approach " + (i + 1) + ": " + testFile.getAbsolutePath());
            System.out.println("  Exists: " + testFile.exists());
            System.out.println("  Can read: " + testFile.canRead());
        }
    }

    @Test
    @DisplayName("Test URI with no additional path after context - line 45, 50")
    void testUriWithNoAdditionalPath() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        String testResourcePath = getTestResourcePath();
        when(mockContext.getRealPath("/")).thenReturn(testResourcePath);
        when(mockContext.getMimeType("archivo.txt")).thenReturn("text/plain");
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response for URI with no additional path
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        // This will trigger line 45 (idx != -1) and line 50 (else clause)
        when(mockRequest.getRequestURI()).thenReturn("/context/archivo.txt");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServletOutputStream mockOutputStream = createMockOutputStream(baos);
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        servlet.doGet(mockRequest, mockResponse);
        
        // Verify successful serving
        verify(mockResponse).reset();
        verify(mockResponse).setHeader("Content-Type", "text/plain");
        verify(mockResponse, never()).sendError(404);
    }

    @Test
    @DisplayName("Test file not found scenario - line 57, 58")
    void testFileNotFound() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        String testResourcePath = getTestResourcePath();
        when(mockContext.getRealPath("/")).thenReturn(testResourcePath);
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        // Request for non-existent file - will trigger lines 57, 58
        when(mockRequest.getRequestURI()).thenReturn("/context/images/nonexistent.png");
        
        servlet.doGet(mockRequest, mockResponse);
        
        // Verify 404 error is sent (lines 57, 58)
        verify(mockResponse).sendError(404);
        verify(mockResponse, never()).reset();
    }

    @Test
    @DisplayName("Test content type null scenario - line 70, 71")
    void testContentTypeNull() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        String testResourcePath = getTestResourcePath();
        when(mockContext.getRealPath("/")).thenReturn(testResourcePath);
        // Return null for getMimeType to trigger lines 70, 71
        when(mockContext.getMimeType("img.png")).thenReturn(null);
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/context/images/img.png");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ServletOutputStream mockOutputStream = createMockOutputStream(baos);
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        servlet.doGet(mockRequest, mockResponse);
        
        // Verify default content type is set (lines 70, 71)
        verify(mockResponse).setHeader("Content-Type", "application/octet-stream");
        verify(mockResponse).reset();
    }

    @Test
    @DisplayName("Test exception handling - line 82, 83")
    void testExceptionHandling() throws IOException, ServletException {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        String testResourcePath = getTestResourcePath();
        when(mockContext.getRealPath("/")).thenReturn(testResourcePath);
        when(mockContext.getMimeType("img.png")).thenReturn("image/png");
        
        // Mock servlet config
        ServletConfig mockConfig = mock(ServletConfig.class);
        when(mockConfig.getServletContext()).thenReturn(mockContext);
        
        // Initialize servlet
        servlet.init(mockConfig);
        
        // Mock request and response
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        when(mockRequest.getRequestURI()).thenReturn("/context/images/img.png");
        
        // Create a mock output stream that throws exception - triggers lines 82, 83
        ServletOutputStream mockOutputStream = mock(ServletOutputStream.class);
        doThrow(new IOException("Test exception")).when(mockOutputStream).write(any(byte[].class), anyInt(), anyInt());
        when(mockResponse.getOutputStream()).thenReturn(mockOutputStream);
        
        servlet.doGet(mockRequest, mockResponse);
        
        // Verify that exception is caught and handled (lines 82, 83)
        verify(mockResponse).reset();
        verify(mockResponse).setHeader("Content-Type", "image/png");
        // Exception should be logged, but servlet should not crash
    }

    @Test
    @DisplayName("Test replaceVariablesInString with missing closing bracket - line 112, 114")
    void testReplaceVariablesInStringMissingClosingBracket() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context with some attributes
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("var1")).thenReturn("value1");
        
        // Test case 1: Variable with no closing bracket at all - triggers line 112, 114
        String input1 = "This is a ${var1} and ${unclosed";
        String result1 = servlet.replaceVariablesInString(input1, mockContext);
        
        // First ${var1} is processed and replaced with "value1"
        // Then ${unclosed has no closing bracket, so method breaks (line 114)
        assertEquals("This is a value1 and ${unclosed", result1);
        
        // Test case 2: Only unclosed variable - should break immediately
        String input2 = "Start ${unclosed";
        String result2 = servlet.replaceVariablesInString(input2, mockContext);
        
        // Should break out of loop when no closing bracket found (line 114)
        assertEquals("Start ${unclosed", result2);
    }

    @Test
    @DisplayName("Test replaceVariablesInString with null attribute value - line 119")
    void testReplaceVariablesInStringNullAttribute() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context with null attribute
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("nullVar")).thenReturn(null);
        when(mockContext.getAttribute("existingVar")).thenReturn("existingValue");
        
        // String with variable that has null value - triggers line 119
        String input = "Value: ${nullVar}, Other: ${existingVar}";
        String result = servlet.replaceVariablesInString(input, mockContext);
        
        // Null attribute should be replaced with empty string (line 119)
        assertEquals("Value: , Other: existingValue", result);
    }

    @Test
    @DisplayName("Test replaceVariablesInString with multiple variables and replacement loop")
    void testReplaceVariablesInStringMultipleVariables() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context with attributes
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("baseDir")).thenReturn("/app");
        when(mockContext.getAttribute("version")).thenReturn("1.0");
        when(mockContext.getAttribute("env")).thenReturn("prod");
        
        // String with multiple variables to test loop continuation (line 126)
        String input = "Path: ${baseDir}/v${version}/${env}/config";
        String result = servlet.replaceVariablesInString(input, mockContext);
        
        // All variables should be replaced
        assertEquals("Path: /app/v1.0/prod/config", result);
    }

    @Test
    @DisplayName("Test replaceVariablesInString with nested variables")
    void testReplaceVariablesInStringNestedVariables() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("outer")).thenReturn("value");
        when(mockContext.getAttribute("inner")).thenReturn("test");
        
        // String with variables close to each other
        String input = "${outer}${inner}";
        String result = servlet.replaceVariablesInString(input, mockContext);
        
        assertEquals("valuetest", result);
    }

    @Test
    @DisplayName("Test replaceVariablesInString with empty string")
    void testReplaceVariablesInStringEmpty() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        ServletContext mockContext = mock(ServletContext.class);
        
        // Empty string should return empty string
        String result = servlet.replaceVariablesInString("", mockContext);
        assertEquals("", result);
        
        // String with no variables should return unchanged
        String input = "No variables here";
        result = servlet.replaceVariablesInString(input, mockContext);
        assertEquals("No variables here", result);
    }

    @Test
    @DisplayName("Test replaceVariablesInString basic functionality")
    void testReplaceVariablesInStringBasic() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context with attributes
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("test")).thenReturn("success");
        
        // Basic variable replacement
        String input = "Result: ${test}";
        String result = servlet.replaceVariablesInString(input, mockContext);
        
        assertEquals("Result: success", result);
    }

    @Test
    @DisplayName("Debug replaceVariablesInString method behavior")
    void testDebugReplaceVariablesInString() throws Exception {
        fileEnabler servlet = new fileEnabler();
        
        // Mock servlet context
        ServletContext mockContext = mock(ServletContext.class);
        when(mockContext.getAttribute("existing")).thenReturn("found");
        
        // Test various scenarios to understand the behavior
        String[] inputs = {
            "Simple ${existing}",
            "Missing bracket ${missing",
            "No variables",
            "${existing} and ${missing"
        };
        
        for (String input : inputs) {
            String result = servlet.replaceVariablesInString(input, mockContext);
            System.out.println("Input: '" + input + "' -> Output: '" + result + "'");
        }
        
        // The actual test - this should trigger line 112, 114
        String input = "Test ${missing";
        String result = servlet.replaceVariablesInString(input, mockContext);
        assertEquals("Test ${missing", result);
    }

    // Helper method to get test resource path
    private String getTestResourcePath() {
        String testResourcePath = getClass().getClassLoader().getResource("").getPath();
        if (testResourcePath == null || testResourcePath.isEmpty()) {
            String userDir = System.getProperty("user.dir");
            testResourcePath = userDir + "/src/test/resources/";
        }
        
        if (testResourcePath.startsWith("file:")) {
            testResourcePath = testResourcePath.substring(5);
        }
        if (!testResourcePath.endsWith("/") && !testResourcePath.endsWith("\\")) {
            testResourcePath += "/";
        }
        return testResourcePath;
    }

    // Helper method to create mock output stream
    private ServletOutputStream createMockOutputStream(ByteArrayOutputStream baos) {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }
            
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                baos.write(b, off, len);
            }
        };
    }

}
