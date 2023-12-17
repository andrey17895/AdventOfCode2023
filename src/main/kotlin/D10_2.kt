import java.util.*
import kotlin.jvm.optionals.getOrElse

object D10_2 {

    private fun checkSolution(res: String, expected: Int) {
        val input: String = readInput(res)
        val answer = solve(input)
        println(answer)
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
    }

    private val pipe = mutableMapOf<Pos, Tile>()

    @JvmStatic
    fun main(args: Array<String>) {
//        checkSolution("10/sample2.txt", 4)
        println("===================")
        checkSolution("10/input.txt", 1)
        println("===================")
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

        val cleanMap = drawPipe(tileMap, pipe)

        var count = 0

        for (row in cleanMap.indices) {
            var inside = false
//            var count = 0
            for (col in cleanMap[row].indices) {
                when (cleanMap[row][col]) {
                    in setOf('┛', '┗', '┃', 'S') -> inside = !inside
                    '.' -> if (inside) {
                        cleanMap[row][col] = '#'
                        count += 1
                    }
                }
            }

        }

        cleanMap.forEach {
            println(it.joinToString(separator = "") { it.toString() })
        }

        return count
    }

    private fun drawPipe(tileMap: List<List<Tile>>, pipe: Map<Pos, Tile>): MutableList<MutableList<Char>> {
        val newMap = MutableList(tileMap.size) { MutableList(tileMap[0].size) { '.' } }
        for (row in tileMap.indices) {
            print("$row: ")
            for (col in tileMap[0].indices) {
                val pos = Pos(row, col)
                val char = if (pipe.containsKey(pos))
                    when (pipe[pos]) {
                        Tile.V -> '┃'
                        Tile.H -> '━'
                        Tile.UR -> '┗'
                        Tile.UL -> '┛'
                        Tile.DR -> '┏'
                        Tile.DL -> '┓'
                        Tile.S -> 'S'
                        Tile.E -> '.'
                        else -> error("OMGWTF")
                    }
                else
                    '.'
                print(char)
                newMap[row][col] = char
            }
            println()
        }
        return newMap
    }


    private fun solvePipe(tileMap: List<List<Tile>>, startPosition: Pos, startDir: Dir): Int {

        pipe.clear()

        println("counting pipe")
        val hBound = tileMap[0].indices
        val vBound = tileMap.indices

        var currentPosition = startPosition + startDir.delta
        if (!checkBoundaries(currentPosition, hBound, vBound))
            return -1
        var currentTile = tileMap[currentPosition.row][currentPosition.col]
        pipe[currentPosition] = currentTile
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
            pipe[currentPosition] = currentTile
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
        V(mapOf(Dir.UP to Dir.UP, Dir.DOWN to Dir.DOWN)),
        H(mapOf(Dir.LEFT to Dir.LEFT, Dir.RIGHT to Dir.RIGHT)),
        UR(mapOf(Dir.DOWN to Dir.RIGHT, Dir.LEFT to Dir.UP)),
        UL(mapOf(Dir.DOWN to Dir.LEFT, Dir.RIGHT to Dir.UP)),
        DR(mapOf(Dir.UP to Dir.RIGHT, Dir.LEFT to Dir.DOWN)),
        DL(mapOf(Dir.RIGHT to Dir.DOWN, Dir.UP to Dir.LEFT)),
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