package com.sa.core;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.TipoPerfil;
import com.sa.entities.Usuario;
import com.sa.form.LoginForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

class PuntoEntradaActionTest {

  @Mock
  ActionForward actionForwardMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  TipoPerfil tipoPerfilMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  LoginForm loginFormMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  HttpSession httpSessionMocked;
  @InjectMocks
  PuntoEntradaAction puntoEntradaAction;

  public static Stream<Arguments> executeSource() {
    //given
    String userNotNull = "0";
    String userNull = "1";

    return Stream.of(
        Arguments.of(userNotNull),
        Arguments.of(userNull)
                    );
  }

  public static Stream<Arguments> executeWithNullUserSource() {
    //given
    String username = "A127216";
    String pass = "pass";
    String userNotNull = "0";
    String userNull = "1";

    return Stream.of(
        Arguments.of("", "", null),
        Arguments.of(username, pass, userNotNull),
        Arguments.of(username, pass, userNull),
        Arguments.of(username, null, userNotNull)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeSource")
  @DisplayName("Should execute")
  void shouldExecute(String opt) throws Exception {
    //when
    when(httpServletRequestMocked.getHeader("iv-user")).thenReturn("A127216");
    when(loginFormMocked.getUsername()).thenReturn("");
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(usuarioMocked.getTipoPerfil()).thenReturn(tipoPerfilMocked);
    when(tipoPerfilMocked.getPantalla()).thenReturn(TipoPerfil.VIEW_APROBACION.getPantalla());
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMocked);
    when(actionMappingMock.findForward("failure")).thenReturn(actionForwardMocked);

//    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
//        (mockManagerTransaction, context) -> {
//          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
//          when(mockManagerTransaction.getDataReturn()).thenReturn(isUserNull(opt));
//          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
//        })) {
      //then
//      final ActionForward actionForwardToAssert = puntoEntradaAction.execute(actionMappingMock, loginFormMocked, samWebApplicationMocked, samWebClientMocked,
//          httpServletRequestMocked, httpServletResponseMocked);
//      assertNotNull(actionForwardToAssert);
//    }
  }

  @ParameterizedTest
  @MethodSource("executeWithNullUserSource")
  @DisplayName("Should execute with null user")
  void shouldExecuteWithNullUser(String username, String pass, String opt) throws Exception {
    //when
    when(httpServletRequestMocked.getHeader("iv-user")).thenReturn(null);
    when(httpServletRequestMocked.getAttribute("iv-user")).thenReturn("A127216");

    when(loginFormMocked.getUsername()).thenReturn(username);
    when(loginFormMocked.getPassword()).thenReturn(pass);
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(usuarioMocked.getTipoPerfil()).thenReturn(tipoPerfilMocked);
    when(tipoPerfilMocked.getPantalla()).thenReturn(TipoPerfil.VIEW_APROBACION.getPantalla());
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMocked);
    when(actionMappingMock.findForward("failure")).thenReturn(actionForwardMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturn()).thenReturn(isUserNull(opt));
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      final ActionForward actionForwardToAssert = puntoEntradaAction.execute(actionMappingMock, loginFormMocked, samWebApplicationMocked, samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  private Usuario isUserNull(String opt) {
    return "0".equals(opt)? usuarioMocked : null;
  }

  @Test
  void testExecute_ThrowsException() {
    // Setup
    final ActionMapping mapping = new ActionMapping();
    final ActionForm form = null;
    final SAMWebApplication samApplication = new SAMWebApplication();
    final SAMWebClient samClient = new SAMWebClient();
    final HttpServletRequest mockRequest = mock(HttpServletRequest.class);

    // Run the test
    assertThrows(Exception.class, () -> puntoEntradaAction.execute(mapping, form, samApplication, samClient, mockRequest, null));
  }
}
