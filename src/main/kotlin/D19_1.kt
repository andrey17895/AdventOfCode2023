object D19_1 {

    private const val DAY = 19

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution({ solve(readInput("$DAY/sample.txt")) }, 19114)
        checkSolution({ solve(readInput("$DAY/input.txt")) }, null)
    }

    private fun solve(input: String): Any {

        val startWorkflowName = "in"

        val (workflowsStr, partsStr) = input.split("\n\n")

        val workflows = workflowsStr.split("\n")
            .associate {
                val (name, steps) = it.split("{")
                val parsedStep = steps.substring(0, steps.length - 1).split(",")
                    .map { step -> if (step.contains(":")) ConditionalStep(step) else UnconditionalStep(step) }
                name to parsedStep
            }

        val parts = partsStr.split("\n")
            .map { it.replace("[{}xmas=]".toRegex(), "") }
            .map {
                val (x, m, a, s) = it.split(",").map { n -> n.toInt() }
                Part(x, m, a, s)
            }

        return parts.filter { processPart(it, startWorkflowName, workflows) is Accepted }
            .sumOf { it.x + it.m + it.a + it.s }
    }

    private fun processPart(part: Part, workflow: String, workflowMap: Map<String, List<Step>>): ProcessResult {

        val steps = workflowMap[workflow] ?: error("Unknown workflow: $workflow")

        for (step in steps) {
            return when(val currentStep = step.process(part)) {
                Accepted -> Accepted
                Rejected -> Rejected
                is Sent -> processPart(part, currentStep.destination, workflowMap)
                Skipped -> continue
            }
        }
        error("Workflow $workflow for part $part haven't terminated")
    }

    private sealed class Step {
        abstract fun process(part: Part): ProcessResult
    }

    private class ConditionalStep(val condition: String) : Step() {

        val categorySymbol = condition.first()
        val conditionSymbol = condition[1]
        val value = condition.drop(2).takeWhile { it != ':' }.toInt()
        val destination = strToDestination(condition.takeLastWhile { it != ':' })

        override fun process(part: Part): ProcessResult {
            val category = when (categorySymbol) {
                'x' -> part.x
                'm' -> part.m
                'a' -> part.a
                's' -> part.s
                else -> error("Unknown symbol $categorySymbol")
            }
            val satisfied = when (conditionSymbol) {
                '<' -> category < value
                '>' -> category > value
                else -> error("Unknown symbol $conditionSymbol")
            }
            return if (satisfied) destination else Skipped
        }

    }

    private class UnconditionalStep(val destinationStr: String) : Step() {
        override fun process(part: Part): ProcessResult {
            return strToDestination(destinationStr)
        }
    }

    private fun strToDestination(it: String) = when (it) {
        "A" -> Accepted
        "R" -> Rejected
        else -> Sent(it)
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int)

    private sealed class ProcessResult

    private data object Accepted : ProcessResult()
    private data object Rejected : ProcessResult()
    private data class Sent(val destination: String) : ProcessResult()
    private data object Skipped : ProcessResult()

}