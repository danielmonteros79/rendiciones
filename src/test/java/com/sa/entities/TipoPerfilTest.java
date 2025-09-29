package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TipoPerfilTest {

    private TipoPerfil entity;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void values() {
        assertEquals("VIEW_ALL",entity.VIEW_ALL.toString());
        assertEquals("VIEW_ALL_LESS_PARAMS_CIERRE",entity.VIEW_ALL_LESS_PARAMS_CIERRE.toString());
        assertEquals("VIEW_ALL_LESS_CIERRE",entity.VIEW_ALL_LESS_CIERRE.toString());
        assertEquals("VIEW_ALL_LESS_PARAMS",entity.VIEW_ALL_LESS_PARAMS.toString());
        assertEquals("VIEW_APROBACION",entity.VIEW_APROBACION.toString());
        assertEquals("VIEW_APROBACION_DELEGADO",entity.VIEW_APROBACION_DELEGADO.toString());
        assertEquals("VIEW_REND_DELEGADO",entity.VIEW_REND_DELEGADO.toString());
        assertEquals("VIEW_REND_APROB_DELEGADO",entity.VIEW_REND_APROB_DELEGADO.toString());
    }

}