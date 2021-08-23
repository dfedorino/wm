package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Set;

@RestController
public class WashingMachineController {
    @Autowired
    private WashingMachine washingMachine;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Washing Machine API");
    }

    @GetMapping("/programs")
    public ResponseEntity<Set<String>> getPrograms() {
        return ResponseEntity.ok(washingMachine.getPrograms());
    }

    @GetMapping("/programs/{name}")
    public ResponseEntity<String> executeProgram(@PathVariable String name) {
        return ResponseEntity.ok(washingMachine.getProgram(name.toLowerCase(Locale.ROOT)).execute());
    }
}
