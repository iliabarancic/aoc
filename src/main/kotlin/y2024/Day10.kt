package y2024

import println
import readInput

fun main() {
    part(readInput("Day10", 2024)).println()
}


private fun part(input: List<String>): Long {

    val map = input.mapIndexed { y, string -> string.mapIndexed { x, value -> Position(x, y, value.digitToInt()) } }

    val startPositions = map.flatMap { positions -> positions.filter { it.value == 0 } }

   return startPositions.map {
       collectNextNumber(mutableListOf(it), map)
   }.sumOf {
       it.count()
   }.toLong()
}

private fun collectNextNumber(values: MutableList<Position>, map: List<List<Position>>): List<List<Position>> {
    val (x, y, value) = values.last()

    if (value == 9) return listOf(values)

    val aroundCoordinates = listOf(y to x + 1, y - 1 to x, y to x - 1, y + 1 to x)
    return aroundCoordinates.flatMap {
        val orNull = map.getOrNull(it.first)?.getOrNull(it.second)
        if (orNull != null && orNull.value == value + 1) {
            collectNextNumber(values.toMutableList().apply { add(orNull) }, map)
        } else {
            emptyList<List<Position>>()
        }
    }
}

private data class Position(val x: Int, val y: Int, val value: Int)





