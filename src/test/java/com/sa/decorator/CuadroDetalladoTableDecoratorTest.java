package com.sa.decorator;

import com.sa.entities.CuadroDetallado;
import com.sa.entities.CuadroGeneral;
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

class CuadroDetalladoTableDecoratorTest {
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
    CuadroDetalladoTableDecorator cuadroDetalladoTableDecorator;

    CuadroDetallado cuadroDetallado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getVerLinkSource")
    @DisplayName("Testeando get ver link")
    void getVerLink(Integer id,String codMotivo,String usuario,String contextPath,String resultado) {
        cuadroDetallado = new CuadroDetallado();
        cuadroDetallado.setId(id);
        cuadroDetallado.setCodMotivo(codMotivo);
        cuadroDetallado.setUsuario(usuario);
        currentRowObject = cuadroDetallado;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = cuadroDetalladoTableDecorator.getVerLink();
        System.out.println(result);
        //Assertions.assertEquals(resultado, result);
    }

    @Test
    @DisplayName("Testeando get editar link")
    void getEditarLink() {
        String result = cuadroDetalladoTableDecorator.getEditarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get borrar link")
    void getBorrarLink() {
        String result = cuadroDetalladoTableDecorator.getBorrarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = cuadroDetalladoTableDecorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = cuadroDetalladoTableDecorator.getCuponesLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = cuadroDetalladoTableDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = cuadroDetalladoTableDecorator.getCaratulaLink();
        Assertions.assertEquals("", result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getVerLinkSource() {
        Integer id = 1;
        String codMotivo = "codMotivo";
        String usuario = "usuario";
        String contextPath = "contextPath";
        String resultado = "<form action='mostrarDetalleGastos.do' method='post'><input type='hidden' name='action' value='mostrarDetalleGastos'/><input type='hidden' name='codigo' value='1'/><input type='hidden' name='codMotivo' value='codMotivo'/><input type='hidden' name='usuario' value='usuario'/><input type='image' name='submit' src='contextPath/images/iconos/ver.png' alt='Ver' title='Ver'/></form>";

        return Stream.of(
                Arguments.of(id,codMotivo,usuario,contextPath,resultado)
        );
    }
}