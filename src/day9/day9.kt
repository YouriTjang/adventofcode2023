package day9

import println
import readInput

fun main() {
    fun parse(input: List<String>): List<MutableList<Int>> =
        input.map {line ->
            line.split(" ")
                .map { it.toInt() }
                .toMutableList()
        }

    fun part1CalcLastNumber(input: MutableList<Int>): Int {
        var numbers = input
        val diffs = mutableListOf<MutableList<Int>>(numbers)
        while (!numbers.all { it == 0 }) {
            numbers = numbers
                .windowed(2, 1)
                .map { it[1]-it[0] }
                .toMutableList()
            diffs.add(numbers)
        }

        diffs.last().add(0)
        val diffsr = diffs.reversed()
        for(i in 1 until diffsr.size) {
            diffsr[i].add(diffsr[i-1].last() + diffsr[i].last())
        }

        return diffsr.last().last()
    }

    fun part2CalcLastNumber(input: MutableList<Int>): Int {
        var numbers = input.reversed().toMutableList()
        val diffs = mutableListOf<MutableList<Int>>(numbers)
        while (!numbers.all { it == 0 }) {
            numbers = numbers
                .windowed(2, 1)
                .map { it[1]-it[0] }
                .toMutableList()
            diffs.add(numbers)
        }

        diffs.last().add(0)
        val diffsr = diffs.reversed()
        for(i in 1 until diffsr.size) {
            diffsr[i].add(diffsr[i-1].last() + diffsr[i].last())
        }

        return diffsr.last().last()
    }

    fun part1(input: List<String>): Int =
        parse(input).sumOf { part1CalcLastNumber(it) }


    fun part2(input: List<String>): Int =
        parse(input).sumOf { part2CalcLastNumber(it) }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day9/test")
    check(part1(testInput) == 114)

    val input = readInput("day9/data")
    part1(input).println()

    val testInputPart2 = readInput("day9/test")
    val part2test = part2(testInputPart2)
    check(part2test == 2)

    part2(input).println()
}
