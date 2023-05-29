package com.sa.decorator;

import com.sa.entities.Gastos;
import com.sa.entities.Rendicion;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class AprobacionesTableDecoratorTest {
    @Spy
    Object currentRowObject;
    @Mock
    Map propertyMap;
    @Mock
    PageContext pageContext;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext context;
    @Mock
    Object decoratedObject;
    @Mock
    TableModel tableModel;
    @InjectMocks
    AprobacionesTableDecorator aprobacionesTableDecorator;

    Rendicion rendicion;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando get ver link")
    void getVerLink() {
        String result = aprobacionesTableDecorator.getVerLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get borrar link")
    void getBorrarLink() {
        String result = aprobacionesTableDecorator.getBorrarLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get destinatarios link")
    void getDestinatariosLink() {
        String result = aprobacionesTableDecorator.getDestinatariosLink();
        Assertions.assertEquals("", result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() {
        String result = aprobacionesTableDecorator.getCuponesLink();
        Assertions.assertEquals("<input type=\"checkbox\" name=\"asignada\" value=\"on\" onclick=\"checkRendiciones(this)\" id=\"checkAprobacion\">", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = aprobacionesTableDecorator.getScanLink();
        Assertions.assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("getCaratulaLinkSource")
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink(String idu, String adea, Integer id, String contextPath, String resultado) {
        rendicion = new Rendicion();
        rendicion.setIdu(idu);
        rendicion.setAdea(adea);
        rendicion.setId(id);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = aprobacionesTableDecorator.getCaratulaLink();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getEditarLinkSource")
    @DisplayName("Testeando get editar link")
    void getEditarLink(String usuarioRendicion, Integer id, String estado, String contextPath, String resultado) {
        rendicion = new Rendicion();
        rendicion.setUsuarioRendicion(usuarioRendicion);
        rendicion.setId(id);
        rendicion.setEstado(estado);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = aprobacionesTableDecorator.getEditarLink();
        Assertions.assertEquals(resultado, result);
    }

    @ParameterizedTest
    @MethodSource("getThubanLinkSource")
    @DisplayName("Testeando get thuban link")
    void getThubanLink(Integer id, String linkThuban, String respuesta) {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(id);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("rendicion.link.thuban")).thenReturn(linkThuban);


        String result = aprobacionesTableDecorator.getThubanLink();
        Assertions.assertEquals(respuesta, result);
    }

    @ParameterizedTest
    @MethodSource("getJournalLinkSource")
    @DisplayName("Testeando get journal link")
    void getJournalLink(Integer id,String contextPath,String respuesta) {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(id);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);

        String result = aprobacionesTableDecorator.getJournalLink();
        Assertions.assertEquals(respuesta, result);
    }

    @ParameterizedTest
    @MethodSource("getOpcionesSource")
    @DisplayName("Testeando get opciones link")
    void getOpciones(String usuarioRendicion, Integer id, String estado, String contextPath, String linkThuban, String resultado) {
        rendicion = new Rendicion();
        rendicion.setUsuarioRendicion(usuarioRendicion);
        rendicion.setId(id);
        rendicion.setEstado(estado);
        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn(contextPath);
        when(httpServletRequest.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("rendicion.link.thuban")).thenReturn(linkThuban);

        String result = aprobacionesTableDecorator.getOpciones();
        Assertions.assertEquals(resultado, result);
    }

    // ------ Sources ------

    private static Stream<Arguments> getCaratulaLinkSource() {
        String idu = "idu";
        String idu2 = null;
        String idu3 = "";
        String adea = "adea";
        String adea2 = null;
        String adea3 = "";
        Integer id = 1;
        String contextPath = "contextPath";
        String resultado = "<a href=\"contextPath/rendicionAviso.do?generate=anymode&rnd=1\"><img src=\"contextPath/images/iconos/pdf.png\" alt=\"Caratula\" title=\"Caratula\" border=\"0\" /></a>";
        String resultado2 = "";

       return Stream.of(
                Arguments.of(idu, adea, id, contextPath, resultado),
                Arguments.of(idu2, adea, id, contextPath, resultado2),
                Arguments.of(idu3, adea, id, contextPath, resultado2),
                Arguments.of(idu, adea2, id, contextPath, resultado2),
                Arguments.of(idu, adea3, id, contextPath, resultado2)
        );
    }

    private static Stream<Arguments> getEditarLinkSource(){
        String usuarioRendicion = "usuarioRendicion";
        Integer id = 1;
        String estado = "estado";
        String contextPath = "contextPath";
        String resultado = "<a href=\"contextPath/aprobacionDetalle.do?action=aprobacionDetalle&codigo=1&usuarioRendicion=usuarioRendicion&glg=null&estadoRend=estado\"><img width='24px' height='24px' src=\"contextPath/images/search_button_32x32.png\" alt=\"Ver detalle\" title=\"Ver detalle\" border=\"0\" /></a></a>";

        return Stream.of(
                Arguments.of(usuarioRendicion, id, estado, contextPath, resultado)
        );
    }

    private static Stream<Arguments> getThubanLinkSource(){
        Integer id = 1;
        String linkThuban = "linkThuban";
        String respuesta = "<a href=\"#\" onclick=\"showThuban('linkThuban','1')\"><img src=\"contextPath/images/iconos/info.png\" alt=\"Thuban\" title=\"Thuban\" border=\"0\" style=\"margin-bottom:4px;\"/>";

        return Stream.of(
                Arguments.of(id, linkThuban, respuesta)
        );
    }

    private static Stream<Arguments> getJournalLinkSource(){
        Integer id = 1;
        String contextPath = "contextPath";
        String respuesta = "<a href=\"#\" onclick=\"showJournal('1')\"><img width='20px' height='20px' style='margin-bottom:2px;' src=\"contextPath/images/iconos/journal.png\" alt=\"Journal\" title=\"Journal\" border=\"0\" /></a>";

        return Stream.of(
                Arguments.of(id, contextPath, respuesta)
        );
    }

    private static Stream<Arguments> getOpcionesSource(){
        String usuarioRendicion = "usuarioRendicion";
        Integer id = 1;
        String estado = "estado";
        String contextPath = "contextPath";
        String linkThuban = "linkThuban";
        String resultado = "<a href=\"contextPath/aprobacionDetalle.do?action=aprobacionDetalle&codigo=1&usuarioRendicion=usuarioRendicion&glg=null&estadoRend=estado\"><img width='24px' height='24px' src=\"contextPath/images/search_button_32x32.png\" alt=\"Ver detalle\" title=\"Ver detalle\" border=\"0\" /></a></a>&nbsp;<a href=\"#\" onclick=\"showThuban('linkThuban','1')\"><img src=\"contextPath/images/iconos/info.png\" alt=\"Thuban\" title=\"Thuban\" border=\"0\" style=\"margin-bottom:4px;\"/>&nbsp;<a href=\"#\" onclick=\"showJournal('1')\"><img width='20px' height='20px' style='margin-bottom:2px;' src=\"contextPath/images/iconos/journal.png\" alt=\"Journal\" title=\"Journal\" border=\"0\" /></a>";

        return Stream.of(
                Arguments.of(usuarioRendicion, id, estado, contextPath, linkThuban, resultado)
        );
    }
}

