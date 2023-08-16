package com.sa.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.sa.entities.parametros.Resumen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.stream.Stream;

class ConsumosNoRendidosTableDecoratorTest {

    @Mock
    PageContext pageContext;

    @Mock
    HttpServletRequest request;

    @InjectMocks
    ConsumosNoRendidosTableDecorator consumosNoRendidosTableDecorator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConstructor() {
        ConsumosNoRendidosTableDecorator actualConsumosNoRendidosTableDecorator = new ConsumosNoRendidosTableDecorator();
        assertEquals("", actualConsumosNoRendidosTableDecorator.getBorrarLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getCaratulaLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getCuponesLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getDestinatariosLink());
        assertEquals("", actualConsumosNoRendidosTableDecorator.getEditarLink());
        assertNull(actualConsumosNoRendidosTableDecorator.getScanLink());
        assertEquals("", actualConsumosNoRendidosTableDecorator.getVerLink());
    }

    @ParameterizedTest
    @MethodSource("getEstadoSource")
    @DisplayName("Testeando getEstado")
    void testGetEstado(Resumen resumen, String res) {
        consumosNoRendidosTableDecorator.initRow(resumen,0,0);
        consumosNoRendidosTableDecorator.init(pageContext, null, null);

        when(pageContext.getRequest()).thenReturn(request);
        when(request.getContextPath()).thenReturn("contextPath");


        String result = consumosNoRendidosTableDecorator.getEstado();
        assertEquals(res, result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getEstadoSource() {
        Resumen resumen = new Resumen();
        Resumen resumen2 = new Resumen();
        Resumen resumen3 = new Resumen();
        String res = "<div class='balloon' style='color:red; font-weight:bold;' title='El consumo se debitar&aacute; en caso de no rendirse'>A DEBITAR&nbsp;&nbsp;&nbsp;<img class='blink' style='width:18px;vertical-align:middle;' src='contextPath/images/Warning_48x48.png' /></div>";
        String res2 = "<div class='balloon' title='Nro. Rendici&oacute;n: null'>EN PROCESO</div>";
        String res3 = "<div>OTRO</div>";

        resumen.setEstado("A DEBITAR");
        resumen2.setEstado("EN PROCESO");
        resumen3.setEstado("OTRO");

        return Stream.of(
                Arguments.of(resumen, res),
                Arguments.of(resumen2, res2),
                Arguments.of(resumen3, res3)
        );
    }
}

