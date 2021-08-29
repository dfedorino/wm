package com.dfedorino.wm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MachineAlreadyRunningException.class)
    public ResponseEntity<Object> handleMachineAlreadyRunning() {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Machine is running");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProgramNotFoundException.class)
    public ResponseEntity<ApiExceptionDetails> handleProgramNotFound(ProgramNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiExceptionDetails(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNpe(RuntimeException exception, WebRequest request) {
        return ResponseEntity.status(500).body("Server error, cause: " + exception.getClass().getSimpleName());
    }
}
