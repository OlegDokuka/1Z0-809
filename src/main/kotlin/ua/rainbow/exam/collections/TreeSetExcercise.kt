package ua.rainbow.exam.collections;

import java.util.*

fun testNavigableSet() {
    val set = TreeSet<Int>()

    set.add(1)
    set.add(3)
    set.add(4)
    set.add(5)
    set.add(6)

    println("The input set is $set")

    println("lower then 2 is ${set.lower(2)}")
    println("floor to 2 is ${set.floor(2)}")
    println("ceiling to 2 is ${set.ceiling(2)}")
    println("higher then 2 is ${set.higher(2)}")
    println("floor then 5 is ${set.floor(5)}")
    println("ceiling then 5 is ${set.ceiling(5)}")
}

fun main(args: Array<String>) {
    testNavigableSet()
}