package com.dfedorino.wm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WashingMachineController {
    @Autowired
    private WashingMachineService washingMachineService;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok(washingMachineService.getGreeting());
    }

    @GetMapping("/programs")
    public ResponseEntity<List<String>> getPrograms() {
        return ResponseEntity.ok(washingMachineService.getPrograms());
    }

    @GetMapping("/programs/{id}")
    public ResponseEntity<String> executeProgram(@PathVariable long id) {
        washingMachineService.executeProgram(id);
        return ResponseEntity.ok(washingMachineService.getStatus());
    }
}
