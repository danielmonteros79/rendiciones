package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
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

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
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
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(response.getWriter()).thenReturn(writer);
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenReturn(motivo);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenReturn(comboOpcion);
        })) {

            Field cmbMotivo = CuadroGeneralLoadAction.class.getDeclaredField("cmbMotivo");
            cmbMotivo.setAccessible(true);
            cmbMotivo.set(cuadroGeneralLoadAction, motivo);

            Field mapGlgMotivos1 = CuadroGeneralLoadAction.class.getDeclaredField("mapGlgMotivos");
            mapGlgMotivos1.setAccessible(true);
            mapGlgMotivos1.set(cuadroGeneralLoadAction, mapGlgMotivos);


            ActionForward result = cuadroGeneralLoadAction.executeAction(mapping, form, null, null, request,response);
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
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(response.getWriter()).thenReturn(writer);
            when(mockRendicionesService.getMotivoRendiciones(any(),any(), any())).thenReturn(motivo);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
            when(mockRendicionesService.getGlgsUsuario(any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {

            Field cmbMotivo = CuadroGeneralLoadAction.class.getDeclaredField("cmbMotivo");
            cmbMotivo.setAccessible(true);
            cmbMotivo.set(cuadroGeneralLoadAction, motivo);

            Field mapGlgMotivos1 = CuadroGeneralLoadAction.class.getDeclaredField("mapGlgMotivos");
            mapGlgMotivos1.setAccessible(true);
            mapGlgMotivos1.set(cuadroGeneralLoadAction, mapGlgMotivos);


            ActionForward result = cuadroGeneralLoadAction.executeAction(mapping, form, null, null, request,response);
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
        Map<String, List<ComboMotivo>> mapGlgMotivos = new HashMap<String, List<ComboMotivo>>();
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
}

