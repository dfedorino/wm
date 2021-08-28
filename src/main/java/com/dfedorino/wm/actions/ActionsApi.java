package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/api", produces = "application/json")
public interface ActionsApi {

    @GetMapping(value = "/actions")
    ResponseEntity<List<Action>> getActions();


    @PostMapping(value = "/actions/drain")
    ResponseEntity<ActionResult> postDrain();


    @PostMapping(value = "/actions/unlock")
    ResponseEntity<ActionResult> postUnlock();


    @PostMapping(value = "/actions/run", consumes = "application/json")
    ResponseEntity<ActionResult> postRun(@RequestBody Program body);
}
