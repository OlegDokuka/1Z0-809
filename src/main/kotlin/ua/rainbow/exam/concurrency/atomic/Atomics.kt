package ua.rainbow.exam.concurrency.atomic

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong


class Atomics {
    private var incrementer: AtomicLong = AtomicLong(0);

    fun run() {
        var executorService: ExecutorService? = null;

        try {
            executorService = Executors.newFixedThreadPool(10);

            for (i in 0..19) {
                executorService.submit { print(" ".plus(incrementer.incrementAndGet())) };
            }

        } finally {
            executorService?.shutdown();
        }
    }
}

fun main(args: Array<String>) {
    Atomics().run();

}