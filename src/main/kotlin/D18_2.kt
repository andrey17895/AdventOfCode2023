import kotlin.time.measureTime

object D18_2 {

    private const val DAY = 18

    private fun checkSolution(res: String, expected: Any?) {
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
        checkSolution("$DAY/sample.txt", 952_408_144_115L)
//        checkSolution("$DAY/input.txt", null)
    }

    private fun solve(input: String): Any {
        val startPosition = Pos(0, 0)
        val visited = mutableSetOf(startPosition)
        var currentPosition = startPosition

        var sum = 0L

        input.split("\n").forEach {
            val (_, _, hex) = it.split(" ")
            val dir = when (val ch = hex[hex.length - 2]) {
                '3' -> Dir.UP
                '1' -> Dir.DOWN
                '2' -> Dir.LEFT
                '0' -> Dir.RIGHT
                else -> error("Unknown direction: $ch")
            }
            val count = hex.substring(2, 7).toInt(radix = 16)
            sum += count
            repeat(count) {
                currentPosition += dir.delta
                visited.add(currentPosition)
            }
        }
        println(String.format("%,d", sum))
//        printVisited(visited)
        println(visited.maxOf { it.col })
        println(visited.maxOf { it.row })
        println(visited.minOf { it.col })
        println(visited.minOf { it.row })


//        return countFilledTiles(visited)
        return 1
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
