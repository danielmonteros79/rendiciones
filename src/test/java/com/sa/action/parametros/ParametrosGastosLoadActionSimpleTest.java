package com.sa.action.parametros;

import com.sa.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ParametrosGastosLoadActionSimpleTest {

    @InjectMocks
    ParametrosGastosLoadAction parametrosGastosLoadAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup usuario for session
        Usuario usuario = new Usuario("testUser", "perfil", "Test User", 1, "sector", new ArrayList<>());
        parametrosGastosLoadAction.setSessionUser(usuario);
        parametrosGastosLoadAction.setSessionUserWorking(usuario);
    }

    @Test
    void shouldHaveUserConfiguredCorrectly() {
        // Act
        Usuario sessionUser = parametrosGastosLoadAction.getSessionUser();
        Usuario sessionUserWorking = parametrosGastosLoadAction.getSessionUserWorking();
        
        // Assert
        assertNotNull(sessionUser, "SessionUser should not be null");
        assertNotNull(sessionUserWorking, "SessionUserWorking should not be null");
        assertEquals("testUser", sessionUser.getIdUser());
        assertEquals("testUser", sessionUserWorking.getIdUser());
    }
}
