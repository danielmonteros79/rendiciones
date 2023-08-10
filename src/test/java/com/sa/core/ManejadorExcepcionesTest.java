package com.sa.core;

import com.sa.exceptions.SessionTimeOutException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManejadorExcepcionesTest {

  @Mock
  ActionForward actionForwardMocked;
  @Mock
  Exception exceptionMocked;
  @Mock
  ExceptionConfig exceptionConfigMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @InjectMocks
  ManejadorExcepciones manejadorExcepciones;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should execute the exception with an error")
  void shouldExecuteTheExceptionWithAnError() throws ServletException {
    //when
    when(actionMappingMocked.findForward("errorService")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = manejadorExcepciones.execute(exceptionMocked, exceptionConfigMocked, actionMappingMocked, actionFormMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should execute the exception AccesoNoPermitidoException")
  void shouldExecuteTheExceptionAccesoNoPermitidoException() throws ServletException {
    //given
    AccesoNoPermitidoException accesoNoPermitidoException = new AccesoNoPermitidoException();
    //when
    when(actionMappingMocked.findForward("permisoDenegado")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = manejadorExcepciones.execute(accesoNoPermitidoException, exceptionConfigMocked, actionMappingMocked, actionFormMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should execute the exception ServiceException")
  void shouldExecuteTheExceptionServiceException() throws ServletException {
    //given
    ServiceException serviceException = new ServiceException();
    //when
    when(actionMappingMocked.findForward("errorService")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = manejadorExcepciones.execute(serviceException, exceptionConfigMocked, actionMappingMocked, actionFormMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should execute the exception SessionTimeOutException")
  void shouldExecuteTheExceptionSessionTimeOutException() throws ServletException {
    //given
    SessionTimeOutException sessionTimeOutException = new SessionTimeOutException();
    //when
    when(actionMappingMocked.findForward("sessionTimeOut")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = manejadorExcepciones.execute(sessionTimeOutException, exceptionConfigMocked, actionMappingMocked, actionFormMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }
}
