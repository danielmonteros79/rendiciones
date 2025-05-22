package com.sa.form;

import com.sa.entities.Archivo;
import com.sa.entities.Rendicion;
import com.sa.entities.Usuario;
import org.apache.struts.upload.FormFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RendicionAvisoFormTest {

    private RendicionAvisoForm service;
    private FormFile archivo;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new RendicionAvisoForm();
        
        usuario = new Usuario("idUser", "perfil", "nombre", 0, "sector", new ArrayList<Usuario>());
        usuario.setNombre("Test User");
        archivo = new FormFile() {
			
			@Override
			public void setFileSize(int fileSize) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setFileName(String fileName) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setContentType(String contentType) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public InputStream getInputStream() throws FileNotFoundException, IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getFileSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getFileName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public byte[] getFileData() throws FileNotFoundException, IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void destroy() {
				// TODO Auto-generated method stub
				
			}
		};
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
        assertNull(service.getAccion(), "El campo 'accion' no es null después de clean()");
        assertNull(service.getArchivo(), "El campo 'archivo' no es null después de clean()");
        assertEquals(Collections.emptyList(), service.getArchivosASubir(), "ArchivosASubir no está vacío después de clean()");
        assertNull(service.getUsuario(), "El campo 'usuario' no es null después de clean()");
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