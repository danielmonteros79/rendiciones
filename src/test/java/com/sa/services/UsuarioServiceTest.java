package com.sa.services;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;
import ar.com.itrsa.sam.impl.TransactionManagerImpl;
import com.sa.entities.Usuario;
import com.sa.form.AprobacionForm;
import com.sa.manager.ManagerTransaction;
import com.sa.services.Transaction;
import com.sa.services.UsuarioService;
import com.sa.services.trxs.SU52;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Spy
    SAMWebClient samWebClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testeando obtener delegados usuario")
    void obtenerDelegadosUsuario() throws Exception {
        try(MockedConstruction<ManagerTransaction> mock = Mockito.mockConstruction(ManagerTransaction.class, (mockM, context) -> {
            doNothing().when(mockM).executeTrx(any(),anyMap());
            when(mockM.getDataReturn()).thenReturn(new Usuario("idUser","perfil","nombre",1,"sector",new ArrayList<>()));
            when(mockM.getMensajeAviso()).thenReturn("Mensaje de aviso");
        })) {

            UsuarioService usuarioService = new UsuarioService(samWebClient);
            Usuario usuario = usuarioService.obtenerDelegadosUsuario("usuario");
            assertNotNull(usuario);
        }
    }

    @Test
    @Disabled("Desabilitado porque no se puede mockear el valor de msg")
    @DisplayName("Testeando get msg")
    void getMsg() {
        UsuarioService usuarioService = new UsuarioService(samWebClient);
        usuarioService.getMsg();
        assertNull(usuarioService.getMsg());
    }
}