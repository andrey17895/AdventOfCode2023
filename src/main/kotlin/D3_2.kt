import java.util.Optional
import java.util.stream.Collectors

object D3_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        println(readInput("03/sample.txt"))
        println(solve(readInput("03/sample.txt")))
        println("467835")
        println("===================")
//        println("Not:  524810")
        println(solve(readInput("03/input.txt")))
    }

    fun solve(input: String): Any {
        val lines = input.split("\n").toMutableList()

        val starIndexes = lines.flatMapIndexed() { i, line ->
            line.mapIndexed { index, c -> Pair(index, c) }
                .filter { pair -> pair.second == '*' }
                .map { Pair(i, it.first) }
        }

        return starIndexes.map { gearRatio(lines, it) }.filter { it.isPresent }.sumOf { it.get() }

        }

    fun gearRatio(lines: List<String>, place: Pair<Int, Int>): Optional<Long> {
        val dirs = listOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, -1),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
        )

        val gearSet = dirs.filter {
            val y = place.first + it.first
            val x = place.second + it.second
            (0 <= y && y < lines.size)
                    && (0 <= x && x < lines[y].length)
                    && (lines[y][x].isDigit())
        }
            .map { extractDigit(lines[place.first + it.first], place.second + it.second) }
            .toSet()

        return if (gearSet.size == 2) {
            println(gearSet)
            Optional.of(gearSet.map { it.toLong() }
                .reduce {acc, i -> acc * i }
            )
        } else {
            Optional.empty<Long>()
        }
    }

    private fun extractDigit(line: String, index: Int): Int {
        var index_last = line.substring(index).indexOfFirst { !it.isDigit() }
        index_last = if (index_last != -1) {
            index + index_last
        } else {
            line.length
        }
        var index_first = line.substring(0, index).reversed().indexOfFirst { !it.isDigit() }
        index_first = if (index_first != -1) {
            index - index_first
        } else {
            0
        }
        return line.substring(index_first, index_last).toInt()
    }

}

