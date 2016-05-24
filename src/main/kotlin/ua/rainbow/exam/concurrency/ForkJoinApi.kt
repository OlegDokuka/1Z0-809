package ua.rainbow.exam.concurrency

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction

/**
 * Created by Shadowgun on 23.05.16.
 */


class ForkJoinApi {
    fun test() {
        ForkJoinPool().submit(ZooTask(DoubleArray(25, { it.toDouble() }))).get();
    }
}

class ZooTask(private val something: DoubleArray) : RecursiveAction() {
    override fun compute() {
        when {
            something.size > 3 -> invokeAll(
                    ZooTask(something.sliceArray(0..(something.size / 2))),
                    ZooTask(something.sliceArray((something.size / 2 + 1)..(something.size - 1)))
            );
            else -> something.forEach { println("Action hash: ${hashCode()}; Val: $it;") }
        }
    }
}

fun main(args: Array<String>) {
    ForkJoinApi().test();
}