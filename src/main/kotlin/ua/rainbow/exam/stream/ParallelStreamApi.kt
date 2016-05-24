package ua.rainbow.exam.stream

import java.util.concurrent.ForkJoinPool
import java.util.stream.Collectors
import java.util.stream.IntStream


class ParallelStreamApi {

    fun runSimple() {
        val start = System.currentTimeMillis();

        IntStream.range(0, 10000).parallel().forEach { Thread.sleep(5) };

        println("Tasks completed in: ${(System.currentTimeMillis() - start) / 1000.0} seconds");
    }

    fun runOrdered() {
        val start = System.currentTimeMillis();

        IntStream.range(0, 10000).parallel().forEachOrdered { Thread.sleep(5) };

        println("Tasks completed in: ${(System.currentTimeMillis() - start) / 1000.0} seconds");
    }

    fun runWithCustomForkJoinPool() {
        ForkJoinPool(Runtime.getRuntime().availableProcessors() * 2).submit {
            runSimple();
        }.get()
    }

    fun collectToConccurentMap() {
        println(IntStream
                .range(0, 100000)
                .parallel()
                .mapToObj { it.toString() }
                .collect(Collectors.toConcurrentMap({ it: String -> it }, { 0 })))

    }

}

fun main(args: Array<String>) {
    ParallelStreamApi().runSimple();
    ParallelStreamApi().runOrdered();
    ParallelStreamApi().runWithCustomForkJoinPool();
}