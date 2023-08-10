package com.sa.action;

import com.sa.core.SecurityActionMapping;
import com.sa.entities.Usuario;
import com.sa.services.LoggerSUM;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestriccionActionTest {

  @Mock
  LoggerSUM loggerMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  SecurityActionMapping securityActionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Spy
  RestriccionAction restriccionAction;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should execute")
  void shouldExecute() throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(usuarioMocked.getIdUser()).thenReturn("00");
    //then
    ActionForward actionForwardToAssert = restriccionAction.execute(actionMappingMocked, actionFormMocked, httpServletRequestMocked, httpServletResponseMocked);
    verify(restriccionAction).execute(actionMappingMocked, actionFormMocked, httpServletRequestMocked, httpServletResponseMocked);
  }

  @Test
  void testExecute_ThrowsException() {
    //then
    assertThrows(Exception.class, () -> restriccionAction.execute(actionMappingMocked, actionFormMocked, httpServletRequestMocked, httpServletResponseMocked));
  }

  @Test
  void testDoRestriccion() throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuarioMocked);
    when(usuarioMocked.getPerfil()).thenReturn(1);
    //then
    restriccionAction.doRestriccion(securityActionMappingMocked, actionFormMocked, httpServletRequestMocked, httpServletResponseMocked, loggerMocked);
    verify(restriccionAction).doRestriccion(securityActionMappingMocked, actionFormMocked, httpServletRequestMocked, httpServletResponseMocked, loggerMocked);
  }

  @Test
  void testCerrarSesion() {
    restriccionAction.cerrarSesion(httpServletRequestMocked);
    verify(restriccionAction).cerrarSesion(httpServletRequestMocked);
  }

  @Test
  void testChequearTimeOutAltamira() {
    restriccionAction.chequearTimeOutAltamira(httpServletRequestMocked);
    verify(restriccionAction).chequearTimeOutAltamira(httpServletRequestMocked);
  }
}
