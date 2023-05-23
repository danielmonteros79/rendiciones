package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.ComboMotivo;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import com.sa.services.AprobacionesService;
import com.sa.services.RendicionesService;
import com.sa.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.struts.action.*;
import org.apache.struts.util.TokenProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AprobacionDetalleLoadActionTest {
    @Mock
    Log log;
    @Mock
    TokenProcessor token;
    @Mock
    ActionServlet servlet;
    @Mock
    ActionMapping actionMapping;
    @Mock
    RendicionForm rendicionForm;
    @Mock
    SAMWebApplication samWebApplication;
    @Mock
    SAMWebClient samWebClient;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpServletResponse httpServletResponse;
    @Mock
    HttpSession httpSession;
    @Mock
    ServletContext servletContext;
    @InjectMocks
    AprobacionDetalleLoadAction aprobacionDetalleLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("executeActionSource")
    @DisplayName("Testeando execute action")
    void executeAction(Usuario user, Usuario u, String usuarioRend, String hide, String codigo, List<Rendicion> rendiciones, String msg, List<Gastos> gastos, Usuario usuarioRendicion, String msgUsuarioService, String glg, List<ComboMotivo> motivo, String thuban, ActionForward ret) throws Exception {

        doNothing().when(rendicionForm).reset(any(),any());
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("usuario")).thenReturn(user);
        when(httpSession.getAttribute("userWorking")).thenReturn(u);
        when(httpServletRequest.getParameter("usuarioRendicion")).thenReturn(usuarioRend);
        when(httpServletRequest.getParameter("hide")).thenReturn(hide);
        when(httpServletRequest.getParameter("codigo")).thenReturn(codigo);
        when(httpServletRequest.getParameter("glg")).thenReturn(glg);
        when(httpSession.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("rendicion.link.thuban")).thenReturn(thuban);
        when(actionMapping.findForward("success")).thenReturn(ret);

        try(MockedConstruction<UsuarioService> mock = Mockito.mockConstruction(UsuarioService.class, (mockUsuarioService, context) -> {
            when(mockUsuarioService.obtenerDelegadosUsuario(anyString())).thenReturn(usuarioRendicion);
            when(mockUsuarioService.getMsg()).thenReturn(msgUsuarioService);
        })) {
            try(MockedConstruction<RendicionesService> mock2 = Mockito.mockConstruction(RendicionesService.class, (mockRendicionesService, context2) -> {
                when(mockRendicionesService.obtenerListadoRendiciones(anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(rendiciones);
                when(mockRendicionesService.getMsg()).thenReturn(msg);
                when(mockRendicionesService.getGastos(anyString(),anyString(),anyString(),anyString())).thenReturn(gastos);
                when(mockRendicionesService.getMotivoRendiciones(anyString(),anyString())).thenReturn(motivo);
            })) {
                ActionForward result = aprobacionDetalleLoadAction.executeAction(actionMapping, rendicionForm, samWebApplication, samWebClient, httpServletRequest, httpServletResponse);
                assertAll(
                        () -> assertNotNull(result),
                        () -> assertEquals(ret, result)
                );
            }
        }
    }

    // ------ Sources ------

    private static Stream<Arguments> executeActionSource() {
        Usuario user = new Usuario("idUser", "perfil", "nombre", 1, "sector",new ArrayList<>());
        Usuario u = new Usuario("idUser", "perfil", "nombre", 1, "sector",new ArrayList<>());
        String usuarioRend = "usuarioRend";
        String hide = "hide";
        String hide2 = null;
        String codigo = "1";
        List<Rendicion> rendiciones = new ArrayList<>();

        List<Rendicion> rendiciones2 = new ArrayList<>();
        Rendicion rend = new Rendicion();
        rend.setCodMotivo("codMotivo");
        rend.setFechaDesde(new Date());
        rend.setFechaHasta(new Date());
        rend.setId(1);
        rend.setMotivo("motivo");
        rend.setDescripcion("descripcion");
        rend.setMotivoRechazo("motivoRechazo");
        rendiciones2.add(rend);

        List<Rendicion> rendiciones3 = new ArrayList<>();
        Rendicion rend2 = new Rendicion();
        rend2.setCodMotivo("codMotivo");
        rend2.setFechaDesde(new Date());
        rend2.setFechaHasta(new Date());
        rend2.setId(1);
        rend2.setMotivo("motivo");
        rend2.setDescripcion("descripcion");
        rend2.setMotivoRechazo("");
        rendiciones3.add(rend2);

        String msg = "msg";
        List<Gastos> gastos = new ArrayList<>();

        Usuario usuarioRendicion = new Usuario("idUser", "perfil", "nombre", 1, "sector",new ArrayList<>());
        usuarioRendicion.setIdUser("idUser");
        usuarioRendicion.setNombre("nombre");
        usuarioRendicion.setCcostos(1);
        usuarioRendicion.setSector("sector");

        String msgUsuarioService = "msg";
        String glg = "glg";
        List<ComboMotivo> motivo = new ArrayList<>();
        String thuban = "thuban";
        ActionForward ret = new ActionForward("success","path",true);

        return Stream.of(
                Arguments.of(user, u, usuarioRend, hide, codigo, rendiciones, msg, gastos, usuarioRendicion, msgUsuarioService, glg, motivo, thuban, ret),
                Arguments.of(user, u, usuarioRend, hide2, codigo, rendiciones2, msg, gastos, usuarioRendicion, msgUsuarioService, glg, motivo, thuban, ret),
                Arguments.of(user, u, usuarioRend, hide2, codigo, rendiciones3, msg, gastos, usuarioRendicion, msgUsuarioService, glg, motivo, thuban, ret)
        );
    }

}