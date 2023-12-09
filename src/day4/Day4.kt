package day4

import kotlin.math.pow
import println
import readInput

fun main() {
    fun part1(input: List<String>): Int =
        input.map { line ->
            val (_, right) = line.split(":")
            val (winning, numbers) = right.split("|")

            val winningStrings = winning.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSortedSet()
            val numberStrings = numbers.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

            val count = numberStrings.count { winningStrings.contains(it) }
            if (count > 0) {
                2.0.pow(count - 1)
            } else {
                0
            }
        }.sumOf { it.toInt() }

    fun part2(input: List<String>): Int {
        val results = input.map { line ->
            val (_, right) = line.split(":")
            val (winning, numbers) = right.split("|")

            val winningStrings = winning.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSortedSet()
            val numberStrings = numbers.trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

            val count = numberStrings.count { winningStrings.contains(it) }
            count
        }

        val winCount = List(input.size) { _ -> 1 }.toMutableList()

        for (i in 0 until winCount.size) {
            for (j in 1..results[i]) {
                if (i + j < winCount.size) {
                    winCount[i + j] += winCount[i]
                }
            }
        }
        return winCount.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/test")
    check(part1(testInput) == 13)

    val input = readInput("day4/data")
    part1(input).println()

    val testInputPart2 = readInput("day4/test")
    val part2test = part2(testInputPart2)
    check(part2test == 30)

    part2(input).println()

}
