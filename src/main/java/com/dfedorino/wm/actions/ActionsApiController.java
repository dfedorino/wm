package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActionsApiController implements ActionsApi {
    @Autowired
    private ActionsService actionsService;
    @Autowired
    private ActionModelAssembler assembler;

    @Override
    public ResponseEntity<CollectionModel<EntityModel<Action>>> getActions() {
        return ResponseEntity.ok(assembler.toCollection(actionsService.findAll()));
    }

    @Override
    public ResponseEntity<EntityModel<ActionResult>> postDrain() {
        ActionResult actionResult = actionsService.applyDrain();
        EntityModel<ActionResult> entityModel = EntityModel.of(actionResult,
                linkTo(methodOn(ActionsApi.class).postDrain()).withRel("action"),
                linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entityModel, HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<EntityModel<ActionResult>> postUnlock() {
        ActionResult actionResult = actionsService.applyUnlock();
        EntityModel<ActionResult> entityModel = EntityModel.of(actionResult,
                linkTo(methodOn(ActionsApi.class).postUnlock()).withRel("action"),
                linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entityModel, HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<EntityModel<ActionResult>> postRun(@RequestBody Program program) {
        ActionResult actionResult = actionsService.applyRun(program);
        EntityModel<ActionResult> entityModel = EntityModel.of(actionResult,
                linkTo(methodOn(ActionsApi.class).postRun(null)).withRel("action"),
                linkTo(methodOn(ActionsApi.class).getActions()).withRel("actions"));
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(entityModel, HttpStatus.CONFLICT);
        }
    }
}
