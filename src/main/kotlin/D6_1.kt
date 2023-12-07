object D6_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("06/sample.txt")
        println(input)
        println()
        val answer = solve(input)
        println(answer)
        val expected = 288
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("06/input.txt"))
        println("===================")
        println(result)
    }

    private fun solve(input: String): Any {
        val split = input.split("\n").map { it.replace(" +".toRegex(), " ") }

        val times = split[0].split(" ").drop(1).map { it.toInt() }
        val distances = split[1].split(" ").drop(1).map { it.toInt() }
        val races = times.indices.map { Race(times[it], distances[it]) }

        return races.map { race ->
            (1..<race.time).map {
                (race.time - it) * it
            }.filter { it > race.distance }
                .count()
        }.fold(1) {acc, i ->  acc * i}
    }

    data class Race(val time: Int, val distance: Int)
}



