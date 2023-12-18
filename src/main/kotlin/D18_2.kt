import java.util.PriorityQueue
import kotlin.time.measureTime

object D18_2 {

    private const val DAY = 18

    private fun checkSolution(res: String, expected: Any?) {
        val input: String = readInput(res)
//        println(input)
        val measureTime = measureTime {
            val answer = solve(input)
            println("Result: $answer")
            if (expected != null)
                check(answer == expected) { "Expected: $expected, Actual: $answer" }
        }
        println("Time taken: $measureTime")
        println("===================")
    }

    @JvmStatic
    fun main(args: Array<String>) {
//        checkSolution("$DAY/sample.txt", null)
//        checkSolution("$DAY/sample.txt", 952_408_144_115L)
        checkSolution("$DAY/input.txt", null)
    }

    private fun solve(input: String): Any {
        val startPosition = Pos(0, 0)
        val visited = mutableListOf<Pos>()
        var currentPosition = startPosition


        val hexes = input.split("\n").map {
            val (_, _, hex) = it.split(" ")
            hex
        }

        val consecutive = hexes.zipWithNext().count { (a, b) -> a[a.length - 2] == b[b.length - 2] }
        println("Same direction in consecutive: $consecutive")

        val instructions = hexes.map { hex ->
            val dir = when (val ch = hex[hex.length - 2]) {
                '3' -> Dir.UP
                '1' -> Dir.DOWN
                '2' -> Dir.LEFT
                '0' -> Dir.RIGHT
                else -> error("Unknown direction: $ch")
            }
            val count = hex.substring(2, 7).toInt(radix = 16)
            dir to count
        }

        instructions.forEach { (dir, count) -> repeat(count) {
            currentPosition += dir.delta
            visited.add(currentPosition)
        } }

        val sumOutside = instructions.sumOf { (_, count) -> count.toLong() }

        println(String.format("%,d", sumOutside))
        println(visited.maxOf { it.col })
        val maxV = visited.maxOf { it.row }
        println(maxV)
        println(visited.minOf { it.col })
        println(visited.minOf { it.row })

        val transactions = List(maxV + 1) { PriorityQueue<Int>() }

        visited.asSequence().windowed(3).forEach {(e1,e2,e3) ->
            if (
                e1.col == e2.col && e2.col == e3.col ||
                e2 + Dir.UP.delta == e1 && e2 + Dir.RIGHT.delta == e3 ||
                e2 + Dir.UP.delta == e3 && e2 + Dir.RIGHT.delta == e1 ||
                e2 + Dir.UP.delta == e1 && e2 + Dir.LEFT.delta == e3 ||
                e2 + Dir.UP.delta == e3 && e2 + Dir.LEFT.delta == e1
            ) {
                transactions[e2.row].add(e2.col)
            }
        }

        val sumInside = transactions.sumOf { row ->
            var sum = 0L
            val pairs = row.zipWithNext()
            for (i in pairs.indices step 2) {
                sum += pairs[i].second - pairs[i].first - 1
            }
            sum
        }

//        return countFilledTiles(visited)
        return sumOutside + sumInside
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
