import kotlin.math.pow
import kotlin.system.measureTimeMillis

object D4_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("04/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 30L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("04/input.txt"))
        println("===================")
        println("Not 10423697")
        println(result)
    }

    private fun solve(input: String): Any {
        cardsMap = input.split("\n")
            .map {
                Card(
                    "(\\d+)".toRegex().find(it)!!.groupValues[0].toInt(),
                    it.substring(it.indexOf(':') + 2).replace("  ", " ")
                )
            }
            .associate { it.id to countIntersected(it.numbers) }
//        println(cardsMap)
        resolvedCards.clear()
        val cards = (1..cardsMap.size).reversed().toMutableList()
        return cards.sumOf { resolveCard(it) }
    }

    private fun countIntersected(it: String): Int {
        val splitted = it.split(" | ")
        val expected = splitted[0].trim().split(" ").map { it.toInt() }.toSet()
        val actual = splitted[1].trim().split(" ").map { it.toInt() }.toSet()
        return expected.intersect(actual).count()
    }

    private var cardsMap: Map<Int, Int> = mapOf()
    private val resolvedCards = mutableMapOf<Int, Long>()

    private fun resolveCard(id: Int): Long {
        if (resolvedCards.containsKey(id)) {
            return resolvedCards[id]!!
        }
//        println(resolvedCards)
//        print("$id ")

        val newCards = (id + 1..id + cardsMap[id]!!).toList()
//        print("$newCards ")
        val result = 1L + newCards.sumOf { resolveCard(it) }
//        println(result)
//        println()
        resolvedCards[id] = result
        return result

    }

    data class Card(
        val id: Int,
        val numbers: String
    )

}



