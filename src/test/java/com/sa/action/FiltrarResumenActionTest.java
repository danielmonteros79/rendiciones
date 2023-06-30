package com.sa.action;

import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Usuario;
import com.sa.entities.parametros.Resumen;
import com.sa.form.ResumenForm;
import com.sa.services.ResumenService;
import org.apache.log4j.Logger;
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

class FiltrarResumenActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    FiltrarResumenAction filtrarResumenAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, ActionMapping mapping, ResumenForm form,List<Resumen> resumenes) throws Exception {
        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
            when(mockM.getResumenes(any(),any())).thenReturn(resumenes);
            when(mockM.getMsg()).thenReturn("msg");
        })) {
            ActionForward result = filtrarResumenAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("msg",request.getAttribute("message")),
                    () -> assertEquals(form.getCmbResumen(),request.getAttribute("cmbResumen")),
                    () -> assertEquals(resumenes,request.getAttribute("resumenes"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action exception")
    void executeActionException(HttpServletRequest request, ActionMapping mapping, ResumenForm form,List<Resumen> resumenes) throws Exception {
        try(MockedConstruction<ResumenService> mock = Mockito.mockConstruction(ResumenService.class, (mockM, context) -> {
            when(mockM.getResumenes(any(),any())).thenThrow(new TransactionException("TransactionException", new Throwable("TransactionException")));
        })) {
            ActionForward result = filtrarResumenAction.executeAction(mapping, form, null, null, request, null);
            assertAll(
                    () -> assertEquals("success",result.getName()),
                    () -> assertEquals("ERROR: TransactionException",request.getAttribute("message")),
                    () -> assertEquals(form.getCmbResumen(),request.getAttribute("cmbResumen")),
                    () -> assertEquals(resumenes,request.getAttribute("resumenes"))
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        ResumenForm form = new ResumenForm();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<Resumen> resumenes = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("userWorking", usuario);
        request.setHttpSession(session);

        form.setResumen("resumen");
        form.setCmbResumen(new ArrayList<>());

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
            Arguments.of(request,mapping, form, resumenes)
        );
    }


}