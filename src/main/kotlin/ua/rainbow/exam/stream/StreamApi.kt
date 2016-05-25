package ua.rainbow.exam.stream

import java.util.stream.IntStream

class StreamApi {
    fun unordered(){

        IntStream.range(0, 10000)
                .unordered()
                .skip(10)
                .limit(20)
                .forEach { print("$it ") }
    }
}


fun main(args: Array<String>) {
    StreamApi().unordered();
}