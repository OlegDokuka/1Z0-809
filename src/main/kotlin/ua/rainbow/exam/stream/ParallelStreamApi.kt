package ua.rainbow.exam.stream

import java.util.concurrent.ForkJoinPool
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream


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

    fun findAny() {
        println("The result is ${IntStream.range(0, 10000).parallel().findFirst().asInt}");
    }

    fun ordered() {
        val start = System.currentTimeMillis();

        IntStream.range(0, 100000)
                .unordered()
                .parallel()
                .map { it * 2 }
                .forEach { "$it " }

        println("\n-------------------------------Ordered-------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
        println("\n--------------------------------------------------------------------")
    }

    //May produce additional performance
    fun unordered() {
        val start = System.currentTimeMillis();

        IntStream.range(0, 100000)
                .unordered()
                .map { it * 2 }
                .forEach { "$it " }
        println("\n----------------------------unordered--------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
        println("\n--------------------------------------------------------------------")
    }

    fun nonparallel() {
        val start = System.currentTimeMillis();

        IntStream.range(0, 100000)
                .map { it * 2 }
                .forEach { "$it " }

        println("\n----------------------------nonparallel--------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
        println("\n--------------------------------------------------------------------")
    }
    // 3 Args reduce is faster then 2 args one
    fun reducing() {
        val start = System.currentTimeMillis();
        val result = Stream.of('T', 'h', 'e', 'r', 'e', ' ', 'i', 's', ' ', 'm', 'o', 'r', 'e', ' ', 'd', 'i', 'f', 'f', 'i', 'c', 'u', 'l', 't', ' ', 't', 'h', 'a', 'n', ' ', 'i', 't', ' ', 's', 'e', 'e', 'm', 's', ' ', 't', 'o', ' ', 'b', 'e')
                .unordered()
                .parallel()
                .unordered()
                .reduce("", { idt, v -> idt.plus(v) }, { st1, st2 -> st1.plus(st2) })

        println("Result of reducing is '$result'");
        println("\n----------------------------3 Args Reducing--------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
    }

    fun reducingWithStringBuilder() {
        val start = System.currentTimeMillis();
        val result = Stream.of('T', 'h', 'e', 'r', 'e', ' ', 'i', 's', ' ', 'm', 'o', 'r', 'e', ' ', 'd', 'i', 'f', 'f', 'i', 'c', 'u', 'l', 't', ' ', 't', 'h', 'a', 'n', ' ', 'i', 't', ' ', 's', 'e', 'e', 'm', 's', ' ', 't', 'o', ' ', 'b', 'e')
                .unordered()
                .parallel()
                .unordered()
                .reduce(StringBuilder(), { idt, v -> StringBuilder().append(idt).append(v) }, StringBuilder::append)

        println("Result of reducing is '$result'");
        println("\n----------------------------3 Args Reducing--------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
    }

    fun reducing2() {
        val start = System.currentTimeMillis();
        val result = Stream.of('T', 'h', 'e', 'r', 'e', ' ', 'i', 's', ' ', 'm', 'o', 'r', 'e', ' ', 'd', 'i', 'f', 'f', 'i', 'c', 'u', 'l', 't', ' ', 't', 'h', 'a', 'n', ' ', 'i', 't', ' ', 's', 'e', 'e', 'm', 's', ' ', 't', 'o', ' ', 'b', 'e')
                .unordered()
                .parallel()
                .unordered()
                .map { it.toString() }
                .reduce("", { st1, st2 -> st1.plus(st2) })

        println("Result of reducing is '$result'");
        println("\n----------------------------2 Args Reducing--------------------------------------")
        println("Take ${System.currentTimeMillis() - start} ms");
    }

}

fun main(args: Array<String>) {
    ParallelStreamApi().runSimple();
    ParallelStreamApi().runOrdered();
    ParallelStreamApi().runWithCustomForkJoinPool();

    ParallelStreamApi().reducing();
    ParallelStreamApi().reducingWithStringBuilder();
    ParallelStreamApi().reducing2();
    ParallelStreamApi().reducing();
    ParallelStreamApi().reducing2();
    ParallelStreamApi().reducing();
    ParallelStreamApi().reducing2();


    ParallelStreamApi().findAny();
    ParallelStreamApi().nonparallel();
    ParallelStreamApi().ordered();
    ParallelStreamApi().unordered();
    ParallelStreamApi().nonparallel();
    ParallelStreamApi().ordered();
    ParallelStreamApi().unordered();
    ParallelStreamApi().nonparallel();
    ParallelStreamApi().ordered();
    ParallelStreamApi().unordered();
    ParallelStreamApi().nonparallel();
}