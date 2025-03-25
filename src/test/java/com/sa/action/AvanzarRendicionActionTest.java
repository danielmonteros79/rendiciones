package com.sa.action;

import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import com.sa.services.AprobacionesService;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvanzarRendicionActionTest {

    @InjectMocks private AvanzarRendicionAction avanzarRendicionAction;

    @Mock private SAMWebClient samWebClient;
    @Mock private AprobacionesService aprobacionesService;
    @Mock private RendicionesService rendicionesService;
    @Mock private PagosService pagosService;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private ActionMapping actionMapping;

    private Usuario usuarioMock;
    private Rendicion rendicionMock;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        usuarioMock = new Usuario("1234", "SS", "PEPE", 1212, "", null);
        avanzarRendicionAction.setSessionUser(usuarioMock);
        avanzarRendicionAction.setSessionUserWorking(usuarioMock);

        rendicionMock = new Rendicion();
        rendicionMock.setId(1);
        rendicionMock.setEstado("PENDI");
        rendicionMock.setUsuarioRendicion("USER123");
        rendicionMock.setCodMotivo("MOT01");
        
        aprobacionesService = mock(AprobacionesService.class);
        avanzarRendicionAction.setAprobacionesService(aprobacionesService);
        
        //when(request.getSession()).thenReturn(session);
        //when(session.getAttribute("usuario")).thenReturn(usuarioMock);
        //when(session.getAttribute("userWorking")).thenReturn(usuarioMock);
        

		when(response.getWriter()).thenReturn(mock(PrintWriter.class));
    }

    @Test
    @DisplayName("✅ Debe llamar a validarRend() cuando action=validarRend")
    void executeAction_ShouldCallValidarRend_WhenActionIsValidarRend() throws Exception {
        when(request.getParameter("action")).thenReturn("validarRend");
        when(request.getParameter("idRendicion")).thenReturn("1");

        ActionForward result = avanzarRendicionAction.executeAction(actionMapping, null, null, samWebClient, request, response);

        assertNull(result); // validarRend() devuelve null
        verify(pagosService, times(1)).getValidacionRendicion(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("✅ Debe avanzar la rendición cuando es una aprobación")
    void executeAction_ShouldAdvanceRendicion_WhenEsAprobacionIsTrue() throws Exception {
        when(request.getParameter("action")).thenReturn("avanzar");
        when(request.getParameter("idRendicion")).thenReturn("1");
        when(request.getParameter("glg")).thenReturn("04");
        when(request.getParameter("esAprobacion")).thenReturn("true");
        
        doReturn(Arrays.asList(rendicionMock)).when(aprobacionesService)
        .getAprobacionesPendientes(any(), eq(null), eq(null), any(), any());

        //when(aprobacionesService.getAprobacionesPendientes(anyString(), any(), any(), anyString(), anyString()))
        //        .thenReturn(Collections.singletonList(rendicionMock));

        when(rendicionesService.getGastos(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList(mock(Gastos.class)));

        ActionForward result = avanzarRendicionAction.executeAction(actionMapping, null, null, samWebClient, request, response);

        assertNull(result); // Se espera un JSON como respuesta
        verify(aprobacionesService, times(1)).getAprobacionesPendientes(any(), any(), any(), any(), any());
        verify(rendicionesService, times(1)).getGastos(any(), any(), any(), any());
    }

    @Test
    @DisplayName("✅ Debe avanzar la rendición cuando no es una aprobación")
    void executeAction_ShouldAdvanceRendicion_WhenEsAprobacionIsFalse() throws Exception {
        when(request.getParameter("action")).thenReturn("avanzar");
        when(request.getParameter("idRendicion")).thenReturn("1");
        when(request.getParameter("glg")).thenReturn("04");
        when(request.getParameter("esAprobacion")).thenReturn("false");

        when(rendicionesService.obtenerListadoRendiciones(anyString(), anyString(), any(), any(), any()))
                .thenReturn(Arrays.asList(rendicionMock));

        when(rendicionesService.getGastos(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Arrays.asList(mock(Gastos.class)));

        ActionForward result = avanzarRendicionAction.executeAction(actionMapping, null, null, samWebClient, request, response);

        assertNull(result); // Se espera un JSON como respuesta
        verify(rendicionesService, times(1)).obtenerListadoRendiciones(any(), any(), any(), any(), any());
        verify(rendicionesService, times(1)).getGastos(any(), any(), any(), any());
    }

    @Test
    @DisplayName("❌ Debe devolver error si la rendición no tiene gastos")
    void executeAction_ShouldReturnError_WhenRendicionHasNoGastos() throws Exception {
        when(request.getParameter("action")).thenReturn("avanzar");
        when(request.getParameter("idRendicion")).thenReturn("1");
        when(request.getParameter("glg")).thenReturn("04");
        when(request.getParameter("esAprobacion")).thenReturn("true");

        when(aprobacionesService.getAprobacionesPendientes(anyString(), any(), any(), anyString(), anyString()))
                .thenReturn(Arrays.asList(rendicionMock));

        when(rendicionesService.getGastos(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        ActionForward result = avanzarRendicionAction.executeAction(actionMapping, null, null, samWebClient, request, response);

        assertNull(result); // writeError devuelve null
        verify(rendicionesService, times(1)).getGastos(any(), any(), any(), any());
    }
}
