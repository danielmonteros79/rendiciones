package com.sa.decorator;

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

class SumTableDecoratorTest {

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
    SumTableDecorator decorator = new SumTableDecorator() {
        @Override
        protected String getVerLink() {
            return "";
        }

        @Override
        protected String getEditarLink() {
            return "";
        }

        @Override
        protected String getBorrarLink() {
            return "";
        }

        @Override
        protected String getDestinatariosLink() {
            return "";
        }

        @Override
        protected String getCuponesLink() {
            return "";
        }

        @Override
        protected String getScanLink() {
            return "";
        }

        @Override
        protected String getCaratulaLink() {
            return "";
        }
    };

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        assertEquals("",decorator.getVerLink());
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        assertEquals("",decorator.getEditarLink());
    }

    @Test
    @DisplayName("Testeando getBorrarLink")
    void getBorrarLink() {
        assertEquals("",decorator.getBorrarLink());
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
        assertEquals("",decorator.getCaratulaLink());
    }

    @Test
    @DisplayName("Testeando getOpciones")
    void getOpciones() {
        assertEquals("&nbsp;&nbsp;",decorator.getOpciones());
    }

    @Test
    @DisplayName("Testeando getScan")
    void getScan() {
        assertEquals("",decorator.getScan());
    }

    @Test
    @DisplayName("Testeando getCaratula")
    void getCaratula() {
        assertEquals("",decorator.getCaratula());
    }

    @Test
    @DisplayName("Testeando getComentarios")
    void getComentarios() {
        assertEquals("",decorator.getComentarios());
    }

    @Test
    @DisplayName("Testeando getCupones")
    void getCupones() {
        assertEquals("",decorator.getCupones());
    }
}