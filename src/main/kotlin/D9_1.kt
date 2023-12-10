object D9_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("09/sample.txt")
        val answer = solve(input)
        println(answer)
        val expected = 114
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("09/input.txt"))
        println("===================")
        println(result)
        val expected2 = 1939607039
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        return input.split("\n")
            .map {
                it.split(" ").map { it.toInt() }
            }.sumOf { predict(it) }
    }

    private fun predict(numbers: List<Int>): Int {
        val list = mutableListOf<MutableList<Int>>()
        list.add(numbers.toMutableList())
        while (list.last().any { it != 0 }) {
            list.add(
                list.last().windowed(2).map { it[1] - it[0] }.toMutableList()
            )
        }
        list.last()
        list.reversed().windowed(2).forEach {
            it[1].add(it[0].last() + it[1].last())
        }
        return list.first().last()
    }
}



