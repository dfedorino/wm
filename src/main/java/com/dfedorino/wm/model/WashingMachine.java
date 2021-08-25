package com.dfedorino.wm.model;

import com.dfedorino.wm.exception.MachineAlreadyRunningException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Component
public class WashingMachine {
    private final AtomicBoolean isRunningAProgram = new AtomicBoolean(false);
    private Status status = Status.WAITING;

    public String getStatus() {
        return status.toString();
    }

    public void run(Program program) {
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
                unlock();
                log.info(Thread.currentThread().getName() + " finished the " + program.getName() + " program");
                status = Status.WAITING;
                isRunningAProgram.set(false);
            };
            new Thread(process).start();
        } else {
            throw new MachineAlreadyRunningException();
        }
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

    public void drainWater() {
        log.info("Water is drained");
    }

    private void dry() {
        log.info("Machine dried the clothes");
    }

    public void unlock() {
        log.info("Door is unlocked");
    }

    private enum Status {
        WAITING, WASHING
    }
}
