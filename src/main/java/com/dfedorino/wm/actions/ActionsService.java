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
    @Autowired
    private ActionResult actionResult;

    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    public ActionResult applyDrain() {
        actionResult.setApplied(washingMachine.drainWater());
        return actionResult;
    }

    public ActionResult applyUnlock() {
        actionResult.setApplied(washingMachine.unlock());
        return actionResult;
    }

    public ActionResult applyRun(Program program) {
        actionResult.setApplied(washingMachine.run(program));
        return actionResult;
    }
}
