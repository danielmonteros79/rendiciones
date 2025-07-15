package com.sa.util;

import static ar.com.bbva.utils.WSUtils.getServletContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
}

