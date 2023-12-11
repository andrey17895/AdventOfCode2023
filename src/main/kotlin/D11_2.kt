import kotlin.math.abs

object D11_2 {

    private const val GALAXY_SYMBOL = '#'

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("11/sample.txt")
        val answer = solve(input, 2)
        println(answer)
        val expected = 374L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        check(solve(input, 10) == 1030L)
        check(solve(input, 100) == 8410L)
        println("===================")
        val result = solve(readInput("11/input.txt"), 1_000_000)
        println("===================")
        println(result)
        val expected2 = 377318892554L
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String, expansionRate: Int): Any {
        val map = input.split("\n").map { it.toList() }.toList()
        val verticalExpansions = findVerticalExpansions(map)
        val horizontalExpansions = findHorizontalExpantions(map)

        val galaxies = findGalaxies(map)

        val expandedGalaxies = galaxies.map { galaxy ->
            Galaxy(
                galaxy.row + (expansionRate - 1) * verticalExpansions.count { it < galaxy.row },
                galaxy.col + (expansionRate - 1) * horizontalExpansions.count { it < galaxy.col }
            )
        }


        val pairs = findUniquePairs(expandedGalaxies)
        return pairs.sumOf { (g1, g2) -> abs(g2.row - g1.row) + abs(g2.col - g1.col) }
    }

    private fun findUniquePairs(galaxies: List<Galaxy>): Set<Pair<Galaxy, Galaxy>> {
        val pairs = mutableSetOf<Pair<Galaxy, Galaxy>>()
        for (g1 in galaxies) {
            for (g2 in galaxies) {
                if (g1 == g2) continue
                if (!pairs.contains(g1 to g2) && !pairs.contains(g2 to g1)) {
                    pairs.add(g1 to g2)
                }
            }
        }
        return pairs
    }

    private fun findGalaxies(map: GalaxyMap): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        for (row in map.indices) {
            for (col in map[row].indices) {
                if (map[row][col] == GALAXY_SYMBOL)
                    galaxies.add(Galaxy(row.toLong(), col.toLong()))
            }
        }
        return galaxies
    }

    data class Galaxy(val row: Long, val col: Long)

    private fun findHorizontalExpantions(initialMap: GalaxyMap): List<Int> {
        return findVerticalExpansions(initialMap.transpose())

    }

    private fun findVerticalExpansions(map: GalaxyMap): List<Int> {
        return map.withIndex().filter { it.value.all { ch -> ch == '.' } }.map { it.index }
    }

    fun <T> List<List<T>>.transpose(): List<List<T>> {
        return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
    }


}