package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RendicionTest {

    private Rendicion entity = new Rendicion();
    private List<Gastos> gastosRendicion = new ArrayList<Gastos>();

    @BeforeEach
    void setup(){
        entity = new Rendicion(1,"","",new Date(),new Date(),"",gastosRendicion,"","");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando set y getId")
    void getId() {
        entity.setId(1);
        Integer resultTest = entity.getId();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(1,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMotivo")
    void getMotivo() {
        entity.setMotivo("");
        String resultTest = entity.getMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getDescripcion")
    void getDescripcion() {
        entity.setDescripcion("");
        String resultTest = entity.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaDesde")
    void getFechaDesde() {
        Date fdesde = new Date();
        entity.setFechaDesde(fdesde);
        Date resultTest = entity.getFechaDesde();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(fdesde,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaHasta")
    void getFechaHasta() {
        Date fhasta = new Date();
        entity.setFechaHasta(fhasta);
        Date resultTest = entity.getFechaHasta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(fhasta,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getImporte")
    void getImporte() {
        entity.setImporte("");
        String resultTest = entity.getImporte();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getOpciones")
    void getOpciones() {
        entity.setOpciones("");
        String resultTest = entity.getOpciones();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getGastosRendicion")
    void getGastosRendicion() {
        entity.setGastosRendicion(gastosRendicion);
        List<Gastos> resultTest = entity.getGastosRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(gastosRendicion,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getComentarios")
    void getComentarios() {
        entity.setComentarios("");
        String resultTest = entity.getComentarios();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getEstado")
    void getEstado() {
        entity.setEstado("");
        String resultTest = entity.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getUsuarioRendicion")
    void getUsuarioRendicion() {
        entity.setUsuarioRendicion("");
        String resultTest = entity.getUsuarioRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getIdMotivo")
    void getIdMotivo() {
        entity.setIdMotivo(1);
        Integer resultTest = entity.getIdMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(1,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCodMotivo")
    void getCodMotivo() {
        entity.setCodMotivo("");
        String resultTest = entity.getCodMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getStatusColor")
    void getStatusColor() {
        entity.setStatusColor("");
        String resultTest = entity.getStatusColor();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getIdu")
    void getIdu() {
        entity.setIdu("");
        String resultTest = entity.getIdu();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getAdea")
    void getAdea() {
        entity.setAdea("");
        String resultTest = entity.getAdea();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCaratula")
    void getCaratula() {
        entity.setCaratula("");
        String resultTest = entity.getCaratula();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getUsuarioAprobador")
    void getUsuarioAprobador() {
        entity.setUsuarioAprobador("");
        String resultTest = entity.getUsuarioAprobador();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getDescripcionEstado")
    void getDescripcionEstado() {
        entity.setDescripcionEstado("");
        String resultTest = entity.getDescripcionEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getMotivoRechazo")
    void getMotivoRechazo() {
        entity.setMotivoRechazo("");
        String resultTest = entity.getMotivoRechazo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCodUsuarioAprobador")
    void getCodUsuarioAprobador() {
        entity.setCodUsuarioAprobador("");
        String resultTest = entity.getCodUsuarioAprobador();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getDescripcionMotivo")
    void getDescripcionMotivo() {
        entity.setDescripcionMotivo("");
        String resultTest = entity.getDescripcionMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNombreUsuarioRendicion")
    void getNombreUsuarioRendicion() {
        entity.setNombreUsuarioRendicion("");
        String resultTest = entity.getNombreUsuarioRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getFechaUltimaModificacion")
    void getFechaUltimaModificacion() {
        entity.setFechaUltimaModificacion("");
        String resultTest = entity.getFechaUltimaModificacion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getAviso")
    void getAviso() {
        entity.setAviso("");
        String resultTest = entity.getAviso();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getGlg")
    void getGlg() {
        entity.setGlg("");
        String resultTest = entity.getGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getImporteTarjeta")
    void getImporteTarjeta() {
        entity.setImporteTarjeta("");
        String resultTest = entity.getImporteTarjeta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getJournal")
    void getJournal() {
        entity.setJournal("");
        String resultTest = entity.getJournal();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCostosDestino")
    void getCostosDestino() {
        entity.setCostosDestino("");
        String resultTest = entity.getCostosDestino();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getAlerta")
    void getAlerta() {
        entity.setAlerta("");
        String resultTest = entity.getAlerta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals("",resultTest)
        );
    }
}