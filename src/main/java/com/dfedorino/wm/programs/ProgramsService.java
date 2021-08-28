package com.dfedorino.wm.programs;

import com.dfedorino.wm.exception.ProgramNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramsService {
    @Autowired
    private ProgramRepository programRepository;

    public Program findByName(String programName) {
        return programRepository.findByName(programName)
                .orElseThrow(() -> new ProgramNotFoundException(programName));
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }
}
