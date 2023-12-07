import java.util.stream.Collectors

object D2_1 {

    data class Game(
        val index: Int,
        val rounds: List<Round>
    )

    data class Round(
        val red: Int,
        val green: Int,
        val blue: Int
    )

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(readInput("02/sample1.txt")))
        println("===================")
        println(solve(readInput("02/input.txt")))
    }

    fun solve(input: String): Any {
        val games = input.split("\n")
            .map {
                val (i, gamesStr) = Regex("Game (.+?): (.+?)$").find(it)!!.destructured
                val games = gamesStr.split("; ")
                    .map { strToRound(it) }
                Game(i.toInt(), games)
            }
        return games.filter {
            it.rounds.all { round ->
                round.red <= 12 && round.green <= 13 && round.blue <= 14
            }
        }.sumOf { it.index }
    }

    private fun strToRound(s: String): Round {
        val map = s.split(", ").stream()
            .collect(Collectors.toMap({ it.substringAfter(' ') }, { it.substringBefore(' ').toInt() }))
        return Round(
            red = map.getOrDefault("red", 0),
            green = map.getOrDefault("green", 0),
            blue = map.getOrDefault("blue", 0),

            )
    }
}