package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametroMotivo;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class ParametrosMotivoTableDecoratorTest {
    @Spy
    Object currentRowObject;
    @Mock
    Map propertyMap;
    @Mock
    PageContext pageContext;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    Object decoratedObject;
    @Mock
    TableModel tableModel;
    @InjectMocks
    ParametrosMotivoTableDecorator parametrosMotivoTableDecorator;

    ParametroMotivo parametroMotivo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get ver link")
    void getVerLink() {
        String result = parametrosMotivoTableDecorator.getVerLink();
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("getEditarLinkSource")
    @DisplayName("Testeando get editar link")
    void getEditarLink(String codigo, String contextPath, String resultado) {
        parametroMotivo = new ParametroMotivo();
        parametroMotivo.setCodigo(codigo);
        currentRowObject = parametroMotivo;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = parametrosMotivoTableDecorator.getEditarLink();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getBorrarLinkSource")
    @DisplayName("Testeando get borrar link")
    void getBorrarLink(String codigo, String contextPath, String resultado) {
        parametroMotivo = new ParametroMotivo();
        parametroMotivo.setCodigo(codigo);
        currentRowObject = parametroMotivo;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = parametrosMotivoTableDecorator.getBorrarLink();
        Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = parametrosMotivoTableDecorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = parametrosMotivoTableDecorator.getCuponesLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void testGetScanLink() {
        String result = parametrosMotivoTableDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void testGetCaratulaLink() {
        String result = parametrosMotivoTableDecorator.getCaratulaLink();
        Assertions.assertEquals("", result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getEditarLinkSource() {
        String codigo = "codigo";
        String contextPath = "contextPath";
        String resultado = "<form method='post' id='edit_codigo' action='contextPath/parametrosMotivoDetalle.do' style='display:none;'><input type='hidden' name='codigo' value='codigo'/><input type='hidden' name='accion' value='modificacion'/></form><a href='#' onclick='modificarMotivo(\"codigo\")'><img src='contextPath/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/></a>";

        return Stream.of(
          Arguments.of(codigo,contextPath,resultado)
        );
    }

    private static Stream<Arguments> getBorrarLinkSource() {
        String codigo = "codigo";
        String contextPath = "contextPath";
        String resultado = "<form method='post' id='delete_codigo' action='contextPath/parametrosMotivoDetalle.do' style='display:none;'><input type='hidden' name='codigo' value='codigo'/><input type='hidden' name='accion' value='baja'/></form><a href='#' onclick='eliminarMotivo(\"codigo\")'><img src='contextPath/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/></a>";

        return Stream.of(
                Arguments.of(codigo,contextPath,resultado)
        );
    }

}

