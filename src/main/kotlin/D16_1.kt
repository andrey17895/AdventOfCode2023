import java.util.Stack

object D16_1 {

    private const val DAY = 16

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 46
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
//        val expected2 = 508552
//        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        val map = input.split("\n").map {
            it.map { ch ->
                when (ch) {
                    '.' -> Tile.EMPTY
                    '/' -> Tile.FWD_MIRROR
                    '\\' -> Tile.BCW_MIRROR
                    '|' -> Tile.VER_SPLITTER
                    '-' -> Tile.HOR_SPLITTER
                    else -> error("Unknown symbol '$ch'")
                }
            }
        }
        val horRange = map[0].indices
        val verRange = map.indices

        val visitationMap = MutableList(map.size) { MutableList(map[0].size) { mutableListOf<Dir>() } }

        val stack = ArrayDeque<Beam>()
        stack.addLast(Beam(Pos(0, 0), Dir.RIGHT))

        while (stack.isNotEmpty()) {
            val beam = stack.removeLast()
            val pos = beam.pos
            if (visitationMap[pos.row][pos.col].contains(beam.dir)) {
                continue
            }
            visitationMap[pos.row][pos.col].add(beam.dir)
            val tile = map[pos.row][pos.col]
            tile.move(beam.dir).forEach {
                val newPos = Pos(pos.row + it.row, pos.col + it.col)
                if (checkBoundaries(newPos, horRange, verRange)) {
                    stack.addLast(Beam(newPos, it))
                }
            }
        }

        println()
        visitationMap.forEach{
            it.forEach{
                print(if(it.size > 0) '#' else '.')
            }
            println()
        }

        return visitationMap.sumOf { row ->
            row.count {
                it.size > 0
            }
        }


    }

    private fun checkBoundaries(pos: Pos, horRange: IntRange, verRange: IntRange) =
        pos.row in verRange && pos.col in horRange

    private class Beam(val pos: Pos, val dir: Dir)

    data class Pos(val row: Int, val col: Int)

    private enum class Tile(val move: (Dir) -> List<Dir>) {
        EMPTY({ dir -> listOf(dir) }),
        FWD_MIRROR({ dir ->
            when (dir) {
                Dir.LEFT -> listOf(Dir.UP)
                Dir.RIGHT -> listOf(Dir.DOWN)
                Dir.UP -> listOf(Dir.RIGHT)
                Dir.DOWN -> listOf(Dir.LEFT)
            }
        }),
        BCW_MIRROR({ dir ->
            when (dir) {
                Dir.LEFT -> listOf(Dir.DOWN)
                Dir.RIGHT -> listOf(Dir.UP)
                Dir.UP -> listOf(Dir.LEFT)
                Dir.DOWN -> listOf(Dir.RIGHT)
            }
        }),
        VER_SPLITTER({ dir ->
            when (dir) {
                Dir.LEFT, Dir.RIGHT -> listOf(Dir.UP, Dir.DOWN)
                Dir.UP -> listOf(Dir.UP)
                Dir.DOWN -> listOf(Dir.DOWN)
            }
        }),
        HOR_SPLITTER({ dir ->
            when (dir) {
                Dir.LEFT -> listOf(Dir.LEFT)
                Dir.RIGHT -> listOf(Dir.RIGHT)
                Dir.UP, Dir.DOWN -> listOf(Dir.LEFT, Dir.RIGHT)
            }
        }),
    }


}