package com.sa.form.parametros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosExceptuadosFormTest {

    private ParametrosExceptuadosForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosExceptuadosForm();
    }

    @Test
    @DisplayName("Testeando setters, getters y metodo clear")
    void clear() {
        form.setMotivoUsuario("");
        form.setDescripcionNombre("");
        form.setHasta("");
        form.setDesde("");
        form.setEstado("");
        form.setDesMotivo("");
        form.setDescripcionCodigo("");
        form.setMarca("");
        form.clear();

        assertAll(
                ()->assertEquals(form.getMotivoUsuario(),null),
                ()->assertEquals(form.getDescripcionNombre(),null),
                ()->assertEquals(form.getHasta(),null),
                ()->assertEquals(form.getDesde(),null),
                ()->assertEquals(form.getEstado(),null),
                ()->assertEquals(form.getDesMotivo(),null),
                ()->assertEquals(form.getDescripcionCodigo(),null),
                ()->assertEquals(form.getMarca(),null)
        );
    }

    @Test
    @DisplayName("testeando set y getAccion")
    void getAccion() {
        form.setAccion("");
        String resultTest = form.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getAccion())
        );
    }

}