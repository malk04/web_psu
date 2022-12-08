package ru.laba5.cars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(RegistrationException.class)
    public Map<String, String> handleInvalidRegistration(RegistrationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        if (ex.getMessage() == "Пользователь с таким именем уже зарегистрирован"){
            errorMap.put("username", ex.getMessage());
        } else if (ex.getMessage() == "Пользователь с такой почтой уже зарегистрирован"){
            errorMap.put("email", ex.getMessage());
        }
        return errorMap;
    }
}
