object D3_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve(readInput("03/sample.txt")))
        println("4361")//4361
        println("===================")
        println("Not:  524810")
        println(solve(readInput("03/input.txt")))
    }

    fun solve(input: String): Any {
        val lines = input.split("\n").toMutableList()

        val parts = arrayListOf<String>()

        val n = lines.size
        for (i in 0..<n) {
            println(lines[i])
            val m = lines[i].length
            var firstIndex = -1
            if (lines[i][0].isDigit()) {
                firstIndex = 0
            }
            for (j in 1..<m) {
                if (lines[i][j].isDigit() && !lines[i][j - 1].isDigit()) {
                    firstIndex = j
                }
                if ((lines[i][j].isDigit() && j == m - 1) || (!lines[i][j].isDigit() && lines[i][j - 1].isDigit())) {
                    val ind = if (j == m - 1 && lines[i][j].isDigit()) { m } else { j }
                    if (firstIndex != -1 && checkPart(lines, i, firstIndex, ind, n, m)) {
                        parts.add(lines[i].substring(firstIndex, ind))
                        print(lines[i].substring(firstIndex, ind))
                        print(" ")
                        firstIndex = -1
                    }
                }
            }
            println("")
        }

        return parts.sumOf { it.toInt() }
    }

    private fun checkPart(lines: List<String>, lineNum: Int, firstIndex: Int, lastIndex: Int, n: Int, m: Int): Boolean {
        val dirs = listOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, -1),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
        )
        return (firstIndex..<lastIndex).any {
            dirs.any { dir ->

                val x = lineNum + dir.first
                val y = it + dir.second
                if (x in 0..<n && y in 0..<m) {
                    val char = lines[x][y]
                    char != '.' && !char.isDigit()
                } else {
                    false
                }
            }
        }
    }


}