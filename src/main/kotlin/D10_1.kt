import Dir.UP
import Dir.DOWN
import Dir.LEFT
import Dir.RIGHT
import java.util.Optional
import kotlin.jvm.optionals.getOrElse

typealias MutablePipeMap = MutableList<MutableList<Char>>
typealias PipeMap = List<List<Char>>

object D10_1 {


    @JvmStatic
    fun main(args: Array<String>) {
        val input: String = readInput("10/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 8
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("10/input.txt"))
        println("===================")
        println(result)
        val expected2 = 6733
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private const val START_CHAR = 'S'
    private fun solve(input: String): Any {
        val charMap = input.split("\n").map { it.toList() }
        val startPosition = findStart(charMap, START_CHAR)
        val tileMap = charMap.map { row ->
            row.map { charToTile(it) }
        }

        val length = Dir.entries.asSequence().map { solvePipe(tileMap, startPosition, it) }
            .first { it != -1 }
        return length / 2
    }

    private fun solvePipe(tileMap: List<List<Tile>>, startPosition: Pos, startDir: Dir): Int {

        val hBound = tileMap[0].indices
        val vBound = tileMap.indices

        var currentPosition = startPosition + startDir.delta
        if (!checkBoundaries(currentPosition, hBound, vBound))
            return -1
        var currentTile = tileMap[currentPosition.row][currentPosition.col]
        var currentDir = startDir
        var length = 0

        while (true) {
            length += 1
            if (currentTile == Tile.S) return length
            currentDir = currentTile.move(currentDir).getOrElse { return -1 }
            currentPosition += currentDir.delta
            if (!checkBoundaries(currentPosition, hBound, vBound))
                return -1
            currentTile = tileMap[currentPosition.row][currentPosition.col]
        }
    }

    private fun checkBoundaries(pos: Pos, hBound: IntRange, vBound: IntRange): Boolean =
        pos.row in vBound && pos.col in hBound

    private fun charToTile(it: Char) = when (it) {
        '|' -> Tile.V
        '-' -> Tile.H
        'L' -> Tile.UR
        'J' -> Tile.UL
        '7' -> Tile.DL
        'F' -> Tile.DR
        '.' -> Tile.E
        'S' -> Tile.S
        else -> error("Unknown tile $it")
    }


    private enum class Tile(val map: Map<Dir, Dir>) {
        V(mapOf(UP to UP, DOWN to DOWN)),
        H(mapOf(LEFT to LEFT, RIGHT to RIGHT)),
        UR(mapOf(DOWN to RIGHT, LEFT to UP)),
        UL(mapOf(DOWN to LEFT, RIGHT to UP)),
        DR(mapOf(UP to RIGHT, LEFT to DOWN)),
        DL(mapOf(RIGHT to DOWN, UP to LEFT)),
        S(mapOf()),
        E(mapOf());

        fun move(dir: Dir): Optional<Dir> = Optional.ofNullable(map[dir])

    }

    private fun findStart(map: PipeMap, c: Char): Pos {
        val row = map.indexOfFirst { it.contains(c) }
        check(row != -1) { "Couldn't find line with '$c'" }
        val column = map[row].indexOf(c)
        check(column != -1) { "Couldn't find column with '$c'" }
        return Pos(row, column)
    }


}
