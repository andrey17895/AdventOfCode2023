object D22_1 {

    private const val DAY = 22

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution({ solve(readInput("$DAY/sample.txt")) }, 94)
        checkSolution({ solve(readInput("$DAY/input.txt")) }, 2442)
    }

    private fun solve(input: String): Any {
        val map = input.split("\n").map { it.toList() }

        val startPos = Pos(0, map[0].indexOf('.'))
        val endPos = Pos(map.size - 1, map[map.size - 1].indexOf('.'))

        return findRoute(startPos, 0, mutableSetOf(), endPos, map)!!
    }

    private fun findRoute(pos: Pos, distance: Int, visited: MutableSet<Pos>, endPos: Pos, map: List<List<Char>>): Int? {
        if (pos == endPos) return distance
        val newVisited = visited.toMutableSet()
        newVisited.add(pos)
        return possibleMoves(pos, map).filter { !visited.contains(it) }.mapNotNull {
            findRoute(it, distance + 1, newVisited, endPos, map)
        }.maxOrNull()
    }

    // ^, >, v, and <

    private fun possibleMoves(pos: Pos, map: List<List<Char>>): List<Pos> {
        val tile = map[pos.row][pos.col]
        return when (tile) {
            '.' -> Dir.entries.map { pos + it.delta }
            '^' -> listOf(pos + Dir.UP.delta)
            '>' -> listOf(pos + Dir.RIGHT.delta)
            'v' -> listOf(pos + Dir.DOWN.delta)
            '<' -> listOf(pos + Dir.LEFT.delta)
            else -> error("Unknown symbol $tile")
        }.filter { it.row in map.indices && it.col in map[0].indices && map[it.row][it.col] != '#' }
    }

}