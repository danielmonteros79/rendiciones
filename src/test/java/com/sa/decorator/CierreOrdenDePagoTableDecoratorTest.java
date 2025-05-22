package com.sa.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.sa.entities.Rendicion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

class CierreOrdenDePagoTableDecoratorTest {

    @Mock
    PageContext pageContext;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Mock
    ServletContext servletContext;

    @InjectMocks
    CierreOrdenDePagoTableDecorator cierreOrdenDePagoTableDecorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void testGetEditarLink() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);
        rendicion.setUsuarioRendicion("usuarioRendicion");

        when(pageContext.getRequest()).thenReturn(request);
        when(request.getContextPath()).thenReturn("contextPath");

        cierreOrdenDePagoTableDecorator.init(pageContext, null, null);
        cierreOrdenDePagoTableDecorator.initRow(rendicion, 0, 0);

        String result = cierreOrdenDePagoTableDecorator.getEditarLink();

        assertEquals("<a href=\"contextPath/cierreDetalle.do?codigo=1&usuarioRend=usuarioRendicion\"><i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i></a>",result);
    }


    @Test
    @DisplayName("Testeando getThubanLink")
    void testGetThubanLink() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);

        when(pageContext.getRequest()).thenReturn(request);
        when(request.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("rendicion.link.thuban")).thenReturn("linkThuban");

        cierreOrdenDePagoTableDecorator.init(pageContext, null, null);
        cierreOrdenDePagoTableDecorator.initRow(rendicion, 0, 0);

        String result = cierreOrdenDePagoTableDecorator.getThubanLink();

        assertEquals("<a href=\"#a\" class=\"text-gray\" onclick=\"showThuban('linkThuban1')\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Thuban\"></i></a>",result);
    }

    @Test
    @DisplayName("Testeando getJournalLink")
    void testGetJournalLink() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);

        cierreOrdenDePagoTableDecorator.init(pageContext, null, null);
        cierreOrdenDePagoTableDecorator.initRow(rendicion, 0, 0);

        String result = cierreOrdenDePagoTableDecorator.getJournalLink();

        assertEquals("<a href=\"#a\" class=\"text-gray\" onclick=\"modalJournalShow('1')\"><i class=\"bbva-icon icon-coronita_bookstore fa-lg\" data-toggle=\"tooltip\" title=\"Journal\"></i></a>",result);
    }

    @Test
    @DisplayName("Testeando getOpciones")
    void testGetOpciones() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);
        rendicion.setUsuarioRendicion("usuarioRendicion");

        when(pageContext.getRequest()).thenReturn(request);
        when(request.getContextPath()).thenReturn("contextPath");
        when(request.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("rendicion.link.thuban")).thenReturn("linkThuban");

        cierreOrdenDePagoTableDecorator.init(pageContext, null, null);
        cierreOrdenDePagoTableDecorator.initRow(rendicion, 0, 0);

        String result = cierreOrdenDePagoTableDecorator.getOpciones();

        assertEquals("<a href=\"contextPath/cierreDetalle.do?codigo=1&usuarioRend=usuarioRendicion\"><i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i></a>&nbsp;<a href=\"#a\" class=\"text-gray\" onclick=\"showThuban('linkThuban1')\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Thuban\"></i></a>&nbsp;<a href=\"#a\" class=\"text-gray\" onclick=\"modalJournalShow('1')\"><i class=\"bbva-icon icon-coronita_bookstore fa-lg\" data-toggle=\"tooltip\" title=\"Journal\"></i></a>",result);
    }

    @Test
    @DisplayName("Testeando getCheck")
    void testGetCheck() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);

        cierreOrdenDePagoTableDecorator.init(pageContext, null, null);
        cierreOrdenDePagoTableDecorator.initRow(rendicion, 0, 0);

        String result = cierreOrdenDePagoTableDecorator.getCheck();

        assertEquals("<input type='checkbox' value='1' onclick=\"clickCheckbox(1, this)\">",result);
    }

    @Test
    void testConstructor() {
        CierreOrdenDePagoTableDecorator actualCierreOrdenDePagoTableDecorator = new CierreOrdenDePagoTableDecorator();
        assertEquals("", actualCierreOrdenDePagoTableDecorator.getBorrarLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getCaratulaLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getCuponesLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getDestinatariosLink());
        assertNull(actualCierreOrdenDePagoTableDecorator.getScanLink());
        assertEquals("", actualCierreOrdenDePagoTableDecorator.getVerLink());
    }
}

