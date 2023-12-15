package day10

import day10.Direction.DOWN
import day10.Direction.LEFT
import day10.Direction.RIGHT
import day10.Direction.UP
import kotlin.math.abs
import println
import readInput

fun main() {
    class Matrix(input: List<String>) {
        val matrix: List<List<String>> = input.map { line -> line.split("").filter { it.isNotEmpty() } }
        val distances1: List<MutableList<Int>> =
            List(matrix.size) { List(matrix[0].size) { -1 }.toMutableList() }
        val distances2: List<MutableList<Int>> =
            List(matrix.size) { List(matrix[0].size) { -1 }.toMutableList() }

        private var start: Point = Point()
        private var current: Point = Point()

        init {
            for (y in matrix.indices) {
                for (x in matrix[0].indices) {
                    if (matrix[y][x] == "S") {
                        start = Point(x, y)
                    }
                }
            }
            current = start
            distances1[current.y][current.x] = 0
            distances2[current.y][current.x] = 0
        }

        fun walk(): Int {
            var old = start
            val reachableNeighbors1 = mutableListOf(reachableNeighbors(old, distances1)[0])
            val reachableNeighbors2 = mutableListOf(reachableNeighbors(old, distances2)[1])

            while (reachableNeighbors1.isNotEmpty()) {
                current = reachableNeighbors1.removeFirst()
                current.setDistance1(old.getDistance1() + 1)
                val elements = reachableNeighbors(current, distances1).filter { it != old }
                reachableNeighbors1.addAll(elements)
                old = current.copy()
            }

            old = start
            current = start
            while (reachableNeighbors2.isNotEmpty()) {
                current = reachableNeighbors2.removeFirst()
                current.setDistance2(old.getDistance2() + 1)
                val elements = reachableNeighbors(current, distances2).filter { it != old }
                reachableNeighbors2.addAll(elements)
                old = current.copy()
            }

            var result = Point()
            for (y in matrix.indices) {
                for (x in matrix[0].indices) {
                    val p = Point(x, y)
                    if (distances1[p] != -1 && distances2[p] != -1 &&
                        abs(distances1[p] - distances2[p]) < 2 &&
                        p != start
                    ) {
                        result = p.copy()
                    }
                }
            }
            return distances2[result]
        }

        fun reachableNeighbors(p: Point = current, distances: List<MutableList<Int>>) =
            Direction.entries.distinct()
                .flatMap { isCurrentConnectedToDirection(p) }.distinct()
                .filter { isNeighborConnectedToCurrent(it) }
                .map { reachableDir -> p + reachableDir.point }.distinct()
                .filter { reachablePoint -> distances[reachablePoint] == -1 || distances[reachablePoint] < distances[p] + 1 }
                .toList()

        private fun isNeighborConnectedToCurrent(dir: Direction): Boolean {
            val newPoint = current + dir.point
            if (newPoint.x < 0 || newPoint.x > matrix[0].size ||
                newPoint.y < 0 || newPoint.y > matrix.size
            ) {
                return false
            }

            val neighborPipe = this[newPoint]
            return when (dir) {
                UP -> listOf("|", "F", "7").contains(neighborPipe)
                RIGHT -> listOf("-", "7", "J").contains(neighborPipe)
                DOWN -> listOf("|", "J", "L").contains(neighborPipe)
                LEFT -> listOf("-", "L", "F").contains(neighborPipe)
            }
        }

        private fun isCurrentConnectedToDirection(p: Point): List<Direction> {
            val currentPipe = this[p]
            return when (currentPipe) {
                "|" -> listOf(UP, DOWN)
                "-" -> listOf(LEFT, RIGHT)
                "L" -> listOf(UP, RIGHT)
                "J" -> listOf(UP, LEFT)
                "7" -> listOf(LEFT, DOWN)
                "F" -> listOf(DOWN, RIGHT)
                "." -> listOf()
                "S" -> listOf(UP, DOWN, LEFT, RIGHT)
                else -> throw Exception("Not a covered pipe character")
            }
        }

        private operator fun get(p: Point) =
            matrix[p.y][p.x]

        private operator fun List<List<Int>>.get(p: Point) =
            this[p.y][p.x]

        private fun Point.setDistance1(dist: Int) {
            distances1[this.y][this.x] = dist
        }

        private fun Point.getDistance1() =
            distances1[this.y][this.x]

        private fun Point.setDistance2(dist: Int) {
            distances2[this.y][this.x] = dist
        }

        private fun Point.getDistance2() =
            distances2[this.y][this.x]

        fun <T> print(m: List<List<T>>) {
            for (y in matrix.indices) {
                for (x in matrix[0].indices) {
//                    print(if (m[y][x] == -1) "." else m[y][x])
                    print(if (m[y][x] == -1) " " else ".")
                }
                "".println()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val m = Matrix(input)
        val result = m.walk()

        println()
        m.print(m.distances1)

        return result
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10/test")
    check(part1(testInput) == 4)

    val testInput2 = readInput("day10/test2")
    check(part1(testInput2) == 8)

    val input = readInput("day10/data")
    part1(input).println()

//    val testInputPart2 = readInput("day3/test")
//    val part2test = part2(testInputPart2)
//    check(part2test == 2286)
//
//    part2(input).println()
}

data class Point(
    val x: Int = -1,
    val y: Int = -1,
) {
    infix operator fun plus(other: Point): Point =
        Point(this.x + other.x, this.y + other.y)
}

enum class Direction(val point: Point) {
    UP(Point(0, -1)),
    RIGHT(Point(1, 0)),
    DOWN(Point(0, 1)),
    LEFT(Point(-1, 0));
}
