package ua.rainbow.exam.concurrency

import java.util.*
import java.util.concurrent.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.exitProcess


class Executors {
    fun test() {
        val executorService = Executors.newSingleThreadExecutor();
        val future: Future<*>;

        println("-----");

        executorService.submit { println("hello") };
        executorService.submit { println("middle"); Thread.sleep(2000) };
        future = executorService.submit { println("bay") };

        println("-----");

        while (!future.isDone) {
            println("has not done yet");
            Thread.sleep(1000);
        }
    }
}


class CheckResults {
    var counter = 0;

    fun main() {
        val service: ExecutorService = Executors.newSingleThreadExecutor();
        try {
            val result = service.submit {
                for (i in 0..500) counter++;
            };
            result.get(10, TimeUnit.SECONDS);
            System.out.println("Reached! Result:".plus(counter));
        } catch (e: TimeoutException) {
            System.out.println("Not reached in time");
        } finally {
            service.shutdown();
        }
    }
}

class AddData {
    fun main() {
        val service: ExecutorService = Executors.newSingleThreadExecutor();
        try {
            val result: Future<Int> = service.submit(Callable { 30 + 10 });
            System.out.println(result.get());
        } finally {
            service.shutdown();
        }
    }
}

class PerformanceCollections {
    val synchronizedMap: MutableMap<Int, Any> = Collections.synchronizedMap(HashMap());
    val concurrentMap: MutableMap<Int, Any> = ConcurrentHashMap();
    val addSResult = AtomicLong();
    val addCResult = AtomicLong();
    val sizeC = AtomicInteger();
    val sizeS = AtomicInteger();

    fun test() {
        var executor: ExecutorService? = null;
        try {
            executor = Executors.newFixedThreadPool(100);

            for (i in 0..499) {
                executor.submit {
                    val start = System.currentTimeMillis();

                    (0..10000).forEach { concurrentMap.put(sizeC.andIncrement, Any()) };

                    addCResult.addAndGet(System.currentTimeMillis() - start);
                };

                executor.submit {
                    val start = System.currentTimeMillis();

                    (0..10000).forEach { synchronizedMap.put(sizeS.andIncrement, Any()) };

                    addSResult.addAndGet(System.currentTimeMillis() - start);
                };
            }
        } finally {
            executor?.shutdown();

            executor?.awaitTermination(2, TimeUnit.MINUTES);

            println("Synchronized Map: $addSResult; Concurrent Map: $addCResult;");
            println("Synchronized Map Size: ${synchronizedMap.size}; Concurrent Map: ${concurrentMap.size};");
        }
    }

}

fun main(args: Array<String>) {
    AddData().main();

    ua.rainbow.exam.concurrency.Executors().test();

    CheckResults().main()

    PerformanceCollections().test();

    exitProcess(0);
}