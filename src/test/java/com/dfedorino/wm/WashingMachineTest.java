package com.dfedorino.wm;

import com.dfedorino.wm.programs.Program;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class WashingMachineTest {
    @Test
    void testRun_whenMultipleThreadsRunPrograms_thenOnlyOneSucceeds() throws InterruptedException {
        int requests = 1000;
        ExecutorService simultaneousRequests = Executors.newFixedThreadPool(requests);
        WashingMachine washingMachine = new WashingMachine();
        Program program = new Program("daily", 1000, 60, 2400);
        Callable<Boolean> runProgramTask = () -> washingMachine.run(program);
        List<Callable<Boolean>> tasks = IntStream.range(0, requests)
                .mapToObj(i -> runProgramTask)
                .collect(Collectors.toList());
        List<Boolean> result = simultaneousRequests.invokeAll(tasks).stream()
                .map(this::wrappedGet)
                .collect(Collectors.toList());
        assertThat(result).containsOnlyOnce(true);
    }

    @Test
    void testRun_whenProgramIsRunning_thenForceUnlockReturnsFalse() {
        ExecutorService simultaneousRequests = Executors.newFixedThreadPool(2);
        WashingMachine washingMachine = new WashingMachine();
        Program program = new Program("daily", 10, 10, 10);
        Future<Boolean> runProgramResult = simultaneousRequests.submit(() -> washingMachine.run(program));
        Future<Boolean> forceUnlockResult = simultaneousRequests.submit(washingMachine::forceUnlock);
        assertThat(wrappedGet(runProgramResult)).isTrue();
        assertThat(wrappedGet(forceUnlockResult)).isFalse();
    }

    @Test
    void testRun_whenProgramIsRunning_thenForceDrainReturnsFalse() {
        ExecutorService simultaneousRequests = Executors.newFixedThreadPool(2);
        WashingMachine washingMachine = new WashingMachine();
        Program program = new Program("daily", 10, 10, 10);
        Future<Boolean> runProgramResult = simultaneousRequests.submit(() -> washingMachine.run(program));
        Future<Boolean> forceDrainResult = simultaneousRequests.submit(washingMachine::forceUnlock);
        assertThat(wrappedGet(runProgramResult)).isTrue();
        assertThat(wrappedGet(forceDrainResult)).isFalse();
    }

    private <T> T wrappedGet(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}