package com.sa.form;

import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlertaFormTest {

	AlertaForm realService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        realService = new AlertaForm();
    }

    @Test
    @DisplayName("Testeando set y get HTML")
    void setgetHtml() {
        realService.setHtml("html");
        String resultTest = realService.getHtml();
        assertAll(
                ()-> assertNotNull(resultTest),
                ()-> assertEquals(resultTest,"html" )
        );
    }

    @Test
    @DisplayName("Testeando set y get Name File")
    void getNameFile() {
        realService.setNameFile("NameFile");
        String resultTest = realService.getNameFile();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"NameFile")
        );
    }

    @Test
    @DisplayName("Testeando set y get FileType")
    void setygetFileType() {
        realService.setFileType("FileType");
        String resultTest = realService.getFileType();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FileType")
        );
    }

    @Test
    @DisplayName("Testeando set y get FilePath")
    void setygetFilePath() {
        realService.setFilePath("FilePath");
        String resultTest = realService.getFilePath();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FilePath")
        );
    }

    @Test
    @DisplayName("Testeando set y get NombreUsuario")
    void setygetNombreUsuario() {
        realService.setNombreUsuario("NombreUsuario");
        String resultTest = realService.getNombreUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"NombreUsuario")
        );
    }

    @Test
    @DisplayName("Testeando set y get NombreUsuario (object)")
    void testSetNombreUsuario() {
        String arg = "Nombre";
        Object obj = arg;

        realService.setNombreUsuario(obj);
        String resultTest = realService.getNombreUsuario();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,obj)
        );
    }

    @Test
    @DisplayName("Testeando set y get User")
    void setygetUser() {
        realService.setUser("User");
        String resultTest =realService.getUser();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"User")
        );

    }

    @Test
    @DisplayName("Testeando set y get Motivo")
    void setygetMotivo() {
        realService.setMotivo("Motivo");
        String resultTest = realService.getMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Motivo")
        );

    }

    @Test
    @DisplayName("Testeando set y get Descripcion")
    void setygetDescripcion() {
        realService.setDescripcion("Descripcion");
        String resultTest = realService.getDescripcion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Descripcion")
        );

    }

    @Test
    @DisplayName("Testeando set y get IdRendicion")
    void setygetIdRendicion() {
        realService.setIdRendicion(1);
        Integer resultTest = realService.getIdRendicion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,1)
        );
    }

    @Test
    @DisplayName("Testeando get y Set User (Object)")
    void testSetyGetUser() {
        String arg ="User";
        Object obj = arg;

        realService.setUser(obj);
        String resultTest =realService.getUser();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,obj)
        );
    }

  

    @Test
    @DisplayName("Testeando set y get FechaDesde")
    void setygetFechaDesde() {
        realService.setFechaDesde("FechaDesde");
        String resultTest = realService.getFechaDesde();
        assertAll(
            ()->assertNotNull(resultTest),
            ()->assertEquals(resultTest,"FechaDesde")
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaHasta")
    void setygetFechaHasta() {
        realService.setFechaHasta("FechaHasta");
        String resultTest = realService.getFechaHasta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FechaHasta")
        );
    }

   
    @Test
    @DisplayName("Testeando set y get Idu")
    void setygetIdu() {
        realService.setIdu("Idu");
        String resultTest =  realService.getIdu();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Idu")
        );
    }

    @Test
    @DisplayName("Testeando set y get CodAdea")
    void setygetCodAdea() {
        realService.setCodAdea("CodAdea");
        String resultTest = realService.getCodAdea();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CodAdea")
        );
    }

    @Test
    @DisplayName("Testeando set y get CodMotivo")
    void setygetCodMotivo() {
        realService.setCodMotivo("CodMotivo");
        String resultTest = realService.getCodMotivo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CodMotivo")
        );
    }

   
    @Test
    @DisplayName("Testeando set y get UsuarioRend")
    void setygetUsuarioRend() {
        realService.setUsuarioRend("UsuarioRend");
        String resultTest = realService.getUsuarioRend();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"UsuarioRend")
        );
    }




    @Test
    @DisplayName("Testeando set y get FechaHoy")
    void setygetFechaHoy() {
        realService.setFechaHoy("FechaHoy");
        String resultTest = realService.getFechaHoy();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FechaHoy")
        );
    }
    
    @Test
    @DisplayName("Testeando set y get Alerta")
    void setygetAlerta() {
        realService.setAlerta("Alerta");
        String resultTest = realService.getAlerta();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Alerta")
        );
    }
    
    @Test
    @DisplayName("Testeando set y get Alerta detalle")
    void setygetAlertaDetalle() {
        realService.setAlertaDetalle("detalle");
        String resultTest = realService.getAlertaDetalle();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"detalle")
        );
    }


  
    @Test
    @DisplayName("Testeando set y get Aviso")
    void setygetAviso() {
        realService.setAviso("Aviso");
        String resultTest = realService.getAviso();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Aviso")
        );
    }

  

    @Test
    @DisplayName("Testeando set y get Accion")
    void setygetAccion() {
        realService.setAccion("Accion");
        String resultTest = realService.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Accion")
        );
    }

    
    @Test
    @DisplayName("Testeando de rest")
    void reset() {
        realService.reset();;
        assertAll(
                ()->assertNull(realService.getMotivo()),
                ()->assertNull(realService.getFechaDesde()),
                ()->assertNull(realService.getFechaHasta()),
                ()->assertNull(realService.getDescripcion())
   
        );
    }
 
    

}