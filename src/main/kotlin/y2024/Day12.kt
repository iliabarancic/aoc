package y2024

import println
import readInput

fun main() {
    part(readInput("Day12", 2024)).println()
}


private fun part(input: List<String>): Long {
    val map = input.mapIndexed { y, string -> string.mapIndexed { x, value -> Plot(x, y, value) } }

//    map.flatMap { it.map { it.value } }.distinct()..println()


    val sortedCoordinates = map.flatMap { it.filter { it.value == 'I' } }.sortedWith(compareBy({ it.x }, { it.y }))

    var groupKey = 0


    return 0

}

private data class Plot(val x: Int, val y: Int, val value: Char)







