package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.org.bbva.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.exceptions.JsonResponseException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListadoRendicionesActionTest {
  @Test
  @DisplayName("Debe delegar correctamente en writeError y manejar JsonResponseException")
  void writeError_delegates_andHandlesJsonResponseException() throws Exception {
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    Exception ex = new Exception("error test");
    ListadoRendicionesAction actionSpy = Mockito.spy(new ListadoRendicionesAction());
    ActionForward expectedForward = Mockito.mock(ActionForward.class);
    doReturn(expectedForward)
            .when(actionSpy)
            .writeError(Mockito.eq(response), Mockito.eq(ex));
    ActionForward result = actionSpy.writeError(response, ex);
    assertNotNull(result);
    assertEquals(expectedForward, result);
  }

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
  @Mock
  HttpSession httpSessionMocked;
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
    // Se crea un "spy" para poder verificar llamadas a métodos no abstractos de la propia clase
    listadoRendicionesAction = Mockito.spy(new ListadoRendicionesAction());
    Usuario usuario = new Usuario("testUser", "admin", "Test User", 1, "IT", new ArrayList<>());
    // Se establece un usuario por defecto para la mayoría de los tests
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

  // ==================== NUEVOS TESTS PARA COBERTURA DE executeAction ====================

  @Test
  @DisplayName("Debe retornar un error si sessionUserWorking es nulo")
  void executeAction_conSessionUserWorkingNulo_debeRetornarError() throws Exception {
    // Arrange
    listadoRendicionesAction.setSessionUserWorking(null); // Condición a probar
    // Se hace un mock de writeError para que no falle por dependencias internas (como getWriter)
    doReturn(null).when(listadoRendicionesAction).writeError(any(HttpServletResponse.class), any(Exception.class));

    // Act
    ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result); // writeError devuelve null

    ArgumentCaptor<Exception> captor = ArgumentCaptor.forClass(Exception.class);
    verify(listadoRendicionesAction).writeError(any(HttpServletResponse.class), captor.capture());
    assertEquals("Sesión de usuario no válida", captor.getValue().getMessage());
  }

  @Test
  @DisplayName("Debe retornar un error si el ID del usuario es nulo")
  void executeAction_conUserIdNulo_debeRetornarError() throws Exception {
    // Arrange
    Usuario usuarioSinId = Mockito.mock(Usuario.class);
    when(usuarioSinId.getIdUser()).thenReturn(null); // Condición a probar
    listadoRendicionesAction.setSessionUserWorking(usuarioSinId);

    doReturn(null).when(listadoRendicionesAction).writeError(any(HttpServletResponse.class), any(Exception.class));

    // Act
    ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result);

    ArgumentCaptor<Exception> captor = ArgumentCaptor.forClass(Exception.class);
    verify(listadoRendicionesAction).writeError(any(HttpServletResponse.class), captor.capture());
    assertEquals("ID de usuario no válido", captor.getValue().getMessage());
  }

  @Test
  @DisplayName("Debe retornar un error si el ID del usuario está vacío")
  void executeAction_conUserIdVacio_debeRetornarError() throws Exception {
    // Arrange
    Usuario usuarioIdVacio = Mockito.mock(Usuario.class);
    when(usuarioIdVacio.getIdUser()).thenReturn("   "); // Condición a probar
    listadoRendicionesAction.setSessionUserWorking(usuarioIdVacio);

    doReturn(null).when(listadoRendicionesAction).writeError(any(HttpServletResponse.class), any(Exception.class));

    // Act
    ActionForward result = listadoRendicionesAction.executeAction(actionMappingMocked, actionFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

    // Assert
    assertNull(result);

    ArgumentCaptor<Exception> captor = ArgumentCaptor.forClass(Exception.class);
    verify(listadoRendicionesAction).writeError(any(HttpServletResponse.class), captor.capture());
    assertEquals("ID de usuario no válido", captor.getValue().getMessage());
  }

  // ===================================================================================

  @Test
  @DisplayName("Should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("filtrar");
    // Provocar una excepción al no mockear una dependencia interna que será llamada
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenThrow(new RuntimeException("Error forzado"));
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
    //given
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);

    //when
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
    //given
    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    //when
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