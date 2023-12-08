package day5

import println
import readInput

fun main() {
    data class Mapping(
        val inStart: Long,
        val inSize: Long,
        val outStart: Long,
    ) {
        fun inRange() = inStart until (inStart + inSize)
        fun output(x: Long) = outStart + (x - inStart)
    }

    fun parse(input: List<String>): Pair<List<Long>, MutableList<List<Mapping>>> {
        val seeds = input[0].split(":")[1].split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

        val allMappings = mutableListOf<List<Mapping>>()
        var mappings = mutableListOf<Mapping>()
        for (line in input.drop(3)) {
            if (line.isNotBlank()) {
                if (line.matches(Regex("^[a-z].*"))) {
                    allMappings.add(mappings.toList()) //copy
                    mappings.clear()
                } else {
                    val numbers = line.split(" ").map { it.toLong() }
                    val mapping = Mapping(numbers[1], numbers[2], numbers[0])

                    mappings.add(mapping)
                }
            }
        }
        allMappings.add(mappings)
        return seeds to allMappings
    }

    fun part1(input: List<String>): Long {
        val (seeds, maps) = parse(input)
        val outputs = mutableListOf<Long>()
        for (seed in seeds) {
            var input = seed
            for(map in maps) {
                val inMapping = map.filter { input in it.inRange() }
                input = if (inMapping.isEmpty()) input else inMapping.first().output(input)
            }
            outputs.add(input)
        }
        return outputs.min()
    }

    fun part2(input: List<String>): Int {
        TODO()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/test")
    check(part1(testInput) == 35L)

    val input = readInput("day5/data")
    part1(input).println()

//    val testInputPart2 = readInput("day5/test")
//    val part2test = part2(testInputPart2)
//    check(part2test == 2286)
//
//    part2(input).println()
}
