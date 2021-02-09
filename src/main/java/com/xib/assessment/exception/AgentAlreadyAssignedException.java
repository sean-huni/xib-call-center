package com.xib.assessment.exception;

import lombok.Getter;

@Getter
public class AgentAlreadyAssignedException extends Exception {
    private Long agentId;
    private Long teamId;
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param agentId
     * @param teamId
     */
    public AgentAlreadyAssignedException(String message, Long agentId, Long teamId) {
        super(message);
        this.agentId = agentId;
        this.teamId = teamId;
    }
}
