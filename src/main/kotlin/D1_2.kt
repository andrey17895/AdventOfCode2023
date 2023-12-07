object D1_2 {

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve2(readInput("01/sample2.txt")))
        println("\n======================\n")
        println(solve2(readInput("01/input1.txt")))
        //55929
    }

    fun solve2(input: String): String {
        val result = input.split("\n")
            .map { transform(it) }
            .map { it.filter { it.isDigit() } }
            .map { "" + it[0] + it[it.length - 1] }
            .sumOf { it.toInt() }
        return result.toString()
    }

    fun transform(str: String): String {
        if (str.isEmpty()) return ""
        if (str[0].isDigit()) return str[0] + transform(str.substring(1, str.length))
        map.keys.forEach {
            if (str.startsWith(it)) return map[it]!! + transform(str.substring(1, str.length))
        }
        return transform(str.substring(1, str.length))
    }

    val map: Map<String, String> = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )
}