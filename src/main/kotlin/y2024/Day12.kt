package y2024

import println
import readInput

fun main() {
    part(readInput("Day12", 2024)).println()
}


private fun part(input: List<String>): Int {
    val map = input.mapIndexed { y, string -> string.mapIndexed { x, value -> Plot(x, y, value) } }

    // Part I
//    return partI(map)

    return partII(map)
}

private fun partI(map: List<List<Plot>>) = getRegions(map).sumOf { region -> region.size * region.sumOf { it.fences } }

private fun partII(map: List<List<Plot>>) = getRegions(map).sumOf { it.calculateLength() * it.size }

private fun getRegions(map: List<List<Plot>>): List<List<Plot>> = map.flatMap { it }.mapNotNull {
    if (!it.visited) calculateRegion(ArrayDeque<Plot>().apply { add(it) }, map) else null
}

private fun calculateRegion(queue: ArrayDeque<Plot>, map: List<List<Plot>>): List<Plot> {

    if (queue.isEmpty()) return listOf()

    val currentPlot = queue.removeFirst()
    if (currentPlot.visited) return calculateRegion(queue, map)

    val x = currentPlot.x
    val y = currentPlot.y
    val value = currentPlot.value
    currentPlot.visited = true

    val aroundCoordinates = listOf(y to x + 1, y - 1 to x, y to x - 1, y + 1 to x)
    aroundCoordinates.forEach {
        val neighbour = map.getOrNull(it.first)?.getOrNull(it.second)
        if (neighbour != null && neighbour.value == value && neighbour.visited == false) {
            queue.add(neighbour)
        }

        if (neighbour == null || neighbour.value != value) {
            when (it) {
                y to x + 1 -> currentPlot.right = true
                y - 1 to x -> currentPlot.top = true
                y to x - 1 -> currentPlot.left = true
                y + 1 to x -> currentPlot.bottom = true
            }
        }
    }

    return mutableListOf(currentPlot) + calculateRegion(queue, map)

}

private data class Plot(
    val x: Int,
    val y: Int,
    val value: Char,

    var left: Boolean = false,
    var top: Boolean = false,
    var right: Boolean = false,
    var bottom: Boolean = false,
    var visited: Boolean = false
) {
    val fences: Int get() = listOf(left, top, right, bottom).count { it == true }

    val twoEdges get() = fences == 3
    val topLeft get() = left && top && !right && !bottom
    val topRight get() = right && top && !left && !bottom
    val bottomLeft get() = left && bottom && !right && !top
    val bottomRight get() = right && bottom && !left && !top

    val fourEdges get() = fences == 4
}

private fun List<Plot>.calculateLength() = sumOf {
    when {
        it.twoEdges -> 2
        it.topLeft || it.topRight || it.bottomLeft || it.bottomRight -> 1
        it.fourEdges -> 4
        else -> 0
    } + it.outerEdge(this)
}

private fun Plot.outerEdge(region: List<Plot>): Int {
    var result = 0

    if (bottom && getPlot(region, 1, 1)?.left == true) result += 1
    if (right && getPlot(region, 1, -1)?.bottom == true) result += 1
    if (top && getPlot(region, -1, -1)?.right == true) result += 1
    if (left && getPlot(region, -1, 1)?.top == true) result += 1

    // remove double fences on edges
    if (top && left && getPlot(region, -1, -1)?.let { it.bottom && it.right } == true) result -= 2
    if (top && right && getPlot(region, 1, -1)?.let { it.bottom && it.left } == true) result -= 2


    return result
}

private fun Plot.getPlot(region: List<Plot>, xOffset: Int, yOffset: Int): Plot? =
    region.find { it.y == y + yOffset && it.x == x + xOffset }




