import kotlin.time.measureTime

object D18_1 {

    private const val DAY = 18

    private fun checkSolution(res: String, expected: Int?) {
        val input: String = readInput(res)
//        println(input)
        val measureTime = measureTime {
            val answer = solve(input)
            println(answer)
            if (expected != null)
                check(answer == expected) { "Expected: $expected, Actual: $answer" }
        }
        println("Time taken: $measureTime")
        println("===================")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution("$DAY/sample.txt", 62)
        checkSolution("$DAY/input.txt", 52055)
    }

    private fun solve(input: String): Any {
        val startPosition = Pos(0, 0)
        val visited = mutableSetOf(startPosition)
        var currentPosition = startPosition
        input.split("\n").forEach {
            val (ch, count) = it.split(" ")
            val dir = when (ch) {
                "U" -> Dir.UP
                "D" -> Dir.DOWN
                "L" -> Dir.LEFT
                "R" -> Dir.RIGHT
                else -> error("Unknown direction: $ch")
            }
            repeat(count.toInt()) {
                currentPosition += dir.delta
                visited.add(currentPosition)
            }
        }
        printVisited(visited)

        return countFilledTiles(visited)
    }

    private fun printVisited(visited: Set<Pos>) {

        val maxHor = visited.maxOf { it.col }
        val maxVer = visited.maxOf { it.row }
        val minHor = visited.minOf { it.col }
        val minVer = visited.minOf { it.row }

        var count = 0

        for (row in minVer..maxVer) {
            var inside = false
            for (col in minHor..maxHor) {
                val currentPos = Pos(row, col)
                if (currentPos in visited) {
                    print('#')
                    if (
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.DOWN.delta in visited ||
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.LEFT.delta in visited ||
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.RIGHT.delta in visited
                    )
                    inside = !inside
                } else if (inside) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    private fun countFilledTiles(visited: Set<Pos>): Int {

        val maxHor = visited.maxOf { it.col }
        val maxVer = visited.maxOf { it.row }
        val minHor = visited.minOf { it.col }
        val minVer = visited.minOf { it.row }

        var count = 0

        for (row in minVer..maxVer) {
            var inside = false
            for (col in minHor..maxHor) {
                val currentPos = Pos(row, col)
                if (currentPos in visited) {
                    count += 1
                    if (
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.DOWN.delta in visited ||
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.LEFT.delta in visited ||
                        currentPos + Dir.UP.delta in visited && currentPos + Dir.RIGHT.delta in visited
                    )
                    inside = !inside
                } else if (inside) {
                    count += 1
                }
            }
        }
        return count
    }

}
