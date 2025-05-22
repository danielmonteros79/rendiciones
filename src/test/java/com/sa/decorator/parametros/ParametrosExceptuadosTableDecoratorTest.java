package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametroExceptuado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import javax.swing.table.TableModel;
import java.util.Map;
import java.util.stream.Stream;

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


    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        String resultTest = "<form method='post' id='edit_motivo' action='contextPath/parametrosExceptuadosDetalle.do' style='display:none;'><input type='hidden' name='motivoUsuario' value='motivo'/><input type='hidden' name='marca' value=''/><input type='hidden' name='accion' value='modificacion'/></form><a href='#' onclick='modificarExcepcion(\"motivo\")'><i class='bbva-icon icon-coronita_contract fa-lg text-gray' style='cursor:pointer' /> </i></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }


    @ParameterizedTest
    @MethodSource("getBorrarLinkSource")
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink(ParametroExceptuado excepcion,String res) {
        currentRowObject = excepcion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(res, result)
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

    // ------ Sources ------

    private static Stream<Arguments> getBorrarLinkSource() {
        ParametroExceptuado excepcion = new ParametroExceptuado();
        ParametroExceptuado excepcion2 = new ParametroExceptuado();
        String res = "";
        String res2 = "<form method='post' id='delete_motivoUsuario' action='contextPath/parametrosExceptuadosDetalle.do' style='display:none;'><input type='hidden' name='motivoUsuario' value='motivoUsuario'/> <input type='hidden' name='marca' value='tipo'/><input type='hidden' name='accion' value='baja'/></form><a href='#' onclick='confirmEliminarExcepcion(\"motivoUsuario\")'><i class=\"bbva-icon icon-coronita_trash text-gray fa-lg\" alt='Eliminar' style='cursor:pointer'  title='Eliminar' border='0' /> </i></a>";

        excepcion.setTipo("tipo");
        excepcion.setEstado("C");

        excepcion2.setTipo("tipo");
        excepcion2.setEstado("A");
        excepcion2.setMotivoUsuario("motivoUsuario");

        return Stream.of(
                        Arguments.of(excepcion,res),
                        Arguments.of(excepcion2,res2)
        );
    }

}