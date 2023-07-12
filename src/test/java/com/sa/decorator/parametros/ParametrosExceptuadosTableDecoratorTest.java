package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametroExceptuado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

class ParametrosExceptuadosTableDecoratorTest {


    @Spy
    private Object currentRowObject;
    @Mock
    private Map propertyMap;
    @Mock
    private PageContext pageContext;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private Object decoratedObject;
    @Mock
    private TableModel tableModel;

    @InjectMocks
    private ParametrosExceptuadosTableDecorator decorator;

    @BeforeEach
    void setup(){
        ParametroExceptuado pExc = new ParametroExceptuado();
        pExc.setTipo("");
        pExc.setMotivoUsuario("motivo");
        currentRowObject = pExc;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        String result = decorator.getVerLink();
        assertEquals("", result);
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        String resultTest = "<form method='post' id='edit_motivo' action='contextPath/parametrosExceptuadosDetalle.do' style='display:none;'><input type='hidden' name='motivoUsuario' value='motivo'/><input type='hidden' name='marca' value=''/><input type='hidden' name='accion' value='modificacion'/></form><a href='#' onclick='modificarExcepcion(\"motivo\")'><img src='contextPath/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Disabled("Desabilitado porque se debe adaptar a la version actual")
    @Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        String resultTest = "<form method='post' id='delete_motivo' action='contextPath/parametrosExceptuadosDetalle.do' style='display:none;'><input type='hidden' name='motivoUsuario' value='motivo'/><input type='hidden' name='marca' value=''/><input type='hidden' name='accion' value='baja'/></form><a href='#' onclick='confirmEliminarExcepcion(\"motivo\")'><img src='contextPath/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/></a>";

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
        assertEquals("", result);
    }
}