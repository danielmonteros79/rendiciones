package com.sa.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SoaTimeOutExceptionTest {

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instace")
  void shouldCreateAnInstace() {
    //then
    SoaTimeOutException exception = new SoaTimeOutException();
    assertNotNull(exception);
  }

  @Test
  @DisplayName("Should create an instace with a message")
  void shouldCreateAnInstaceWithAMessage() {
    //then
    SoaTimeOutException exception = new SoaTimeOutException("message");
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
  }

  @Test
  @DisplayName("Should create an instace with a cause")
  void shouldCreateAnInstaceWithACause() {
    //given
    Throwable cause = new Throwable();
    //then
    SoaTimeOutException exception = new SoaTimeOutException(cause);
    assertNotNull(exception);
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should create an instace with a message and cause")
  void shouldCreateAnInstaceWithAMessageAndACause() {
    //given
    Throwable cause = new Throwable();
    //then
    SoaTimeOutException exception = new SoaTimeOutException("message", cause);
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
