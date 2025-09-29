package com.sa.form.parametros;

import com.sa.form.parametros.RelacionUsuarioDelegadoForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RelacionUsuarioDelegadoFormTest {

    private RelacionUsuarioDelegadoForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new RelacionUsuarioDelegadoForm();
    }

    @Test
    @DisplayName("Testeando set y get Usuario")
    void setUsuario() {
        form.setUsuario("");
        String resultTest = form.getUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando set y get Informe")
    void setInforme() {
        form.setInforme("");
        String resultTest = form.getInforme();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando set y get Opcion")
    void setOpcion() {
        form.setOpcion("");
        String resultTest = form.getOpcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando set y get FeDesdeOld")
    void setFeDesdeOld() {
        form.setFeDesdeOld("");
        String resultTest = form.getFeDesdeOld();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando set y get FeHastaOld")
    void setFeHastaOld() {
        form.setFeHastaOld("");
        String resultTest = form.getFeHastaOld();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando clearData con sus setter y getters")
    void clearData() {
        form.setDelegadoUser("");
        form.setDelegadoNombre("");
        form.setDelegadoCentroCostos("");
        form.setDelegadoSector("");
        form.setFeDesde("");
        form.setFeHasta("");
        form.setEstado("");
        form.setAccion("");

        form.clearData();

        assertAll(
                ()->assertEquals(form.getDelegadoUser(),""),
                ()->assertEquals(form.getDelegadoNombre(),""),
                ()->assertEquals(form.getDelegadoCentroCostos(),""),
                ()->assertEquals(form.getDelegadoSector(),""),
                ()->assertEquals(form.getFeDesde(),""),
                ()->assertEquals(form.getFeHasta(),""),
                ()->assertEquals(form.getEstado(),""),
                ()->assertEquals(form.getAccion(),"I")
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaAlta")
    void setFechaAlta() {
        form.setFechaAlta("");
        String resultTest = form.getFechaAlta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }

    @Test
    @DisplayName("Testeando set y get UserAlta")
    void setUserAlta() {
        form.setUserAlta("");
        String resultTest = form.getUserAlta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }
}