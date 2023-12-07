import java.util.Optional
import java.util.stream.Collectors
import kotlin.math.pow

object D4_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("04/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        check(answer == 13) { "Expected: 13, Actual: $answer" }
        println("===================")
        println(solve(readInput("04/input.txt")))
    }

    private fun solve(input: String): Any {
        return input.split("\n")
            .map { it.substring(it.indexOf(':') + 2).replace("  ", " ") }
            .map {
//                println("[$it]")
                val splitted = it.split(" | ")
                val expected = splitted[0].trim().split(" ").map { it.toInt() }.toSet()
                val actual = splitted[1].trim().split(" ").map { it.toInt() }.toSet()
                expected.intersect(actual).count()
            }.filter { it != 0 }
            .sumOf { 2.0.pow((it - 1)) }
            .toInt()
    }
}



