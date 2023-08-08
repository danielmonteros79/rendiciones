package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class RendicionLoadActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionForward actionForwardMocked;
  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  RendicionForm rendicionFormMocked;
  @Mock
  SAMWebApplication samWebApplicationMocked;
  @Mock
  SAMWebClient samWebClientMocked;
  @Mock
  HttpServletResponse httpServletResponseMocked;
  @InjectMocks
  RendicionLoadAction rendicionLoadAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    Usuario usuario = new Usuario("", "", "", 1, "", new ArrayList<>());
    HttpSession session = new MockHttpSession();
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestCheckCDestino = new MockHttpServletRequest();

    session.setAttribute("userWorking", usuario);
    session.setAttribute("usuario", usuario);
    requestEmptyAction.setHttpSession(session);
    requestCheckCDestino.setHttpSession(session);
    requestCheckCDestino.addParameter("action", "checkCDestino");

    return Stream.of(
        Arguments.of(requestEmptyAction)
//        Arguments.of(requestCheckCDestino) // No se realiza la asignacion
                    );
  }

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Method under test: {@link RendicionLoadAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  void testExecuteAction(MockHttpServletRequest request) throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    when(actionMappingMocked.findForward("success")).thenReturn(actionForwardMocked);
    //then
    ActionForward actionForwardToAssert = rendicionLoadAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
        samWebClientMocked, request, httpServletResponseMocked);
    assertNotNull(actionForwardToAssert);
  }
}
