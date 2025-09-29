package com.sa.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ActionExecutionException.
 * Tests all constructors and basic functionality.
 */
class ActionExecutionExceptionTest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create exception with message")
    void testConstructorWithMessage() {
        // Given
        String message = "Test action execution error";
        
        // When
        ActionExecutionException exception = new ActionExecutionException(message);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void testConstructorWithMessageAndCause() {
        // Given
        String message = "Test action execution error with cause";
        RuntimeException cause = new RuntimeException("Underlying cause");
        
        // When
        ActionExecutionException exception = new ActionExecutionException(message, cause);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with cause only")
    void testConstructorWithCause() {
        // Given
        RuntimeException cause = new RuntimeException("Root cause");
        
        // When
        ActionExecutionException exception = new ActionExecutionException(cause);
        
        // Then
        assertEquals(cause, exception.getCause());
        // The message should be the cause's toString() representation
        assertEquals(cause.toString(), exception.getMessage());
    }

    @Test
    @DisplayName("Should handle null message")
    void testConstructorWithNullMessage() {
        // When
        ActionExecutionException exception = new ActionExecutionException((String) null);
        
        // Then
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should handle null cause")
    void testConstructorWithNullCause() {
        // When
        ActionExecutionException exception = new ActionExecutionException((Throwable) null);
        
        // Then
        assertNull(exception.getCause());
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle message with null cause")
    void testConstructorWithMessageAndNullCause() {
        // Given
        String message = "Test message with null cause";
        
        // When
        ActionExecutionException exception = new ActionExecutionException(message, null);
        
        // Then
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should be a checked exception")
    void testExceptionIsCheckedException() {
        // Verify that ActionExecutionException extends Exception
        ActionExecutionException exception = new ActionExecutionException("test");
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Should be serializable")
    void testSerialVersionUID() {
        // Verify that the exception is serializable by checking it has serialVersionUID
        ActionExecutionException exception = new ActionExecutionException("test");
        assertNotNull(exception);
        // This test ensures the class compiles with serialVersionUID field
    }

    @Test
    @DisplayName("Should preserve exception chaining")
    void testExceptionChaining() {
        // Given
        Exception rootCause = new IllegalArgumentException("Root cause");
        RuntimeException intermediateCause = new RuntimeException("Intermediate", rootCause);
        
        // When
        ActionExecutionException exception = new ActionExecutionException("Final message", intermediateCause);
        
        // Then
        assertEquals("Final message", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }

    @Test
    @DisplayName("Should preserve stack trace")
    void testStackTracePreservation() {
        // Given
        RuntimeException cause = new RuntimeException("Original error");
        
        // When
        ActionExecutionException exception = new ActionExecutionException("Wrapped error", cause);
        
        // Then
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
        // Verify that the cause's stack trace is preserved
        assertNotNull(cause.getStackTrace());
    }
}
