package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CierreTarjetaTest {

    private CierreTarjeta cierreTarjeta = new CierreTarjeta();
    @BeforeEach
    void setUp() {
        cierreTarjeta = new CierreTarjeta();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getIdResumen y setIdResumen no den null y devuelvan el mismo dato")
    void getIdSecResumen() {
        cierreTarjeta.setIdSecResumen(123);
        int resultSetIdSecResumen = cierreTarjeta.getIdSecResumen();
        assertAll(
                ()->assertNotNull(resultSetIdSecResumen),
                ()->assertEquals(123, resultSetIdSecResumen)
        );
    }

    @Test
    @DisplayName("Testeando getNroTarjeta y setNroTarjeta no den null y devuelvan el mismo dato")
    void getNroTarjeta() {
        cierreTarjeta.setNroTarjeta("nroTarjeta");
        String resultSetNroTarjeta = cierreTarjeta.getNroTarjeta();
        assertAll(
                ()->assertNotNull(resultSetNroTarjeta),
                ()->assertEquals("nroTarjeta",resultSetNroTarjeta)
        );
    }

    @Test
    @DisplayName("Testeando setFechaCupon y getFechaCupon no den null y devuelvan el mismo dato")
    void getFechaCupon() {
        cierreTarjeta.setFechaCupon(new Date(2023-1-1));
        Date resultSetFechaCupon = cierreTarjeta.getFechaCupon();
        assertAll(
                ()->assertNotNull(resultSetFechaCupon),
                ()->assertEquals(new Date(2023-1-1 ), resultSetFechaCupon)
        );
    }

    @Test
    @DisplayName("Testeando setCuponTarjeta y getCuponTarjeta no de null y devuelva el mismo dato")
    void getCuponTarjeta() {
        cierreTarjeta.setCuponTarjeta("cuponTarjeta");
        String resultSetCuponTarjeta = cierreTarjeta.getCuponTarjeta();
        assertAll(
                ()->assertNotNull(resultSetCuponTarjeta),
                ()->assertEquals("cuponTarjeta", resultSetCuponTarjeta)
        );
    }

    @Test
    @DisplayName("Testeando setCuponAdmDev y getCuponAdmDev no den null y devuelvan el mismo dato")
    void getCuponAdmDev() {
        cierreTarjeta.setCuponAdmDev("cuponAdmDev");
        String resultSetCuponAdmDev = cierreTarjeta.getCuponAdmDev();
        assertAll(
                ()->assertNotNull(resultSetCuponAdmDev),
                ()->assertEquals("cuponAdmDev", resultSetCuponAdmDev)
        );
    }

    @Test
    @DisplayName("Testeando setCuponAdmCred y getCuponAdmCred no den null y devuelvan el mismo dato")
    void getCuponAdmCred() {
        cierreTarjeta.setCuponAdmCred("cuponAdmCred");
        String resultSetCuponAdmCred = cierreTarjeta.getCuponAdmCred();
        assertAll(
                ()->assertNotNull(resultSetCuponAdmCred),
                ()->assertEquals("cuponAdmCred", resultSetCuponAdmCred)
        );
    }

    @Test
    @DisplayName("Testeando setEstablecimiento y getEstablecimiento no den null y devuelvan el mismo dato")
    void getEstablecimiento() {
        cierreTarjeta.setEstablecimiento("establecimiento");
        String resultSetEstablecimiento = cierreTarjeta.getEstablecimiento();
        assertAll(
                ()-> assertNotNull(resultSetEstablecimiento),
                ()->assertEquals("establecimiento", resultSetEstablecimiento)
        );
    }

    @Test
    @DisplayName("Testeando setMontoCupon y getMontoCupon no den null y devuelvan el mismo dato")
    void getMontoCupon() {
        cierreTarjeta.setMontoCupon(123D);
        double resultSetMontoCupon = cierreTarjeta.getMontoCupon();
        assertAll(
                ()->assertNotNull(resultSetMontoCupon),
                ()->assertEquals(123D,resultSetMontoCupon)
        );
    }

    @Test
    @DisplayName("Testeando setMoneda y getMoneda no den null y devuelvan el mismo dato")
    void getMoneda() {
        cierreTarjeta.setMoneda("moneda");
        String resultSetMoneda = cierreTarjeta.getMoneda();
        assertAll(
                ()->assertNotNull(resultSetMoneda),
                ()->assertEquals("moneda",resultSetMoneda)
        );
    }

    @Test
    @DisplayName("Testeando setImporteGtosRend y getImporteGtosRend")
    void getImporteGtosRend() {
        cierreTarjeta.setImporteGtosRend("importeGtosRend");
        String resultSetImporteGtosRend = cierreTarjeta.getImporteGtosRend();
        assertAll(
                ()->assertNotNull(resultSetImporteGtosRend),
                ()->assertEquals("importeGtosRend", resultSetImporteGtosRend)
        );
    }

    @Test
    @DisplayName("Testeando setFechaResumen y getFechaResumen no den null y devuelvan el mimso dato")
    void getFechaResumen() {
        cierreTarjeta.setFechaResumen(new Date(2023-1-1));
        Date resultSetFechaResumen = cierreTarjeta.getFechaResumen();
        assertAll(
                ()->assertNotNull(resultSetFechaResumen),
                ()->assertEquals(new Date(2023-1-1), resultSetFechaResumen)
        );
    }

    @Test
    @DisplayName("testeando setEstadoResumen y getEstadoResumen no den null y devuelvan el mismo dato")
    void getEstadoResumen() {
        cierreTarjeta.setEstadoResumen("estadoResumen");
        String resultSetEstadoResumen = cierreTarjeta.getEstadoResumen();
        assertAll(
                ()->assertNotNull(resultSetEstadoResumen),
                ()->assertEquals("estadoResumen",resultSetEstadoResumen)
        );
    }

    @Test
    @DisplayName("Testeando setFechaCierre y getFechaCierre no den null y devuelvan el mismo dato")
    void getFechaCierre() {
        cierreTarjeta.setFechaCierre(new Date(2023-1-1));
        Date resultSetFechaCierre = cierreTarjeta.getFechaCierre();
        assertAll(
                ()->assertNotNull(resultSetFechaCierre),
                ()->assertEquals(new Date(2023-1-1),resultSetFechaCierre)
        );
    }

    @Test
    @DisplayName("Testeando setFechaAlta y getFechaAlta no den null y devuelvan el mismo dato")
    void getFechaAlta() {
        cierreTarjeta.setFechaAlta("fechaAlta");
        String resultFechaAlta = cierreTarjeta.getFechaAlta();
        assertAll(
                ()->assertNotNull(resultFechaAlta),
                ()->assertEquals("fechaAlta",resultFechaAlta)
        );
    }

    @Test
    @DisplayName("Testeando setUserAlta y getUserAlta no den null y devuelvan el mismo dato")
    void getUserAlta() {
        cierreTarjeta.setUserAlta("userAlta");
        String resultadoSetUserAlta = cierreTarjeta.getUserAlta();
        assertAll(
                ()->assertNotNull(resultadoSetUserAlta),
                ()->assertEquals("userAlta",resultadoSetUserAlta)
        );
    }

    @Test
    @DisplayName("Testeando setFechaultModif y getFechaUltModif no den null y devuelvan el mismo dato")
    void getFechaUltModif() {
        cierreTarjeta.setFechaUltModif("fechaUltModif");
        String resultSetFechaUltModif = cierreTarjeta.getFechaUltModif();
        assertAll(
                ()->assertNotNull(resultSetFechaUltModif),
                ()->assertEquals("fechaUltModif", resultSetFechaUltModif)
        );
    }

    @Test
    @DisplayName("Testeando setUsuario y getUsuario")
    void getUsuario() {
        cierreTarjeta.setUsuario("usuario");
        String resultSetUsuario = cierreTarjeta.getUsuario();
        assertAll(
                ()->assertNotNull(resultSetUsuario),
                ()->assertEquals("usuario", resultSetUsuario)
        );
    }
}