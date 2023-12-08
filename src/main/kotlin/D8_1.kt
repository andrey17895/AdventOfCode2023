object D8_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("08/sample.txt")
        val answer = solve(input)
        println(answer)
        val expected = 6
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("08/input.txt"))
        println("===================")
        println(result)
        val expected2 = 17287
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        val splitted = input.split("\n")
        val path = splitted.first()
        val map = splitted.drop(2).associate {
            val (current, left, right) = """(.+) = \((.+), (.+)\)""".toRegex().find(it)!!.destructured
            current to Pair(left, right)
        }

        var count = 0
        var currentPlace = "AAA"

        while (currentPlace != "ZZZ") {
            val directions = map[currentPlace] ?: error("no node ${currentPlace} in the map")
            val direction = path[count % path.length]
            currentPlace = when (direction) {
                'L' -> directions.first
                'R' -> directions.second
                else -> error("unknown input: $direction")
            }
            count += 1
        }
        return count


    }
}



