package com.sa.delegados.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReplaceDelegadoActionTest {

  @Mock
  Usuario usuarioMocked;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  ActionForward actionForwardMock;
  @Mock
  ActionForm actionFormMock;
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
  ReplaceDelegadoAction replaceDelegadoAction;

  public static Stream<Arguments> executeActionSource() {
    //then
    Usuario usuario = new Usuario("", "1", "1", 0, "1", new ArrayList<>());
    Usuario usuario1 = new Usuario("1", "1", "1", 0, "1", new ArrayList<>());
    List<Usuario> usuarioList = new ArrayList<>();
    usuarioList.add(usuario);
    usuario.setDelegadosAsignados(usuarioList);
    usuario1.setDelegadosAsignados(usuarioList);

    return Stream.of(
        Arguments.of(usuarioList, usuario),
        Arguments.of(usuarioList, usuario1)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute the action")
  void shouldExecuteTheAction(List<Usuario> usuarioList, Usuario usuario) throws Exception {
    //when
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getAttribute("usuario")).thenReturn(usuario);
    when(usuarioMocked.getIdUser()).thenReturn("1");
    when(usuarioMocked.getDelegadosAsignados()).thenReturn(usuarioList);
    when(actionMappingMock.findForward("success")).thenReturn(actionForwardMock);
    //then
    ActionForward actionForwardToAssert = replaceDelegadoAction.executeAction(actionMappingMock, actionFormMock, samWebApplicationMocked,  samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }
}
