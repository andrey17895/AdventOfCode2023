import java.util.PriorityQueue
import kotlin.time.measureTime
import kotlin.Comparable as Comparable

object D17_2 {

    private const val DAY = 17

    //732 - low

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
    }

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution("$DAY/sample.txt", 94)
        println("===================")
        checkSolution("$DAY/sample2.txt", 71)
        println("===================")
        checkSolution("$DAY/input.txt", 734)
        println("===================")
    }

    private fun solve(input: String): Any {

        val map = input.split("\n").map { row -> row.map { it.digitToInt() } }

        val height = map.size
        val width = map[0].size

        val weightMap = MutableList(height) { MutableList(width) { mutableMapOf<Pair<Dir, Int>, Int>() } }

        val endPos = Pos(map.size - 1, map[0].size - 1)

        val startPos = Pos(0, 0)

        stepsTaken = 0L

        val stack = PriorityQueue<SolverState>()

        Dir.entries
            .filter { checkBorders(startPos + it.delta, map.indices, map[0].indices) }
            .map { moveDir ->
                SolverState(
                    startPos + moveDir.delta,
                    moveDir,
                    1,
                    0,
                    1
                )
            }.forEach {
                stack.add(it)
            }

        while (stack.size > 0) {
            val state = stack.poll()
            findRoutes(
                state.position,
                state.dir,
                state.accumulatedPrice,
                state.stepsTaken,
                endPos,
                map,
                weightMap,
                state.depth
            ).forEach { stack.add(it) }
            val targetTile = weightMap[endPos.row][endPos.col].values.minOrNull()
            if (targetTile != null) stack.clear()
        }

        weightMap.forEach(::println)

        println("Steps taken: $stepsTaken")
        return weightMap[endPos.row][endPos.col].values.min()
    }

    private var stepsTaken = 0L

    private data class SolverState(
        val position: Pos,
        val dir: Dir,
        val stepsTaken: Int,
        val accumulatedPrice: Int,
        val depth: Int
    ) : Comparable<SolverState> {
        override fun compareTo(other: SolverState): Int =
            this.accumulatedPrice - other.accumulatedPrice
    }

    private fun findRoutes(
        pos: Pos,
        dir: Dir,
        accumulatedPrice: Int,
        stepsTaken: Int,
        endPos: Pos,
        map: List<List<Int>>,
        weights: List<List<MutableMap<Pair<Dir, Int>, Int>>>,
        depth: Int
    ): List<SolverState> {

        this.stepsTaken += 1
        val tilePrice = map[pos.row][pos.col]
        val pathPrice = tilePrice + accumulatedPrice
        val tileWeight = weights[pos.row][pos.col]

//        if (pos == endPos && stepsTaken < 4) return emptyList()

        val currentBestPrice = weights[endPos.row][endPos.col].values.minOrNull()
        if (currentBestPrice != null && pathPrice > currentBestPrice) return emptyList()


//        if (tileWeight.containsKey(dir to remainingSteps) && (pathPrice > (tileWeight.values.minOrNull()
//                ?: Int.MAX_VALUE))
//        ) {
        if (tileWeight.containsKey(dir to stepsTaken) && (pathPrice > tileWeight[dir to stepsTaken]!!)) {
            return emptyList()
        } else {
            if (pos != endPos) {
                tileWeight[dir to stepsTaken] = pathPrice
            } else if (stepsTaken >= 4) {
                tileWeight[dir to stepsTaken] = pathPrice
            }
        }

        if (pos == endPos) return emptyList()

        val possibleMoves = findMoves(pos, dir, stepsTaken, map.indices, map[0].indices)

        return possibleMoves.map { moveDir ->
            SolverState(
                pos + moveDir.delta,
                moveDir,
                if (moveDir != dir) 1 else stepsTaken + 1,
                pathPrice,
                depth + 1
            )
        }

//        possibleMoves.forEach { moveDir ->
//            findRoutes(
//                pos + moveDir.delta,
//                moveDir,
//                pathPrice,
//                if (moveDir != dir) 2 else remainingSteps - 1,
//                endPos,
//                map,
//                weights,
//            )
//        }

    }

    private fun findMoves(pos: Pos, dir: Dir, takenSteps: Int, vBorder: IntRange, hBorder: IntRange): List<Dir> {
        return buildList {
            if (takenSteps < 10)
                add(dir)
            if (takenSteps > 3)
                when (dir) {
                    Dir.UP, Dir.DOWN -> {
                        add(Dir.LEFT); add(Dir.RIGHT)
                    }

                    Dir.LEFT, Dir.RIGHT -> {
                        add(Dir.UP); add(Dir.DOWN)
                    }
                }
        }.filter { checkBorders(pos + it.delta, vBorder, hBorder) }
    }

    private fun checkBorders(pos: Pos, vBorder: IntRange, hBorder: IntRange) =
        pos.row in vBorder && pos.col in hBorder

}