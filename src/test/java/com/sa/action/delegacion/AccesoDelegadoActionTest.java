package com.sa.action.delegacion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sa.entities.TipoPerfil;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class AccesoDelegadoActionTest {

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
  HttpServletRequest httpServletRequestMocked;
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
    MockHttpServletRequest requestReemplazar2 = new MockHttpServletRequest();

    requestEmptyAction.addParameter("action", "");
    requestReemplazar.addParameter("action", "reemplazar");
    requestReemplazar.addParameter("delegado", "");

    requestReemplazar2.addParameter("action", "reemplazar");
    requestReemplazar2.addParameter("delegado", "1");

    ParametriaUsuarioDelegado parametriaUsuarioDelegadoA = new ParametriaUsuarioDelegado();
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoI = new ParametriaUsuarioDelegado();
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoT = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegadoA.setDelegadoAccion("A");
    parametriaUsuarioDelegadoA.setDelegadoUser("");
    parametriaUsuarioDelegadoI.setDelegadoAccion("I");
    parametriaUsuarioDelegadoI.setDelegadoUser("");
    parametriaUsuarioDelegadoT.setDelegadoAccion("T");
    parametriaUsuarioDelegadoT.setDelegadoUser("");
    List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList = new ArrayList<>();
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoA);
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoI);
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegadoT);

    Usuario user = new Usuario("", "", "", 0, "", new ArrayList<>());
    Usuario user2 = new Usuario("1", "", "", 0, "", new ArrayList<>());
    List<Usuario> usuarioArrayList = new ArrayList<>();
    usuarioArrayList.add(user);
    usuarioArrayList.add(user2);
    Usuario usuario = new Usuario("", "", "", 0, "", usuarioArrayList);
    usuario.setTipoPerfil(TipoPerfil.VIEW_APROBACION.getPantalla());
    Usuario usuarioEmptyList = new Usuario("", "", "", 0, "", new ArrayList<>());

    return Stream.of(
        Arguments.of(requestEmptyAction, action, parametriaUsuarioDelegadoList, usuarioEmptyList),
        Arguments.of(requestReemplazar, actionReemplazar, parametriaUsuarioDelegadoList, usuarioEmptyList),
        Arguments.of(requestReemplazar, actionReemplazar, parametriaUsuarioDelegadoList, usuario),
        Arguments.of(requestReemplazar2, actionReemplazar, parametriaUsuarioDelegadoList, usuario)
                    );
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
  @DisplayName("Should execute action")
  void shouldExecuteAction(MockHttpServletRequest request, String action, List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList, Usuario usuario) throws Exception {
    //given
    accesoDelegadoAction.setSessionUser(usuario);
    //when
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(parametriaUsuarioDelegadoList);
          when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
        })) {
      //then
      ActionForward actionForwardToAssert = accesoDelegadoAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked, request, httpServletResponseMocked);
      if (action.equals("")) {
        assertNotNull(actionForwardToAssert);
      } else {
        assertNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("reemplazar");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = accesoDelegadoAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
