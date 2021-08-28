package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;

public interface IWashingMachine {
    String getStatus();

    boolean run(Program program);

    boolean drainWater();

    boolean unlock();
}
