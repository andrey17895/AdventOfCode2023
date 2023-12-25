import kotlin.math.abs

object D24_1 {

    private const val DAY = 24

    @JvmStatic
    fun main(args: Array<String>) {
        checkSolution({ solve(readInput("$DAY/sample.txt"), 7, 27) }, 2)
        checkSolution({ solve(readInput("$DAY/input.txt"), 200000000000000, 400000000000000) }, 17867)
    }

    private fun solve(input: String, loverBound: Long, upperBound: Long): Any {
        val beams = input.split("\n")
            .map { line ->
                val spl = line.split(" @ ", ", ").map { it.toDouble() }
                check(spl.all { !it.equlasDelta(0.0) })
                Ray(spl[0], spl[1], spl[2], spl[3], spl[4], spl[5])
            }

        val pairs = buildList {
            for (i in beams.indices) {
                for (j in i + 1..<beams.size) {
                    add(beams[i] to beams[j])
                }
            }
        }

        return pairs.count {
            hasIntersection(it.first, it.second, loverBound.toDouble().rangeTo(upperBound.toDouble()))
        }
    }

    private fun hasIntersection(a: Ray, b: Ray, range: ClosedFloatingPointRange<Double>): Boolean {

        val delimiter = b.dx * a.dy - b.dy * a.dx
        if (delimiter.equlasDelta(0.0)) {
            return false
        }
        val beta = (b.y * a.dx - a.y * a.dx - b.x * a.dy + a.x * a.dy) / delimiter
        val x = b.x + beta * b.dx
        val y = b.y + beta * b.dy

        if (!checkOnRay(x, y, a) || !checkOnRay(x, y, b)) {
            return false
        }
        if (x !in range || y !in range) {
            return false
        }
        return true
    }

    // Does not work with ray.dx == 0 or ray.dy == 0 (such values doesn't exist in input)
    private fun checkOnRay(x: Double, y: Double, ray: Ray): Boolean =
        (ray.x - x > 0) xor (ray.dx > 0) &&
                (ray.y - y > 0) xor (ray.dy > 0)


    data class Ray(
        val x: Double, val y: Double, val z: Double,
        val dx: Double, val dy: Double, val dz: Double
    )


    private fun Double.equlasDelta(other: Double) = abs(this - other) < 0.0001
}