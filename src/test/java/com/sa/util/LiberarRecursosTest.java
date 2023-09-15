package com.sa.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ar.com.bbva.soa.conectores.BbvaSoaMensaje;
import ar.com.bbva.soa.conectores.BbvaSoaStatus;
import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.factory.SAMReference;
import ar.com.itrsa.sam.impl.ContextImpl;
import ar.com.itrsa.sam.impl.ServiceAccessManagerImpl;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import com.bbva.sam.bbvaPaq.BbvaPaqConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.ServletContext;

class LiberarRecursosTest {

    @Mock
    IServiceAccessManager sam;
    @InjectMocks
    LiberarRecursos liberarRecursos;


    @Mock
    Map<Object, Object> paramsMock;
    @Mock
    ServletContext context;

    @Mock
    BbvaSoaStatus status;
    @Mock
    List lista;
    @Mock
    BbvaSoaMensaje mensajes;

    @Mock
    Iterator<?> iterator;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testExecute2() {
        LiberarRecursos liberarRecursos = new LiberarRecursos(new ServiceAccessManagerImpl(), null);
        assertTrue(liberarRecursos.execute(new HashMap<>()).isEmpty());
    }

    @Test
    void testExecute3() {
        LiberarRecursos liberarRecursos = new LiberarRecursos(null, null);
        assertTrue(liberarRecursos.execute(new HashMap<>()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("executeSource")
    @DisplayName("Testeando execute")
    void execute(Map<Object, Object> para) throws Exception {
        {
            List a = new ArrayList<>();
            a.add(0,mensajes);
            a.add(1,mensajes);
            Map<Object, Object> params = new HashMap<>();
            params.put(BbvaPaqConstants.NOMBRE_PARAM_STATUS,status);

            Field paramsLogOffSetPrivate = LiberarRecursos.class.getDeclaredField("paramsLogoff");
            paramsLogOffSetPrivate.setAccessible(true);
            paramsLogOffSetPrivate.set(liberarRecursos, params);
            BbvaSoaMensaje mensaje = new BbvaSoaMensaje();

           // when(paramsMock.get(anyString())).thenReturn(status);
            try (MockedStatic<SAMReference> samReferenceMockedStatic = mockStatic(SAMReference.class)) {
                samReferenceMockedStatic.when(() -> SAMReference.getSAM()).thenReturn(sam);
                doNothing().when(sam).execute(any(), any(), any());
                when(status.getListaErrores()).thenReturn(a);
                when(mensajes.getCodigo()).thenReturn("a");
                when(mensajes.getDescripcion()).thenReturn("a");
                when(mensajes.getDescripcionDetallada()).thenReturn("a");

                //when(status.isOk()).thenReturn(true);
                Map<Object, Object> result = liberarRecursos.execute(para);
                assertNotNull(result);



                //when(status).thenReturn((BbvaSoaStatus) parameters.get(STATUS));


            }
        }
    }

    @ParameterizedTest
    @MethodSource("executeSource")
    @DisplayName("Testeando execute")
    void executeIsOk(Map<Object, Object> para) throws Exception {
        {
            List a = new ArrayList<>();
            a.add(0,mensajes);
            a.add(1,mensajes);
            Map<Object, Object> params = new HashMap<>();
            params.put(BbvaPaqConstants.NOMBRE_PARAM_STATUS,status);

            Field paramsLogOffSetPrivate = LiberarRecursos.class.getDeclaredField("paramsLogoff");
            paramsLogOffSetPrivate.setAccessible(true);
            paramsLogOffSetPrivate.set(liberarRecursos, params);
            BbvaSoaMensaje mensaje = new BbvaSoaMensaje();

            // when(paramsMock.get(anyString())).thenReturn(status);
            try (MockedStatic<SAMReference> samReferenceMockedStatic = mockStatic(SAMReference.class)) {
                samReferenceMockedStatic.when(() -> SAMReference.getSAM()).thenReturn(sam);
                doNothing().when(sam).execute(any(), any(), any());
                when(status.isOk()).thenReturn(true);


                //when(status.isOk()).thenReturn(true);
                Map<Object, Object> result = liberarRecursos.execute(para);
                assertNotNull(result);



                //when(status).thenReturn((BbvaSoaStatus) parameters.get(STATUS));


            }
        }
    }

    private static Stream<Arguments> executeSource() {
        Map<Object, Object> a = new HashMap<>();


        return Stream.of(
                Arguments.of(a)

        );
    }
}

