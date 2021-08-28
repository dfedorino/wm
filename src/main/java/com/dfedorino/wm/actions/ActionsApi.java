package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api", produces = "application/json")
public interface ActionsApi {

    @GetMapping(value = "/actions")
    ResponseEntity<CollectionModel<EntityModel<Action>>> getActions();

    @PostMapping(value = "/actions/unlock")
    ResponseEntity<EntityModel<ActionResult>> postUnlock();

    @PostMapping(value = "/actions/drain")
    ResponseEntity<EntityModel<ActionResult>> postDrain();

    @PostMapping(value = "/actions/run", consumes = "application/json")
    ResponseEntity<EntityModel<ActionResult>> postRun(@RequestBody Program body);
}
