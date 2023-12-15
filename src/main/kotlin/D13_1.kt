import java.util.Optional
import kotlin.jvm.optionals.getOrElse

object D13_1 {

    private const val DAY = 13

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 405
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
        val expected2 = 27742
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

    private fun findVertical(map: List<List<Char>>, multiplier: Int): Optional<Int>
        = findHorizontal(map.transpose(), multiplier)

    private fun findHorizontal(map: List<List<Char>>, multiplier: Int): Optional<Int> {
        for (i in 0..<map.size - 1) {
            if (map[i] == map[i + 1]) {
                val range = if (i + 1 < map.size - i) i else map.size - i - 2
                if ((0..range)
                        .all { map[i - it] == map[i + 1 + it] }
                )
                    return Optional.of((i + 1) * multiplier)
            }
        }
        return Optional.empty()
    }

}