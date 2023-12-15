object D15_1 {

    private const val DAY = 15

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 1320
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
        val expected2 = 508552
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        check(!input.contains("\n")) {"Input contain line separators"}
        return input.splitToSequence(",")
            .sumOf { hash(it) }
    }

    private fun hash(s: String): Int =
        s.fold(0) {acc, c -> ((acc + c.code) * 17) % 256 }
}