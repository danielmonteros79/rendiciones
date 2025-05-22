package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.ComboMotivo;
import com.sa.entities.CuadroGeneral;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CuadroGeneralFiltroActionTest {
    @Mock
    Log log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    CuadroGeneralFiltroAction cuadroGeneralFiltroAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,CuadroFiltroForm form, ActionMapping mapping,List<CuadroGeneral> rendicion ) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getCuadroGeneral(any(),any(),any(),any(),any(),any(),any(),any(),any())).thenReturn(rendicion);
            when(mockRendicionesService.getMsg()).thenReturn("msg");
        })) {
            ActionForward result = cuadroGeneralFiltroAction.executeAction(mapping,form, null, null,request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals(form.getComboGlg(),request.getAttribute("ComboGlg")),
                    () -> assertEquals(form.getComboMotivo(),request.getAttribute("ComboMotivo")),
                    () -> assertEquals("t",request.getAttribute("Tabla")),
                    () -> assertEquals("msg",request.getAttribute("message")),
                    () -> assertEquals(rendicion,request.getAttribute("Rendicion"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request,CuadroFiltroForm form, ActionMapping mapping,List<CuadroGeneral> rendicion ) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getCuadroGeneral(any(),any(),any(),any(),any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
        })) {
            ActionForward result = cuadroGeneralFiltroAction.executeAction(mapping,form, null, null,request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals(form.getComboGlg(),request.getAttribute("ComboGlg")),
                    () -> assertEquals(form.getComboMotivo(),request.getAttribute("ComboMotivo")),
                    () -> assertEquals("t",request.getAttribute("Tabla")),
                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                    () -> assertEquals(rendicion,request.getAttribute("Rendicion"))
            );
        }
    }


    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<CuadroGeneral> rendicion = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);

        request.setHttpSession(session);

        form.setComboGlg(new ArrayList<>());
        form.setComboMotivo(new ArrayList<>());
        form.setFechaDesde("01/01/2030");
        form.setFechaHasta("01/01/2030");
        form.setOpcion("opcion");
        form.setMontoDesde("montoDesde");
        form.setMontoHasta("montoHasta");
        form.setCodMotivo("codMotivo");
        form.setCodGlg("codGlg");
        form.setUsuario("usuario");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));


        return Stream.of(
                Arguments.of(request,form,mapping,rendicion)
        );
    }


}