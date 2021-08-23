package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WashingMachineController {
    @Autowired
    private WashingMachine washingMachine;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Washing Machine API");
    }

    @GetMapping("/programs")
    public ResponseEntity<List<String>> getPrograms() {
        return ResponseEntity.ok(washingMachine.getPrograms());
    }
}
