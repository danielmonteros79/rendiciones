package com.sa.exceptions;

import java.io.IOException;

/**
 * Excepción específica para errores en la escritura de respuestas JSON.
 * Reemplaza el uso de Exception genérica según SonarQube S112.
 */
public class JsonResponseException extends IOException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor con mensaje de error.
     * @param message mensaje descriptivo del error
     */
    public JsonResponseException(String message) {
        super(message);
    }
    
    /**
     * Constructor con mensaje y causa raíz.
     * @param message mensaje descriptivo del error
     * @param cause excepción que causó este error
     */
    public JsonResponseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor solo con causa raíz.
     * @param cause excepción que causó este error
     */
    public JsonResponseException(Throwable cause) {
        super(cause);
    }
}
