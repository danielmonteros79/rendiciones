package com.sa.exceptions;

/**
 * Exception thrown when an action execution fails due to business logic errors,
 * system errors, or validation failures.
 * 
 * This is a more specific alternative to the generic Exception class
 * for action execution contexts.
 */
public class ActionExecutionException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new ActionExecutionException with the specified message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public ActionExecutionException(String message) {
        super(message);
    }

    /**
     * Creates a new ActionExecutionException with the specified message and cause.
     *
     * @param message the detail message explaining the cause of the exception
     * @param cause   the underlying cause of this exception
     */
    public ActionExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new ActionExecutionException wrapping an existing exception.
     *
     * @param cause the underlying cause of this exception
     */
    public ActionExecutionException(Throwable cause) {
        super(cause);
    }
}
