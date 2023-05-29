package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametroGasto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.swing.table.TableModel;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ParametrosGastosTableDecoratorTest {

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
    ParametrosGastosTableDecorator decorator;

    @BeforeEach
    void setup(){
        ParametroGasto param = new ParametroGasto();
        param.setGasto("");
        param.setDescripcionGasto("");
        param.setMotivo("");
        currentRowObject = param;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        String result = decorator.getVerLink();
        assertEquals("",result);
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        String resultTest = "<form method='post' id='edit_' action='contextPath/parametrosGastosDetalle.do' style='display:none;'><input type='hidden' name='codigo' value=''/><input type='hidden' name='descripcionGasto' value=''/><input type='hidden' name='motivo' value=''/><input type='hidden' name='accion' value='modificacion'/><input type='hidden' name='back' value='false'/></form><a href='#' onclick='modificarGasto(\"\")'><img src='contextPath/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        String resultTest = "<form method='post' id='delete_' action='contextPath/parametrosGastosDetalle.do' style='display:none;'><input type='hidden' name='codigo' value=''/><input type='hidden' name='descripcionGasto' value=''/><input type='hidden' name='motivo' value=''/><input type='hidden' name='accion' value='baja'/><input type='hidden' name='back' value='false'/></form><a href='#' onclick='eliminarGasto(\"\")'><img src='contextPath/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getDestinatariosLink")
    void getDestinatariosLink() {
        String result = decorator.getDestinatariosLink();
        assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink() {
        String result = decorator.getCuponesLink();
        assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        String result = decorator.getScanLink();
        assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink() {
        String result = decorator.getCaratulaLink();
        assertEquals(null, result);
    }
}