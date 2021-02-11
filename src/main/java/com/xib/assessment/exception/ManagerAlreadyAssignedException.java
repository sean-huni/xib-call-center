package com.xib.assessment.exception;

import lombok.Getter;

@Getter
public class ManagerAlreadyAssignedException extends Exception {
    private final Long managerId;
    private final Long teamId;

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param managerId
     * @param teamId
     */
    public ManagerAlreadyAssignedException(String message, Long managerId, Long teamId) {
        super(message);
        this.managerId = managerId;
        this.teamId = teamId;
    }
}
