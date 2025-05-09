package com.sa.action.parametros;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.ParametroMotivo;
import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import com.sa.services.ParametrosService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.mock.MockServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParametrosMotivoLoadActionTest {

  @Mock
  HttpServletResponse httpServletResponse;
  @Mock
  ActionMapping actionMappingMock;
  @Mock
  HttpServletRequest requestMock;
  @Mock
  SAMWebApplication samWebApplicationMock;
  @Mock
  SAMWebClient samWebClientMock;
  @Mock
  ParametrosMotivoFiltroForm formMock;

  @InjectMocks
  ParametrosMotivoLoadAction parametrosMotivoLoadAction;

  private Usuario usuarioMock;

  public static Stream<Arguments> executeActionSource() {
    //given
    ActionMapping actionMapping = new ActionMapping();
    SAMWebApplication samWebApplication = new SAMWebApplication();
    HttpSession httpSession = new MockHttpSession();
    SAMWebClient samWebClient = new SAMWebClient();
    MockHttpServletRequest request = new MockHttpServletRequest();
    List<Usuario> delegados = new ArrayList<>();
    ServletContext servletContext = new MockServletContext();

    ParametrosMotivoFiltroForm parametrosMotivoFiltroForm = new ParametrosMotivoFiltroForm();
    parametrosMotivoFiltroForm.setCodigo("11");

    ParametroMotivo parametroMotivo = new ParametroMotivo();

    List<ParametroMotivo> parametroMotivoList = new ArrayList<>();
    parametroMotivoList.add(parametroMotivo);

    Usuario usuario2 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    Usuario usuario3 = new Usuario("55", "2", "", 77, "2c", new ArrayList<>());
    delegados.add(usuario2);
    delegados.add(usuario3);
    Usuario usuario = new Usuario("55", "2", "Luis Machado", 77, "2c", delegados);

    httpSession.setAttribute("usuario", usuario);

    request.setHttpSession(httpSession);

    actionMapping.addForwardConfig(new ActionForward("success", "path1", false));

    samWebClient.setSession(httpSession);
    samWebClient.setLoginOk(true);
    samWebClient.setId("55");
    samWebClient.setAttribute("usuario", usuario);

    samWebApplication.setContext(servletContext);
    samWebApplication.setClientClass("");
    samWebApplication.setAttribute("usuario", usuario);

    return Stream.of(
        Arguments.of(actionMapping, samWebApplication, samWebClient, request, parametrosMotivoFiltroForm, usuario, parametroMotivoList)
                    );
  }

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);

    usuarioMock = new Usuario("1234", "SS", "PEPE", 1212, "", null);
    parametrosMotivoLoadAction.setSessionUser(usuarioMock);
    parametrosMotivoLoadAction.setSessionUserWorking(usuarioMock);

  }

  /*@ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should determine what action perform")
  void shouldDetermineWhatActionPerform(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                                        ParametrosMotivoFiltroForm parametrosMotivoFiltroForm, Usuario usuario, List<ParametroMotivo> parametroMotivoList) throws Exception {
    //when
    try (MockedConstruction<ParametrosService> parametrosServiceMC = Mockito.mockConstruction(ParametrosService.class,
        (mockParametrosService, context) -> {
          when(mockParametrosService.getMotivos(parametrosMotivoFiltroForm.getCodigo(), usuario.getIdUser())).thenReturn(parametroMotivoList);
          when(mockParametrosService.getMsgAviso()).thenReturn("Message");
        })) {
      //then
      ActionForward actionForwardToAssert = parametrosMotivoLoadAction.executeAction(actionMapping, parametrosMotivoFiltroForm, samApplication, samClient, request,
          httpServletResponse);
      assertNotNull(actionForwardToAssert);
    }
  }*/

  @ParameterizedTest
  @MethodSource("executeActionSource")
  @DisplayName("Should catch exception")
  void shouldCatchException(ActionMapping actionMapping, SAMWebApplication samApplication, SAMWebClient samClient, MockHttpServletRequest request,
                            ParametrosMotivoFiltroForm parametrosMotivoFiltroForm, Usuario usuario, List<ParametroMotivo> parametroMotivoList) throws Exception {
    //then
    ActionForward actionForwardToAssert = parametrosMotivoLoadAction.executeAction(actionMapping, parametrosMotivoFiltroForm, samApplication, samClient, request,
        httpServletResponse);
    assertNotNull(actionForwardToAssert);
  }
  
  @Test
  @DisplayName("Debe ejecutar action normalmente con número")
  void executeAction_filtrarPorNumero() throws Exception {

      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("11");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      List<ParametroMotivo> motivos = new ArrayList<>();
      ParametroMotivo motivo = new ParametroMotivo();
      motivo.setCodigo("0011");
      motivo.setDescripcion("Descripción test");
      motivo.setCodSup("PSUP");
      motivo.setCodAprobacionGlg("MONTO");
      motivo.setCodFirma("MONTO");
      motivo.setEstado("A");
      motivo.setLastElement("S");
      motivos.add(motivo);

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
          when(serviceMock.getMsgAviso()).thenReturn("Mensaje exitoso");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);

          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }
  
  @Test
  @DisplayName("Debe ejecutar action filtrando por texto")
  void executeAction_filtrarPorTexto() throws Exception {

      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("test");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      List<ParametroMotivo> motivos = new ArrayList<>();
      ParametroMotivo motivo = new ParametroMotivo();
      motivo.setCodigo("TEST01");
      motivo.setDescripcion("Una descripcion con test");
      motivo.setCodSup("SUPER");
      motivo.setCodAprobacionGlg("PGLG");
      motivo.setCodFirma("PFIRM");
      motivo.setEstado("I");
      motivo.setLastElement("S");
      motivos.add(motivo);

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
          when(serviceMock.getMsgAviso()).thenReturn("Mensaje exitoso");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);

          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }
  
  @Test
  @DisplayName("Debe ejecutar action sin código enviado")
  void executeAction_sinCodigo() throws Exception {
	  
      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      List<ParametroMotivo> motivos = new ArrayList<>();
      ParametroMotivo motivo = new ParametroMotivo();
      motivo.setCodigo("0002");
      motivo.setDescripcion("Test sin codigo");
      motivo.setCodSup("PSUP");
      motivo.setCodAprobacionGlg("MONTO");
      motivo.setCodFirma("PFIRM");
      motivo.setEstado("A");
      motivo.setLastElement("S");
      motivos.add(motivo);

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
          when(serviceMock.getMsgAviso()).thenReturn("OK");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);

          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }
  
  @Test
  @DisplayName("Debe capturar excepción y devolver success")
  void executeAction_capturaExcepcion() throws Exception {
	  StringWriter stringWriter = new StringWriter();
	  PrintWriter printWriter = new PrintWriter(stringWriter);
	  when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));
      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(actionMappingMock.findForward("success")).thenReturn(new ActionForward("success", "/pathSuccess", false));

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Error esperado"));
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);

          assertNull(forward);
      }
  }
  
  @Test
  @DisplayName("Debe detenerse si getMotivos devuelve vacío")
  void executeAction_sinMotivosDevueltos() throws Exception {
      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("11");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(Collections.emptyList());
          when(serviceMock.getMsgAviso()).thenReturn("Mensaje sin motivos");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);
          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }
  
  @Test
  @DisplayName("Debe detener búsqueda si texto no coincide con ningún motivo")
  void executeAction_textoSinCoincidencia() throws Exception {
      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("nope");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      List<ParametroMotivo> motivos = new ArrayList<>();
      ParametroMotivo motivo = new ParametroMotivo();
      motivo.setCodigo("AAA");  // no contiene "nope"
      motivo.setDescripcion("Algo irrelevante");
      motivo.setLastElement("S");
      motivos.add(motivo);

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
          when(serviceMock.getMsgAviso()).thenReturn("Mensaje sin coincidencias");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);
          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }

  @Test
  @DisplayName("Debe mantener valores si no coinciden con condiciones de transformación")
  void executeAction_valoresNoTransformados() throws Exception {
      when(requestMock.getParameter("action")).thenReturn("filtrar");
      when(requestMock.getParameter("codigo")).thenReturn("9999");
      when(actionMappingMock.findForward("parametrosMotivoFiltro")).thenReturn(new ActionForward("parametrosMotivoFiltro", "/path", false));

      List<ParametroMotivo> motivos = new ArrayList<>();
      ParametroMotivo motivo = new ParametroMotivo();
      motivo.setCodigo("9999");
      motivo.setDescripcion("Otra desc");
      motivo.setCodSup("ABC");
      motivo.setCodAprobacionGlg("XYZ");
      motivo.setCodFirma("QWE");
      motivo.setEstado("Z");
      motivo.setLastElement("S");
      motivos.add(motivo);

      try (MockedConstruction<ParametrosService> mock = mockConstruction(ParametrosService.class, (serviceMock, context) -> {
          when(serviceMock.getMotivos(anyString(), anyString(), anyString())).thenReturn(motivos);
          when(serviceMock.getMsgAviso()).thenReturn("Sin transformaciones");
      })) {
          ActionForward forward = parametrosMotivoLoadAction.executeAction(actionMappingMock, formMock, samWebApplicationMock, samWebClientMock, requestMock, httpServletResponse);
          assertNotNull(forward);
          assertEquals("parametrosMotivoFiltro", forward.getName());
      }
  }


}
