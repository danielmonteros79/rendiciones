package com.sa.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ImposibleLeerXMLExceptionTest {

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instance with a message and a cause")
  void shouldCreateAnInstanceWithAMessageAndACause() {
    //given
    Throwable cause = new Throwable();
    //then
    ImposibleLeerXMLException exception = new ImposibleLeerXMLException("message", cause);
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("Should create an instance with a cause")
  void shouldCreateAnInstanceWithACause() {
    //given
    Throwable cause = new Throwable();
    //then
    ImposibleLeerXMLException exception = new ImposibleLeerXMLException(cause);
    assertNotNull(exception);
    assertEquals(cause, exception.getCause());
  }
}
