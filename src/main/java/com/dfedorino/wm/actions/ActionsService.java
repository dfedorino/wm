package com.dfedorino.wm.actions;

import com.dfedorino.wm.WashingMachine;
import com.dfedorino.wm.programs.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ActionsService {
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private WashingMachine washingMachine;

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public ActionResult applyDrain() {
        boolean isApplied = washingMachine.drainWater();
        Instant timestamp = ZonedDateTime.now().toInstant();
        return new ActionResult(isApplied, "drain", timestamp);
    }

    public ActionResult applyUnlock() {
        boolean isApplied = washingMachine.unlock();
        Instant timestamp = ZonedDateTime.now().toInstant();
        return new ActionResult(isApplied, "unlock", timestamp);
    }

    public ActionResult applyRun(Program program) {
        boolean isApplied = washingMachine.run(program);
        Instant timestamp = ZonedDateTime.now().toInstant();
        return new ActionResult(isApplied, "run", timestamp);
    }
}
