package com.sa.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JsonResponseException Tests")
class JsonResponseExceptionTest {

    @Test
    @DisplayName("Should create exception with message constructor")
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String expectedMessage = "JSON response writing failed";
        
        // Act
        JsonResponseException exception = new JsonResponseException(expectedMessage);
        
        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
        assertTrue(exception instanceof IOException);
    }

    @Test
    @DisplayName("Should create exception with null message")
    void shouldCreateExceptionWithNullMessage() {
        // Arrange & Act
        JsonResponseException exception = new JsonResponseException((String) null);
        
        // Assert
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with empty message")
    void shouldCreateExceptionWithEmptyMessage() {
        // Arrange
        String emptyMessage = "";
        
        // Act
        JsonResponseException exception = new JsonResponseException(emptyMessage);
        
        // Assert
        assertNotNull(exception);
        assertEquals(emptyMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and cause constructor")
    void shouldCreateExceptionWithMessageAndCause() {
        // Arrange
        String expectedMessage = "Error writing JSON response";
        RuntimeException cause = new RuntimeException("Network error");
        
        // Act
        JsonResponseException exception = new JsonResponseException(expectedMessage, cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
    }

    @Test
    @DisplayName("Should create exception with null message and valid cause")
    void shouldCreateExceptionWithNullMessageAndValidCause() {
        // Arrange
        IllegalArgumentException cause = new IllegalArgumentException("Invalid JSON format");
        
        // Act
        JsonResponseException exception = new JsonResponseException(null, cause);
        
        // Assert
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with message and null cause")
    void shouldCreateExceptionWithMessageAndNullCause() {
        // Arrange
        String expectedMessage = "JSON serialization error";
        
        // Act
        JsonResponseException exception = new JsonResponseException(expectedMessage, null);
        
        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with cause-only constructor")
    void shouldCreateExceptionWithCauseOnly() {
        // Arrange
        IOException cause = new IOException("Stream closed unexpectedly");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        // The message should include the cause's toString()
        assertTrue(exception.getMessage().contains("IOException"));
        assertTrue(exception.getMessage().contains("Stream closed unexpectedly"));
    }

    @Test
    @DisplayName("Should create exception with RuntimeException cause")
    void shouldCreateExceptionWithRuntimeExceptionCause() {
        // Arrange
        RuntimeException cause = new RuntimeException("Connection failed");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        // Message should be derived from cause
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("RuntimeException"));
        assertTrue(exception.getMessage().contains("Connection failed"));
    }

    @Test
    @DisplayName("Should create exception with IllegalArgumentException cause")
    void shouldCreateExceptionWithIllegalArgumentExceptionCause() {
        // Arrange
        IllegalArgumentException cause = new IllegalArgumentException("Invalid JSON format");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        assertTrue(exception.getMessage().contains("IllegalArgumentException"));
        assertTrue(exception.getMessage().contains("Invalid JSON format"));
    }

    @Test
    @DisplayName("Should create exception with NullPointerException cause")
    void shouldCreateExceptionWithNullPointerExceptionCause() {
        // Arrange
        NullPointerException cause = new NullPointerException("Null reference in JSON processing");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        assertTrue(exception.getMessage().contains("NullPointerException"));
        assertTrue(exception.getMessage().contains("Null reference in JSON processing"));
    }

    @Test
    @DisplayName("Should create exception with cause having null message")
    void shouldCreateExceptionWithCauseHavingNullMessage() {
        // Arrange
        RuntimeException cause = new RuntimeException((String) null);
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        // When cause has null message, the exception message should still be generated
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("RuntimeException"));
    }

    @Test
    @DisplayName("Should create exception with cause having empty message")
    void shouldCreateExceptionWithCauseHavingEmptyMessage() {
        // Arrange
        IllegalStateException cause = new IllegalStateException("");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof IOException);
        assertTrue(exception.getMessage().contains("IllegalStateException"));
    }

    @Test
    @DisplayName("Should create exception with deeply nested cause chain")
    void shouldCreateExceptionWithDeeplyNestedCauseChain() {
        // Arrange
        IllegalStateException rootCause = new IllegalStateException("Root cause");
        RuntimeException intermediateCause = new RuntimeException("Intermediate cause", rootCause);
        IOException directCause = new IOException("Direct cause", intermediateCause);
        
        // Act
        JsonResponseException exception = new JsonResponseException(directCause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(directCause, exception.getCause());
        assertEquals(intermediateCause, exception.getCause().getCause());
        assertEquals(rootCause, exception.getCause().getCause().getCause());
        assertTrue(exception instanceof IOException);
        assertTrue(exception.getMessage().contains("IOException"));
        assertTrue(exception.getMessage().contains("Direct cause"));
    }

    @Test
    @DisplayName("Should maintain original cause stack trace when using cause-only constructor")
    void shouldMaintainOriginalCauseStackTrace() {
        // Arrange
        Exception cause = new Exception("Original exception");
        StackTraceElement[] originalStackTrace = cause.getStackTrace();
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertEquals(cause, exception.getCause());
        assertArrayEquals(originalStackTrace, exception.getCause().getStackTrace());
    }

    @Test
    @DisplayName("Should create exception with cause-only constructor and verify inheritance")
    void shouldCreateExceptionWithCauseOnlyAndVerifyInheritance() {
        // Arrange
        Exception cause = new Exception("Test cause");
        
        // Act
        JsonResponseException exception = new JsonResponseException(cause);
        
        // Assert
        assertNotNull(exception);
        assertTrue(exception instanceof JsonResponseException);
        assertTrue(exception instanceof IOException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("Should create exception with null cause in cause-only constructor")
    void shouldCreateExceptionWithNullCauseInCauseOnlyConstructor() {
        // Arrange & Act
        JsonResponseException exception = new JsonResponseException((Throwable) null);
        
        // Assert
        assertNotNull(exception);
        assertNull(exception.getCause());
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should maintain inheritance hierarchy")
    void shouldMaintainInheritanceHierarchy() {
        // Arrange
        JsonResponseException exception = new JsonResponseException("Test message");
        
        // Act & Assert
        assertTrue(exception instanceof IOException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    @DisplayName("Should have correct serial version UID")
    void shouldHaveCorrectSerialVersionUID() throws Exception {
        // Arrange
        Class<JsonResponseException> clazz = JsonResponseException.class;
        
        // Act
        java.lang.reflect.Field serialVersionUIDField = clazz.getDeclaredField("serialVersionUID");
        serialVersionUIDField.setAccessible(true);
        long serialVersionUID = serialVersionUIDField.getLong(null);
        
        // Assert
        assertEquals(1L, serialVersionUID);
    }

    @Test
    @DisplayName("Should support stack trace operations")
    void shouldSupportStackTraceOperations() {
        // Arrange
        JsonResponseException exception = new JsonResponseException("Stack trace test");
        
        // Act
        StackTraceElement[] stackTrace = exception.getStackTrace();
        
        // Assert
        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
        assertEquals("shouldSupportStackTraceOperations", stackTrace[0].getMethodName());
    }

    @Test
    @DisplayName("Should support exception chaining with multiple causes")
    void shouldSupportExceptionChainingWithMultipleCauses() {
        // Arrange
        IllegalStateException rootCause = new IllegalStateException("Root cause");
        RuntimeException intermediateCause = new RuntimeException("Intermediate cause", rootCause);
        
        // Act
        JsonResponseException exception = new JsonResponseException("Final exception", intermediateCause);
        
        // Assert
        assertNotNull(exception);
        assertEquals("Final exception", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }

    @Test
    @DisplayName("Should handle special characters in message")
    void shouldHandleSpecialCharactersInMessage() {
        // Arrange
        String messageWithSpecialChars = "JSON error: {\"field\": \"value with üñîçødé characters\"} & <script>alert('xss')</script>";
        
        // Act
        JsonResponseException exception = new JsonResponseException(messageWithSpecialChars);
        
        // Assert
        assertNotNull(exception);
        assertEquals(messageWithSpecialChars, exception.getMessage());
    }

    @Test
    @DisplayName("Should preserve cause type information")
    void shouldPreserveCauseTypeInformation() {
        // Arrange
        NullPointerException specificCause = new NullPointerException("Null reference in JSON processing");
        
        // Act
        JsonResponseException exception = new JsonResponseException("JSON processing failed", specificCause);
        
        // Assert
        assertNotNull(exception);
        assertTrue(exception.getCause() instanceof NullPointerException);
        assertEquals("Null reference in JSON processing", exception.getCause().getMessage());
    }
}
