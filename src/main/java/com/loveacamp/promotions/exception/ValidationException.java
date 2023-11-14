package com.loveacamp.promotions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (ObjectError globalError : globalErrors) {
            errorResponse.getErrors().add(new ValidationError(globalError.getDefaultMessage(), null, globalError.getCode()));
        }

        for (FieldError fieldError : fieldErrors) {
            errorResponse.getErrors().add(new ValidationError(fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getCode()));
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public static class ValidationError {
        private String message;
        private String field;
        private String code;

        protected ValidationError(String message, String field, String code) {
            this.message = message;
            this.field = field;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public ValidationError setMessage(String message) {
            this.message = message;
            return this;
        }

        public String getField() {
            return field;
        }

        public ValidationError setField(String field) {
            this.field = field;
            return this;
        }

        public String getCode() {
            return code;
        }

        public ValidationError setCode(String code) {
            this.code = code;
            return this;
        }
    }

    public static class ValidationErrorResponse {
        private List<ValidationError> errors = new ArrayList<>();

        public List<ValidationError> getErrors() {
            return errors;
        }

        public ValidationErrorResponse setErrors(List<ValidationError> errors) {
            this.errors = errors;
            return this;
        }
    }
}
