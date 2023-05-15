package com.sa.form.parametros;

import com.sa.form.parametros.ParametrosMotivoFiltroForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosMotivoFiltroFormTest {

    private ParametrosMotivoFiltroForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosMotivoFiltroForm();
    }

    @Test
    @DisplayName("Testeando setter, getter y método clear")
    void clear() {
        form.setCodigo("codigo1");
        form.clear();
        assertEquals(form.getCodigo(),null);
    }

}