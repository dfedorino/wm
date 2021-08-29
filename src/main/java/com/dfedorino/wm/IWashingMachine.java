package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;

public interface IWashingMachine {
    /**
     * A command that runs specified program on the washing machine
     * @param program - a Program object from the predefined list of programs /api/programs
     * @return true if the command was successfully applied, false if another program is already running
     */
    boolean run(Program program);

    /**
     * A command that drains water in case of an unsuccessful program completion
     * @return true if the command was successfully applied, false if a program is running
     */
    boolean forceDrain();

    /**
     * A command that unlocks the door in case of an unsuccessful program completion
     * @return true if the command was successfully applied, false if a program is running
     */
    boolean forceUnlock();
}
