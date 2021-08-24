package com.dfedorino.wm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class WashingMachineControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Object> handleNpe(RuntimeException exception, WebRequest request) {
        return ResponseEntity.status(500).body("Server error, cause: " + exception.getClass().getSimpleName());
    }
}
