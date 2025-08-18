package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.CuadroFiltroForm;
import com.sa.form.FiltrarAprobacionForm;
import com.sa.services.RendicionesService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpSession;
import org.apache.struts.util.TokenProcessor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class CuadroGeneralLoadActionTest {
    @Mock
    Log log;
    @Mock
    Map<String, List<ComboMotivo>> mapGlgMotivos;
    @Mock
    List<ComboMotivo> cmbMotivo;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    ar.com.bbva.web.impl.SAMWebApplication samWebApplication;
    @InjectMocks
    CuadroGeneralLoadAction cuadroGeneralLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request,CuadroFiltroForm form, ActionMapping mapping, List<ComboMotivo> motivo,Map<String, List<ComboMotivo>> mapGlgMotivos,List<ComboOpcion> comboOpcion, PrintWriter writer) throws Exception {
        // Configure the response mock to return the writer
        when(response.getWriter()).thenReturn(writer);
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenReturn(motivo);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenReturn(comboOpcion);
        })) {

            // Fields are now local variables - no need for reflection

            ActionForward result = cuadroGeneralLoadAction.executeAction(mapping, form, samWebApplication, samWebClient, request,response);
            if(request.getParameter("accion").equals("selectGlg")){
                assertEquals(null, result);
            } else {
                assertAll(
                        () -> assertEquals("success",result.getName()),
                        () -> assertEquals("msg<br>msg",request.getAttribute("message")),
                        () -> assertEquals(motivo,request.getAttribute("ComboMotivo")),
                        () -> assertEquals(form.getComboGlg(),request.getAttribute("ComboGlg")),
                        () -> assertEquals("f",request.getAttribute("Tabla"))
                );
            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(MockHttpServletRequest request,CuadroFiltroForm form, ActionMapping mapping, List<ComboMotivo> motivo,Map<String, List<ComboMotivo>> mapGlgMotivos,List<ComboOpcion> comboOpcion, PrintWriter writer) throws Exception {
        // Configure the response mock to return the writer
        when(response.getWriter()).thenReturn(writer);
        
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenReturn(motivo);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {

            // Fields are now local variables - no need for reflection

            ActionForward result = cuadroGeneralLoadAction.executeAction(mapping, form, samWebApplication, samWebClient, request,response);
            if(request.getParameter("accion").equals("selectGlg")){
                assertEquals(null, result);
            } else {
                assertAll(
                        () -> assertEquals("success",result.getName()),
                        () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                        () -> assertEquals(motivo,request.getAttribute("ComboMotivo")),
                        () -> assertEquals(form.getComboGlg(),request.getAttribute("ComboGlg")),
                        () -> assertEquals("f",request.getAttribute("Tabla"))
                );
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        MockHttpServletRequest request3 = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<ComboMotivo> motivo = new ArrayList<>();
        Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
        List<ComboOpcion> comboOpcion = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();
        PrintWriter writer = new PrintWriter(new ByteArrayOutputStream());

        session.setAttribute("userWorking", usuario);
        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);
        request.addParameter("accion","selectGlg");
        request.addParameter("codGlg",null);

        request2.setHttpSession(session);
        request2.addParameter("accion","selectGlg");
        request2.addParameter("codGlg","codGlg");

        request3.setHttpSession(session);
        request3.addParameter("accion","accion");
        request3.addParameter("codGlg",null);

        ComboMotivo comboMotivo = new ComboMotivo();
        comboMotivo.setDescripcion("2345678");

        motivo.add(comboMotivo);

        mapGlgMotivos.put("codGlg",motivo);


        mapping.addForwardConfig(new ActionForward("success", "path1", false));


        return Stream.of(
                Arguments.of(request,form,mapping,motivo,mapGlgMotivos,comboOpcion,writer), // Retorna null
                Arguments.of(request2,form,mapping,motivo,mapGlgMotivos,comboOpcion,writer),
                Arguments.of(request3,form,mapping,motivo,mapGlgMotivos,comboOpcion,writer)
        );
    }

    // ------ Tests for new refactored methods ------

    @Test
    @DisplayName("Test transformMotivosList method")
    void testTransformMotivosList() throws Exception {
        // given
        List<ComboMotivo> motivos = new ArrayList<>();
        ComboMotivo motivo1 = new ComboMotivo();
        motivo1.setDescripcion("Test1");
        ComboMotivo motivo2 = new ComboMotivo();
        motivo2.setDescripcion("Test2");
        motivos.add(motivo1);
        motivos.add(motivo2);

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("transformMotivosList", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) method.invoke(cuadroGeneralLoadAction, motivos);

        // then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("3Test1", result.get(0)),
                () -> assertEquals("3Test2", result.get(1))
        );
    }

    @Test
    @DisplayName("Test transformMotivosList method with null descriptions")
    void testTransformMotivosListWithNullDescriptions() throws Exception {
        // given
        List<ComboMotivo> motivos = new ArrayList<>();
        ComboMotivo motivo1 = new ComboMotivo();
        motivo1.setDescripcion("ValidDescription");
        ComboMotivo motivo2 = new ComboMotivo();
        motivo2.setDescripcion(null); // null description should be filtered out
        ComboMotivo motivo3 = new ComboMotivo();
        motivo3.setDescripcion("AnotherValid");
        motivos.add(motivo1);
        motivos.add(motivo2);
        motivos.add(motivo3);

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("transformMotivosList", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) method.invoke(cuadroGeneralLoadAction, motivos);

        // then
        assertAll(
                () -> assertEquals(2, result.size()), // Only non-null descriptions should be included
                () -> assertEquals("3ValidDescription", result.get(0)),
                () -> assertEquals("3AnotherValid", result.get(1))
        );
    }

    @Test
    @DisplayName("Test buildMessageFromService method")
    void testBuildMessageFromService() throws Exception {
        // given
        RendicionesService service = mock(RendicionesService.class);
        when(service.getMsg()).thenReturn("Service message");
        String initialMessage = "Initial: ";

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("buildMessageFromService", RendicionesService.class, String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(cuadroGeneralLoadAction, service, initialMessage);

        // then
        assertEquals("Initial: Service message<br>", result);
    }

    @Test
    @DisplayName("Test buildMessageFromService with null message")
    void testBuildMessageFromServiceWithNull() throws Exception {
        // given
        RendicionesService service = mock(RendicionesService.class);
        when(service.getMsg()).thenReturn(null);
        String initialMessage = "Initial: ";

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("buildMessageFromService", RendicionesService.class, String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(cuadroGeneralLoadAction, service, initialMessage);

        // then
        assertEquals("Initial: ", result);
    }

    @Test
    @DisplayName("Test appendServiceMessage method")
    void testAppendServiceMessage() throws Exception {
        // given
        RendicionesService service = mock(RendicionesService.class);
        when(service.getMsg()).thenReturn("Additional message");
        String message = "Current message";

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("appendServiceMessage", RendicionesService.class, String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(cuadroGeneralLoadAction, service, message);

        // then
        assertEquals("Current messageAdditional message", result);
    }

    @Test
    @DisplayName("Test isEmptyOrNull method")
    void testIsEmptyOrNull() throws Exception {
        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("isEmptyOrNull", String.class);
        method.setAccessible(true);

        // then
        assertAll(
                () -> assertEquals(true, (Boolean) method.invoke(cuadroGeneralLoadAction, (String) null)),
                () -> assertEquals(true, (Boolean) method.invoke(cuadroGeneralLoadAction, "")),
                () -> assertEquals(true, (Boolean) method.invoke(cuadroGeneralLoadAction, "   ")),
                () -> assertEquals(false, (Boolean) method.invoke(cuadroGeneralLoadAction, "value"))
        );
    }

    @Test
    @DisplayName("Test createJsonObject method")
    void testCreateJsonObject() throws Exception {
        // given
        ComboMotivo motivo = new ComboMotivo();
        motivo.setId("123");
        motivo.setDescripcion("Test Description");

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("createJsonObject", ComboMotivo.class);
        method.setAccessible(true);
        net.sf.json.JSONObject result = (net.sf.json.JSONObject) method.invoke(cuadroGeneralLoadAction, motivo);

        // then
        assertAll(
                () -> assertEquals("123", result.get("codigo")),
                () -> assertEquals("Test Description", result.get("descripcion"))
        );
    }

    @Test
    @DisplayName("Test populateGlgMotivos method")
    void testPopulateGlgMotivos() throws Exception {
        // given
        List<String> motivos = new ArrayList<>();
        motivos.add("2MOT1Test Description");
        motivos.add("1MOT2Another Description"); // Should not be processed (codGlg != "2")
        motivos.add("2MOT3Third Description");

        Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
        List<ComboMotivo> cmbMotivo = new ArrayList<>();

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("populateGlgMotivos", 
                List.class, Map.class, List.class);
        method.setAccessible(true);
        method.invoke(cuadroGeneralLoadAction, motivos, mapGlgMotivos, cmbMotivo);

        // then
        assertAll(
                () -> assertEquals(2, cmbMotivo.size()),
                () -> assertEquals("MOT1", cmbMotivo.get(0).getId()),
                () -> assertEquals("MOT3", cmbMotivo.get(1).getId()),
                () -> assertEquals(1, mapGlgMotivos.size()),
                () -> assertEquals(2, mapGlgMotivos.get("2").size())
        );
    }

    @Test
    @DisplayName("Test addToGlgMotivoMap method with new key")
    void testAddToGlgMotivoMapNewKey() throws Exception {
        // given
        Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
        ComboMotivo motivo = new ComboMotivo();
        motivo.setId("TEST");
        motivo.setDescripcion("Test Description");

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("addToGlgMotivoMap", 
                Map.class, String.class, ComboMotivo.class);
        method.setAccessible(true);
        method.invoke(cuadroGeneralLoadAction, mapGlgMotivos, "2", motivo);

        // then
        assertAll(
                () -> assertEquals(1, mapGlgMotivos.size()),
                () -> assertEquals(1, mapGlgMotivos.get("2").size()),
                () -> assertEquals("TEST", mapGlgMotivos.get("2").get(0).getId())
        );
    }

    @Test
    @DisplayName("Test addToGlgMotivoMap method with existing key")
    void testAddToGlgMotivoMapExistingKey() throws Exception {
        // given
        Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<>();
        List<ComboMotivo> existingList = new ArrayList<>();
        ComboMotivo existingMotivo = new ComboMotivo();
        existingMotivo.setId("EXISTING");
        existingList.add(existingMotivo);
        mapGlgMotivos.put("2", existingList);

        ComboMotivo newMotivo = new ComboMotivo();
        newMotivo.setId("NEW");
        newMotivo.setDescripcion("New Description");

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("addToGlgMotivoMap", 
                Map.class, String.class, ComboMotivo.class);
        method.setAccessible(true);
        method.invoke(cuadroGeneralLoadAction, mapGlgMotivos, "2", newMotivo);

        // then
        assertAll(
                () -> assertEquals(1, mapGlgMotivos.size()),
                () -> assertEquals(2, mapGlgMotivos.get("2").size()),
                () -> assertEquals("EXISTING", mapGlgMotivos.get("2").get(0).getId()),
                () -> assertEquals("NEW", mapGlgMotivos.get("2").get(1).getId())
        );
    }

    @Test
    @DisplayName("Test setRequestAttributes method")
    void testSetRequestAttributes() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        List<ComboMotivo> motivos = new ArrayList<>();
        CuadroFiltroForm form = new CuadroFiltroForm();
        List<ComboOpcion> comboGlg = new ArrayList<>();
        form.setComboGlg(comboGlg);

        // when
        Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("setRequestAttributes", 
                HttpServletRequest.class, List.class, CuadroFiltroForm.class);
        method.setAccessible(true);
        method.invoke(cuadroGeneralLoadAction, request, motivos, form);

        // then
        assertAll(
                () -> assertEquals(motivos, request.getAttribute("ComboMotivo")),
                () -> assertEquals(comboGlg, request.getAttribute("ComboGlg")),
                () -> assertEquals("f", request.getAttribute("Tabla"))
        );
    }

    @Test
    @DisplayName("Test handleSelectGlgAction method")
    void testHandleSelectGlgAction() throws Exception {
        // given
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id", "perfil", "nombre", 1, "sector", new ArrayList<>());
        session.setAttribute("userWorking", usuario);
        request.setHttpSession(session);
        request.addParameter("codGlg", "2");
        
        SAMWebClient samClient = mock(SAMWebClient.class);

        try (MockedConstruction<RendicionesService> serviceMock = Mockito.mockConstruction(RendicionesService.class, 
                (mockService, context) -> {
                    when(mockService.getMotivoRendiciones(any(), any(), any())).thenReturn(new ArrayList<>());
                })) {

            // when
            Method method = CuadroGeneralLoadAction.class.getDeclaredMethod("handleSelectGlgAction", 
                    HttpServletResponse.class, HttpServletRequest.class, SAMWebClient.class);
            method.setAccessible(true);
            ActionForward result = (ActionForward) method.invoke(cuadroGeneralLoadAction, response, request, samClient);

            // then
            assertAll(
                    () -> assertEquals(null, result),
                    () -> verify(response).setContentType("application/json"),
                    () -> verify(response.getWriter()).flush(),
                    () -> verify(response.getWriter()).close()
            );
        }
    }
}

