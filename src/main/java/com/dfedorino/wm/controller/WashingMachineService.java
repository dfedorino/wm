package com.dfedorino.wm.controller;

import com.dfedorino.wm.model.Program;
import com.dfedorino.wm.model.ProgramRepository;
import com.dfedorino.wm.model.WashingMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WashingMachineService {
    @Autowired
    private WashingMachine washingMachine;
    @Autowired
    private ProgramRepository programRepository;

    public String getGreeting() {
        return "Washing Machine API";
    }

    public List<String> getPrograms() {
        return programRepository.findAll().stream()
                .map(Program::getName)
                .collect(Collectors.toList());
    }

    public String getStatus() {
        return washingMachine.getStatus();
    }

    public void executeProgram(long id) {
        Program program = programRepository.findById(id);
        washingMachine.run(program);
    }
}
