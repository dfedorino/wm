package com.dfedorino.wm.actions;

import com.dfedorino.wm.WashingMachine;
import com.dfedorino.wm.programs.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(washingMachine.drainWater());
        return actionResult;
    }

    public ActionResult applyUnlock() {
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(washingMachine.unlock());
        return actionResult;
    }

    public ActionResult applyRun(Program program) {
        ActionResult actionResult = new ActionResult();
        actionResult.setApplied(washingMachine.run(program));
        return actionResult;
    }
}
