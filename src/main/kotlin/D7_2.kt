object D7_2 {

    @JvmStatic
    fun main(args: Array<String>) {

        val input = readInput("07/sample.txt")
        println(input)
        println()
        val answer = solve(input)
        println(answer)
        val expected = 5905
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("07/input.txt"))
        println("===================")
        println(result)
    }

    private fun solve(input: String): Any {
        return input.split("\n")
            .map {
                val (cards, bid) = it.split(" ")
                Hand(cards, bid.toInt())
            }.onEach { println("${it.strength}, ${it.cardsStrength}") }
            .sortedWith { e1: Hand, e2: Hand ->
                if (e1.strength == e2.strength)
                    e1.cardsStrength.zip(e2.cardsStrength)
                        .find { it.first != it.second }
                        .let { if (it != null) it.first - it.second else 0}
                else e1.strength - e2.strength
            }.onEach { println(it) }
            .mapIndexed { index, hand -> index + 1 to hand.bid }
            .fold(0) {acc, (rank, bid) -> acc + rank * bid }
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
        println(cards)
        println(jokersCount)
        val possibleCombinations = if (jokersCount > 0) replaceJokers(jokersCount - 1, cardValues.toList().map { it.toString() }.map { cardsWithoutJoker + it })
            else listOf(cards)
        println(possibleCombinations)
        println()

        return possibleCombinations.map { combination ->
            combination.groupBy { it }.map { it.value.count() }.sortedDescending()
            .also { println(it) }
            .let {
                if (it[0] == 5) 7//five of a kind
                else if (it[0] == 4) 6//four of a kind
                else if (it[0] == 3 && it[1] == 2) 5//full house
                else if (it[0] == 3) 4// three of a kind
                else if (it[0] == 2 && it[1] == 2) 3//two pairs
                else if (it[0] == 2) 2 //one pair
                else if (it[0] == 1) 1//high card
                else error("Unrecognised hand: $cards $it")
            }
        }.max()
    }
    private const val cardValues = "AKQT98765432"

    data class Hand(val cards:String, val bid: Int) {
        val strength = strengthWithJoker(cards)



        val cardsStrength = cards.map{
            cardToStrength[it] ?: error("No strength for ${cards.first()}")
        }
    }

    val cardToStrength = mapOf(
        'A' to 14,
        'K' to 13,
        'Q' to 12,
        'T' to 10,
        '9' to 9,
        '8' to 8,
        '7' to 7,
        '6' to 6,
        '5' to 5,
        '4' to 4,
        '3' to 3,
        '2' to 2,
        'J' to 1,
    )
}



