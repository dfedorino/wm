package com.dfedorino.wm.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class WashingMachine {
    private Map<String, Program> programs;
    private boolean isLocked;
    private boolean isFilledWithWater;

    public WashingMachine() {
        programs = new HashMap<>();
        programs.put("daily60", new DailyProgram(this));
    }

    public Program getProgram(String name) {
        return programs.get(name);
    }

    public Set<String> getPrograms() {
        return programs.keySet();
    }

    public String lock() {
        isLocked = true;
        return "Machine is locked";
    }

    public String fill(int waterVolume) {
        if (isLocked) {
            isFilledWithWater = true;
            return "Machine is filled with " + waterVolume + " liters of water";
        } else {
            throw new IllegalStateException("Machine is not locked");
        }
    }

    public String heatWater(int temperature) {
        if (isFilledWithWater) {
            return "Water temperature is " + temperature + " Celsius degrees";
        } else {
            throw new IllegalStateException("Machine is not filled with water");
        }
    }

    public String wash(long duration) {
        isFilledWithWater = false;
        return "Machine have been washing the clothes for " + duration + " seconds";
    }

    public String dry() {
        return "Machine dried the clothes";
    }

    public String unlock() {
        isLocked = false;
        return "Machine is unlocked";
    }
}
