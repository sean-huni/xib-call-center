package com.xib.assessment.controller.errorhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public abstract class MessageSourceCtrlAdvice {
    private static final Locale LOCALE = Locale.ENGLISH;
    private final MessageSource messageSource;

    protected MessageSourceCtrlAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    String extractMessageSource(final String sourceCode) {
        return messageSource.getMessage(sourceCode, null, LOCALE);
    }

}
