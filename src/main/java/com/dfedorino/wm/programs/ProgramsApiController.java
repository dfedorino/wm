package com.dfedorino.wm.programs;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping(value = "/api", produces = "application/json")
public class ProgramsApiController {
    @Autowired
    private ProgramsService programsService;

    @GetMapping(value = "/programs/{programName}")
    public ResponseEntity<Program> getProgram(@PathVariable("programName") String programName) {
        return new ResponseEntity<>(programsService.findByName(programName), HttpStatus.OK);
    }

    @GetMapping(value = "/programs")
    public ResponseEntity<List<Program>> getPrograms() {
        return new ResponseEntity<>(programsService.findAll(), HttpStatus.OK);
    }
}
