package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.org.bbva.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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
import java.util.Date;
import java.util.stream.Stream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @InjectMocks
  ListadoRendicionesAction listadoRendicionesAction;

  public static Stream<Arguments> executeActionSource() {
    //given
    String action = "";
    String actionFiltrar = "filtrar";
    String actionEliminar = "eliminar";

    return Stream.of(
        Arguments.of(action),
        Arguments.of(actionFiltrar),
        Arguments.of(actionEliminar)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    listadoRendicionesAction.setSessionUser(usuario);
    listadoRendicionesAction.setSessionUserWorking(usuario);
  }

  /**
   * Method under test: {@link ListadoRendicionesAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should execute action")
  void shouldExecuteAction(String action) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);

    when(httpServletRequestMocked.getParameter("id")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("17/08/2023");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("17/08/2023");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class,
        (mockRendicionesService, context) -> {
          when(mockRendicionesService.obtenerListadoRendiciones(anyString(), anyString(), any(), anyString(), anyString())).thenReturn(new ArrayList<>());
        })) {
      //then
      ActionForward actionForwardToAssert = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
          samWebClientMocked,
          httpServletRequestMocked, httpServletResponseMocked);
      if (action.equals("eliminar")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
        samWebClientMocked,
        httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
  
  @Test
  @DisplayName("Should filter with empty date filters")
  void shouldFilterWithEmptyDateFilters() throws Exception {
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");
    when(httpServletRequestMocked.getParameter("id")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("");
    when(actionMappingMocked.findForward("rendiciones")).thenReturn(actionForwardMocked);

    Rendicion rendicion = new Rendicion();
    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());

    try (MockedConstruction<RendicionesService> mocked = Mockito.mockConstruction(RendicionesService.class,
        (mock, context) -> {
          when(mock.obtenerListadoRendiciones(anyString(), anyString(), any(), anyString(), anyString()))
            .thenReturn(Collections.singletonList(rendicion));
          when(mock.getMsg()).thenReturn("msg");
        })) {
      ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked,
          samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(result);
      verify(actionMappingMocked).findForward("rendiciones");
    }
  }
  
  @Test
  @DisplayName("Should filter and exclude out-of-range rendicion")
  void shouldFilterAndExcludeOutOfRangeRendicion() throws Exception {
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");
    when(httpServletRequestMocked.getParameter("id")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("17/08/2023");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("17/08/2023");
    when(actionMappingMocked.findForward("rendiciones")).thenReturn(actionForwardMocked);

    Date fueraDeRangoDesde = DateUtils.dfDDMMYYYY.parse("10/08/2023");
    Date fueraDeRangoHasta = DateUtils.dfDDMMYYYY.parse("11/08/2023");

    Rendicion rendicion = new Rendicion();
    rendicion.setFechaDesde(fueraDeRangoDesde);
    rendicion.setFechaHasta(fueraDeRangoHasta);

    try (MockedConstruction<RendicionesService> mocked = Mockito.mockConstruction(RendicionesService.class,
        (mock, context) -> {
          when(mock.obtenerListadoRendiciones(anyString(), anyString(), any(), anyString(), anyString()))
            .thenReturn(Collections.singletonList(rendicion));
          when(mock.getMsg()).thenReturn("msg");
        })) {
      ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked,
          samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(result);
      verify(actionMappingMocked).findForward("rendiciones");
    }
  }
  
  @Test
  @DisplayName("Should call eliminar and return JSON response")
  void shouldCallEliminar() throws Exception {
    when(httpServletRequestMocked.getParameter("action")).thenReturn("eliminar");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("123");
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<RendicionesService> mocked = Mockito.mockConstruction(RendicionesService.class,
        (mock, context) -> {
          doNothing().when(mock).bajaRendicion(anyString(), anyString());
          when(mock.getMsg()).thenReturn("Eliminado con éxito");
        })) {
      ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked,
          samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNull(result); // porque writeJson devuelve null
    }
  }



}
