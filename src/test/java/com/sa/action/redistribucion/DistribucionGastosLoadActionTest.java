package com.sa.action.redistribucion;

import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.PagosService;
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

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistribucionGastosLoadActionTest {
    @Mock
    Logger log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @InjectMocks
    DistribucionGastosLoadAction distribucionGastosLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(MockHttpServletRequest request, RendicionForm form, ActionMapping mapping, List<Rendicion> rendiciones, List<ComboMotivo> motivo,List<Gastos> gastos) throws Exception {
        try (MockedConstruction<RendicionesService> rendicionesServiceMC = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context) -> {
            when(mockRendicionesService.obtenerListadoRendiciones(any(),any(),any(),any(),any())).thenReturn(rendiciones);
            when(mockRendicionesService.getMotivoRendiciones(any(),any())).thenReturn(motivo);
            when(mockRendicionesService.getGastos(any(),any(),any(),any())).thenReturn(gastos);
        })) {
            ActionForward result = distribucionGastosLoadAction.executeAction(mapping,form, null, null, request, null);
            assertAll(
                    () -> assertEquals(gastos,request.getAttribute("Gastos")),
                    () -> assertEquals(1,request.getAttribute("idRendicion")),
                    () -> assertEquals("descripcion",request.getAttribute("descripcionMotivo")),
                    () -> assertEquals("1",request.getAttribute("codMotivo")),
                    () -> assertEquals("success",result.getName()),
                    () -> assertNotNull(request.getSession().getAttribute("rendicionSelectDerrame"))
            );
        }
    }

    @ParameterizedTest
    @MethodSource("daysBetweenSource")
    @DisplayName("Testeando daysBetween")
    void daysBetween(Date d1, Date d2, int res) {
        int result = distribucionGastosLoadAction.daysBetween(d1,d2);
        assertEquals(res, result);
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        RendicionForm form = new RendicionForm();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        Usuario usuario = new Usuario("id","perfil", "nombre", 1, "sector", new ArrayList<>());
        List<Rendicion> rendiciones = new ArrayList<>();
        Rendicion rendicion = new Rendicion();
        List<ComboMotivo> motivoList = new ArrayList<>();
        ComboMotivo motivo = new ComboMotivo();
        List<Gastos> gastos = new ArrayList<>();
        ActionMapping mapping = new ActionMapping();

        session.setAttribute("usuario", usuario);
        session.setAttribute("userWorking", usuario);

        rendicion.setId(1);
        rendicion.setCodMotivo("1");

        rendiciones.add(rendicion);

        motivo.setId("1");
        motivo.setDescripcion("descripcion");

        motivoList.add(motivo);

        request.setHttpSession(session);
        request.addParameter("idRendicion","1");
        request.addParameter("usuarioRendicion","usuarioRendicion");

        mapping.addForwardConfig(new ActionForward("success", "path1", false));

        return Stream.of(
                Arguments.of(request,form,mapping,rendiciones,motivoList,gastos)
        );
    }

    private static Stream<Arguments> daysBetweenSource(){
        Date d1 = new GregorianCalendar(2023, Calendar.JUNE, 30, 10, 15).getTime();
        Date d2 = new GregorianCalendar(2023, Calendar.JUNE, 30, 10, 15).getTime();
        int result = 0;

        Date d3 = new GregorianCalendar(2023, Calendar.JUNE, 30, 10, 15).getTime();
        Date d4 = new GregorianCalendar(2023, Calendar.JUNE, 31, 10, 15).getTime();
        int result2 = 1;

        int result3 = -1;


        return Stream.of(
                Arguments.of(d1,d2,result),
                Arguments.of(d3,d4,result2),
                Arguments.of(d4,d3,result3)
        );
    }

}

