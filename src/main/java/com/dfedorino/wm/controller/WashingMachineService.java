package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.Program;
import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WashingMachineService {
    @Autowired
    private WashingMachine washingMachine;

    public String getGreeting() {
        return "Washing Machine API";
    }

    public Set<String> getPrograms() {
        return washingMachine.getPrograms();
    }

    public Program getProgram(String name) {
        return washingMachine.getProgram(name);
    }
}
