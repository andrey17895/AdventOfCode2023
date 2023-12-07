object D1_1 {
    @JvmStatic
    fun main(args: Array<String>) {

        fun solve(input: String): String {
            val result = input.split("\n")
                .map { it.filter { it.isDigit() } }
                .map { "" + it[0] + it[it.length - 1] }
                .sumOf { it.toInt() }
            return result.toString()
        }


        println(solve(readInput("01/sample1.txt")))
        println("\n======================\n")
        println(solve(readInput("01/input1.txt")))
    }

}