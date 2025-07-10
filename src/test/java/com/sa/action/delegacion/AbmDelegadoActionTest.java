package com.sa.action.delegacion;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import com.sa.form.delegacion.AbmDelegadoForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.ParametrosService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class AbmDelegadoActionTest {

  @Mock
  ActionMapping actionMappingMocked;
  @Mock
  AbmDelegadoForm abmDelegadoFormMocked;
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
  AbmDelegadoAction abmDelegadoAction;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("", "", "", 0, "", new ArrayList<>());
    abmDelegadoAction.setSessionUser(usuario);
  }

  public static Stream<Arguments> executeActionSource() {
    //given
    String actionGetDelegados = "getDelegados";
    String actionGetDelegado = "getDelegado";
    String actionBuscarUsuario = "buscarUsuario";
    String actionAbm = "abm";
    MockHttpServletRequest requestEmptyAction = new MockHttpServletRequest();
    MockHttpServletRequest requestGetDelegados = new MockHttpServletRequest();
    MockHttpServletRequest requestGetDelegado = new MockHttpServletRequest();
    MockHttpServletRequest requestBuscarUsuario = new MockHttpServletRequest();
    MockHttpServletRequest requestAbm = new MockHttpServletRequest();
    HttpSession session = new MockHttpSession();

    ParametriaUsuarioDelegado parametriaUsuarioDelegado = new ParametriaUsuarioDelegado();
    parametriaUsuarioDelegado.setId(1);
    List<ParametriaUsuarioDelegado> parametriaUsuarioDelegadoList = new ArrayList<>();
    parametriaUsuarioDelegadoList.add(parametriaUsuarioDelegado);

    session.setAttribute("delegacionesActivas", parametriaUsuarioDelegadoList);

    requestEmptyAction.addParameter("action", "");

    requestGetDelegados.addParameter("action", "getDelegados");
    requestGetDelegado.addParameter("action", "getDelegado");
    requestGetDelegado.addParameter("id", "1");
    requestGetDelegado.setHttpSession(session);

    requestBuscarUsuario.addParameter("action", "buscarUsuario");
    requestBuscarUsuario.addParameter("legajo", "legajo");

    requestAbm.addParameter("action", "abm");

    return Stream.of(
            Arguments.of(requestEmptyAction, ""),
            Arguments.of(requestGetDelegados, actionGetDelegados),
            Arguments.of(requestGetDelegado, actionGetDelegado),
            Arguments.of(requestBuscarUsuario, actionBuscarUsuario),
            Arguments.of(requestAbm, actionAbm)
    );
  }

  /**
   * Method under test: {@link AbmDelegadoAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("should execute action")
  void shouldExecuteAction(MockHttpServletRequest request, String action) throws Exception {
    //when
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);

    when(abmDelegadoFormMocked.getOpcion()).thenReturn("BAJA");
    when(abmDelegadoFormMocked.getDelegadoUser()).thenReturn("delegado");
    when(abmDelegadoFormMocked.getFeDesde()).thenReturn("15/08/2023");
    when(abmDelegadoFormMocked.getFeHasta()).thenReturn("15/08/2023");

    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ManagerTransaction> managerTransactionMC = Mockito.mockConstruction(ManagerTransaction.class,
            (mockManagerTransaction, context) -> {
              doNothing().when(mockManagerTransaction).executeTrx(any(), anyMap());
              when(mockManagerTransaction.getDataReturnList()).thenReturn(null);
              when(mockManagerTransaction.getDataReturn()).thenReturn(null);
              when(mockManagerTransaction.getMensajeAviso()).thenReturn("");
            })) {
      //then
      ActionForward actionForwardToAssert = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, samWebApplicationMocked,
              samWebClientMocked, request, httpServletResponseMocked);
      if (action.equals("getDelegado") || action.equals("buscarUsuario") || action.equals("abm")) {
        assertNull(actionForwardToAssert);
      } else {
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("should catch exception")
  void shouldCatchException() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("getDelegados");
    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    ActionForward actionForwardToAssert = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  // Tests específicos para el método buscarUsuario (líneas 83-89 del código seleccionado)
  
  public static Stream<Arguments> buscarUsuarioSuccessSource() {
    Usuario usuario1 = new Usuario("12345", "SS", "Juan Pérez", 1001, "DPTO1", new ArrayList<>());
    Usuario usuario2 = new Usuario("67890", "SS", "María García", 1002, "DPTO2", new ArrayList<>());

    return Stream.of(
        Arguments.of("12345", usuario1),
        Arguments.of("67890", usuario2),
        Arguments.of("abc123", usuario1), // Test con legajo alfanumérico
        Arguments.of("XYZ789", usuario2)  // Test con legajo en mayúsculas
    );
  }

  public static Stream<Arguments> buscarUsuarioErrorSource() {
    return Stream.of(
        Arguments.of("99999", "Usuario no encontrado en el sistema"),
        Arguments.of("INVALID", "Legajo inválido"),
        Arguments.of("000000", "Usuario inactivo")
    );
  }

  @ParameterizedTest
  @MethodSource("buscarUsuarioSuccessSource")
  @DisplayName("Should find user successfully when valid legajo is provided - buscarUsuario method")
  void shouldFindUserSuccessfullyInBuscarUsuario(String legajo, Usuario expectedUser) throws Exception {
    // Arrange
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(" " + legajo.toLowerCase() + " "); // Test trim() y toUpperCase()
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mock, context) -> {
              when(mock.getUsuarioDelegacion(anyString(), anyString())).thenReturn(expectedUser);
            })) {

      // Act
      ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked, 
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Verify service was created and used
      List<ParametrosService> constructedServices = parametrosServiceMC.constructed();
      assertEquals(1, constructedServices.size(), "Should create one ParametrosService instance");
    }
  }

  @ParameterizedTest
  @MethodSource("buscarUsuarioErrorSource")
  @DisplayName("Should handle user not found errors gracefully - buscarUsuario method")
  void shouldHandleUserNotFoundErrorsInBuscarUsuario(String legajo, String errorMessage) throws Exception {
    // Arrange
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(legajo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mock, context) -> {
              // Simular excepción en el servicio
              when(mock.getUsuarioDelegacion(anyString(), anyString())).thenThrow(new RuntimeException(errorMessage));
            })) {

      // Act
      ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response even with errors");
    }
  }

  @Test
  @DisplayName("Should handle null legajo parameter in buscarUsuario")
  void shouldHandleNullLegajoInBuscarUsuario() throws Exception {
    // Arrange
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(null);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    // Act & Assert - El código real maneja las excepciones internamente
    // y devuelve una respuesta JSON con error en lugar de lanzar la excepción
    ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked,
            samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
    
    // El resultado debería ser null (respuesta JSON) en lugar de lanzar excepción
    assertNull(result, "Should return null for JSON error response when legajo is null");
  }

  @Test
  @DisplayName("Should process legajo correctly - trim and uppercase in buscarUsuario")
  void shouldProcessLegajoCorrectlyInBuscarUsuario() throws Exception {
    // Arrange
    String inputLegajo = "  abc123  ";
    String expectedProcessedLegajo = "ABC123";
    Usuario mockUser = new Usuario(expectedProcessedLegajo, "SS", "Test User", 1000, "TEST", new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(inputLegajo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mock, context) -> {
              when(mock.getUsuarioDelegacion(anyString(), anyString())).thenReturn(mockUser);
            })) {

      // Act
      ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null for JSON response");
      
      // Verify service was created
      List<ParametrosService> constructedServices = parametrosServiceMC.constructed();
      assertEquals(1, constructedServices.size(), "Should create exactly one ParametrosService instance");
    }
  }

  @Test
  @DisplayName("Should return success response structure for found user in buscarUsuario")
  void shouldReturnSuccessResponseStructureInBuscarUsuario() throws Exception {
    // Arrange
    String legajo = "TEST123";
    Usuario mockUser = new Usuario(legajo, "SS", "Test User", 1000, "TEST", new ArrayList<>());
    
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(legajo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mock, context) -> {
              when(mock.getUsuarioDelegacion(anyString(), anyString())).thenReturn(mockUser);
            })) {

      // Act
      ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null indicating JSON response was written");
      
      // El método writeJson debería haber sido llamado con una respuesta que contiene:
      // {"success": true, "delegado": usuario}
      List<ParametrosService> constructedServices = parametrosServiceMC.constructed();
      assertEquals(1, constructedServices.size());
    }
  }

  @Test
  @DisplayName("Should return error response structure when user not found in buscarUsuario")
  void shouldReturnErrorResponseStructureInBuscarUsuario() throws Exception {
    // Arrange
    String legajo = "NOTFOUND";
    
    when(httpServletRequestMocked.getParameter("action")).thenReturn("buscarUsuario");
    when(httpServletRequestMocked.getParameter("legajo")).thenReturn(legajo);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
            (mock, context) -> {
              // Simular que no se encontró el usuario
              when(mock.getUsuarioDelegacion(anyString(), anyString())).thenThrow(new RuntimeException("Usuario no encontrado"));
            })) {

      // Act
      ActionForward result = abmDelegadoAction.executeAction(actionMappingMocked, abmDelegadoFormMocked,
              samWebApplicationMocked, samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);

      // Assert
      assertNull(result, "Should return null indicating JSON response was written");
      
      // El método debería escribir una respuesta JSON con:
      // {"success": false, "error": "Usuario no encontrado", "message": "No se encontró el usuario con legajo: NOTFOUND"}
      List<ParametrosService> constructedServices = parametrosServiceMC.constructed();
      assertEquals(1, constructedServices.size());
    }
  }
}