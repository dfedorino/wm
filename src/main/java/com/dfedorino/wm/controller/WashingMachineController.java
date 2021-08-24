package com.dfedorino.wm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
public class WashingMachineController {
    @Autowired
    private WashingMachineService washingMachineService;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(washingMachineService.getGreeting());
    }

    @GetMapping("/programs")
    public ResponseEntity<Set<String>> getPrograms() {
        return ResponseEntity.ok(washingMachineService.getPrograms());
    }

    @GetMapping("/programs/{name}")
    public ResponseEntity<String> executeProgram(@PathVariable long id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(washingMachineService.executeProgram(id));
    }
}
