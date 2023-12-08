fun main() {
    part1(readInput("Day01")).println()
    part2(readInput("Day01_2")).println()
}

fun part1(input: List<String>) = input.sumOf { with(it.toCharArray().filter(Char::isDigit)) { "${first()}${last()}" }.toInt() }

fun part2(input: List<String>): Int {
    return input.sumOf { getFirstAndLastNumber(it) }
}

fun getFirstAndLastNumber(line: String): Int {
    val allNumbers = numbers.keys + numbers.values.map { it.toString() }
    val first = line.findAnyOf(allNumbers)?.second.toNumber()
    val second = line.findLastAnyOf(allNumbers)?.second.toNumber()
    return "$first$second".toInt()
}

private fun String?.toNumber() = this?.let { if (it.length > 1) numbers[it] else it }

val numbers = mapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)