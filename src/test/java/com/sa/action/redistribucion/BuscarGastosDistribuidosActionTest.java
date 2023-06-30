package com.sa.action.redistribucion;

import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.RendicionesService;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
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

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class BuscarGastosDistribuidosActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    BuscarGastosDistribuidosAction buscarGastosDistribuidosAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request, RendicionForm form, List<Gastos> gastos, PrintWriter printWriter) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.getGastosDistribuidos(any(),any(),any(),any())).thenReturn(gastos);
            when(response.getWriter()).thenReturn(printWriter);
        })) {
            ActionForward result = buscarGastosDistribuidosAction.executeAction(null, form, null, null, request, response);
            Assertions.assertEquals(null, result);
        }
    }
    
    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm form = new RendicionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        Rendicion rendicion = new Rendicion();
        List<Gastos> gastos = new ArrayList<>();
        Gastos gasto = new Gastos();
        PrintWriter printWriter = new PrintWriter(new ByteArrayOutputStream());

        rendicion.setId(1);
        rendicion.setCodMotivo("codMotivo");

        gasto.setIdGastoOriginal("idGasto");

        gastos.add(gasto);

        request.addParameter("gastoSeleccionado", "idGastoOriginal");

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking",usuario);
        session.setAttribute("rendicionSelectDerrame",rendicion);

        request.setHttpSession(session);

        return Stream.of(
                Arguments.of(request,form,gastos,printWriter)
        );
    }



}

