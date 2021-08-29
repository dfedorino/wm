package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ActionsApiController {
    @Autowired
    private ActionsService actionsService;

    @GetMapping(value = "/actions")
    ResponseEntity<CollectionModel<EntityModel<Action>>> getActions() {
        List<EntityModel<Action>> actionEntities = convertActionsToEntityModels();
        actionEntities.forEach(actionEntityModel -> addLinks(actionEntityModel, actionEntityModel.getContent().getName()));
        CollectionModel<EntityModel<Action>> collectionModel = CollectionModel.of(actionEntities,
                        linkTo(methodOn(ActionsApiController.class).getActions())
                        .withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping(value = "/actions/drain")
    ResponseEntity<EntityModel<ActionResult>> drain() {
        ActionResult actionResult = actionsService.applyDrain();
        return responseEntityWithLinks(actionResult, "drain");
    }

    @PostMapping(value = "/actions/unlock")
    ResponseEntity<EntityModel<ActionResult>> unlock() {
        ActionResult actionResult = actionsService.applyUnlock();
        return responseEntityWithLinks(actionResult, "unlock");
    }

    @PostMapping(value = "/actions/run", consumes = "application/json")
    ResponseEntity<EntityModel<ActionResult>> run(@RequestBody Program program) {
        ActionResult actionResult = actionsService.applyRun(program);
        return responseEntityWithLinks(actionResult, "run");
    }

    private List<EntityModel<Action>> convertActionsToEntityModels() {
        return actionsService.findAll().stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());
    }

    private ResponseEntity<EntityModel<ActionResult>> responseEntityWithLinks(ActionResult actionResult, String actionName) {
        EntityModel<ActionResult> resultModel = EntityModel.of(actionResult);
        addLinks(resultModel, actionName);
        HttpStatus status = actionResult.isApplied() ? HttpStatus.OK : HttpStatus.CONFLICT;
        return new ResponseEntity<>(resultModel, status);
    }

    private <T> void addLinks(EntityModel<T> actionModel, String actionName) {
        switch (actionName) {
            case "unlock":
                actionModel.add(linkTo(methodOn(ActionsApiController.class).unlock()).withSelfRel());
                break;
            case "drain":
                actionModel.add(linkTo(methodOn(ActionsApiController.class).drain()).withSelfRel());
                break;
            case "run":
                actionModel.add(linkTo(methodOn(ActionsApiController.class).run(null)).withSelfRel());
                break;
        }
        actionModel.add(linkTo(methodOn(ActionsApiController.class).getActions()).withRel("actions"));
    }
}
