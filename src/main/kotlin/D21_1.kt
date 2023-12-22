import java.util.PriorityQueue
import java.util.Stack
import kotlin.time.measureTime

object D21_1 {

    private const val DAY = 21

    private fun checkSolution(answer: Any, expected: Any?) {
        val measureTime = measureTime {
            println(answer)
            if (expected != null)
                check(answer.toString() == expected.toString()) { "Expected: $expected, Actual: $answer" }
        }
        println("Time taken: $measureTime")
        println("===================")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution(solve(readInput("$DAY/sample.txt"), 6), 16)
        checkSolution(solve(readInput("$DAY/input.txt"), 64), 3788)
    }

    private fun solve(input: String, stepsCount: Int): Any {
        val map = input.split("\n").map { it.toList() }
        val startRow = map.indexOfFirst { it.contains('S') }
        val startCol = map[startRow].indexOf('S')
        val startPos = Pos(startRow, startCol)
        val weightMap = map.map { row ->
            row.map {
                when (it) {
                    '.' -> Int.MAX_VALUE
                    '#' -> -1
                    'S' -> Int.MAX_VALUE
                    else -> error("Unknown symbol: $it")
                }
            }.toMutableList()
        }.toMutableList()


        val stack = PriorityQueue<Cursor>()
        stack.add(Cursor(0, startPos))

        while (stack.size > 0) {
            val (distance, pos) = stack.poll()
            if (distance < weightMap[pos.row][pos.col]) {
                weightMap[pos.row][pos.col] = distance
                Dir.entries.forEach {
                    val newPos = pos + it.delta
                    if (
                        newPos.row in weightMap.indices
                        && newPos.col in weightMap[0].indices
                        && weightMap[newPos.row][newPos.col] > distance + 1)
                        stack.add(Cursor(distance + 1, newPos))
                }
            }
        }

        val oddity = stepsCount % 2

        return weightMap.sumOf { row ->
            row.count {
                it >= 0 && it % 2 == oddity && it <= stepsCount
            }
        }
    }

    data class Cursor(val step: Int, val pos: Pos): Comparable<Cursor> {
        override fun compareTo(other: Cursor): Int {
            return this.step - other.step
        }
    }

}