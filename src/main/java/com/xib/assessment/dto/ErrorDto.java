package com.xib.assessment.dto;

import lombok.Getter;

@Getter
public class ErrorDto {
    private final String field;
    private final String rejectedValue;
    private final String message;

    public ErrorDto(Builder builder) {
        this.field = builder.field;
        this.message = builder.message;
        this.rejectedValue = builder.rejectedValue;
    }

    public static class Builder {
        private final String field;
        private final String rejectedValue;
        private String message;

        public Builder(String field, String rejectedValue) {
            this.field = field;
            this.rejectedValue = rejectedValue;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorDto build() {
            return new ErrorDto(this);
        }
    }
}
