 package ua.rainbow.exam.concurrency

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction
import java.util.concurrent.RecursiveTask

class ForkJoinApi {
    fun test() {
        ForkJoinPool().invoke(ZooAction(DoubleArray(25, Int::toDouble)));
    }

    fun testSum() {
        println("Result sum = ${ForkJoinPool().submit(ZooTask(DoubleArray(25, { it.toDouble() }))).join()}")
    }
}

class ZooAction(private val something: DoubleArray) : RecursiveAction() {
    override fun compute() {
        when {
            something.size > 3 -> invokeAll(
                    ZooAction(something.sliceArray(0..(something.size / 2))),
                    ZooAction(something.sliceArray((something.size / 2 + 1)..(something.size - 1)))
            );
            else -> something.forEach { println("Thread id: ${Thread.currentThread()}; Action hash: ${hashCode()}; Val: $it;") }
        }
    }
}

class ZooTask(private val something: DoubleArray) : RecursiveTask<Double>() {
    override fun compute(): Double {
        return when {
            something.size > 3 ->
                ZooTask(something.sliceArray((something.size / 2 + 1)..(something.size - 1))).fork().run { ZooTask(something.sliceArray(0..(something.size / 2))).compute() + this.join() };
            else -> something.sum();
        }
    }
}

fun main(args: Array<String>) {
    ForkJoinApi().test();
    println("---------------------------------------------------");
    ForkJoinApi().testSum();
}