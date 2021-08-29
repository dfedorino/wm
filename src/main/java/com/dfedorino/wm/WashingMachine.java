package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Component
public class WashingMachine implements IWashingMachine {
    private final AtomicBoolean isRunningAProgram = new AtomicBoolean(false);
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    @Override
    public synchronized boolean run(Program program) {
        Objects.requireNonNull(program);
        Runnable process = getRunnableProgram(program);
        if (!isRunningAProgram.get()) {
            isRunningAProgram.compareAndSet(false, true);
            singleThreadExecutor.submit(process);
            return true;
        }
        return false;
    }

    @Override
    public boolean forceDrain() {
        if (isRunningAProgram.get()) {
            log.warn("Attempt to drain water while running a program");
            return false;
        }
        drain();
        return true;
    }

    @Override
    public boolean forceUnlock() {
        if (isRunningAProgram.get()) {
            log.warn("Attempt to unlock the machine while running a program");
            return false;
        }
        unlock();
        return true;
    }

    private Runnable getRunnableProgram(Program program) {
        return () -> {
            log.info(Thread.currentThread().getName() + " started the " + program.getName() + " program");
            lock();
            fill(program.getWaterVolume());
            heatWater(program.getWaterTemperature());
            wash(program.getWashingTime());
            drain();
            dry();
            unlock();
            isRunningAProgram.compareAndSet(true, false);
            log.info(Thread.currentThread().getName() + " finished the " + program.getName() + " program");
        };
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
            Thread.sleep(duration);
            log.info("Machine has been washing the clothes for " + duration + " milliseconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drain() {
        log.info("Water is drained");
    }

    private void dry() {
        log.info("Machine dried the clothes");
    }

    private void unlock() {
        log.info("Door is unlocked");
    }
}
