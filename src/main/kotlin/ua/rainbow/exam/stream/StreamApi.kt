package ua.rainbow.exam.stream

import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

fun unordered() {

    IntStream.range(0, 10000)
            .unordered()
            .skip(10)
            .limit(20)
            .forEach { print("$it ") }
}


fun orderByMax() {
    println()
    println(Stream
            .generate { Random().nextInt(100) }
            .skip(0)
            .limit(10)
            .sorted(Int::compareTo)
            .map(Int::toString)
            .collect(Collectors.joining("|")))
}

fun reduceToStringUsingBuilder() {
    print("Result is: ")
    print(Stream
            .generate(Math::random)
            .parallel()
            .unordered()
            .map(Double::toString)
            .limit(20)
            .reduce(StringBuilder(), StringBuilder::append, StringBuilder::appendln)
    )
}

fun streamConcatenation() {
    print("Result of stream concatenation is: ")
    print(
            Stream.concat(
                    Stream.of("H", "l", "o", "W", "r", "d"),
                    Stream.of("e", "l", " ", "o", "l")
            ).reduce({ a, b -> a + b })
    )
}

fun debugStreamUsingPeek() {
    println()
    println("The debug of stream started")
    println("\n\rThe average is :${Random()
            .doubles(20, 20.0, 100.0)
            .peek(::print)
            .average().orElse(0.0)}")
}


fun main(args: Array<String>) {
    unordered()
    orderByMax()
    reduceToStringUsingBuilder()
    streamConcatenation()
    debugStreamUsingPeek()
}