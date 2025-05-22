package com.sa.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SessionTimeOutExceptionTest {

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instance")
  void shouldCreateAnInstance() {
    //then
    SessionTimeOutException exception = new SessionTimeOutException();
    assertNotNull(exception);
  }

  @Test
  @DisplayName("Should create an instance with a message")
  void shouldCreateAnInstanceWithAMessage() {
    //then
    SessionTimeOutException exception = new SessionTimeOutException("message");
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
  }

  @Test
  @DisplayName("Should create an instance with a cause")
  void shouldCreateAnInstanceACause() {
    //given
    Throwable cause = new Throwable();
    //then
    SessionTimeOutException exception = new SessionTimeOutException(cause);
    assertNotNull(exception);
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should create an instance with a message and a cause")
  void shouldCreateAnInstanceWithAMessageAndACause() {
    //given
    Throwable cause = new Throwable();
    //then
    SessionTimeOutException exception = new SessionTimeOutException("message", cause);
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
