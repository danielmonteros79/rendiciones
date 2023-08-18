package com.sa.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class DAOExceptionTest {

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should create an instance with an exception")
  void shouldCreateAnInstanceWithAnException() {
    //then
    DAOException exception = new DAOException(new RuntimeException());
    assertNotNull(exception);
  }

  @Test
  @DisplayName("Should create an instance with a message")
  void shouldCreateAnInstanceWithAMessage() {
    //then
    DAOException exception = new DAOException("message");
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
  }

  @Test
  @DisplayName("Should create an instance with an exception and a message")
  void shouldCreateAnInstanceWithAnExceptionAndAMessage() {
    //then
    DAOException exception = new DAOException("message", new RuntimeException());
    assertNotNull(exception);
    assertEquals("message", exception.getMessage());
  }
}
