import java.util.PriorityQueue

object D22_2 {

    private const val DAY = 22

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution({ solve(readInput("$DAY/sample.txt")) }, 154)
        checkSolution({ solve(readInput("$DAY/input.txt")) }, 6898)
    }

    private fun solve(input: String): Any {
        val map = input.split("\n").map { it.toList() }

        val startPos = Pos(0, map[0].indexOf('.'))
        val endPos = Pos(map.size - 1, map[map.size - 1].indexOf('.'))

        val nodes = findNodes(map)

        val graph = nodes.associateWith { findNeighbors(0, it, it, mutableSetOf(), nodes, map).toMap() }



        return findRoute(startPos, 0, mutableSetOf(), endPos, graph)!!
    }

    private fun findNodes(map: List<List<Char>>): Set<Pos> =
        buildSet {
            for (r in map.indices)
                for (c in map[0].indices)
                    if (map[r][c] != '#' && possibleMoves(Pos(r, c), map).size != 2)
                        add(Pos(r, c))
        }


    private fun findRoute(
        pos: Pos,
        distance: Int,
        visited: MutableSet<Pos>,
        endPos: Pos,
        graph: Map<Pos, Map<Pos, Int>>
    ): Int? {
        if (pos == endPos) return distance
        val newVisited = visited.toMutableSet()
        newVisited.add(pos)
        return graph[pos]!!.filter { (p, _) -> !visited.contains(p) }.mapNotNull { (p, i) ->
            findRoute(p, distance + i, newVisited, endPos, graph)
        }.maxOrNull()
    }

    private fun findNeighbors(
        distance: Int,
        pos: Pos,
        startPos: Pos,
        visited: MutableSet<Pos>,
        nodes: Set<Pos>,
        map: List<List<Char>>
    ): List<Pair<Pos, Int>> {
        if (pos in nodes && pos != startPos) {
            return listOf(pos to distance)
        }
        visited.add(pos)
        return possibleMoves(pos, map)
            .filter { !visited.contains(it) }
            .flatMap { findNeighbors(distance + 1, it, startPos, visited, nodes, map) }

    }

    private fun possibleMoves(pos: Pos, map: List<List<Char>>): List<Pos> {
        return Dir.entries.map { pos + it.delta }.filter {
            it.row in map.indices && it.col in map[0].indices && map[it.row][it.col] != '#'
        }
    }
}