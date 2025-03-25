package com.sa.action;

import ar.com.bbva.web.impl.SAMWebApplication;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.sa.entities.ComboGasto;
import com.sa.entities.ComboOpcion2;
import com.sa.entities.Gastos;
import com.sa.entities.Usuario;
import com.sa.form.RendicionDetalleForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.PagosService;
import com.sa.services.RendicionesService;
import com.sa.util.ParamsConstants;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ModificarGastoActionTest {

	@Mock private HttpServletRequest httpServletRequest;
    @Mock private HttpServletResponse httpServletResponse;
    @Mock private HttpSession session;
    @Mock private ActionMapping actionMapping;
    @Mock private RendicionesService rendicionesService;
    @Mock private PagosService pagosService;
    @Mock private RendicionDetalleForm rendicionDetalleForm;
    @Mock SAMWebApplication samWebApplication;
    @Mock SAMWebClient samWebClient;
    
    @InjectMocks private ModificarGastoAction modificarGastoAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simular sesión de usuario
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getIdUser()).thenReturn("1234");

        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getAttribute("userWorking")).thenReturn(usuarioMock);
        when(session.getAttribute("usuario")).thenReturn(usuarioMock);

        rendicionesService = mock(RendicionesService.class);
        pagosService = mock(PagosService.class);
        modificarGastoAction = new ModificarGastoAction(rendicionesService, pagosService);
        
        ComboGasto comboGastoMock = new ComboGasto("G1", "Gasto de Viaje");
        List<ComboGasto> listaComboGastos = Collections.singletonList(comboGastoMock);
        when(httpServletRequest.getAttribute("ComboGastos")).thenReturn(listaComboGastos);
        
        ComboOpcion2 comboMonedaMock = new ComboOpcion2("USD", "Dólar");
        List<ComboOpcion2> listaComboMonedas = Collections.singletonList(comboMonedaMock);
        when(httpServletRequest.getAttribute("ComboMoneda")).thenReturn(listaComboMonedas);
        
        ComboOpcion2 comboComprobanteMock = new ComboOpcion2("F", "Factura");
        List<ComboOpcion2> listaComboComprobantes = Collections.singletonList(comboComprobanteMock);
        when(httpServletRequest.getAttribute("ComboComprobante")).thenReturn(listaComboComprobantes);

        Gastos gastoMock = mock(Gastos.class);
        when(gastoMock.getIdGasto()).thenReturn("123");
        when(gastoMock.getDescGasto()).thenReturn("Gasto de Viaje");
        when(gastoMock.getMoneda()).thenReturn("USD");
        when(gastoMock.getComprobante()).thenReturn("Factura");
        when(gastoMock.getFechagastos()).thenReturn("2024-01-15");
        when(gastoMock.getMonto()).thenReturn("1000.00");
        when(gastoMock.getObservacionGasto()).thenReturn("Gasto justificado");
        
        
        when(httpServletRequest.getParameter("tieneCupon")).thenReturn("1");
        when(httpServletRequest.getParameter("listadoAprob")).thenReturn("listadoAprob");
        when(httpServletRequest.getParameter("opcion")).thenReturn("MODI");
        when(httpServletRequest.getParameter("user")).thenReturn("usuario123");
        when(httpServletRequest.getParameter("glg")).thenReturn("04");
        
        RendicionDetalleForm rendicionDetalleFormMock = mock(RendicionDetalleForm.class);
        when(rendicionDetalleFormMock.getMonto()).thenReturn("1000.00");

        try {
			when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));
			
			doReturn(Collections.singletonList(new ComboOpcion2("1", "Option"))).when(rendicionesService)
				.getComboOpcion2(any(), any(), any(), any());
			
			doReturn(Collections.singletonList(mock(Gastos.class))).when(rendicionesService)
				.getGastos(anyString(), anyString(), anyString(), anyString());
			
	        when(rendicionesService.getGastos(anyString(), anyString(), anyString(), anyString()))
	            .thenReturn(Collections.singletonList(mock(Gastos.class)));
	        
	        doReturn(Collections.singletonList(new ComboGasto("001", "Descripcion"))).when(pagosService)
	        	.getComboGasto(any(), any(), any());
	        
		} catch (TransactionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }

    @Test
    @DisplayName("Debe ejecutar correctamente el Action y devolver 'success'")
    void executeAction_Success() throws Exception {
        // Mock de parámetros en el request
        when(httpServletRequest.getParameter("estadoRendicion")).thenReturn("Aprobada");
        when(httpServletRequest.getParameter("codigo")).thenReturn("5678");
        when(httpServletRequest.getParameter("cCosto")).thenReturn("CostCenter");
        when(httpServletRequest.getParameter("fechaD")).thenReturn("2024-01-01");
        when(httpServletRequest.getParameter("fechaH")).thenReturn("2024-01-31");
        when(httpServletRequest.getParameter("codMotivo")).thenReturn("MOTIVO_001");
        when(httpServletRequest.getParameter("idGasto")).thenReturn("123");
        when(httpServletRequest.getParameter("opcion")).thenReturn("MODI");
        when(httpServletRequest.getParameter("listadoAprob")).thenReturn("1");
        when(httpServletRequest.getParameter("tieneCupon")).thenReturn("1");

        // Mock de los servicios
        when(rendicionesService.getComboOpcion2(any(), any(), any(), any()))
            .thenReturn(Collections.singletonList(new ComboOpcion2("1", "Option")));
        when(pagosService.getComboGasto(any(), any(), any()))
            .thenReturn(Collections.singletonList(new ComboGasto("Gasto_001", "Descripción")));

        // Mock de gastos
        Gastos gastoMock = mock(Gastos.class);
        when(gastoMock.getIdGasto()).thenReturn("123");
        when(gastoMock.getMoneda()).thenReturn("ARS");
        when(gastoMock.getComprobante()).thenReturn("Factura");
        when(gastoMock.getFechagastos()).thenReturn("2024-01-15");
        when(gastoMock.getMonto()).thenReturn("1000.00");
        
        RendicionDetalleForm rendicionDetalleFormMock = mock(RendicionDetalleForm.class);
        when(rendicionDetalleFormMock.getMonto()).thenReturn("1000.00");

        when(rendicionesService.getGastos(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Collections.singletonList(gastoMock));

        // Mock de ActionMapping
        when(actionMapping.findForward("success")).thenReturn(new ActionForward("success"));

        // Ejecutar Action
        ActionForward result = modificarGastoAction.executeAction(
                actionMapping, rendicionDetalleFormMock, samWebApplication, samWebClient, httpServletRequest, httpServletResponse
        );

        // Validar resultados
        assertNotNull(result, "El resultado no debe ser null");
        assertEquals("success", result.getPath(), "El forward debe ser 'success'");
    }
}
