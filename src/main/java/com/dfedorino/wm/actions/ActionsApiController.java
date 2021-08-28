package com.dfedorino.wm.actions;

import com.dfedorino.wm.programs.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActionsApiController implements ActionsApi {
    @Autowired
    private ActionsService actionsService;

    public ResponseEntity<List<Action>> getActions() {
        return new ResponseEntity<>(actionsService.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<ActionResult> postDrain() {
        ActionResult actionResult = actionsService.applyDrain();
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(actionResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(actionResult, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<ActionResult> postUnlock() {
        ActionResult actionResult = actionsService.applyUnlock();
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(actionResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(actionResult, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<ActionResult> postRun(@RequestBody Program program) {
        ActionResult actionResult = actionsService.applyRun(program);
        if (actionResult.isApplied()) {
            return new ResponseEntity<>(actionResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(actionResult, HttpStatus.CONFLICT);
        }
    }
}
