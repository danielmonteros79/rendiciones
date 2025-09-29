package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TipoCampoTest {

    private TipoCampo entity;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void values() {

        assertAll(
                ()->assertTrue("TXT".equals(entity.TXT.toString())),
                ()->assertTrue("COD".equals(entity.COD.toString())),
                ()->assertTrue("FEC".equals(entity.FEC.toString()))
        );
    }

}