package ua.rainbow.exam.concurrency;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class CyclicBarrierTest {
    private static CyclicBarrier c1 = null;

    /**
     * Bad practice is reusing already broker barrier
     * So, create pass supplier instead of reference to object
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        pensManaging(4, 4);
        pensManaging(2, 4);
    }

    private static void pensManaging(int workerCount, int pensCount) throws InterruptedException {
        ExecutorService service = null;
        try {
            service = java.util.concurrent.Executors.newFixedThreadPool(workerCount);
            c1 = new CyclicBarrier(pensCount, () -> {
                System.out.println("All animal has been removed");
//                c1 = new CyclicBarrier(pensCount, () -> System.out.println("All pens has been cleaned"));
            });
            JavaLionPenManager manager = new JavaLionPenManager(() -> c1);
            for (int i = 0; i < workerCount; i++)
                service.submit(() -> {
                    try {
                        manager.performTask();
                    } catch (TimeoutException e) {
                        System.err.println("Uncompleted task");
                        c1.reset();
                    }
                });
        } finally {
            if (service != null) service.shutdown();
        }

        service.awaitTermination(1, TimeUnit.DAYS);
    }
}

class JavaLionPenManager {
    private final Supplier<CyclicBarrier> barrierSupplier;

    JavaLionPenManager(Supplier<CyclicBarrier> barrierSupplier) {
        this.barrierSupplier = barrierSupplier;
    }

    private void removeAnimals() {
        System.out.println("Removing animals");
    }

    private void cleanPen() {
        System.out.println("Cleaning the pen");
    }

    private void addAnimals() {
        System.out.println("Adding animals");
    }

    void performTask() throws TimeoutException {
        try {
            removeAnimals();
            barrierSupplier.get().await(3, TimeUnit.SECONDS);
            cleanPen();
            barrierSupplier.get().await(3, TimeUnit.SECONDS);
            addAnimals();
        } catch (InterruptedException | BrokenBarrierException ignored) {
        }
    }
}
