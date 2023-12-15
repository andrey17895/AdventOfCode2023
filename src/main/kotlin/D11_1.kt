import kotlin.math.abs

typealias GalaxyMap = List<List<Char>>


object D11_1 {

    private const val GALAXY_SYMBOL = '#'

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("11/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 374
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("11/input.txt"))
        println("===================")
        println(result)
        val expected2 = 9647174
        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        val initialMap = input.split("\n").map { it.toList() }.toList()
        val expandedMap = expandVertical(expandHorizontal(initialMap))
        expandedMap.onEach { println( it.joinToString(separator = "") { it.toString() }) }
        val galaxies = findGalaxies(expandedMap)
        galaxies.forEach(::println)
//        println(galaxies.size)
        val pairs = findUniquePairs(galaxies)
//        println(pairs.size)
//        pairs.forEach(::println)
        return pairs.sumOf { (g1, g2) -> abs(g2.row - g1.row) + abs(g2.col - g1.col) }
    }

    private fun findUniquePairs(galaxies: List<Galaxy>): Set<Pair<Galaxy, Galaxy>> {
        val pairs = mutableSetOf<Pair<Galaxy,Galaxy>>()
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
                    galaxies.add(Galaxy(row, col))
            }
        }
        return galaxies
    }

    data class Galaxy(val row: Int, val col: Int)

    private fun expandHorizontal(initialMap: GalaxyMap): GalaxyMap {
        return expandVertical(initialMap.transpose()).transpose()

    }

    private fun expandVertical(map: GalaxyMap): GalaxyMap {
        return map.flatMap { if (it.all { ch -> ch == '.' }) listOf(it, it) else listOf(it) }
    }
}