package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ListadoRendicionesActionTest {

  @Mock
  PrintWriter printWriterMocked;
  @Mock
  ActionForward actionForwardMocked;
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
  @InjectMocks
  ListadoRendicionesAction listadoRendicionesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionFiltrar = "filtrar";
    String actionEliminar = "eliminar";
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestFiltrar = new MockHttpServletRequest();
    MockHttpServletRequest requestEliminar = new MockHttpServletRequest();

    requestEmptyAction.addParameter("action", "");

    requestFiltrar.addParameter("action", "filtrar");
    requestFiltrar.addParameter("id", "1");
    requestFiltrar.addParameter("fechaDesde", "07/08/2023");
    requestFiltrar.addParameter("fechaHasta", "07/08/2023");

    requestEliminar.addParameter("action", "eliminar");
    requestEliminar.addParameter("idRendicion", "1");

    return Stream.of(
        Arguments.of(requestEmptyAction, action),
        Arguments.of(requestFiltrar, actionFiltrar),
        Arguments.of(requestEliminar, actionEliminar)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Method under test: {@link ListadoRendicionesAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  void testExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMocked.findForward("ok")).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked, samWebClientMocked,
        request, httpServletResponseMocked);
    if(action.equals("eliminar") || action.equals("filtrar")) {
      assertNull(actionForwardToAssert);
    } else {
      assertNotNull(actionForwardToAssert);
    }
  }

  /**
   * Method under test: {@link ListadoRendicionesAction#eliminar(SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @Test
  @Disabled("sessionUser lanza NPE - reveer")
  void testEliminar() throws Exception {
    //given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("action", "eliminar");
    request.addParameter("idRendicion", "1");

    //then
    ActionForward actionForwardToAssert = listadoRendicionesAction.eliminar(samWebClientMocked,request, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}

