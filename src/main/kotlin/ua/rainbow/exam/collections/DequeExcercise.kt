package ua.rainbow.exam.collections;

import java.util.*

private fun testLinkedList() {
    val queue: Deque<Int> = LinkedList<Int>()

    queue.offer(1)
    queue.add(2)
    queue.push(3)

    println("queue == listOf(3, 1, 2): ${queue == listOf(3, 1, 2)}")
    println(queue.first == 3 && queue.size == 3)

    queue.pollLast()

    println(queue == listOf(3, 1))
    println(queue.peekLast() == 1)

    queue.poll()

    println(queue == listOf(1))
    println(queue.pop() == 1)
}

fun main(args: Array<String>) {
    testLinkedList()
}