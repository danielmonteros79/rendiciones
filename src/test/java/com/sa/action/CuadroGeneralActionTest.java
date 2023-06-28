package com.sa.action;

import com.sa.entities.ComboMotivo;
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

class CuadroGeneralActionTest {
    @Mock
    Log log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    CuadroGeneralAction cuadroGeneralAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request,CuadroFiltroForm form, ActionMapping mapping,List<ComboMotivo> motivo  ) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            ActionForward result = cuadroGeneralAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals(motivo,request.getAttribute("ComboMotivo"))
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        CuadroFiltroForm form = new CuadroFiltroForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();
        List<ComboMotivo> motivo = new ArrayList<>();

        session.setAttribute("userWorking", usuario);

        request.setHttpSession(session);

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,form,mapping, motivo)
        );
    }




}

