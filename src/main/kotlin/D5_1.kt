object D5_1 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("05/sample.txt")
        println(input)
        println()
        val answer = solve(input)
        println(answer)
        val expected = 35L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("05/input.txt"))
        println("===================")
        println("66512927 - Wrong")
        println(result)
    }

    private fun solve(input: String): Any {
        val seeds = input.substring(7, input.indexOfFirst { it == '\n' })
            .split(" ")
            .map { it.toLong() }
        val maps = input.split("\n\n").drop(1)
            .map { inputMapFromStr(it) }

        maps.forEach{println(it.name)}

        return seeds.map { seed ->
            println()
            maps.fold(seed) { acc, inputMap ->

                val res = inputMap.get(acc)
                println("${inputMap.name} $acc -> $res")
                res
            }
        }.min()
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
        fun get(value: Long): Long {
            ranges.forEach {
                if (value in it.range) {
                    return it.destinationFirst + (value - it.sourceFirst)
                }
            }
            return value
        }
    }

    class InputRange (
        val destinationFirst: Long,
        val sourceFirst: Long,
        mapRange: Long
    ) {
        val range: LongRange
        init {
            range = sourceFirst ..< sourceFirst + mapRange
        }
    }


}



