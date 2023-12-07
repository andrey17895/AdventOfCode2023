
fun readInput(res: String): String =
    object {}.javaClass.getResourceAsStream(res)?.bufferedReader()?.readText().toString()