object D5_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("05/sample.txt")
        println(input)
        println()
        val answer = solve(input)
        println(answer)
        val expected = 46L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("05/input.txt"))
        println("===================")
        println(result)
    }

    private fun solve(input: String): Any {
        val seeds = input.substring(7, input.indexOfFirst { it == '\n' })
            .split(" ")
            .map { it.toLong() }
        val maps = input.split("\n\n").drop(1)
            .map { inputMapFromStr(it) }

        val startTime = System.currentTimeMillis()
        var done = 0L

        val total = seeds.asSequence().withIndex()
            .groupBy { it.index / 2 }
            .map { it.value.map { it.value } }
            .map { it[1] }
            .sum()

        val result = seeds.asSequence().withIndex()
            .groupBy { it.index / 2 }
            .map { it.value.map { it.value } }
            .map { it[0]..<it[0] + it[1] }
            .asSequence()
            .flatMap { it.asSequence() }
            .map { seed ->
                ++done
                if (done % 1_000_000 == 0L) {
                    println(
                        "Reamin: %,d (%,d/s)".format(
                            ((total - done)),
                            done / (((System.currentTimeMillis() - startTime) / 1000) + 1)
                        )
                    )
                }
                maps.fold(seed) { acc, inputMap ->
                    inputMap.get(acc)
                }
            }.min()
        println()
        println("Total time: ${(System.currentTimeMillis() - startTime) / 1000 / 60}")
        return result
    }

    private fun inputMapFromStr(str: String): InputMap {
        val splitted = str.split("\n")
        return InputMap(
            splitted[0],
            splitted.drop(1).map {
                val list = it.split(" ").map { it.toLong() }
                InputRange(list[0], list[1], list[2])
            }
        )
    }

    class InputMap(
        val name: String,
        val ranges: List<InputRange>
    ) {

        companion object {
            val knowns = mutableMapOf<Long, Long>()
        }

        fun get(value: Long): Long {
            ranges.forEach {
                if (value in it.range) {
                    return it.destinationFirst + (value - it.sourceFirst)
                }
            }
            return value
        }
    }

    class InputRange(
        val destinationFirst: Long,
        val sourceFirst: Long,
        mapRange: Long
    ) {
        val range: LongRange

        init {
            range = sourceFirst..<sourceFirst + mapRange
        }
    }


}



