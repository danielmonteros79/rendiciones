package com.sa.decorator;

import com.sa.entities.Rendicion;
import org.displaytag.model.TableModel;
import org.junit.jupiter.api.Assertions;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        //then
        String result = aprobacionesTableDecorator.getDestinatariosLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get cupones link")
    void getCuponesLink() throws Exception {
        Rendicion rendicion = new Rendicion();
        rendicion.setAdea("1adea");

        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");

        String result = aprobacionesTableDecorator.getCuponesLink();
        Assertions.assertEquals("<a href=\"#a\" class=\"text-gray\" onclick=\"obtenerDetalleAlerta(null,1adea)\"><img width='25px' src='contextPath/images/iconos/alerta_riesgo_grave.png' alt='riesgo' data-toggle='tooltip' title='Riesgo'/></a>", result);
    }

    @Test
    @DisplayName("Testeando get scan link")
    void getScanLink() {
        String result = aprobacionesTableDecorator.getScanLink();
        assertNull(result);
    }

    @Test
    @DisplayName("Testeando get caratula link")
    void getCaratulaLink() {
        String result = aprobacionesTableDecorator.getCaratulaLink();
        assertNull(result);
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

    @Test
    @DisplayName("Should getCheck")
    void shouldGetCheck() {
        Rendicion rendicion = new Rendicion();
        rendicion.setId(1);

        currentRowObject = rendicion;
        MockitoAnnotations.openMocks(this);

        String stringToAssert = aprobacionesTableDecorator.getCheck();
        assertEquals("<input class='seleccionar-todo' type='checkbox' value='1' onchange=\"clickCheckbox(1, this)\" \"' >", stringToAssert);
    }

    @Disabled("No se puede asegurar que el objeto a castear sea una instancia de Rendicion. Refactorizar para poder testear")
    @Test
    @DisplayName("Should getChecks")
    void shouldGetChecks() {
        //then
        String stringToAssert = aprobacionesTableDecorator.getChecks();
        assertEquals("", stringToAssert);
    }

    @ParameterizedTest
    @MethodSource("getAlertasSource")
    @DisplayName("Should getAlertas")
    void shouldGetAlertas(Rendicion rend, String res) {

        currentRowObject = rend;
        MockitoAnnotations.openMocks(this);

        when(pageContext.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getContextPath()).thenReturn("contextPath");

        String stringToAssert = aprobacionesTableDecorator.getAlertas();
        assertEquals(res, stringToAssert);
    }

    @Test
    @DisplayName("Should getScan")
    void shouldGetScan() {
        //then
        String stringToAssert = aprobacionesTableDecorator.getScan();
        assertNull(stringToAssert);
    }

    @Test
    @DisplayName("Should get caratula")
    void shouldGetCaratula() {
        //then
        String stringToAssert = aprobacionesTableDecorator.getCaratula();
        assertNull(stringToAssert);
    }

    @Test
    @DisplayName("Should get comentarios")
    void shouldGetComentarios() {
        //then
        String stringToAssert = aprobacionesTableDecorator.getComentarios();
        assertNull(stringToAssert);
    }

    @Disabled("No se puede asegurar que el objeto a castear sea una instancia de Rendicion. Refactorizar para poder testear")
    @Test
    @DisplayName("Should get cupones")
    void shouldGetCupones() {
        //then
        String stringToAssert = aprobacionesTableDecorator.getCupones();
        assertEquals("", stringToAssert);
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
        String resultado = "<a href=\"contextPath/aprobacionDetalle.do?codigo=1&glg=null\"><i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i></a>";

        return Stream.of(
                Arguments.of(usuarioRendicion, id, estado, contextPath, resultado)
        );
    }

    private static Stream<Arguments> getThubanLinkSource(){
        Integer id = 1;
        String linkThuban = "linkThuban";
        String respuesta = "<a href=\"#a\" class=\"text-gray\" onclick=\"showThuban('linkThuban1')\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Thuban\"></i></a>";

        return Stream.of(
                Arguments.of(id, linkThuban, respuesta)
        );
    }

    private static Stream<Arguments> getJournalLinkSource(){
        Integer id = 1;
        String contextPath = "contextPath";
        String respuesta = "<a href=\"#a\" class=\"text-gray\" onclick=\"modalJournalShow('1')\"><i class=\"bbva-icon icon-coronita_bookstore fa-lg\" data-toggle=\"tooltip\" title=\"Journal\"></i></a>";

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
        String resultado = "<a href=\"contextPath/aprobacionDetalle.do?codigo=1&glg=null\"><i class=\"bbva-icon icon-coronita_search\" data-toggle=\"tooltip\" title=\"Ver\"></i></a>&nbsp;<a href=\"#a\" class=\"text-gray\" onclick=\"showThuban('linkThuban1')\"><i class=\"bbva-icon icon-uniE0D2 fa-lg\" data-toggle=\"tooltip\" title=\"Thuban\"></i></a>&nbsp;<a href=\"#a\" class=\"text-gray\" onclick=\"modalJournalShow('1')\"><i class=\"bbva-icon icon-coronita_bookstore fa-lg\" data-toggle=\"tooltip\" title=\"Journal\"></i></a>";

        return Stream.of(
                Arguments.of(usuarioRendicion, id, estado, contextPath, linkThuban, resultado)
        );
    }

    private static Stream<Arguments> getAlertasSource(){
        Rendicion rend = new Rendicion();
        rend.setAlerta("1");

        Rendicion rend2 = new Rendicion();
        rend2.setAlerta("2");

        Rendicion rend3 = new Rendicion();
        rend3.setAlerta("3");

        Rendicion rend4 = new Rendicion();
        rend4.setAlerta("4");

        Rendicion rend5 = new Rendicion();
        rend5.setAlerta("5");

        String res = "<img width='25px' src='contextPath/images/iconos/alerta_riesgo_grave.png' alt='Riesgo grave' title='Riesgo grave'/>";
        String res2 = "<img width='25px' src='contextPath/images/iconos/alerta_riesgo.png' alt='Riesgo' title='Riesgo'/>";
        String res3 = "<img width='25px' src='contextPath/images/iconos/alerta_incidencia_grave.png' alt='Incidencia grave' title='Incidencia grave'/>";
        String res4 = "<img width='25px' src='contextPath/images/iconos/alerta_incidente.png' alt='Incidente' title='Incidente'/>";
        String res5 = "<img width='25px' src='contextPath/images/iconos/alerta_anomalia.png' alt='Anomal&iacute;a' title='Anomal&iacute;a'/>";

        return Stream.of(
                Arguments.of(rend, res),
                Arguments.of(rend2, res2),
                Arguments.of(rend3, res3),
                Arguments.of(rend4, res4),
                Arguments.of(rend5, res5)
        );
    }

}

