package day2

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val maxSet = OneSet(12, 13, 14)

        return input.sumOf {
            val results = Regex("^Game (?<id>\\d*):(?<sets>.*)").find(it)!!
            val id = results.groupValues[1].toInt()
            val sets = results.groupValues[2]

            val isValid = sets.split(";")
                .all { OneSet.new(it).isValidSet(maxSet) }
            if (isValid) id else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val results = Regex("^Game (?<id>\\d*):(?<sets>.*)").find(it)!!
            val sets = results.groupValues[2]

            sets.split(";")
                .map { OneSet.new(it) }
                .fold(OneSet()) { acc, next -> acc.max(next) }
                .product()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/Day02_test")

    check(part1(testInput) == 8)

    val input = readInput("day2/Day02")
    part1(input).println()

    val testInputPart2 = readInput("day2/Day02_test")
    val part2test = part2(testInputPart2)
    check(part2test == 2286)

    part2(input).println()
}


class OneSet(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
) {

    fun isValidSet(maxSet: OneSet) =
        this.red <= maxSet.red &&
                this.green <= maxSet.green &&
                this.blue <= maxSet.blue

    infix fun max(that: OneSet) =
        OneSet(
            this.red.coerceAtLeast(that.red),
            this.green.coerceAtLeast(that.green),
            this.blue.coerceAtLeast(that.blue),
        )

    fun product() =
        red * green * blue

    companion object {
        fun new(str: String): OneSet {
            val red = str.extractAmountOf("red")
            val green = str.extractAmountOf("green")
            val blue = str.extractAmountOf("blue")

            return OneSet(red, green, blue)
        }

        private fun String.extractAmountOf(color: String) =
            Regex("(\\d*) $color").find(this)?.groupValues?.get(1)?.toInt() ?: 0
    }
}
