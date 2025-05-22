package com.sa.form;

import com.sa.entities.Usuario;
import com.sa.form.RendicionForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RendicionFormTest {

    RendicionForm realService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        realService = new RendicionForm();
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
    @DisplayName("Testeando set y get Costos")
    void setygetCostos() {
        realService.setCostos(1);
        Integer resultTest = realService.getCostos();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,1)
        );
    }

    @Test
    @DisplayName("Testeando set y get Sector")
    void setygetSector() {
        realService.setSector("Sector");
        String resultTest = realService.getSector();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Sector")
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
    @DisplayName("Testeando set y get Estado")
    void setygetEstado() {
        realService.setEstado(1);
        Integer resultTest = realService.getEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,1)
        );
    }

    @Test
    @DisplayName("Testeando set y get EstadoRend")
    void setygetEstadoRend() {
        realService.setEstadoRend("EstadoRend");
        String resultTest = realService.getEstadoRend();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"EstadoRend")
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
    @DisplayName("Testeando set y get Glg")
    void setygetGlg() {
        realService.setGlg("Glg");
        String resultTest = realService.getGlg();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Glg")
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
    @DisplayName("Testeando set y get UsuarioAprobador")
    void setygetUsuarioAprobador() {
        realService.setUsuarioAprobador("UsuarioAprobador");
        String resultTest = realService.getUsuarioAprobador();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"UsuarioAprobador")
        );
    }

    @Test
    @DisplayName("Testeando set y get DescripcionEstado")
    void setygetDescripcionEstado() {
        realService.setDescripcionEstado("DescripcionEstado");
        String resultTest = realService.getDescripcionEstado();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"DescripcionEstado")
        );
    }

    @Test
    @DisplayName("Testeando set y get CantDias")
    void setygetCantDias() {
        realService.setCantDias(1);
        Integer resultTest = realService.getCantDias();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,1)
        );
    }

    @Test
    @DisplayName("Testeando set y get MotivoRechazo")
    void setygetMotivoRechazo() {
        realService.setMotivoRechazo("MotivoRechazo");
        String resultTest = realService.getMotivoRechazo();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"MotivoRechazo")
        );
    }

    @Test
    @DisplayName("Testeando set y get FechaUltimaModificacion")
    void setygetFechaUltimaModificacion() {
        realService.setFechaUltimaModificacion("FechaUltimaModificacion");
        String resultTest = realService.getFechaUltimaModificacion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"FechaUltimaModificacion")
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
    @DisplayName("Testeando set y get LinkThuban")
    void setygetLinkThuban() {

        realService.setLinkThuban("LinkThuban");
        String resultTest = realService.getLinkThuban();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"LinkThuban")
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
    @DisplayName("Testeando set y get CostosDestino")
    void setygetCostosDestino() {
        realService.setCostosDestino("CostosDestino");
        String resultTest = realService.getCostosDestino();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"CostosDestino")
        );
    }
    
    @Test
    @DisplayName("Testeando set y get gastoFechaMin")
    void setygetGastoFechaMin() {
        realService.setGastoFechaMin("fecha");
        String resultTest = realService.getGastoFechaMin();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"fecha")
        );
    }
    
    @Test
    @DisplayName("Testeando set y get gastoFechaMax")
    void setygetGastoFechaMax() {
        realService.setGastoFechaMax("fecha");
        String resultTest = realService.getGastoFechaMax();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"fecha")
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
                ()->assertNull(realService.getDescripcion()),
                ()->assertNull(realService.getGastoFechaMax()),
                ()->assertNull(realService.getGastoFechaMin())
        );
    }
    

}