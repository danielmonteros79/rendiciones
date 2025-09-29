package com.sa.decorator;

import com.sa.decorator.ComentariosDecorator;
import com.sa.entities.Gastos;
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

class ComentariosDecoratorTest {

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
    private ComentariosDecorator decorator;

    @BeforeEach
    void setup(){
        Gastos sector = new Gastos();
        currentRowObject = sector;
        decorator = new ComentariosDecorator();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        assertEquals(null, decorator.getVerLink());
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        assertEquals(null, decorator.getEditarLink());
    }

    @Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
        assertEquals(null, decorator.getBorrarLink());
    }

    @Test
    @DisplayName("Testeando getDestinatariosLink")
    void getDestinatariosLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getDestinatariosLink();

        String resultTest = "<a href=\"contextPath/descripcionObligatoriaPopup.do?action=descripcionObligatoriaPopup&codigo=\"><a href=\"#\" onclick=\"showDescripcionObligatoriaPopup()\"><img src=\"contextPath/images/iconos/message.png\" alt=\"Descripcion\" title=\"Descripcion\" border=\"0\" /> </a></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getCuponesLink")
    void getCuponesLink() {
        assertEquals(null, decorator.getCuponesLink());
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        assertEquals(null, decorator.getScanLink());
    }

    @Test
    @DisplayName("Testeando getCaratulaLink")
    void getCaratulaLink() {
        assertEquals(null, decorator.getCaratulaLink());
    }
}