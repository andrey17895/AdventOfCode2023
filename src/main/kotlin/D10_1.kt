import D10_1.charToDir

typealias MutablePipeMap = MutableList<MutableList<Char>>
typealias PipeMap = List<List<Char>>

object D10_1 {


    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("10/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 8
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("10/input.txt"))
        println("===================")
        println(result)
        val expected2 = 1939607039
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private const val START_CHAR = 'S'
    private const val VISITED = '*'
    private fun solve(input: String): Any {
        val spited = input.split("\n").map { it.toMutableList() }.toMutableList()
        val (startRow, startCol) = findCharPosition(spited, START_CHAR)
        findLoop(spited, Position(startRow, startCol))
        return startRow to startCol
    }

    private fun findCharPosition(map: PipeMap, c: Char): Pair<Int, Int> {
        val row = map.indexOfFirst { it.contains(c) }
        check(row != -1) { "Couldn't find line with '$c'" }
        val column = map[row].indexOf(c)
        check(column != -1) { "Couldn't find column with '$c'" }
        return row to column
    }

    private fun findLoop(map: MutablePipeMap, startPosition: Position): List<Pair<Int, Int>> {
        var currentPosition = startPosition
        if (checkConnection(map, currentPosition, Dir.RIGHT)) {

        }
        return emptyList()
    }

    private fun checkConnection(map: PipeMap, positon: Position, direction: Dir): Boolean {
        return charToDir[map[positon.row + direction.row][positon.col + direction.col]]
            ?.contains(oppositeDir[direction]) ?: false
    }


    private val charToDir = mapOf(
        '|' to listOf(Dir.UP, Dir.DOWN),
        '-' to listOf(Dir.LEFT, Dir.RIGHT),
        'L' to listOf(Dir.UP, Dir.RIGHT),
        'J' to listOf(Dir.UP, Dir.LEFT),
        '7' to listOf(Dir.LEFT, Dir.DOWN),
        'F' to listOf(Dir.RIGHT, Dir.DOWN),
    )

    private val oppositeDir = mapOf(
        Dir.UP to Dir.DOWN,
        Dir.DOWN to Dir.UP,
        Dir.LEFT to Dir.RIGHT,
        Dir.RIGHT to Dir.LEFT
    )

    val pipes = charToDir.keys

    data class Position(val col: Int, val row: Int)

}
