package com.yaren.eventplanner.config;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.yaren.eventplanner.controller")
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<HashMap<String, Object>> errors = parseError(e.getBindingResult().getFieldErrors());
        return ResponseEntity.badRequest().body(errors);
    }

    private List<HashMap<String, Object>> parseError(List<FieldError> fieldErrors) {
        List<HashMap<String, Object>> errors = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            HashMap<String, Object> errorMap = new HashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("message", error.getDefaultMessage());
            errorMap.put("rejectedValue", error.getRejectedValue());
            errors.add(errorMap);
        }
        return errors;
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", e.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", "An unexpected system error occurred: " + e.getMessage());
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Object value = ex.getValue();
        Class<?> requiredType = ex.getRequiredType();
        String message;

        if (requiredType != null) {
            if (requiredType == Long.class || requiredType == Integer.class) {
                message = paramName + " must be a numeric value. Received value: " + value;
            } else if (requiredType == Boolean.class) {
                message = paramName + "  must be true or false. Received value: " + value;
            } else {
                message = paramName + " parametresi için geçersiz değer: " + value;
            }
        } else {
            message = "Invalid value for parameter " + paramName;
        }

        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return ResponseEntity.badRequest().body(error);
    }
}