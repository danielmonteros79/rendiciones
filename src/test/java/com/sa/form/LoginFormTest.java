package com.sa.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class LoginFormTest {

    private LoginForm service;

    @BeforeEach
    void setuUp(){
        MockitoAnnotations.openMocks(this);
        service = new LoginForm();
    }

    @Test
    @DisplayName("Testeando setUsername y getUsername")
    void setygetUsername() {
        service.setUsername("Username");
        String resultTest = service.getUsername();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Username")
        );
    }

    @Test
    @DisplayName("Testeando getPassword y setPassword")
    void setygetPassword() {
        service.setPassword("Password");
        String resultTest = service.getPassword();
        assertAll(
                ()->assertNotNull(resultTest),
                ()->assertEquals(resultTest,"Password")
        );
    }

}