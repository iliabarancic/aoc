object Day02 {

    fun part1(input: List<String>) = input.mapNotNull { isPossible(it) }.sum()
    fun part2(input: List<String>) = input.sumOf { highestNumber2(it) }


    private fun isPossible(game: String): Int? {
        val gameId = """Game (\d+): (.*)""".toRegex()
        val (gameNumber, record) = gameId.matchEntire(game)!!.destructured
        val valid = getSequenceOfNumberWithColor(record).none {
            val (number, color) = it.destructured
            when (color) {
                "red" -> number.toInt() > 12
                "green" -> number.toInt() > 13
                "blue" -> number.toInt() > 14
                else -> true
            }
        }
        return gameNumber.toInt().takeIf { valid }
    }

    private fun getSequenceOfNumberWithColor(record: String) = """(\d+) (blue|red|green)""".toRegex().findAll(record)


    private fun highestNumber2(gameLine: String): Int {
        return getSequenceOfNumberWithColor(gameLine)
            .groupBy({ it.destructured.component2() }, { it.destructured.component1().toInt() })
            .map { it.value.max() }
            .reduce(Int::times)
    }
}


fun main() {
    Day02.part1(readInput("Day02")).println()
    Day02.part2(readInput("Day02_2")).println()
}