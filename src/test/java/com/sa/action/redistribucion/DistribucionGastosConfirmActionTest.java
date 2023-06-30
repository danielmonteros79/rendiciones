package com.sa.action.redistribucion;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class DistribucionGastosConfirmActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    DistribucionGastosConfirmAction distribucionGastosConfirmAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, RendicionForm form, PrintWriter writer) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            when(mockPagosService.redistribuirGastos(any(),any(),any(),any(),any(),any(),any())).thenReturn("msg");
            when(response.getWriter()).thenReturn(writer);
        })) {
            ActionForward result = distribucionGastosConfirmAction.executeAction(null, form, null, null, request, response);
            Assertions.assertEquals(null, result);
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, RendicionForm form, PrintWriter writer) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            when(mockPagosService.redistribuirGastos(any(),any(),any(),any(),any(),any(),any())).thenThrow(new TransactionException("TransactionException",new Throwable("TransactionException")));
            when(response.getWriter()).thenReturn(writer);
        })) {
            ActionForward result = distribucionGastosConfirmAction.executeAction(null, form, null, null, request, response);
            Assertions.assertEquals(null, result);
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm form = new RendicionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        PrintWriter writer = new PrintWriter(new ByteArrayOutputStream());

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);

        request.setHttpSession(session);
        request.addParameter("accion", "accion");
        request.addParameter("gastoRedistribucion", "gastoRedistribucion");
        request.addParameter("idRendicion", "idRendicion");
        request.addParameter("montoGastoItems", "montoGastoItems");
        request.addParameter("centroCostoItems", "centroCostoItems");
        request.addParameter("codGastoRedistribucion", "codGastoRedistribucion");


        return Stream.of(
                Arguments.of(request,form,writer)
        );
    }



}

