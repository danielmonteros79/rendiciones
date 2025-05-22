package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametroAlerta;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.Map;

import static org.mockito.Mockito.*;

class ParametrosAlertasTableDecoratorTest {
    @Spy
    private Object currentRowObject;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private Map propertyMap;
    @Mock
    private PageContext pageContext;
    @Mock
    private Object decoratedObject;
    @Mock
    private TableModel tableModel;
    @InjectMocks
    private ParametrosAlertasTableDecorator decorator;

    @BeforeEach
    void setUp() {
        ParametroAlerta param= new ParametroAlerta();
        param.setTimeStamp("");
        currentRowObject = param;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        String result = decorator.getVerLink();
        Assertions.assertEquals("", result);
    }

    /*@Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        String expected = "<form method='post' id='edit_nullnull' action='contextPath/parametrosAlertasDetalle.do' style='display:none;'><input type='hidden' name='codMotivo' value='null'/><input type='hidden' name='codGasto' value='null'/><input type='hidden' name='timeStamp' value=''/><input type='hidden' name='accion' value='modificacion'/></form><a href='#' onclick='modificarAlerta(\"nullnull\")'><img src='contextPath/images/iconos/editar.png' alt='Modificar' title='Modificar' border='0'/></a>";

        Assertions.assertEquals(expected, result);
    }*/

    /*@Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();
        String expected = "<form method='post' id='delete_nullnull' action='contextPath/parametrosAlertasDetalle.do' style='display:none;'><input type='hidden' name='codMotivo' value='null'/><input type='hidden' name='codGasto' value='null'/><input type='hidden' name='timeStamp' value=''/><input type='hidden' name='accion' value='baja'/></form><a href='#' onclick='eliminarAlerta(\"nullnull\")'><img src='contextPath/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/></a>";

        Assertions.assertEquals(expected, result);
    }*/

    @Test
    @DisplayName("Testeando getDestinatariosLink")
    void getDestinatariosLink() {
        String result = decorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink() {
        String result = decorator.getCuponesLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        String result = decorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink() {
        String result = decorator.getCaratulaLink();
        Assertions.assertEquals("", result);
    }
}
