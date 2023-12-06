package day6

import println
import readInput

fun main() {
    fun raceWins(time: Int, record: Int) =
        (0..time)
            .map {
                val travelTime = time - it
                val distance = it * travelTime
                distance
            }
            .filter { it > record }
            .size

    fun raceWins2(time: Long, record: Long) =
        (0..time)
            .map {
                val travelTime = time - it
                val distance = it * travelTime
                distance
            }
            .filter { it > record }
            .size


    fun part1(input: List<String>): Int {
        val times = input[0].split(":")[1].split(Regex("\\s+")).filter { it.isNotEmpty() }.map { it.toInt() }
        val distances = input[1].split(":")[1].split(Regex("\\s+")).filter { it.isNotEmpty() }.map { it.toInt() }
        return times.zip(distances)
            .map { raceWins(it.first, it.second) }
            .reduce { acc, next -> acc * next }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(":")[1].replace(" ", "").toLong()
        val distance = input[1].split(":")[1].replace(" ", "").toLong()
        return raceWins2(time, distance)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day6/test")
    check(part1(testInput) == 288)

    val input = readInput("day6/data")
    part1(input).println()

    val testInputPart2 = readInput("day6/test")
    val part2test = part2(testInputPart2)
    check(part2test == 71503)

    part2(input).println()
}



