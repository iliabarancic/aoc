package y2024

import println
import readInput

fun main() {
    part(readInput("Day09", 2024)).println()
}


private fun part(input: List<String>): Long {
    val inputLine = input.single()

    val blocks = inputLine.windowed(2, step = 2, partialWindows = true).mapIndexed { i, c ->
        Block(id = i.toLong(), files = c.first().digitToInt(), freeSpace = c.last().digitToInt())
    }.toMutableList()
    val fileBlocks = blocks.flatMap { it.toFileBlocks() }.toMutableList()
    val freeSpaces: List<FreeSpace> = fileBlocks.filter { it is FreeSpace } as List<FreeSpace>

    File(10,mutableListOf(1234,1234,1234)).filesSize.println()

    fileBlocks.reversed().asSequence().filter { it is File }.forEach { file ->
        for (fs in freeSpaces) {
            if (fs.fits(file as File) && file.id >= fs.id) {
                fs.addFile(file)
                break
            }
        }
    }
//    codeLineNumbers.reversed()
//        .take(nullIndexes.size)
//        .filter { it != null }
//        .onEachIndexed { i, v ->
//            codeLineNumbers[nullIndexes[i]] = v
//        }
//
//
    return fileBlocks.flatMap { it.toNumbers() }
        .mapIndexed { i, c ->
            if (c == null) 0 else c * i
        }.sum()

}

private fun Collection<IBlock>.printCode() {
    flatMap { it.toNumbers() }.joinToString("") { if (it == null) "." else "$it" }.println()
}

private data class Block(private val id: Long, private val files: Int, private val freeSpace: Int) {
    private var filesList = MutableList<Long?>(files) { id }
    fun toFileBlocks() = listOf(File(id, filesList), FreeSpace(id, freeSpace))
}

private interface IBlock {
    fun toNumbers(): List<Long?>
}

private data class File(val id: Long, val numbers: MutableList<Long?>) : IBlock {
    override fun toNumbers() = numbers
    val filesSize = numbers.size
    fun empty() = numbers.fill(null)
}

private data class FreeSpace(val id: Long, private var capacity: Int) : IBlock {
    private val files = mutableListOf<Long?>()

    fun fits(file: File) = file.filesSize <= capacity

    fun addFile(file: File) {
        this.files.addAll(file.numbers)
        capacity -= file.filesSize
        file.empty()
    }

    override fun toNumbers() = files + List(capacity) { null }
}




