package com.sa.action.rendiciones;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.ResumenService;
import com.sa.services.UsuarioService;
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

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class RendicionDetalleGastosActionTest {

  @Mock
  Usuario usuarioMocked;
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
  @Mock
  HttpServletRequest httpServletRequestMocked;
  @Mock
  HttpSession httpSessionMocked;
  @Mock
  ServletContext servletContextMocked;
  @InjectMocks
  RendicionDetalleGastosAction rendicionDetalleGastosAction;

  public static Stream<Arguments> executeActionHappyTrailSource() {
    //given
    String codigo = "0";

    Rendicion rendicion = new Rendicion();
    rendicion.setId(0);
    rendicion.setCodMotivo("0");
    rendicion.setFechaDesde(new Date());
    rendicion.setFechaHasta(new Date());
    rendicion.setMotivo("motivo");
    rendicion.setFechaUltimaModificacion("17/08/2023");
    rendicion.setAviso("aviso");
    rendicion.setMotivoRechazo("motivoRechazo");
    rendicion.setDescripcion("descripcion");
    rendicion.setEstado("estado");
    rendicion.setDescripcionEstado("descripcionEstado");
    rendicion.setUsuarioAprobador("usuarioAprobador");
    rendicion.setAdea("adea");
    rendicion.setIdu("idu");
    List<Rendicion> rendicionList = new ArrayList<>();
    rendicionList.add(rendicion);
    List<Rendicion> rendicionEmptyList = new ArrayList<>();

    ComboMotivo comboMotivo = new ComboMotivo();
    comboMotivo.setId("0");
    comboMotivo.setCostosDestino("0");
    List<ComboMotivo> comboMotivoList = new ArrayList<>();
    comboMotivoList.add(comboMotivo);
    List<ComboMotivo> comboMotivoEmptyList = new ArrayList<>();

    Gastos gastos = new Gastos();
    gastos.setFechagastos("");
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);
    List<Gastos> gastosEmptyList = new ArrayList<>();

    return Stream.of(
        Arguments.of(null, rendicionList, comboMotivoList, gastosList),
        Arguments.of(codigo, rendicionEmptyList, comboMotivoList, gastosList),
        Arguments.of(codigo, rendicionList, comboMotivoEmptyList, gastosList),
        Arguments.of(codigo, rendicionList, comboMotivoList, gastosEmptyList)
                    );
  }

  public static Stream<Arguments> executeActionIfElseBlockSource() {
    //given
    String actionGetRendicionGastos = "getRendicionGastos";
    String actionGetConsumosPendientes = "getConsumosPendientes";
    String actionActivarRechazar = "activarRechazar";
    String actionModificarRendicion = "modificarRendicion";
    String actionFinalizarObservacion = "finalizarObservacion";

    Gastos gastos = new Gastos();
    List<Gastos> gastosList = new ArrayList<>();
    gastosList.add(gastos);

    Resumen resumen = new Resumen();
    List<Resumen> resumenList = new ArrayList<>();
    resumenList.add(resumen);

    return Stream.of(
        Arguments.of(actionGetRendicionGastos, gastosList, resumenList),
        Arguments.of(actionGetConsumosPendientes, gastosList, resumenList),
        Arguments.of(actionActivarRechazar, gastosList, resumenList),
        Arguments.of(actionModificarRendicion, gastosList, resumenList),
        Arguments.of(actionFinalizarObservacion, gastosList, resumenList)
                    );
  }

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    Usuario usuario = new Usuario("0", "", "", 0, "", new ArrayList<>());
    usuario.setManejaFacultades(true);
    rendicionDetalleGastosAction.setSessionUserWorking(usuario);
  }

  /**
   * Method under test: {@link RendicionDetalleGastosAction#executeAction(ActionMapping, ActionForm, SAMWebApplication, SAMWebClient, HttpServletRequest, HttpServletResponse)}
   */
  @ParameterizedTest
  @MethodSource("executeActionHappyTrailSource")
  @DisplayName("Should execute action in happy trail")
  void shouldExecuteActionInHappyTrail(String codigo, List<Rendicion> rendicionList, List<ComboMotivo> comboMotivoList,
                                       List<Gastos> gastosList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("0");

    when(httpServletRequestMocked.getParameter("usuario")).thenReturn("");
    when(httpServletRequestMocked.getAttribute("codigo")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn(codigo);
    when(httpServletRequestMocked.getParameter("usuarioRendicion")).thenReturn("usuarioRendicion");

    when(rendicionFormMocked.getAviso()).thenReturn("aviso");
    when(rendicionFormMocked.getMotivoRechazo()).thenReturn("motivo");
    when(rendicionFormMocked.getUsuarioAprobador()).thenReturn("usuario");

    when(actionMappingMocked.findForward("rendicionDetalleGastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<UsuarioService> usuarioServiceMC = mockConstruction(UsuarioService.class, (mockUsuarioService, context) -> {
      when(mockUsuarioService.obtenerDelegadosUsuario(anyString())).thenReturn(usuarioMocked);
    })) {
      try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class,
          (mockRendicionesService, context) -> {
            when(mockRendicionesService.obtenerListadoRendiciones(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(
                rendicionList);
            when(mockRendicionesService.getMotivoRendiciones(anyString(), anyString(), anyString())).thenReturn(comboMotivoList);
            when(mockRendicionesService.getGastos(anyString(), anyString(), anyString(), anyString())).thenReturn(gastosList);
          })) {
        //then
        ActionForward actionForwardToAssert = rendicionDetalleGastosAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
            samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
        assertNotNull(actionForwardToAssert);
      }
    }
  }

  @Test
  @DisplayName("Should catch exception in happy trail")
  void shouldCatchExceptionInHappyTrail() throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn("");

    when(httpServletRequestMocked.getSession()).thenReturn(httpSessionMocked);
    when(httpSessionMocked.getServletContext()).thenReturn(servletContextMocked);
    when(servletContextMocked.getAttribute("rendicion.link.thuban")).thenReturn("0");

    when(httpServletRequestMocked.getParameter("usuario")).thenReturn("");
    when(httpServletRequestMocked.getAttribute("codigo")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("codigo")).thenReturn("0");
    when(httpServletRequestMocked.getParameter("usuarioRendicion")).thenReturn("usuarioRendicion");

    when(rendicionFormMocked.getAviso()).thenReturn("aviso");
    when(rendicionFormMocked.getMotivoRechazo()).thenReturn("motivo");
    when(rendicionFormMocked.getUsuarioAprobador()).thenReturn("usuario");

    when(actionMappingMocked.findForward("rendicionDetalleGastos")).thenReturn(actionForwardMocked);
    when(actionMappingMocked.findForward("gastos")).thenReturn(actionForwardMocked);

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<UsuarioService> usuarioServiceMC = mockConstruction(UsuarioService.class, (mockUsuarioService, context) -> {
      when(mockUsuarioService.obtenerDelegadosUsuario(anyString())).thenReturn(usuarioMocked);
    })) {
      //then
      ActionForward actionForwardToAssert = rendicionDetalleGastosAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
          samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
      assertNotNull(actionForwardToAssert);
    }
  }

  /*@ParameterizedTest
  @MethodSource("executeActionIfElseBlockSource")
  @DisplayName("Should execute action in If Else Block")
  void shouldExecuteActionInIfElseBlock(String action, List<Gastos> gastosList, List<Resumen> resumenList) throws Exception {
    //when
    when(httpServletRequestMocked.getParameter("action")).thenReturn(action);

    when(httpServletRequestMocked.getParameter("estadoRend")).thenReturn("PENDI");
    when(httpServletRequestMocked.getParameter("fechaDesde")).thenReturn("17/08/2023");
    when(httpServletRequestMocked.getParameter("fechaHasta")).thenReturn("17/08/2023");
    when(httpServletRequestMocked.getParameter("idRendicion")).thenReturn("");
    when(httpServletRequestMocked.getParameter("estado")).thenReturn("");
    when(httpServletRequestMocked.getParameter("codMotivo")).thenReturn("");
    when(httpServletRequestMocked.getParameter("descripcion")).thenReturn("");
    when(httpServletRequestMocked.getParameter("idu")).thenReturn("");

    when(actionMappingMocked.findForward(anyString())).thenReturn(actionForwardMocked);
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);

    try (MockedConstruction<RendicionesService> rendicionesServiceMC = mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
      when(mockRendicionesService.getGastos(anyString(), anyString(), anyString(), anyString())).thenReturn(gastosList);
      when(mockRendicionesService.getMsg()).thenReturn("msg");
    })) {
      try (MockedConstruction<ResumenService> resumenServiceMC = mockConstruction(ResumenService.class,
          (mockResumenService, context) -> {
            when(mockResumenService.getConsumos(anyString(), anyString(), anyString(), anyString())).thenReturn(resumenList);
            when(mockResumenService.getMsg()).thenReturn("msg");
          })) {
        try (MockedConstruction<AprobacionesService> aprobacionesServiceMC = mockConstruction(AprobacionesService.class,
            (mockAprobacionesService, context) -> {
              doNothing().when(mockAprobacionesService).cambiarEscanRendicion(anyString(), anyString(), anyString(), anyString());
              when(mockAprobacionesService.getMsg()).thenReturn("msg");
            })) {
          //then
          ActionForward actionForwardToAssert = rendicionDetalleGastosAction.executeAction(actionMappingMocked, rendicionFormMocked, samWebApplicationMocked,
              samWebClientMocked, httpServletRequestMocked, httpServletResponseMocked);
          if (action.equals("activarRechazar") || action.equals("modificarRendicion") || action.equals("finalizarObservacion")) {
            assertNull(actionForwardToAssert);
          } else {
            assertNotNull(actionForwardToAssert);
          }
        }
      }
    }
  }*/

  @Test
  @DisplayName("Should catch exception when rejecting")
  void shouldCatchExceptionWhenRejecting() throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    Method activarRechazarMocked = RendicionDetalleGastosAction.class.getDeclaredMethod("activarRechazar", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class);
    activarRechazarMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) activarRechazarMocked.invoke(rendicionDetalleGastosAction, samWebClientMocked, actionMappingMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should catch exception when modifying")
  void shouldCatchExceptionWhenModifying() throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    Method modificarRendicionMocked = RendicionDetalleGastosAction.class.getDeclaredMethod("modificarRendicion", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class);
    modificarRendicionMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) modificarRendicionMocked.invoke(rendicionDetalleGastosAction, samWebClientMocked, actionMappingMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }

  @Test
  @DisplayName("Should catch exception when finalizing observation")
  void shouldCatchExceptionWhenFinalizingObservation() throws Exception {
    //when
    when(httpServletResponseMocked.getWriter()).thenReturn(printWriterMocked);
    //then
    Method finalizarObservacionMocked = RendicionDetalleGastosAction.class.getDeclaredMethod("finalizarObservacion", SAMWebClient.class, ActionMapping.class, HttpServletRequest.class, HttpServletResponse.class);
    finalizarObservacionMocked.setAccessible(true);
    ActionForward actionForwardToAssert = (ActionForward) finalizarObservacionMocked.invoke(rendicionDetalleGastosAction, samWebClientMocked, actionMappingMocked, httpServletRequestMocked, httpServletResponseMocked);
    assertNull(actionForwardToAssert);
  }
}
