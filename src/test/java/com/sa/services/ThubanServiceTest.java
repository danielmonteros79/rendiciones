package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ThubanServiceTest {

    @InjectMocks
    ThubanService thubanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando publicarDocumentos")
    void publicarDocumentos() throws UnsupportedEncodingException {
        ThubanService thubanService = new ThubanService(new SAMWebClient());
        Rendicion rendicion = new Rendicion();

        Archivo archivo = new Archivo();
        archivo.setIdu("Idu");
        archivo.setInputStream(new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8")));
        archivo.setNomArchivo("Nom Archivo");

        ArrayList<Archivo> files = new ArrayList<>();
        files.add(archivo);
        List<String> actualPublicarDocumentosResult = thubanService.publicarDocumentos("foo", "foo", "foo", rendicion,
                files);
        assertEquals(1, actualPublicarDocumentosResult.size());
        assertEquals(
                "<b>Nom Archivo:</b><br>ar.com.itrsa.sam.TransactionException: ar.com.itrsa.GeneralException: Properties"
                        + " not initialized.",
                actualPublicarDocumentosResult.get(0));
    }

    @Test
    void publicarDoc() {
    }

    @Test
    @DisplayName("Testeando obtenerArchivos")
    void obtenerArchivos() throws Exception {
        ThubanService thubanService = new ThubanService(new SAMWebClient());
        assertTrue(thubanService.obtenerArchivos("Clase Doc", "Usuario Thuban", "Clave Thuban", "42").isEmpty());
        assertNull(thubanService.getMsg());
    }

}