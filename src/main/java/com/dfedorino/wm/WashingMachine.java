package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Component
public class WashingMachine implements IWashingMachine {
    private final AtomicBoolean isRunningAProgram = new AtomicBoolean(false);
    private Status status = Status.WAITING;

    @Override
    public String getStatus() {
        return status.toString();
    }

    @Override
    public boolean run(Program program) {
        Objects.requireNonNull(program);
        if (!isRunningAProgram.get()) {
            Runnable process = () -> {
                isRunningAProgram.set(true);
                log.info(Thread.currentThread().getName() + " started the " + program.getName() + " program");
                status = Status.WASHING;
                int waterVolume = program.getWaterVolume();
                int waterTemperature = program.getWaterTemperature();
                int washingTime = program.getWashingTime();
                lock();
                fill(waterVolume);
                heatWater(waterTemperature);
                wash(washingTime);
                drainWater();
                dry();
                isRunningAProgram.set(false);
                unlock();
                log.info(Thread.currentThread().getName() + " finished the " + program.getName() + " program");
                status = Status.WAITING;
            };
            new Thread(process).start();
            return true;
        }
        return false;
    }

    private void lock() {
        log.info("Door is locked");
    }

    private void fill(int waterVolume) {
        log.info("Drum is filled with " + waterVolume + " liters of water");
    }

    private void heatWater(int temperature) {
        log.info("Water is heated to " + temperature + " degrees Celsius");
    }

    private void wash(long duration) {
        try {
            Thread.sleep(5000);
            log.info("Machine have been washing the clothes for " + duration + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean drainWater() {
        log.info("Water is drained");
        return true;
    }

    private void dry() {
        log.info("Machine dried the clothes");
    }

    @Override
    public boolean unlock() {
        if (isRunningAProgram.get()) {
            log.warn("Attempt to unlock the machine while running a program");
        }
        log.info("Door is unlocked");
        return true;
    }

    private enum Status {
        WAITING, WASHING
    }
}
