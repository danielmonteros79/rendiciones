package com.sa.decorator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class JournalTableDecoratorTest {

    private JournalTableDecorator decorator;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        decorator = new JournalTableDecorator();
    }

    @Test
    @DisplayName("Testeando método getVerLink")
    void getVerLink() {
        //método no implementado
        assertEquals(null, decorator.getVerLink());
    }

    @Test
    @DisplayName("Testeando método getEditarLink")
    void getEditarLink() {
        //método no implementado
        assertEquals(null, decorator.getEditarLink());
    }

    @Test
    @DisplayName("Testeando método getBorrarLink")
    void getBorrarLink() {
        //método no implementado
        assertEquals(null, decorator.getBorrarLink());
    }

    @Test
    @DisplayName("Testeando método getDestinatariosLink")
    void getDestinatariosLink() {
        //método no implementado
        assertEquals(null, decorator.getDestinatariosLink());
    }

    @Test
    @DisplayName("Testeando método getCuponesLink")
    void getCuponesLink() {
        //método no implementado
        assertEquals(null, decorator.getCuponesLink());
    }

    @Test
    @DisplayName("Testeando método getScanLink")
    void getScanLink() {
        //método no implementado
        assertEquals(null, decorator.getScanLink());
    }

    @Test
    @DisplayName("Testeando método getCaratulaLink")
    void getCaratulaLink() {
        //método no implementado
        assertEquals(null, decorator.getCaratulaLink());
    }
}