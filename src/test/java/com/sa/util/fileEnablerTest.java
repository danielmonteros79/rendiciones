package com.sa.util;

import static ar.com.bbva.utils.WSUtils.getServletContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
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
        MockHttpServletRequest arg0 = mock(MockHttpServletRequest.class);
        when(arg0.getRequestURI()).thenReturn("https://example.org/example");
        MockHttpServletResponse arg1 = mock(MockHttpServletResponse.class);
        doNothing().when(arg1).sendError(anyInt());
        fileEnabler.doGet(arg0, arg1);
        verify(arg0).getRequestURI();
        verify(arg1).sendError(anyInt());
    }


    @Test
    void testDoPost() throws IOException, ServletException {
        fileEnabler fileEnabler = new fileEnabler();
        MockHttpServletRequest arg0 = mock(MockHttpServletRequest.class);
        when(arg0.getRequestURI()).thenReturn("https://example.org/example");
        BufferedResponseWrapper13Impl arg1 = mock(BufferedResponseWrapper13Impl.class);
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

        MockServletContext servletContext = new MockServletContext();
        servletContext.setAttribute("test", "test");
        fileEnabler fileEnabler = new fileEnabler();
        String valores = ("${test}");
        fileEnabler.replaceVariablesInString(valores, servletContext);
    }

    @Test
    void testReplaceVariablesInString2() {
        fileEnabler fileEnabler = new fileEnabler();
        assertNull(fileEnabler.replaceVariablesInString(null, new MockServletContext()));
    }

    @Test
    void testReplaceVariablesInString3() {
        MockServletContext servletContext = new MockServletContext();
        servletContext.setAttribute("1", "1");
        fileEnabler fileEnabler = new fileEnabler();
        String valores = ("${1}");
        fileEnabler.replaceVariablesInString(valores, servletContext);
    }
}

