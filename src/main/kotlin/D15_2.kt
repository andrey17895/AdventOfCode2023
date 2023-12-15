object D15_2 {

    private const val DAY = 15

    @JvmStatic
    fun main(args: Array<String>) {
        val input = readInput("$DAY/sample.txt")
        println(input)
        val answer = solve(input)
        println(answer)
        val expected = 145L
        check(answer == expected) { "Expected: $expected, Actual: $answer" }
        println("===================")
        val result = solve(readInput("$DAY/input.txt"))
        println("===================")
        println(result)
//        val expected2 = 508552
//        check(result == expected2) { "Expected: $expected2, Actual: $result" }
    }

    private fun solve(input: String): Any {
        check(!input.contains("\n")) { "Input contain line separators" }
        val actions: List<Action> = input.split(",")
            .map {
                when {
                    it.contains("-") -> RemoveAction(it.substringBefore("-"))
                    it.contains("=") -> AddAction(it)
                    else -> error("command '$it' should contain '=' or '-'")
                }
            }
        val boxes = List(256) { mutableListOf<Lens>() }
        actions.forEach {
            when (it) {
                is AddAction -> {
                    val box = boxes[hash(it.lens.name)]
                    val lensIndex = box.indexOfFirst { lens -> lens.name == it.lens.name }
                    if (lensIndex != -1)
                        box[lensIndex] = it.lens
                    else
                        box.add(it.lens)
                }
                is RemoveAction -> {
                    val box = boxes[hash(it.name)]
                    val lensIndex = box.indexOfFirst { lens -> lens.name == it.name }
                    if (lensIndex != -1) {
                        box.removeAt(lensIndex)
                    }
                }
            }
        }
        boxes.forEachIndexed { index, lens ->
            if (lens.size > 0) {
                println("${index + 1} $lens")
            }
        }
        return boxes.withIndex()
            .sumOf {
                    evaluateBox(it.index, it.value)
            }
    }

    private fun evaluateBox(boxIndex: Int, lenses: List<Lens>): Long {
        return lenses.mapIndexed { index, lens ->
            (boxIndex + 1) * (index + 1) * lens.focalLength
        }.sum()
    }


    private fun hash(s: String): Int =
        s.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }


    sealed class Action()

    data class RemoveAction(val name: String) : Action()
    data class AddAction(val lens: Lens) : Action() {
        constructor(command: String) : this(
            Lens(
                command.substringBefore('='),
                command.substringAfter('=').toLong()
            )
        )
    }

    data class Lens(val name: String, val focalLength: Long)
}