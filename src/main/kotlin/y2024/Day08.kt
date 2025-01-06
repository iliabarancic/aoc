package y2024

import println
import readInput

fun main() {
    part(readInput("Day08")).println()
}


private fun part(input: List<String>): Int {

    val field =
        input.mapIndexed { y, string ->
            string.mapIndexed { x, ch -> if (ch == '.') EmptyCell() else Antenna(ch.toString(), Coordinate(x, y)) }.toMutableList()
        }

    val maxY = field.size - 1
    val maxX = field[0].size - 1

    val allAntennas = field.flatMap { cells ->
        cells
            .filter { it is Antenna }
            .map { it as Antenna }
    }.groupBy { it.symbol }

    return allAntennas.values.flatMap { antennas ->
        antennas.flatMap { antennaA ->
            antennas.filterNot { it == antennaA }
                .flatMap { antennaB ->
                    calcAntinode(antennaA, antennaB)
                }
        }
    }.distinct().count { coordinate -> with(coordinate) { x >= 0 && x <= maxX && y >= 0 && y <= maxY } }


}

private fun calcAntinode(first: Antenna, second: Antenna): List<Coordinate> {
    fun Int.distance(b: Int): List<Int> {
        return listOf(this - b + this, b - this + b)
    }

    val xs = first.coord.x.distance(second.coord.x)
    val ys = first.coord.y.distance(second.coord.y)

    return xs.zip(ys).map { (x, y) -> Coordinate(x, y) }
}

private fun calcAntinodes(first: Antenna, second: Antenna, maxX: Int, maxY: Int): List<Coordinate> {
    val x = first.coord.x - second.coord.x
    val y = first.coord.y - second.coord.y

    val xGenerator = generateSequence(first.coord.x) { if (it + x >= 0 && it + x <= maxX) it + x else null }
    val yGenerator = generateSequence(first.coord.y) { if (it + y >= 0 && it + y <= maxY) it + y else null }

    return xGenerator.zip(yGenerator) { x, y -> Coordinate(x, y) }.toList()
}


private data class EmptyCell(override var isAntinode: Boolean = false) : Cell

private data class Antenna(val symbol: String, val coord: Coordinate) : Cell {
    override var isAntinode = false
}

private data class Coordinate(val x: Int, val y: Int)

interface Cell {
    var isAntinode: Boolean
}


