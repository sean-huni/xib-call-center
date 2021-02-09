package com.xib.assessment.controller.errorhandler;

import com.xib.assessment.dto.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


@Log4j2
@RestControllerAdvice
public class ValidationErrorAdviceCtrl extends MessageSourceCtrlAdvice {
    protected ValidationErrorAdviceCtrl(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<ErrorDto> handleConstraintException(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage(), ex);
        ErrorDto err = buildErrorMsg(ex);
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    private ErrorDto buildErrorMsg(MethodArgumentNotValidException ex) {
        FieldError fieldError = Objects.nonNull(ex) && Objects.nonNull(ex.getBindingResult().getFieldError()) ? ex.getBindingResult().getFieldError() : null;
        String field = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField();
        String rejectedValue = (String) Objects.requireNonNull(fieldError).getRejectedValue();
        String messageSourceCode = Objects.requireNonNull(fieldError).getDefaultMessage();

        String errorResp = extractMessageSource(messageSourceCode);

        log.info("Message Source: {}", errorResp);

        return new ErrorDto.Builder(field, rejectedValue)
                .setMessage(errorResp).build();
    }
}
