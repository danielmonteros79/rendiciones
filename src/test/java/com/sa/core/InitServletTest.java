package com.sa.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InitServletTest {

  @Mock
  HttpServlet httpServletMocked;
  @Mock
  ServletContext servletContextMocked;
  @Mock
  ServletConfig servletConfigMocked;
  @InjectMocks
  InitServlet initServlet;
  @Mock
  InitServlet initServletMocked;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Disabled("ServletContext no se inicializa correctamente")
  @Test
  @DisplayName("Should init servlet")
  void shouldInitServlet() throws ServletException {
    //when
    when(httpServletMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getRealPath("/applicationConfig.xml")).thenReturn("/applicationConfig.xml");
    //then
    initServlet.init(servletConfigMocked);
    verify(initServlet).init(servletConfigMocked);
  }
}
