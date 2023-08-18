package com.sa.core;

import ar.com.bbva.web.servlets.WebAppInitServlet;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InitServletTest {

    @Test
    void testInit1() throws ServletException {
        InitServlet initServlet = new InitServlet();
        MockServletContext mockServletContext = mock(MockServletContext.class);
        when(mockServletContext.getRealPath(Mockito.<String>any()))
                .thenReturn("/applicationConfig.xml/applicationConfig.xml");
        WebAppInitServlet config = mock(WebAppInitServlet.class);
        when(config.getInitParameter(Mockito.<String>any())).thenReturn("Init Parameter");
        when(config.getServletContext()).thenReturn(mockServletContext);
        initServlet.init(config);
        verify(config).getInitParameter(Mockito.<String>any());
        verify(config).getServletContext();
        verify(mockServletContext).getRealPath(Mockito.<String>any());
        assertNull(initServlet.getInitParameterNames());
    }


    @Test
    void testInit2() throws ServletException {
        InitServlet initServlet = new InitServlet();
        MockServletContext mockServletContext = mock(MockServletContext.class);
        when(mockServletContext.getRealPath(Mockito.<String>any()))
                .thenReturn("/applicationConfig.xml/applicationConfig.xml");
        WebAppInitServlet config = mock(WebAppInitServlet.class);
        when(config.getInitParameter(Mockito.<String>any())).thenReturn(null);
        when(config.getServletContext()).thenReturn(mockServletContext);
        initServlet.init(config);
        verify(config).getInitParameter(Mockito.<String>any());
        verify(config).getServletContext();
        verify(mockServletContext).getRealPath(Mockito.<String>any());
        assertNull(initServlet.getInitParameterNames());
    }


    @Test
    void testInit3() throws ServletException {
        InitServlet initServlet = new InitServlet();
        MockServletContext mockServletContext = mock(MockServletContext.class);
        when(mockServletContext.getRealPath(Mockito.<String>any()))
                .thenReturn("/applicationConfig.xml/applicationConfig.xml");
        WebAppInitServlet config = mock(WebAppInitServlet.class);
        when(config.getInitParameter(Mockito.<String>any())).thenReturn("");
        when(config.getServletContext()).thenReturn(mockServletContext);
        initServlet.init(config);
        verify(config).getInitParameter(Mockito.<String>any());
        verify(config).getServletContext();
        verify(mockServletContext).getRealPath(Mockito.<String>any());
        assertNull(initServlet.getInitParameterNames());
    }

    @Test
    void testDestroy() {
        InitServlet initServlet = new InitServlet();
        initServlet.destroy();
        assertNull(initServlet.getServletConfig());
    }
}
