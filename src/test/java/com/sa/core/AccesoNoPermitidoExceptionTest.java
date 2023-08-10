package com.sa.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AccesoNoPermitidoExceptionTest {

  @Mock
  Throwable throwableMocked;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instance")
  void shouldCreateAnInstance() {
    //then
    AccesoNoPermitidoException accesoNoPermitidoExceptionToAssert = new AccesoNoPermitidoException();
    assertNotNull(accesoNoPermitidoExceptionToAssert);
  }

  @Test
  @DisplayName("Should create an instance with a message")
  void shouldCreateAnInstanceWithAMessage() {
    //given
    String message = "message";
    //then
    AccesoNoPermitidoException accesoNoPermitidoExceptionToAssert = new AccesoNoPermitidoException(message);
    assertNotNull(accesoNoPermitidoExceptionToAssert);
    assertEquals(message, accesoNoPermitidoExceptionToAssert.getMessage());
  }

  @Test
  @DisplayName("Should create an instance with a message and a cause")
  void shouldCreateAnInstanceWithAMessageAndACause() {
    //given
    String message = "message";
    //then
    AccesoNoPermitidoException accesoNoPermitidoExceptionToAssert = new AccesoNoPermitidoException(message, throwableMocked);
    assertNotNull(accesoNoPermitidoExceptionToAssert);
    assertEquals(message, accesoNoPermitidoExceptionToAssert.getMessage());
    assertEquals(throwableMocked, accesoNoPermitidoExceptionToAssert.getCause());
  }

  @Test
  @DisplayName("Should create an instance with a cause")
  void shouldCreateAnInstanceWithACause() {
    //then
    AccesoNoPermitidoException accesoNoPermitidoExceptionToAssert = new AccesoNoPermitidoException(throwableMocked);
    assertNotNull(accesoNoPermitidoExceptionToAssert);
    assertEquals(throwableMocked, accesoNoPermitidoExceptionToAssert.getCause());
  }
}
