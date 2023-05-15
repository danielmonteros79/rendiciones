package com.sa.form;

import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RendicionAvisoFormTest {

    private RendicionAvisoForm service;
    private FormFile archivo;
    private Usuario usuario;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new RendicionAvisoForm();
    }

    @Test
    @DisplayName("Testeando clean")
    void clean() {
        service.setAccion("Accion");
        service.setArchivo(archivo);
        service.setArchivosASubir(new ArrayList<>());
        service.setRendicion(new Rendicion());
        service.setUsuario(usuario);

        service.clean();
        assertAll(
                ()->assertEquals(service.getAccion(), null),
                ()->assertEquals(service.getArchivo(),null),
                ()->assertEquals(service.getArchivosASubir(),new ArrayList<>()),
                ()->assertEquals(service.getUsuario(),null)
        );
    }

    @Test
    @DisplayName("Testeando set y getAction")
    void setygetAction() {
        service.setAction("Action");
        String resultTest = service.getAction();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Action")
        );
    }

    @Test
    @DisplayName("Testeando get Rendicion")
    void getRendicion() {
        service.setRendicion(new Rendicion());
        Rendicion resultTest = service.getRendicion();
        assertAll(
                ()->assertNotNull(resultTest)
        );
    }
}