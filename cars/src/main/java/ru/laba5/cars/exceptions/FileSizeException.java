package ru.laba5.cars.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FileSizeException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public Map<String, String> handleInvalidUpload(MultipartException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
}
