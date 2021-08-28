package com.dfedorino.wm.programs;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class ProgramsApiController implements ProgramsApi {
    @Autowired
    private ProgramsService programsService;

    public ResponseEntity<Program> getProgram(@PathVariable("programName") String programName) {
        return new ResponseEntity<>(programsService.findByName(programName), HttpStatus.OK);
    }

    public ResponseEntity<List<Program>> getPrograms() {
        return new ResponseEntity<>(programsService.findAll(), HttpStatus.OK);
    }
}
