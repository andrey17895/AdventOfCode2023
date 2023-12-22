import java.util.PriorityQueue
import kotlin.time.measureTime

object D21_2 {

    private const val DAY = 21

    private fun checkSolution(res: String, steps: Int, expected: Any?) {
        val duration = measureTime {
            val answer = solve(readInput(res), steps)
            println(answer)
            if (expected != null)
                check(answer.toString() == expected.toString()) { "Expected: $expected, Actual: $answer" }
        }
        println("Time taken: $duration")
        println("===================")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution("$DAY/sample.txt", 6, 16)
        checkSolution("$DAY/sample.txt", 10, 50)
        checkSolution("$DAY/sample.txt", 50, 1594)
        checkSolution("$DAY/sample.txt", 100, 6536)
        checkSolution("$DAY/sample.txt", 500, 167004)
        checkSolution("$DAY/sample.txt", 1000, 668697)
        checkSolution("$DAY/sample.txt", 5000, 16733044)
//        checkSolution("$DAY/input.txt", 26_501_365, null)
    }

    private fun solve(input: String, stepsCount: Int): Any {
        val map = input.split("\n").map { it.toList() }
        val startRow = map.indexOfFirst { it.contains('S') }
        val startCol = map[startRow].indexOf('S')
        val height = map.size
        val width = map[0].size
        val startPos = Pos(startRow, startCol)

        val visited = mutableSetOf<Pos>()

        val stack = PriorityQueue<Cursor>()
        stack.add(Cursor(0, startPos))

        val oddity = stepsCount % 2

        var counter = 0L

        while (stack.size > 0) {
            val (distance, pos) = stack.poll()
            val infPos = infinitePosition(pos, height, width)
            if (map[infPos.row][infPos.col] != '#' && !visited.contains(pos)) {
                visited.add(pos)
                if (distance % 2 == oddity) {
                    counter += 1
//                    println(pos)
                }
                Dir.entries.forEach {
                    val newPos = pos + it.delta
                    val infNewPos = infinitePosition(newPos, height, width)
                    if (!visited.contains(newPos) &&
                        map[infNewPos.row][infNewPos.col] != '#'
                        && distance <= stepsCount
                    )
                        stack.add(Cursor(distance + 1, newPos))
                }
            }
        }


        return counter
    }

    private fun infinitePosition(pos: Pos, height: Int, width: Int): Pos =
        Pos(
            if (pos.row >= 0) pos.row % height else height - (-(pos.row + 1) % width) - 1,
            if (pos.col >= 0) pos.col % width else width - (-(pos.col + 1) % width) - 1,
        )


    data class Cursor(val step: Int, val pos: Pos) : Comparable<Cursor> {
        override fun compareTo(other: Cursor): Int {
            return this.step - other.step
        }
    }

}