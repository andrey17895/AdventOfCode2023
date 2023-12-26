import kotlin.time.measureTime

object D8_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution({ solve(readInput("08/sample2.txt")) }, 6)
        checkSolution({ solve(readInput("08/input.txt")) }, 18625484023687)
    }

    private fun solve(input: String): Any {
        val split = input.split("\n")
        val path = split.first()
        val map = split.drop(2).associate {
            val (current, left, right) = """(.+) = \((.+), (.+)\)""".toRegex().find(it)!!.destructured
            current to Pair(left, right)
        }

        return map.keys.filter { it.endsWith("A") }
            .map { countSteps(it, map, path) }
            .reduce { acc, l -> acc.lcm(l) }

    }

    private fun countSteps(start: String, map: Map<String, Pair<String, String>>, path: String): Long {
        var count = 0
        var currentPlace = start

        while (!currentPlace.endsWith("Z")) {
            val directions = map[currentPlace] ?: error("no node $currentPlace in the map")
            val direction = path[count % path.length]
            currentPlace = when (direction) {
                'L' -> directions.first
                'R' -> directions.second
                else -> error("unknown input: $direction")
            }
            count += 1
        }
        return count.toLong()
    }

    private fun Long.lcm(other: Long): Long {
        val larger = if (this > other) this else other
        val maxLcm = this * other
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % this == 0L && lcm % other == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}



