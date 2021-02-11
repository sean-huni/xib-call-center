package com.xib.assessment.exception;

import lombok.Getter;

public class TeamNotFoundException extends Exception {
    @Getter
    private final Long id;

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param id
     */
    public TeamNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
