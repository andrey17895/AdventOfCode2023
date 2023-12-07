object D6_1_optimized {

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
            val first = (1..<race.time).first { (race.time-it)*it > race.distance } - 1
            val last = (1..<race.time).reversed().first { (race.time-it)*it > race.distance }
            last - first
        }.fold(1) {acc, i -> acc * i }
    }

    data class Race(val time: Int, val distance: Int)
}



