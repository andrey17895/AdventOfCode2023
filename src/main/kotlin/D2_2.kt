import java.util.stream.Collectors

object D2_2 {
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
        return games.map {game ->
            val maxRed = game.rounds.maxOf { it.red }
            val maxGreen = game.rounds.maxOf { it.green }
            val maxBlue = game.rounds.maxOf { it.blue }
            maxRed * maxGreen * maxBlue
        }.sum()
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