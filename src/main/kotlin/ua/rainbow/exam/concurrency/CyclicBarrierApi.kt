package ua.rainbow.exam.concurrency

import java.util.concurrent.CyclicBarrier
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CyclicBarrierApi {
    fun test() {
        var service: ExecutorService? = null;
        try {
            service = Executors.newFixedThreadPool(4);
            val manager = LionPenWorker();
            val c1 = CyclicBarrier(4);
            val c2 = CyclicBarrier(4, { println("*** Pen Cleaned!") });

            for (i in 0..3)
                service.submit({ manager.performTask(c1, c2) });
        } finally {
            service?.shutdown();
        }
    }
}

class LionPenWorker {
    private fun removeAnimals() {
        System.out.println("Removing animals");
    }

    private fun cleanPen() {
        System.out.println("Cleaning the pen");
    }

    private fun addAnimals() {
        System.out.println("Adding animals");
    }

    fun performTask(c1: CyclicBarrier, c2: CyclicBarrier) {
        removeAnimals();
        c1.await();
        cleanPen();
        c2.await();
        addAnimals();
    }
}

fun main(args: Array<String>) {
    CyclicBarrierApi().test()
}