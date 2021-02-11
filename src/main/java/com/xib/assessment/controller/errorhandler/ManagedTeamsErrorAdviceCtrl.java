package com.xib.assessment.controller.errorhandler;

import com.xib.assessment.dto.ErrorDto;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ManagedTeamsErrorAdviceCtrl extends MessageSourceCtrlAdvice {

    protected ManagedTeamsErrorAdviceCtrl(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(ManagedTeamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleTeamNotFoundException(ManagedTeamException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleTeamNotFoundException(ManagerNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ManagerAlreadyAssignedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleTeamNotFoundException(ManagerAlreadyAssignedException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    private ErrorDto buildErrorMsg(ManagedTeamException ex) {
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getTeamId(), ex.getMaxPermittedManagers());
        return new ErrorDto.Builder("team-id", ex.getTeamId().toString()).setMessage(errorResp).build();
    }

    private ErrorDto buildErrorMsg(ManagerAlreadyAssignedException ex) {
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getManagerId(), ex.getTeamId());
        return new ErrorDto.Builder("manager-id", ex.getManagerId().toString()).setMessage(errorResp).build();
    }

    private ErrorDto buildErrorMsg(ManagerNotFoundException ex) {
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getId());
        return new ErrorDto.Builder("manager-id", ex.getId().toString()).setMessage(errorResp).build();
    }
}