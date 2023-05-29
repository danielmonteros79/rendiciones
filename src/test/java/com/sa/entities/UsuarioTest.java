package com.sa.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario entity;

    private List<Usuario> delegadosAsignados = new ArrayList<Usuario>();

    public static Stream<Arguments> setTipoPerfilSource() {
        return Stream.of(
                Arguments.of("SS","VIEW_ALL"),
                Arguments.of("SN","VIEW_ALL_LESS_PARAMS"),
                Arguments.of("NS","VIEW_ALL_LESS_CIERRE"),
                Arguments.of("NN","VIEW_ALL_LESS_PARAMS_CIERRE"),
                Arguments.of("99","VIEW_APROBACION"),
                Arguments.of("DELEG_APROB","VIEW_APROBACION_DELEGADO"),
                Arguments.of("DELEG_REND_APROB","VIEW_REND_APROB_DELEGADO"),
                Arguments.of("DELEG_REND","VIEW_REND_DELEGADO")
        );
    }

    @BeforeEach
    void setup(){
        entity = new Usuario("","","",1,"",delegadosAsignados);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDelegadosAsignados() {
        entity.setDelegadosAsignados(delegadosAsignados);
        List<Usuario> resultTest = entity.getDelegadosAsignados();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(delegadosAsignados,resultTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getIdUser")
    void getIdUser() {
        entity.setIdUser("");
        String resulTest = entity.getIdUser();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals("",resulTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getPerfil")
    void getPerfil() {
        entity.setPerfil(1);
        Integer resulTest = entity.getPerfil();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals(1,resulTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getNombre")
    void getNombre() {
        entity.setNombre("");
        String resulTest = entity.getNombre();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals("",resulTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getSector")
    void getSector() {
        entity.setSector("");
        String resulTest = entity.getSector();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals("",resulTest)
        );
    }

    @Test
    @DisplayName("Testeando set y getCcostos")
    void getCcostos() {
        entity.setCcostos(1);
        Integer resulTest = entity.getCcostos();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals(1,resulTest)
        );
    }

    @ParameterizedTest
    @MethodSource("setTipoPerfilSource")
    @DisplayName("Testeando set y getTipoPerfil")
    void getTipoPerfil(String tipoPerfil, String respuesta) {
        entity.setTipoPerfil(tipoPerfil);
        TipoPerfil resulTest = entity.getTipoPerfil();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertTrue(respuesta.equals(resulTest.toString()))
        );
    }

    @Test
    @DisplayName("Testeando set y getFacultades")
    void setManejaFacultades() {
        entity.setManejaFacultades(true);
        boolean is= entity.isManejaFacultades();
        assertTrue(is);
    }

    @Test
    @DisplayName("Testeando set y getFacultades")
    void isManejaFacultades() {

    }

    @Test
    @DisplayName("Testeando set y getFacultades")
    void getFacultades() {
        entity.setFacultades("");
        String resulTest = entity.getFacultades();
        assertAll(
                ()->assertNotNull(resulTest),
                ()->assertEquals("",resulTest)
        );
    }
}