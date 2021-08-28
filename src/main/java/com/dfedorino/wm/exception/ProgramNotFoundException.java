package com.dfedorino.wm.exception;

public class ProgramNotFoundException extends RuntimeException {

    public ProgramNotFoundException(String programName) {
        super("Could not find program " + programName);
    }
}
