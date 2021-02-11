package com.xib.assessment.controller.errorhandler;

import com.xib.assessment.dto.ErrorDto;
import com.xib.assessment.exception.ManagedTeamException;
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

    private ErrorDto buildErrorMsg(ManagedTeamException ex) {
        String errorResp = String.format(extractMessageSource(ex.getMessage()), ex.getTeamId(), ex.getMaxPermittedManagers());
        return new ErrorDto.Builder("teamId", ex.getTeamId().toString()).setMessage(errorResp).build();
    }
}