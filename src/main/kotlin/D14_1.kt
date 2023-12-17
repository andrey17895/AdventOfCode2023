object D14_1 {

    private const val DAY = 14

    private fun checkSolution(res: String, expected: Int?) {
        val input: String = readInput(res)
//        println(input)
        val answer = solve(input)
        println(answer)
        check(answer == expected) { "Expected: $expected, Actual: $answer" }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution("$DAY/sample.txt", 136)
        println("===================")
        checkSolution("$DAY/input.txt", 108813)
        println("===================")
    }

    private const val START_CHAR = 'S'
    private fun solve(input: String): Any {
        val map = input.split("\n").map { it.toMutableList() }.toMutableList()

        val width = map[0].size
        val height = map.size

        for (i in 0..<width) {
            var end = -1
            for (j in 0..<height) {
                val ch = map[j][i]
                when (ch) {
                    '.' -> if (end == -1) end = j
                    'O' -> if (end != -1) {
                        val temp = map[end][i]
                        map[end][i] = map[j][i]
                        map[j][i] = temp
                        end += 1
                    }

                    '#' -> end = -1
                }
            }
        }

        return evaluateMap(map)
    }

    private fun evaluateMap(map: List<List<Char>>): Int {
        val width = map[0].size
        val height = map.size
        var sum = 0
        for (i in 0..<width) {
            for (j in 0..<height) {
                val ch = map[j][i]
                if (ch == 'O') sum += height - j
            }
        }
        return sum
    }
}