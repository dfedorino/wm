package com.dfedorino.wm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class WashingMachineControllerAdvice {

    @ExceptionHandler(MachineAlreadyRunningException.class)
    public ResponseEntity<Object> handleMachineAlreadyRunning() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Machine is running");
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNpe(RuntimeException exception, WebRequest request) {
        return ResponseEntity.status(500).body("Server error, cause: " + exception.getClass().getSimpleName());
    }
}
