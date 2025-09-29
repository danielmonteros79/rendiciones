package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;

import javax.xml.rpc.ServiceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CodigoDeBarrasServiceTest {

    @InjectMocks
    CodigoDeBarrasService codigoDeBarrasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    @DisplayName("Testeando createBarcodeImg")
//    void createBarcodeImg() throws ServiceException {
//        assertThrows(ServiceException.class,
//                () -> (new CodigoDeBarrasService(new SAMWebClient())).createBarcodeImg("Codigo Barra"));
//    }

}