package com.dfedorino.wm.programs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface ProgramsApi {

    @GetMapping(value = "/programs/{programName}", produces = "application/json")
    ResponseEntity<Program> getProgram(@PathVariable("programName") String programName);


    @GetMapping(value = "/programs", produces = "application/json")
    ResponseEntity<List<Program>> getPrograms();
}
