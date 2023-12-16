
fun readInput(res: String): String =
    object {}.javaClass.getResourceAsStream(res)?.bufferedReader()?.readText().toString()

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }
}

enum class Dir(val row: Int, val col: Int) {


    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    val delta= Pos(row, col)
}

data class Pos(val row: Int, val col: Int) {
    operator fun plus(other: Pos): Pos = Pos(this.row + other.row, this.col + other.col)
}
