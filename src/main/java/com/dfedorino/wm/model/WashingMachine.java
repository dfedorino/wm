package com.dfedorino.wm.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class WashingMachine {
    private final AtomicBoolean isRunningAProgram = new AtomicBoolean(false);

    public Future<String> run(Program program) {
        isRunningAProgram.set(true);
        int waterVolume = program.getWaterVolume();
        int waterTemperature = program.getWaterTemperature();
        int washingTime = program.getWashingTime();
        String process = lock() +
                System.lineSeparator() +
                fill(waterVolume) +
                System.lineSeparator() +
                heatWater(waterTemperature) +
                System.lineSeparator() +
                wash(washingTime) +
                System.lineSeparator() +
                drainWater() +
                dry() +
                unlock();
        isRunningAProgram.set(false);
        FutureTask<String> futureTask = new FutureTask<>(() -> process);
        futureTask.run();
        return futureTask;
    }

    private String drainWater() {
        return "Water is drained";
    }

    public String lock() {
        return "Machine is locked";
    }

    public String fill(int waterVolume) {
        return "Machine is filled with " + waterVolume + " liters of water";
    }

    public String heatWater(int temperature) {
        return "Water temperature is " + temperature + " Celsius degrees";
    }

    public String wash(long duration) {
        return "Machine have been washing the clothes for " + duration + " seconds";
    }

    public String dry() {
        return "Machine dried the clothes";
    }

    public String unlock() {
        return "Machine is unlocked";
    }
}
