object D14_2 {

    private const val DAY = 14

    private fun checkSolution(res: String, n:Int, expected: Int?) {
        val input: String = readInput(res)
//        println(input)
        val answer = solve(input, n)
        println(answer)
        check(answer == expected) { "Expected: $expected, Actual: $answer" }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution("$DAY/sample.txt", 1_000_000_000,  64)
        println("===================")
        checkSolution("$DAY/input.txt", 1_000_000_000, 108813)
        println("===================")
    }

    // UP -> transpose
    // DOWN -> transpose reverse
    // Left -> nothing
    // Right -> reverse

    // 1 2   1 3   3 1
    // 3 4   2 4   4 2


    private const val START_CHAR = 'S'
    private fun solve(input: String, n: Int): Any {
        var map = input.split("\n").map { it.toMutableList() }.toMutableList()

        map = map.transpose().map { roll(it) }.toMutableList()

        return evaluateMap(map.transpose())
    }



    private fun roll(list: List<Char>): MutableList<Char> {
        val row = list.toMutableList()
        var end = -1
        for (j in row.indices) {
            val ch = row[j]
            when (ch) {
                '.' -> if (end == -1) end = j
                'O' -> if (end != -1) {
                    val temp = row[end]
                    row[end] = row[j]
                    row[j] = temp
                    end += 1
                }

                '#' -> end = -1
            }
        }
        return row
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