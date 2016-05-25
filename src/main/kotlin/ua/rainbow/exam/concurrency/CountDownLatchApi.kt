package ua.rainbow.exam.concurrency

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CountDownLatchApi {
    fun test() {
        var service: ExecutorService? = null;
        try {
            service = Executors.newFixedThreadPool(4);
            val manager = LionPenManager();
            val c1 = CountDownLatch(4);
            val c2 = CountDownLatch(4);

            for (i in 0..3)
                service.submit({ manager.performTask(c1, c2) });
        } finally {
            service?.shutdown();
        }
    }
}

private class LionPenManager {
    private fun removeAnimals() {
        System.out.println("Removing animals");
    }

    private fun cleanPen() {
        System.out.println("Cleaning the pen");
    }

    private fun addAnimals() {
        System.out.println("Adding animals");
    }

    fun performTask(c1: CountDownLatch, c2: CountDownLatch) {
        removeAnimals();
        c1.countDown()
        c1.await();
        cleanPen();
        c2.countDown()
        c2.await();
        addAnimals();
    }
}

fun main(args: Array<String>) {
    CountDownLatchApi().test()
}

