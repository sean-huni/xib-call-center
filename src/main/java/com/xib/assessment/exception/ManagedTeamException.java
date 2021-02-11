package com.xib.assessment.exception;

import lombok.Getter;

@Getter
public class ManagedTeamException extends Exception {
    private final Long teamId;
    private final Integer maxPermittedManagers;

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message              the detail message. The detail message is saved for
     *                             later retrieval by the {@link #getMessage()} method.
     * @param teamId
     * @param maxPermittedManagers
     */
    public ManagedTeamException(String message, Long teamId, Integer maxPermittedManagers) {
        super(message);
        this.teamId = teamId;
        this.maxPermittedManagers = maxPermittedManagers;
    }
}
