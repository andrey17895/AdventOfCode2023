object D7_2 {

    @JvmStatic
    fun main(args: Array<String>) {

        val input = readInput("07/sample.txt")
        val answer = solve(input)
        println(answer)
        val expected = 5905
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("07/input.txt"))
        println("===================")
        println(result)
        val expected2 = 253473930
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        return input.split("\n")
            .map {
                val (cards, bid) = it.split(" ")
                Hand(cards, bid.toInt())
            }
            .sortedWith(compareBy({ it.strength }, { it.cardsStrength }))
            .foldIndexed(0) {index, acc, hand -> acc + (index + 1) * hand.bid }
    }

    private fun replaceJokers(count: Int, str: List<String>): List<String> {
        if (count <= 0)
            return str

        val flatten = str.map { old ->
            cardValues.map { new ->
                old + new
            }
        }.flatten()
        return replaceJokers(count - 1, flatten)
    }

    fun strengthWithJoker(cards: String): Int {


        val cardsWithoutJoker = cards.filter { it != 'J' }
        val jokersCount = cards.length - cardsWithoutJoker.length
        val possibleCombinations = if (jokersCount > 0) replaceJokers(
            jokersCount - 1,
            cardValues.toList().map { it.toString() }.map { cardsWithoutJoker + it })
        else listOf(cards)

        return possibleCombinations.map { combination ->
            combination.groupBy { it }.map { it.value.count() }.sortedDescending()
                .let {
                    when {
                        it[0] == 5 -> 7 //five of a kind
                        it[0] == 4 -> 6 //four of a kind
                        it[0] == 3 && it[1] == 2 -> 5//full house
                        it[0] == 3 -> 4// three of a kind
                        it[0] == 2 && it[1] == 2 -> 3//two pairs
                        it[0] == 2 -> 2 //one pair
                        it[0] == 1 -> 1//high card
                        else -> error("Unrecognised hand: $cards $it")
                    }
                }
        }.max()
    }

    private const val cardValues = "AKQT98765432"

    data class Hand(val cards: String, val bid: Int) {
        val strength = strengthWithJoker(cards)
        val cardsStrength = cards.map {
            cardToStrength[it] ?: error("No strength for ${cards.first()}")
        }.toString()
    }

    val cardToStrength = "AKQT98765432J".reversed()
        .zip("abcdefghijklm").toMap()

}



