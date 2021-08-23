package com.dfedorino.wm.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WashingMachine {
    private List<String> programs;

    public WashingMachine() {
        programs = new ArrayList<>();
        programs.add("Delicate");
        programs.add("Wool");
        programs.add("Daily 60");
        programs.add("Quick 15");
    }

    public List<String> getPrograms() {
        return programs;
    }
}
