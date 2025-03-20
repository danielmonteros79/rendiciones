package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import com.sa.entities.Rendicion;
import com.sa.manager.ManagerTransaction;
import com.sa.services.trxs.SU62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AprobacionesServiceTest {

	@Mock private SAMWebClient samWebClient;
    @Mock private ManagerTransaction managerTransaction;
    @InjectMocks private AprobacionesService aprobacionesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        samWebClient = mock(SAMWebClient.class);        
        doReturn("123").when(samWebClient).getId();
        doReturn(true).when(samWebClient).isLoginOk();
                
        managerTransaction = mock(ManagerTransaction.class);
        when(managerTransaction.getDataReturnList()).thenReturn(Collections.emptyList());
        when(managerTransaction.getDataReturn()).thenReturn("10");
        when(managerTransaction.getMensajeAviso()).thenReturn("OK");
        
        aprobacionesService = new AprobacionesService(samWebClient, managerTransaction);
        

        try {
			doNothing().when(managerTransaction).executeTrx(any(), any());
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Test
    @DisplayName("Debe obtener aprobaciones pendientes correctamente")
    void getAprobacionesPendientes_Success() throws Exception {
    	
    	Rendicion rendicionMock = mock(Rendicion.class);
        List<Rendicion> rendicionesMock = Collections.singletonList(rendicionMock);

        when(managerTransaction.getDataReturnList()).thenReturn(rendicionesMock);
        when(managerTransaction.getDataReturn()).thenReturn("10");
        when(managerTransaction.getMensajeAviso()).thenReturn("OK");

        doNothing().when(managerTransaction).executeTrx(any(), any());

        List<Rendicion> result = aprobacionesService.getAprobacionesPendientes(
            "123", "usuario1", "motivoX", "estadoA", "usuario2"
        );

        assertNotNull(result, "El resultado no debe ser null");
        assertEquals(1, result.size(), "Debe haber una rendición en la lista");
    }

    @Test
    @DisplayName("Debe manejar TransactionException en getAprobacionesPendientes")
    void getAprobacionesPendientes_Exception() throws Exception {
        doThrow(new TransactionException("Error en SU61"))
            .when(managerTransaction).executeTrx(any(), any());

        assertThrows(TransactionException.class, () -> 
            aprobacionesService.getAprobacionesPendientes("123", "usuario1", "motivoX", "estadoA", "usuario2")
        );
    }

    @Test
    @DisplayName("Debe cambiar estado de rendiciones correctamente")
    void cambiarEstadoRendiciones_Success() throws Exception {
        ManagerTransaction managerMock = mock(ManagerTransaction.class);
        
    	Rendicion rendicionMock = mock(Rendicion.class);
        List<Rendicion> rendicionesMock = Collections.singletonList(rendicionMock);
        
        when(managerTransaction.getDataReturnList()).thenReturn(rendicionesMock);
        when(managerTransaction.getDataReturn()).thenReturn("10");
        when(managerTransaction.getMensajeAviso()).thenReturn("OK");

        when(managerMock.getDataReturn()).thenReturn("OPERACION EFECTUADA");
        doNothing().when(managerMock).executeTrx(any(), any());

        List<Integer> idRendiciones = Arrays.asList(123456);
        String result = aprobacionesService.cambiarEstadoRendiciones("user1", idRendiciones, "APROB", null, "04");

        assertEquals("10", result);
    }

    @Test
    @DisplayName("Debe manejar error en cambiarEstadoRendiciones")
    void cambiarEstadoRendiciones_Exception() throws Exception {
        doThrow(new TransactionException("Error en SU62"))
            .when(managerTransaction).executeTrx(any(), any());

        assertThrows(TransactionException.class, () ->
            aprobacionesService.cambiarEstadoRendiciones("user1", Arrays.asList(123), "RECHA", "Motivo X", "glg123")
        );
    }

    @Test
    @DisplayName("Debe cambiar estado de una rendición correctamente")
    void cambiarEstadoDeUnaRendicion_Success() throws Exception {
        SU62 su62Mock = mock(SU62.class);
        ManagerTransaction managerMock = mock(ManagerTransaction.class);

        when(managerMock.getDataReturn()).thenReturn("CAMBIO EXITOSO");
        doNothing().when(managerMock).executeTrx(any(), any());

        String result = aprobacionesService.cambiarEstadoDeUnaRendicion("user1", "123456", "APROB", "Motivo X", "glg123");

        assertEquals("10", result);
    }

    @Test
    @DisplayName("Debe manejar error en cambiarEstadoDeUnaRendicion")
    void cambiarEstadoDeUnaRendicion_Exception() throws Exception {
        doThrow(new TransactionException("Error en SU62"))
            .when(managerTransaction).executeTrx(any(), any());

        assertThrows(TransactionException.class, () -> 
            aprobacionesService.cambiarEstadoDeUnaRendicion("user1", "123456", "APROB", "Motivo X", "glg123")
        );
    }

//    @Test
//    @DisplayName("Debe manejar correctamente obtenerIDU")
//    void obtenerIDU_Success() {
//        Rendicion rendicionMock = mock(Rendicion.class);
//        when(rendicionMock.getId()).thenReturn(123456);
//
//        when(managerTransaction.getDataReturn()).thenReturn("IDU123");
//        when(managerTransaction.getMensajeAviso()).thenReturn("OK");
//        try {
//			doNothing().when(managerTransaction).executeTrx(any(), any());
//		} catch (TransactionException e) {
//			e.printStackTrace();
//		}
//
//        // Ejecutar método
//        String result = aprobacionesService.obtenerIDU(rendicionMock, "user1", "ADEA");
//
//        assertEquals("IDU123", result);
//    }

    @Test
    @DisplayName("Debe manejar error en obtenerIDU")
    void obtenerIDU_Exception() {
        Rendicion rendicionMock = mock(Rendicion.class);
        when(rendicionMock.getId()).thenReturn(123456);

        try {
			doThrow(new RuntimeException("Error en WM95"))
			    .when(managerTransaction).executeTrx(any(), any());
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String result = aprobacionesService.obtenerIDU(rendicionMock, "user1", "ADEA");
        assertNull(result, "Si ocurre un error, debe retornar null");
    }

//    @Test
//    @DisplayName("Debe retornar cantidad de rendiciones")
//    void getCantRendiciones() {
//    	
//        AprobacionesService service = aprobacionesService;
//        try {
//			service.cambiarEstadoRendiciones("user1", Arrays.asList(123), "APROB", null, "glg123");
//		} catch (TransactionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        
//        assertNotNull(service.getCantRendiciones());
//    }
}
