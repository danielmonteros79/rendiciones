package com.sa.decorator.parametros;

import com.sa.entities.parametros.ParametriaUsuarioDelegado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.swing.table.TableModel;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RelacionUsuarioDelegadoTableDecoratorTest {

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
    RelacionUsuarioDelegadoTableDecorator decorator;

    public static Stream<Arguments> getEditarLinkSource() {
        ParametriaUsuarioDelegado param = new ParametriaUsuarioDelegado();

        String resultTest1 = "<a href=\"contextPath/relacionUsuarioDelegadoAlta.do?optn=M&callParam=1\"><img src=\"contextPath/images/iconos/editar.png\" alt='Editar' title='Editar' border=\"0\" /></a>";
        String resultTest2 = "<a href=\"contextPath/relacionUsuarioDelegadoAlta.do?optn=M&callParam=1\"><img src=\"contextPath/images/iconos/editar.png\" alt='Editar' title='Editar' border=\"0\" /></a>";
        String resultTest3 = "<a href=\"contextPath/relacionUsuarioDelegadoAlta.do?optn=M&callParam=1\"><img src=\"contextPath/images/iconos/ver.png\" alt='Ver' title='Ver' border=\"0\" /></a>";

        return Stream.of(
                Arguments.of(param, 1, "A",resultTest1),
                Arguments.of(param, 1, "I",resultTest2),
                Arguments.of(param, 1, "",resultTest3)
        );
    }

    public static Stream<Arguments> getBorrarLinkSource() {
        ParametriaUsuarioDelegado param = new ParametriaUsuarioDelegado();

        Date fdesde = new Date();
        Date fhasta = new Date();

        String resultTest1 = "<form method='post' id='delete_1' action='contextPath/execAbmDelegaciones.do' style='display:none;'><input type='hidden' name='delegadoUser' value='user'/><input type='hidden' name='opcion' value='BAJA'/><input type='hidden' name='feDesde' value='22-05-2023'/><input type='hidden' name='feHasta' value='22-05-2023'/></form><a href='#' onclick='eliminar(1)'><img src='contextPath/images/iconos/borrar.png' alt='Eliminar' title='Eliminar' border='0'/></a>";
        String resultTest2 = "";

        return Stream.of(
                Arguments.of(param, 1, "A", "user", fdesde, fhasta, resultTest1),
                Arguments.of(param, 1, "B", "user", fdesde, fhasta, resultTest2)
        );
    }

    @BeforeEach
    void setup(){
        ParametriaUsuarioDelegado param = new ParametriaUsuarioDelegado();
        currentRowObject = param;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        Assertions.assertEquals("",decorator.getVerLink());
    }

    @ParameterizedTest
    @MethodSource("getEditarLinkSource")
    @DisplayName("Testeando getEditarLink")
    void getEditarLink(ParametriaUsuarioDelegado param, Integer id ,String estado, String resultTest) {
        param.setId(id);
        param.setDelegadoEstado(estado);
        currentRowObject = param;
        MockitoAnnotations.openMocks(this);
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @ParameterizedTest
    @MethodSource("getBorrarLinkSource")
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink(ParametriaUsuarioDelegado param, Integer id , String estado, String user, Date fdesde, Date fhasta, String resultTest) {
        param.setId(id);
        param.setDelegadoEstado(estado);
        param.setDelegadoUser(user);
        param.setFeDesde(fdesde);
        param.setFeHasta(fhasta);
        currentRowObject = param;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getBorrarLink();

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getDestinatariosLink")
    void getDestinatariosLink() {
        assertEquals("",decorator.getDestinatariosLink());
    }

    @Test
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink() {
        assertEquals("",decorator.getCuponesLink());
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        assertEquals("",decorator.getScanLink());
    }

    @Test
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink() {
        assertEquals(null,decorator.getCaratulaLink());
    }
}