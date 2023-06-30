package com.sa.action.redistribucion;

import com.sa.entities.ComboGasto;
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

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParamsDistribucionGastosActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    HttpServletResponse response;
    @InjectMocks
    ParamsDistribucionGastosAction paramsDistribucionGastosAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request, RendicionForm form,List<ComboGasto> tipoGastos,PrintWriter writer) throws Exception {
        try (MockedConstruction<PagosService> pagosServiceMC = Mockito.mockConstruction(PagosService.class, (mockPagosService, context) -> {
            when(mockPagosService.getComboGasto(any(),any(),any())).thenReturn(tipoGastos);
            when(response.getWriter()).thenReturn(writer);
        })) {
            ActionForward result = paramsDistribucionGastosAction.executeAction(null,form, null, null, request, response);
            assertAll(
                    () -> assertEquals(tipoGastos,request.getAttribute("ComboGastos")),
                    () -> assertNull(result)
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm form = new RendicionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<ComboGasto> tipoGastos = new ArrayList<>();
        PrintWriter writer = new PrintWriter(new ByteArrayOutputStream());

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);

        request.setHttpSession(session);
        request.addParameter("codMotivo", "1");


        return Stream.of(
                Arguments.of(request, form, tipoGastos,writer)
        );
    }


}

