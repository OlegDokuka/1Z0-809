package ua.rainbow.exam.collections;

import java.util.*

private fun testLinkedList() {
    val queue: Queue<Int> = LinkedList<Int>()

    queue.offer(1)
    queue.add(2)
    queue.add(3)

    println(queue == listOf(1, 2, 3))
    println(queue.element() == 1 && queue.size == 3)

    queue.remove()

    println(queue == listOf(2, 3))
    println(queue.peek() == 2)

    queue.poll()

    println(queue == listOf(3))
    println(queue.poll() == 3)
}

fun main(args: Array<String>) {
    testLinkedList()
}