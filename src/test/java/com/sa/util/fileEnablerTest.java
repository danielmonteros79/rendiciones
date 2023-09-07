package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JCategoryLog;
import org.apache.log4j.Category;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockServletContext;
import org.displaytag.filter.BufferedResponseWrapper13Impl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class fileEnablerTest {

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
        BufferedResponseWrapper13Impl arg1 = mock(BufferedResponseWrapper13Impl.class);
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
    void testReplaceVariablesInString() {
        fileEnabler fileEnabler = new fileEnabler();
        assertEquals("Str To Replace", fileEnabler.replaceVariablesInString("Str To Replace", new MockServletContext()));
    }

    @Test
    void testReplaceVariablesInString2() {
        fileEnabler fileEnabler = new fileEnabler();
        assertNull(fileEnabler.replaceVariablesInString(null, new MockServletContext()));
    }

    @Test
    void testReplaceVariablesInString3() {
        fileEnabler fileEnabler = new fileEnabler();

        MockServletContext sc = new MockServletContext();
        sc.setLog(new Log4JCategoryLog(mock(Category.class)));
        assertEquals("Str To Replace", fileEnabler.replaceVariablesInString("Str To Replace", sc));
    }
}

