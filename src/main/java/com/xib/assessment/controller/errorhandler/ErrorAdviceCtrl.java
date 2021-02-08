package com.xib.assessment.controller.errorhandler;

import com.xib.assessment.dto.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;


@Log4j2
@RestControllerAdvice
public class ErrorAdviceCtrl extends MessageSourceCtrlAdvice{
    protected ErrorAdviceCtrl(MessageSource messageSource) {
        super(messageSource);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<ErrorDto> handleConstraintException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        String errorResp = extractMessageSource("error.validation.id");
        log.info("Message Source: {}", errorResp);
        return getErrorResponseEntity(errorResp, ex);
    }


    private ResponseEntity<ErrorDto> getErrorResponseEntity(String errorResp, MethodArgumentNotValidException ex) {
        String field = Objects.nonNull(ex) && Objects.nonNull(ex.getBindingResult().getFieldError()) ? ex.getBindingResult().getFieldError().getField() : null;
        String rejectedValue =  Objects.nonNull(ex) && Objects.nonNull(ex.getBindingResult().getFieldError()) ? ex.getBindingResult().getFieldError().getRejectedValue().toString() : null;
        ErrorDto error = new ErrorDto.Builder(field, rejectedValue)
                .setMessage(errorResp).build();
        log.info("Error Response: {}", error);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
