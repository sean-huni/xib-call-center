package com.xib.assessment.controller.errorhandler;

import com.xib.assessment.dto.ErrorDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.AgentTeamAssignmentException;
import com.xib.assessment.exception.TeamNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class TeamErrorAdviceCtrl extends  MessageSourceCtrlAdvice{
    protected TeamErrorAdviceCtrl(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleTeamNotFoundException(TeamNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgentNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleAgentNotFoundException(AgentNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgentAlreadyAssignedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleAgentNotFoundException(AgentAlreadyAssignedException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgentTeamAssignmentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleAgentNotFoundException(AgentTeamAssignmentException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    private ErrorDto buildErrorMsg(AgentNotFoundException ex){
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getId());
        return new ErrorDto.Builder("agent-id", ex.getId().toString()).setMessage(errorResp).build();
    }

    private ErrorDto buildErrorMsg(TeamNotFoundException ex){
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getId());
        return new ErrorDto.Builder("id", ex.getId().toString()).setMessage(errorResp).build();
    }

    private ErrorDto buildErrorMsg(AgentAlreadyAssignedException ex){
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getAgentId(), ex.getTeamId());
        return new ErrorDto.Builder("agent-id", ex.getAgentId().toString()).setMessage(errorResp).build();
    }

    private ErrorDto buildErrorMsg(AgentTeamAssignmentException ex){
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getId());
        return new ErrorDto.Builder("agent-id", ex.getId().toString()).setMessage(errorResp).build();
    }
}
