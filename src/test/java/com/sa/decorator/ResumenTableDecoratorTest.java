package com.sa.decorator;

import com.sa.entities.parametros.Resumen;
import org.apache.struts.mock.MockHttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.swing.table.TableModel;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResumenTableDecoratorTest {
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
    ResumenTableDecorator decorator;

    @BeforeEach
    void setUp() {
        Resumen resumen = new Resumen();
        resumen.setEstado("A DEBITAR");
        resumen.setIdRendicion("1");
        currentRowObject = resumen;
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEstado() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String harcodedResult ="<div class='balloon' style='color:red; font-weight:bold;' title='El consumo se debitar&aacute; en caso de no rendirse'>A DEBITAR&nbsp;&nbsp;&nbsp;<img class='blink' style='width:18px;vertical-align:middle;' src='contextPath/images/Warning_48x48.png' /></div>";

        String result = decorator.getEstado();
        Assertions.assertEquals(harcodedResult, result);
    }
    @Test
    void getVerLink() {
        String resultTest = decorator.getVerLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        String resultTest = decorator.getEditarLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
                String resultTest = decorator.getBorrarLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getDestinatariosLink")
    void getDestinatariosLink() {
                String resultTest = decorator.getDestinatariosLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink() {
                String resultTest = decorator.getCuponesLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
                String resultTest = decorator.getScanLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink() {
                String resultTest = decorator.getCaratulaLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }
}