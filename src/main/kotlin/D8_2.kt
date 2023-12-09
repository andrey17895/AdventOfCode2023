import kotlin.time.measureTime

object D8_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("08/sample2.txt")
        val answer = solve(input)
        println(answer)
        val expected = 6L
        val time = measureTime {
            check(answer == expected) { "Expected: $expected, Actual: $answer" }
            println("===================")
            val result = solve(readInput("08/input.txt"))
            println("===================")
            println(result)
        }
        println(time)
//        val expected2 = 253933213
//        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        val splitted = input.split("\n")
        val path = splitted.first()
        val map = splitted.drop(2).associate {
            val (current, left, right) = """(.+) = \((.+), (.+)\)""".toRegex().find(it)!!.destructured
            current to Pair(left, right)
        }

        val ghostsStart = map.keys.filter { it.endsWith("A") }
        val ghosts = ghostsStart.toMutableList()

        var count = 0L

        while ( ghosts.any { !it.endsWith("Z") }) {
            for (i in ghosts.indices) {
                val directions = map[ghosts[i]] ?: error("no node ${ghosts[i]} in the map")
                val direction = path[(count % path.length).toInt()]
                ghosts[i] = when (direction) {
                    'L' -> directions.first
                    'R' -> directions.second
                    else -> error("unknown input: $direction")
                }
            }
            count += 1
            if (count % 1_000_000 == 0L) {
                println("%,d".format(count / 1_000_000))
            }
            if (count == Long.MAX_VALUE) {
                error("!!!Overflow!!!")
            }
        }
        return count
    }
}



