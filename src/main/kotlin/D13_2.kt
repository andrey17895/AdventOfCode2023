import java.util.Optional
import kotlin.jvm.optionals.getOrElse

object D13_2 {

    private const val DAY = 13

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 400
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
        val expected2 = 32728
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        val maps = input.split("\n\n")
        return maps.sumOf { map ->
            solveMap(map.split("\n").map { it.toList() })
        }
    }

    private fun solveMap(map: List<List<Char>>): Int {
        return findHorizontal(map, 100).getOrElse {
            findVertical(map, 1).getOrElse {
                error("No symmetry found in\n" + map.joinToString(separator = "\n") { it.toString() })
            }
        }
    }

    private fun findVertical(map: List<List<Char>>, multiplier: Int): Optional<Int> =
        findHorizontal(map.transpose(), multiplier)

    private fun findHorizontal(map: List<List<Char>>, multiplier: Int): Optional<Int> {
        for (i in 0..<map.size - 1)
            if (isMirror(map, i))
                return Optional.of((i + 1) * multiplier)
        return Optional.empty()
    }

    private fun isMirror(
        map: List<List<Char>>,
        i: Int
    ): Boolean {
        val range = if (i + 1 < map.size - i) i else map.size - i - 2
        val difference = (0..range).sumOf { countDifference(map[i - it], map[i + 1 + it]) }
        return difference == 1
    }

    private fun countDifference(chars1: List<Char>, chars2: List<Char>): Int =
        chars1.asSequence().zip(chars2.asSequence()).count { it.second != it.first }

}