package com.dfedorino.wm.programs;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Log4j2
@RequestMapping(value = "/api", produces = "application/json")
public class ProgramsApiController {
    @Autowired
    private ProgramsService programsService;

    @GetMapping(value = "/programs/{programName}")
    public ResponseEntity<EntityModel<Program>> getProgram(@PathVariable("programName") String programName) {
        Program program = programsService.findByName(programName);
        EntityModel<Program> programEntity = EntityModel.of(program,
                linkTo(methodOn(ProgramsApiController.class).getProgram(programName)).withSelfRel(),
                linkTo(methodOn(ProgramsApiController.class).getPrograms()).withRel("programs"));
        return new ResponseEntity<>(programEntity, HttpStatus.OK);
    }

    @GetMapping(value = "/programs")
    public ResponseEntity<CollectionModel<EntityModel<Program>>> getPrograms() {
        List<EntityModel<Program>> programs = programsService.findAll().stream()
                .map(program -> EntityModel.of(program,
                        linkTo(methodOn(ProgramsApiController.class).getProgram(program.getName())).withSelfRel(),
                        linkTo(methodOn(ProgramsApiController.class).getPrograms()).withRel("programs")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(programs,
                linkTo(methodOn(ProgramsApiController.class).getPrograms()).withSelfRel()));
    }
}
