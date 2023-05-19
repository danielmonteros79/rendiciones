package com.sa.decorator;

import com.sa.entities.Rendicion;
import com.sa.form.RendicionForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.swing.table.TableModel;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CierreTableDecoratorTest {

    @Spy
    private Object currentRowObject;
    @Mock
    private Map propertyMap;
    @Mock
    private PageContext pageContext;
    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext context;

    @Mock
    private Object decoratedObject;
    @Mock
    private TableModel tableModel;

    @InjectMocks
    private CierreTableDecorator decorator;

    @BeforeEach
    void setup(){
        Rendicion rendicion = new Rendicion();
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);
    };

    @Test
    @DisplayName("Testeando getVerLink")
    void getVerLink() {
        assertEquals("",decorator.getVerLink());
    }

    @Test
    @DisplayName("Testeando getEditarLink")
    void getEditarLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getEditarLink();

        String resultTest = "<a href=\"contextPath/aprobacionDetalle.do?action=aprobacionDetalle&codigo=null&hide=1&usuarioRendicion=null\"><img width='24px' height='24px' src=\"contextPath/images/search_button_32x32.png\" alt=\"Ver detalle\" title=\"Ver detalle\" border=\"0\" /></a></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @Disabled
    void getThubanLink() {
        String linkThuban = "";
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("rendicion.link.thuban")).thenReturn(linkThuban);

        String result = decorator.getThubanLink();

        String resultTest = "<a href=\"#\" onclick=\"showThuban('','null')\"><img src=\"contextPath/images/iconos/info.png\" alt=\"Thuban\" title=\"Thuban\" border=\"0\" style=\"margin-bottom:4px;\"/>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
    }

    @Test
    @DisplayName("Testeando getJournalLink")
    void getJournalLink() {
        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        String result = decorator.getJournalLink();

        String resultTest = "<a href=\"#\" onclick=\"showJournal('null')\"><img width='20px' height='20px' style='margin-bottom:2px;' src=\"contextPath/images/iconos/journal.png\" alt=\"Journal\" title=\"Journal\" border=\"0\" /></a>";

        assertAll(
                ()->assertNotNull(result),
                ()->assertEquals(resultTest, result)
        );
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
    void getCuponesLink() {
        String result = decorator.getCuponesLink();
        String resultTest ="<input type=\"checkbox\" name=\"asignada\" value=\"on\" onclick=\"checkRendiciones(this)\" id=\"checkCierre\">";
        assertEquals(resultTest,result);
    }

    @Test
    @DisplayName("Testeando getScanLink")
    void getScanLink() {
        assertEquals("",decorator.getScanLink());
    }

    @Test
    void getCaratulaLink() {
        assertEquals(null,decorator.getCaratulaLink());
    }

    @Test
    void getOpciones() {
    }
}