package y2024

import println
import readInput

fun main() {
    part(readInput("Day11", 2024)).println()
}


private fun part(input: List<String>): Long {
    var input = input.first().split(" ").map { it.toLong() }

    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    return input.sumOf { blink(it, 75, cache) }

}

private fun blink(value: Long, numberOfIterations: Int, cache: MutableMap<Pair<Long, Int>, Long>): Long =
    cache.getOrPut(Pair(value, numberOfIterations)) {
        when {
            numberOfIterations == 0 -> 1
            value == 0L -> blink(1, numberOfIterations - 1, cache)
            value.toString().length % 2 == 0 -> getTwoNumbers(value).sumOf { blink(it, numberOfIterations - 1, cache) }
            else -> blink(value * 2024, numberOfIterations - 1, cache)
        }
    }

private fun getTwoNumbers(value: Long): List<Long> {
    val valueAsString = value.toString()
    val middle = valueAsString.length / 2
    return listOf(valueAsString.substring(0, middle).toLong(), valueAsString.substring(middle).toLong())
}








