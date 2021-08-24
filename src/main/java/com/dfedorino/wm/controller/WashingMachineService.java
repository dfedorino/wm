package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.Program;
import com.dfedorino.wm.model.ProgramRepository;
import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WashingMachineService {
    @Autowired
    private WashingMachine washingMachine;
    @Autowired
    private ProgramRepository programRepository;

    public String getGreeting() {
        return "Washing Machine API";
    }

    public List<Program> getPrograms() {
        return programRepository.findAll();
    }

    public String executeProgram(long id) throws ExecutionException, InterruptedException {
        Program program = programRepository.findById(id);
        return washingMachine.run(program).get();
    }
}
