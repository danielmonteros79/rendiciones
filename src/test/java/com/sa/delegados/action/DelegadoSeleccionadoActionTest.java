package com.sa.delegados.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.form.DelegadosAsignadosForm;
import com.sa.manager.ManagerTransaction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DelegadoSeleccionadoActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  ActionForward actionForwardMock;
  @Mock
  DelegadosAsignadosForm delegadosAsignadosFormMocked;
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
  DelegadoSeleccionadoAction delegadoSeleccionadoAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    ParametriaUsuarioDelegado parametriaUsuarioDelegadoA = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegadoA.setDelegadoUser("1");
    parametriaUsuarioDelegadoA.setDelegadoAccion("A");

    ParametriaUsuarioDelegado parametriaUsuarioDelegadoI = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegadoI.setDelegadoUser("1");
    parametriaUsuarioDelegadoI.setDelegadoAccion("I");

    ParametriaUsuarioDelegado parametriaUsuarioDelegadoT = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegadoT.setDelegadoUser("1");
    parametriaUsuarioDelegadoT.setDelegadoAccion("T");

    List<ParametriaUsuarioDelegado> usuarioDelegadoList = new ArrayList<>();
    usuarioDelegadoList.add(parametriaUsuarioDelegadoA);
    usuarioDelegadoList.add(parametriaUsuarioDelegadoI);
    usuarioDelegadoList.add(parametriaUsuarioDelegadoT);

    Usuario usuario = new Usuario("", "", "",0, "", new ArrayList<>());
    List<Usuario> usuarioList = new ArrayList<>();
    usuarioList.add(usuario);
    usuario.setDelegadosAsignados(usuarioList);

    Usuario usuario2 = new Usuario("1", "", "",0, "", new ArrayList<>());
    usuario2.setDelegadosAsignados(usuarioList);

    Usuario usuario3 = new Usuario("", "", "",0, "", new ArrayList<>());
    List<Usuario> usuarioList2 = new ArrayList<>();
    usuarioList2.add(usuario2);
    usuario3.setDelegadosAsignados(usuarioList2);

    return Stream.of(
        Arguments.of(usuarioList, usuario, usuarioDelegadoList),
        Arguments.of(usuarioList, usuario2, usuarioDelegadoList),
        Arguments.of(usuarioList2, usuario3, usuarioDelegadoList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(List<Usuario> usuarioList, Usuario usuario, List<ParametriaUsuarioDelegado> usuarioDelegadoList) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuario);
    when(usuarioMocked.getIdUser()).thenReturn("1");
    when(usuarioMocked.getDelegadosAsignados()).thenReturn(usuarioList);
    when(delegadosAsignadosFormMocked.getDelegado()).thenReturn("");
    when(actionMappingMock.findForward("rendiciones")).thenReturn(actionForwardMock);
    when(actionMappingMock.findForward("aprobaciones")).thenReturn(actionForwardMock);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
        (mockManagerTransaction, context) -> {
          doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
          when(mockManagerTransaction.getDataReturnList()).thenReturn(usuarioDelegadoList);
        })) {
      //then
      ActionForward actionForwardtoAssert = delegadoSeleccionadoAction.executeAction(actionMappingMock, delegadosAsignadosFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardtoAssert);
    }
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should catch an exception")
  void shouldCatchAnException(List<Usuario> usuarioList, Usuario usuario, List<ParametriaUsuarioDelegado> usuarioDelegadoList) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuario);
    when(usuarioMocked.getIdUser()).thenReturn("1");
    when(usuarioMocked.getDelegadosAsignados()).thenReturn(usuarioList);
    when(delegadosAsignadosFormMocked.getDelegado()).thenReturn("");
    when(actionMappingMock.findForward("failure")).thenReturn(actionForwardMock);
    //then
    ActionForward actionForwardtoAssert = delegadoSeleccionadoAction.executeAction(actionMappingMock, delegadosAsignadosFormMocked, samWebApplicationMocked,
        samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardtoAssert);
  }
}
