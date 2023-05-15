package com.sa.form.parametros;

import com.sa.entities.OSCAR;
import com.sa.form.parametros.ParametrosMotivoForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametrosMotivoFormTest {

    private ParametrosMotivoForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosMotivoForm();
    }

    @Test
    @DisplayName("Se testea metodo clear y sus setter y getters")
    void clear() {
        form.setCodigo("");
        form.setDescripcion("");
        form.setIdGlg("");
        form.setIdCentroCostos("");
        form.setEstado("");
        form.setCodSup("");
        form.setCodFirma("");
        form.setCodAprobacionGlg("");
        form.setOscar(new OSCAR());
        OSCAR oldOscar = form.getOscar();
        form.setFechaDesde("");
        form.setFechaHasta("");
        form.setIdNivCarga("");
        form.setIdNivAutoriz("");
        form.setMaInclExcl("");
        form.setTxAviso("");
        form.setIdOperEspe("");
        form.setMeDiasInterv("");
        form.setCentrosCosto("");

        form.clear();

        assertAll(
                ()->assertEquals(form.getCodigo(),null),
                ()->assertEquals(form.getDescripcion(),null),
                ()->assertEquals(form.getIdGlg(),null),
                ()->assertEquals(form.getIdCentroCostos(),null),
                ()->assertEquals(form.getEstado(),null),
                ()->assertEquals(form.getCodSup(),null),
                ()->assertEquals(form.getCodFirma(),null),
                ()->assertEquals(form.getCodAprobacionGlg(),null),
                ()->assertNotEquals(form.getOscar(),oldOscar),
                ()->assertEquals(form.getFechaDesde(),null),
                ()->assertEquals(form.getFechaHasta(),null),
                ()->assertEquals(form.getIdNivCarga(),null),
                ()->assertEquals(form.getIdNivAutoriz(),null),
                ()->assertEquals(form.getMaInclExcl(),null),
                ()->assertEquals(form.getTxAviso(),null),
                ()->assertEquals(form.getIdOperEspe(),null),
                ()->assertEquals(form.getMeDiasInterv(),null),
                ()->assertEquals(form.getCentrosCosto(),new ArrayList<String>())
        );
    }
    @Test
    void setCentrosCostoList() {
        List<String> cdCosto = new ArrayList<>();
        cdCosto.add("");
        form.setCentrosCostoList(cdCosto);
        List<String> resultTest = form.getCentrosCosto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,cdCosto)
        );
    }

    @Test
    @DisplayName("Testeando set y get CentrosCostoI")
    @Disabled("No puede probarse debido a que no se puede settear la lista ya que es una variable privada de la clase original")
    void setCentrosCostoI() {
        form.setCentrosCostoI(1,"");
        String resultTest = form.getCentrosCostoI(1);
        assertNotNull(resultTest);
    }

    @Test
    void setAccion() {
        form.setAccion("");
        String resultTest = form.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }
}