package com.dfedorino.wm.model;

public class DailyProgram implements Program {
    private final WashingMachine washingMachine;

    public DailyProgram(WashingMachine washingMachine) {
        this.washingMachine = washingMachine;
    }

    @Override
    public String execute() {
        int temperature = 60;
        int waterVolume = 100;
        long duration = 2400;

        return washingMachine.lock() +
                System.lineSeparator() +
                washingMachine.fill(waterVolume) +
                System.lineSeparator() +
                washingMachine.heatWater(temperature) +
                System.lineSeparator() +
                washingMachine.wash(duration) +
                System.lineSeparator() +
                washingMachine.dry() +
                System.lineSeparator() +
                washingMachine.unlock();
    }
}
