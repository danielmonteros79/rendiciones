package com.sa.form;

import com.sa.entities.ComboMotivo;
import com.sa.entities.ComboOpcion;
import com.sa.entities.ComboOpcion2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CuadroFiltroFormTest {

    private CuadroFiltroForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new CuadroFiltroForm();
    }

    @Test
    @DisplayName("Testeando clear")
    void clear() {
        form.setOpcion("01");
        form.setFechaDesde("");
        form.setFechaHasta("");
        form.setMontoDesde("");
        form.setMontoHasta("");
        form.setCodEstado("");
        form.setCodMotivo("");
        form.setCodGlg("");
        form.setUsuario("");

        form.clear();

        assertAll(
                ()->assertEquals(form.getOpcion(),"01"),
                ()->assertEquals(form.getFechaDesde(),""),
                ()->assertEquals(form.getFechaHasta(),""),
                ()->assertEquals(form.getMontoDesde(),""),
                ()->assertEquals(form.getMontoHasta(),""),
                ()->assertEquals(form.getCodEstado(),""),
                ()->assertEquals(form.getCodMotivo(),""),
                ()->assertEquals(form.getCodGlg(),""),
                ()->assertEquals(form.getUsuario(),"")
        );

    }

    @Test
    @DisplayName("Testeando set y get NombreUsuario")
    void getNombreUsuario() {
        form.setNombreUsuario("");
        String resultTest = form.getNombreUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getNombreUsuario())
        );
    }

    @Test
    @DisplayName("Testeando set y get Costos")
    void getCostos() {
        form.setCostos(1);
        Integer resultTest = form.getCostos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getCostos())
        );
    }

    @Test
    @DisplayName("Testeando set y get ComboGlg")
    void getComboGlg() {
        form.setComboGlg(new ArrayList<ComboOpcion>());
        List<ComboOpcion> resultTest = form.getComboGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getComboGlg())
        );
    }

    @Test
    @DisplayName("Testeando set y get ComboMotivo")
    void getComboMotivo() {
        form.setComboMotivo(new ArrayList<ComboMotivo>());
        List<ComboMotivo> resultTest = form.getComboMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getComboMotivo())
        );
    }

    @Test
    @DisplayName("Testeando set y get ComboEstado")
    void getComboEstado() {
        form.setComboEstado(new ArrayList<ComboOpcion2>());
        List<ComboOpcion2> resultTest = form.getComboEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getComboEstado())
        );
    }
}