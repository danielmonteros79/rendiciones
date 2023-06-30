package com.sa.action;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Usuario;
import com.sa.services.RendicionesService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MotivoSuspensionActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    MotivoSuspensionAction motivoSuspensionAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(HttpServletRequest request, ActionMapping mapping, List<ComboMotivo> motivo) throws Exception {
        try(MockedConstruction<RendicionesService> mock = Mockito.mockConstruction(RendicionesService.class, (mockM, context) -> {
            when(mockM.getMotivoRendiciones(any(),any())).thenReturn(motivo);
        })) {
            ActionForward result = motivoSuspensionAction.executeAction(mapping, null, null, null, request, null);
            assertAll(
                    () -> assertNotNull(result),
                    () -> Assertions.assertEquals("success", result.getName())
            );
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","name", "lastname", 1, "password", new ArrayList<>());
        ActionMapping mapping = new ActionMapping();
        List<ComboMotivo> motivo = new ArrayList<>();

        session.setAttribute("idRendicion", 1);
        session.setAttribute("userWorking", usuario);

        request.setHttpSession(session);

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
            Arguments.of(request,mapping,motivo)
        );
    }


}