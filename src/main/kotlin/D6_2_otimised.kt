object D6_2_otimised {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("06/sample.txt")
        println(input)
        println()
        val answer = solve(input)
        println(answer)
        val expected = 71503L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("06/input.txt"))
        println("===================")
        println(result)
    }

    private fun solve(input: String): Any {
        val split = input.split("\n").map { it.replace(" +".toRegex(), "") }

        val times = split[0].split(":").drop(1).map { it.toLong() }
        val distances = split[1].split(":").drop(1).map { it.toLong() }
        val races = times.indices.map { Race(times[it], distances[it]) }

        return races.map { race ->
            val first = (1..<race.time).first { (race.time-it)*it > race.distance } - 1
            val last = (1..<race.time).reversed().first { (race.time-it)*it > race.distance }
            last - first
        }.fold(1L) {acc, l -> acc * l }
    }

    data class Race(val time: Long, val distance: Long)
}



