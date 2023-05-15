package com.sa.form.parametros;

import com.sa.entities.OSCAR;
import com.sa.entities.Ristra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParametrosGastosFormTest {

    private ParametrosGastosForm form;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        form = new ParametrosGastosForm();
    }

    @Test
    @DisplayName("Testeando setters, getters y metodo clear")
    void clear() {
        String ristra="0";
        form.setCodigo("");
        form.setDescripcionGasto("");
        form.setMotivo("");
        form.setBimon("");
        form.setComprob("");
        form.setAutoriz("");
        form.setOscar(new OSCAR());
        form.setObserv("");
        form.setMaInclExcl("");
        form.setAntiguedad("");
        form.setEstado("");
        form.setAvisoMonto("");
        form.setIdCentroCostos("");
        form.setIdNivAutoriz("");
        form.setPlazoAprob("");
        form.setReadonly(true);
        form.setCentrosCosto("");

        form.clear();

        assertAll(
                ()->assertEquals(form.getCodigo(),null),
                ()->assertEquals(form.getDescripcionGasto(),null),
                ()->assertEquals(form.getMotivo(),null),
                ()->assertEquals(form.getBimon(),null),
                ()->assertEquals(form.getComprob(),null),
                ()->assertEquals(form.getAutoriz(),null),
                ()->assertEquals(form.getObserv(),null),
                ()->assertEquals(form.getMaInclExcl(),null),
                ()->assertEquals(form.getAntiguedad(),null),
                ()->assertEquals(form.getEstado(),null),
                ()->assertEquals(form.getAvisoMonto(),null),
                ()->assertEquals(form.getIdCentroCostos(),null),
                ()->assertEquals(form.getIdNivAutoriz(),null),
                ()->assertEquals(form.getPlazoAprob(),null),
                ()->assertEquals(form.getReadonly(),false),
                ()->assertEquals(form.getCentrosCosto(),new ArrayList<String>())
        );

    }

    @Test
    void getRistra() {
        String ri="";
        for (int i = 0; i < 18; i++) {
            ri+="iiii";
        }
        form.setRistra(ri);
        Ristra resultTest = form.getRistra();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getRistra())
        );
    }

    @Test
    @DisplayName("Testeando set y get Accion")
    void getAccion() {
        form.setAccion("");
        String resultTest = form.getAccion();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getAccion())
        );
    }

    @Test
    @DisplayName("Testeando set y get Oscar")
    void getOscar() {
        form.setOscar(new OSCAR());
        OSCAR resultTest = form.getOscar();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,form.getOscar())
        );
    }

    @Test
    @DisplayName("Testeando isBack")
    void isBack() {
        form.setBack(true);
        form.isBack();
        assertEquals(true, form.isBack());
    }

    @Test
    @DisplayName("Testeando set y get CentrosCostoList")

    void setCentrosCostoList() {
        List<String> cdCostoList = new ArrayList<>();
        cdCostoList.add("cdCosto1");
        cdCostoList.add("cdCosto2");
        form.setCentrosCostoList(cdCostoList);
        List<String> resultTest = form.getCentrosCosto();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,cdCostoList)
        );
    }

    @Test
    @DisplayName("Testeando set y get CentrosCostoI")
    @Disabled("No puede probarse debido a que no se puede settear la lista ya que es una variable privada de la clase original")
    void setCentrosCostoI() {

        //form.setCentrosCostoI(1,"");

        String resultTest = form.getCentrosCostoI(1);

        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"")
        );
    }
}