package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

class ImagenesFormTest {

	ImagenesForm imagenesFormReal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imagenesFormReal = new ImagenesForm();
    }

    @Test
    @DisplayName("Testeando set y get  action")
    void getAction() {
    	imagenesFormReal.setAction("action");
        String action = imagenesFormReal.getAction();

        assertAll(
                () -> assertNotNull(action),
                () -> assertEquals("action", action)
        );
    }
    
    
    @Test
    @DisplayName("Testeando set y get accion")
    void getAccion() {
    	imagenesFormReal.setAccion("action");
        String action = imagenesFormReal.getAccion();

        assertAll(
                () -> assertNotNull(action),
                () -> assertEquals("action", action)
        );
    }
    
    @Test
    @DisplayName("Testeando set y get archivo")
    void getArchivo() {
    	FormFile archivo = null;
    	imagenesFormReal.setArchivo(archivo);
    	FormFile archivo2 = imagenesFormReal.getArchivo();

        assertAll(
                () -> assertEquals(archivo, archivo2)
        );
    }
    
    @Test
    @DisplayName("Testeando set y get usuario")
    void getUsuario() {
    	List<Usuario> usuarios = new ArrayList<>();
    	Usuario usuario = new Usuario("idUser","perfil","nombre", 1, "sector", usuarios);
    	imagenesFormReal.setUsuario(usuario);
    	Usuario usuario2 = imagenesFormReal.getUsuario();

        assertAll(
                () -> assertEquals(usuario, usuario2)
        );
    }
    
    @Test
    @DisplayName("Testeando set y get rendicion")
    void getRendicion() {
    	Rendicion rend = new Rendicion();
    	imagenesFormReal.setRendicion(rend);
    	Rendicion rend2 = imagenesFormReal.getRendicion();

        assertAll(
                () -> assertEquals(rend, rend2)
        );
    }
    
    @Test
    @DisplayName("Testeando set y get archivos a subir")
    void getArchivosASubir() {
    	List<Archivo> archivos = new ArrayList<>();
    	imagenesFormReal.setArchivosASubir(archivos);
    	List<Archivo> archivos2 = imagenesFormReal.getArchivosASubir();

        assertAll(
                () -> assertEquals(archivos, archivos2)
        );
    }
    
    
    @Test
    @DisplayName("Testeando de clean")
    void clean() {
    	imagenesFormReal.clean();;
        assertAll(
                ()->assertNull(imagenesFormReal.getArchivo()),
                ()->assertNull(imagenesFormReal.getAccion()),
                ()->assertNull(imagenesFormReal.getArchivosASubir()),
                ()->assertNull(imagenesFormReal.getRendicion()),
                ()->assertNull(imagenesFormReal.getUsuario())
        );
    }
    


    

}