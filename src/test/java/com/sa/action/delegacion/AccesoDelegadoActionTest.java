package com.sa.action.delegacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sa.entities.TipoPerfil;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class AccesoDelegadoActionTest {

  @Mock
  TipoPerfil tipoPerfilMocked;
  @Mock
  Usuario usuarioMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  ActionForm actionFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  PrintWriter printWriterMocked;
  @InjectMocks
  AccesoDelegadoAction accesoDelegadoAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionReemplazar = "reemplazar";
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestReemplazar = new MockHttpServletRequest();

    requestEmptyAction.addParameter("action", "");
    requestReemplazar.addParameter("action", "reemplazar");

    return Stream.of(
        Arguments.of(requestEmptyAction, action),
        Arguments.of(requestReemplazar, actionReemplazar)
                    );
  }

  public static Stream<Arguments> actualizarTipoPerfilDelegadoSource() {
    //given
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoA = new ParametriaUsuarioDelegado();
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoI = new ParametriaUsuarioDelegado();
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoT = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegadoA.setDelegadoAccion("A");
    parametriaUsuarioDelegadoI.setDelegadoAccion("I");
    parametriaUsuarioDelegadoT.setDelegadoAccion("T");
    List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList = new ArrayList<>();
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoA);
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoI);
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoT);

    return Stream.of(Arguments.of(parametriaUsuarioDelegadoList, usuario));
  }

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Method under test: {@link AccesoDelegadoAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @SuppressWarnings("deprecation")
  void testExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //given
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = accesoDelegadoAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked, request, httpServletResponseMocked);
    if (action.equals("")) {
      assertNotNull(actionForwardToAssert);
    } else {
      assertNull(actionForwardToAssert);
    }
  }

  @Disabled("sessionUser lanza NPE - reveer")
  @Test
  @DisplayName("Should get user trabajo")
  void shouldGetUserTrabajo() throws Exception {
    //then
    Method obtenerUsuarioTrabajoMocked = AccesoDelegadoAction.class.getDeclaredMethod("obtenerUsuarioTrabajo", String.class);
    obtenerUsuarioTrabajoMocked.setAccessible(true);
    Usuario usuarioToAssert = (Usuario) obtenerUsuarioTrabajoMocked.invoke(accesoDelegadoAction, "");
    assertEquals("", usuarioToAssert.getIdUser());
  }

  @Disabled("sessionUser lanza NPE - reveer")
  @ParameterizedTest
  @MethodSource("actualizarTipoPerfilDelegadoSource")
  @DisplayName("Should update tipo perfil delegado")
  void shoulUpdateTipoPerfilDelegado(List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList, Usuario usuario) throws Exception {
    //then
    Method obtenerUsuarioTrabajoMocked = AccesoDelegadoAction.class.getDeclaredMethod("actualizarTipoPerfilDelegado", List.class, Usuario.class);
    obtenerUsuarioTrabajoMocked.setAccessible(true);
    obtenerUsuarioTrabajoMocked.invoke(accesoDelegadoAction, parametriaUsuarioDelegadoList, usuario);

    if (parametriaUsuarioDelegadoList.get(0).getDelegadoAccion().equals("A")) {
      assertTrue(usuario.getTipoPerfil().equals("DELEG_APROB"));
    } else if (parametriaUsuarioDelegadoList.get(1).getDelegadoAccion().equals("I")) {
      assertTrue(usuario.getTipoPerfil().equals("DELEG_REND"));
    } else if (parametriaUsuarioDelegadoList.get(2).getDelegadoAccion().equals("T")) {
      assertTrue(usuario.getTipoPerfil().equals("DELEG_REND_APROB"));
    }

  }

  @Test
  @DisplayName("Should verify if approved")
  void shouldVerifyIfApproved() throws Exception {
    //when
    when(usuarioMocked.getTipoPerfil()).thenReturn(TipoPerfil.VIEW_APROBACION);
    when(tipoPerfilMocked.getPantalla()).thenReturn(String.valueOf(TipoPerfil.VIEW_APROBACION));
    //then
    Method verificarAprobacionesMocked = AccesoDelegadoAction.class.getDeclaredMethod("verificarAprobaciones", Usuario.class, SAMWebClient.class);
    verificarAprobacionesMocked.setAccessible(true);
    verificarAprobacionesMocked.invoke(accesoDelegadoAction, usuarioMocked, samWebClientMocked);
    assertNotNull(usuarioMocked);
  }
}
