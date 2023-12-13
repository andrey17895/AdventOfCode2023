object D12_2 {

    private const val DAY = 12

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 21
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
//        val expected2 = 9647174
//        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        return input.split("\n")
            .map {
                val (p1, p2) = it.split(" ")
                p1.repeat(5) to "$p2,".repeat(5).trimEnd(',')
            }.asSequence()
            .mapIndexed() { i, it ->
                val expected = it.second.split(",").map { it.toInt() }
                println(i)
                println(it)
                println(expected)
                replaceUnknown(it.first).map { guess ->
                    guess.trim{ch -> ch == '.'}
                        .replace(Regex("\\.+"), ".")
                        .split(".")
                        .map { it.count() }
                }.filter {
                    it == expected
                }.count()
            }.sum()
    }

    private fun replaceUnknown(str: String): List<String> {
        if (str.isEmpty()) return listOf("")
        return if (str.first() == '?') {
            buildList {
                addAll(replaceUnknown(str.drop(1)).map { "#${it}" })
                addAll(replaceUnknown(str.drop(1)).map { ".${it}" })
            }
        } else {
            buildList {
                addAll(replaceUnknown(str.drop(1)).map { "${str.first()}$it" })
            }
        }
    }

}