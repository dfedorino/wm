package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.Program;
import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class WashingMachineService {
    @Autowired
    private WashingMachine washingMachine;

    public String getGreeting() {
        return "Washing Machine API";
    }

    public Set<String> getPrograms() {
        Set<String> programs = new HashSet<>();
        programs.add("Nothing to show yet");
        return programs;
    }

    public String executeProgram(long id) throws ExecutionException, InterruptedException {
        Program daily = new Program(1L, 100, 60, 2400);
        return washingMachine.run(daily).get();
    }
}
