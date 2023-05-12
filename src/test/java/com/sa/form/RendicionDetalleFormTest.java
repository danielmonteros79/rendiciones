package com.sa.form;

import com.sa.form.RendicionDetalleForm;
import com.sa.form.RendicionForm;
import org.apache.struts.action.ActionForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

class RendicionDetalleFormTest {

    @Mock
    ActionForm actionFormMock;

    private RendicionDetalleForm service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new RendicionDetalleForm();
    }

    @Test
    void inicializarCupon() {
        service.setCupon("cupon");
        service.setCupCred("cuponCred");
        service.setDescCupon("descCupon");
        service.setImporteCupon("ImporteCupon");
        service.inicializarCupon();

        assertAll(
                ()->assertEquals(service.getCupon(),null),
                ()->assertEquals(service.getCupCred(),null),
                ()->assertEquals(service.getDescCupon(),null),
                ()->assertEquals(service.getImporteCupon(),null)
        );
    }

    @Test
    @DisplayName("Testeando set y get CupDeb")
    void setygetCupDeb() {
        service.setCupDeb("CupDeb");
        String resultTest=service.getCupDeb();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CupDeb")
        );
    }

    @Test
    @DisplayName("Testeando set y getCupon")
    void setygetCupon() {
        service.setCupon("Cupon");
        String resultTest = service.getCupon();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Cupon")
        );
    }

    @Test
    @DisplayName("Testeando set y get DescCupon")
    void setygetDescCupon() {
        service.setDescCupon("DescCupon");
        String resultTest = service.getDescCupon();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"DescCupon")
        );
    }

    @Test
    @DisplayName("Testeando set y get ImporteCupon")
    void setygetImporteCupon() {
        service.setImporteCupon("ImporteCupon");
        String resultTest = service.getImporteCupon();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"ImporteCupon")
        );
    }

    @Test
    @DisplayName("Testeando set y get NroTarjeta")
    void setygetNroTarjeta() {
        service.setNroTarjeta("Nro Tarjeta");
        String resultTest = service.getNroTarjeta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Nro Tarjeta")
        );
    }

    @Test
    @DisplayName("Testeando set y get Gastos")
    void setygetGastos() {
        service.setGastos("Gastos");
        String resultTest = service.getGastos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Gastos")
        );
    }

    @Test
    @DisplayName("Testeando set y get Monto")
    void setygetMonto() {
        service.setMonto("Monto");
        String resultTest = service.getMonto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Monto")
        );
    }

    @Test
    @DisplayName("Testeando set y get Moneda")
    void setygetMoneda() {
        service.setMoneda("Moneda");
        String resultTest = service.getMoneda();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Moneda")
        );
    }

    @Test
    @DisplayName("Testeando set y get Fechagastos")
    void setygetFechagastos() {
        service.setFechagastos("Fecha Gastos");
        String resultTest = service.getFechagastos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Fecha Gastos")
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante")
    void setygetComprobante() {
        service.setComprobante("Comprobante");
        String resultTest = service.getComprobante();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Comprobante")
        );
    }

    @Test
    @DisplayName("Testeando set y get CmbComprobante")
    void setygetCmbComprobante() {
        service.setCmbComprobante("CmbComprobante");
        String resultTest = service.getCmbComprobante();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CmbComprobante")
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante1")
    void setygetComprobante1() {
        service.setComprobante1("Comprobante1");
        String resultTest = service.getComprobante1();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Comprobante1")
        );
    }

    @Test
    @DisplayName("Testeando set y get Comprobante2")
    void setygetComprobante2() {
        service.setComprobante2("Comprobante2");
        String resultTest = service.getComprobante2();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Comprobante2")
        );
    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void setygetIdRendicion() {
        service.setIdRendicion("IdRendicion");
        String resultTest = service.getIdRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"IdRendicion")
        );
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void setygetCodMotivo() {
        service.setCodMotivo("CodMotivo");
        String resultTest = service.getCodMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CodMotivo")
        );
    }

    @Test
    @DisplayName("Testeando set y get CentroCostos")
    void setygetCentroCostos() {
        service.setCentroCostos("CentroCostos");
        String resultTest = service.getCentroCostos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CentroCostos")
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaD")
    void setygetFechaD() {
        service.setFechaD("FechaD");
        String resultTest = service.getFechaD();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FechaD")
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaH")
    void setygetFechaH() {
        service.setFechaH("FechaH");
        String resultTest = service.getFechaH();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FechaH")
        );
    }

    @Test
    @DisplayName("Testeando set y get Opcion")
    void setygetOpcion() {
        service.setOpcion("Opcion");
        String resultTest = service.getOpcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Opcion")
        );
    }

    @Test
    @DisplayName("Testeando set y get IdG")
    void setygetIdG() {
        service.setIdG("IdG");
        String resultTest = service.getIdG();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"IdG")
        );
    }

    @Test
    @DisplayName("Testeando set y get CuponesCheck")
    void setygetCuponesCheck() {
        service.setCuponesCheck("CuponesCheck");
        String resultTest = service.getCuponesCheck();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CuponesCheck")
        );
    }

    @Test
    @DisplayName("Testeando set y get CupCred")
    void setygetCupCred() {
        service.setCupCred("CupCred");
        String resultTest = service.getCupCred();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CupCred")
        );
    }

    @Test
    @DisplayName("Testeando set y get EstadoRendicion")
    void setygetEstadoRendicion() {
        service.setEstadoRendicion("EstadoRendicion");
        String resultTest = service.getEstadoRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"EstadoRendicion")
        );
    }

    @Test
    @DisplayName("Testeando set y get ObservacionGasto")
    void setygetObservacionGasto() {
        service.setObservacionGasto("ObservacionGasto");
        String resultTest = service.getObservacionGasto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"ObservacionGasto")
        );
    }

    @Test
    @DisplayName("Testeando set y get UsuarioRend")
    void setygetUsuarioRend() {
        service.setUsuarioRend("UsuarioRend");
        String resultTest = service.getUsuarioRend();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"UsuarioRend")
        );
    }

    @Test
    @DisplayName("Testeando set y get Glg")
    void setygetGlg() {
        service.setGlg("Glg");
        String resultTest = service.getGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Glg")
        );
    }

    @Test
    @DisplayName("Testeando set y get Monto Maximo")
    void setygetMontoMaximo() {
        service.setMontoMaximo("MontoMaximo");
        String resultTest = service.getMontoMaximo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"MontoMaximo")
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit1")
    void setygetCuit1() {
        service.setCuit1("Cuit1");
        String resultTest = service.getCuit1();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Cuit1")
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit2")
    void setygetCuit2() {
        service.setCuit2("Cuit2");
        String resultTest = service.getCuit2();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Cuit2")
        );
    }

    @Test
    @DisplayName("Testeando set y get Cuit3")
    void setygetCuit3() {
        service.setCuit3("Cuit3");
        String resultTest = service.getCuit3();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Cuit3")
        );
    }

    @Test
    @DisplayName("Testeando set y get Aviso")
    void setygetAviso() {
        service.setAviso("Aviso");
        String resultTest = service.getAviso();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Aviso")
        );
    }

    @Test
    @DisplayName("Testeando set y get ThubanLink")
    void setygetThubanLink() {
        service.setThubanLink("ThubanLink");
        String resultTest = service.getThubanLink();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"ThubanLink")
        );
    }

    @Test
    @DisplayName("Testeando set y get Accion")
    void setygetAccion() {
        service.setAccion("Accion");
        String resultTest = service.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Accion")
        );
    }

    @Test
    @DisplayName("Testeando set y get User")
    void setygetUser() {
        service.setUser("User");
        String resultTest = service.getUser();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"User")
        );
    }

    @Test
    @DisplayName("Testeando set y get NombreUsuario")
    void setygetNombreUsuario() {
        service.setNombreUsuario("NombreUsuario");
        String resultTest = service.getNombreUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"NombreUsuario")
        );
    }

    @Test
    @DisplayName("Testeando set y get CostosDestino")
    void setygetCostosDestino() {
        service.setCostosDestino("CostosDestino");
        String resultTest = service.getCostosDestino();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CostosDestino")
        );
    }

    @Test
    @DisplayName("Testeando set y get Costos")
    void setygetCostos() {
        service.setCostos(1);
        Integer resultTest = service.getCostos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,1)
        );
    }

    @Test
    @DisplayName("Testeando set y get EsAdelanto")
    void setygetEsAdelanto() {
        service.setEsAdelanto("EsAdelanto");
        String resultTest = service.getEsAdelanto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"EsAdelanto")
        );
    }

}