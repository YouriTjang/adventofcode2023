package day15

import kotlin.streams.toList
import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val steps =
            input.flatMap { line -> line.split(",") }
                .map { entry -> entry.chars().toList() }
        val a = steps.map { it.hashCharacters() }
        return a.sum()
    }

    fun part2(input: List<String>): Int {
        val instructions = input[0].split(",")
        val steps = instructions.map { stepFactory(it) }
        val boxes = mutableMapOf<Int, MutableMap<String, Int>>()

        steps.forEach {
            val box = it.box()
            when (it) {
                is Step.OpStep -> {
                    if (boxes[box] == null) {
                        boxes[box] = mutableMapOf()
                    }
                    val boxContents = boxes[box]!!
                    boxContents[it.label] = it.focalLength
                }

                is Step.SwapStep -> {
                    if (boxes[box] != null && boxes[box]!![it.label] != null) {
                        boxes[box]!!.remove(it.label)
                    }
                }
            }

        }

        val map = boxes.map { (boxNumber, value) ->
            value.asIterable()
                .mapIndexed { slot, (_, focalLength) -> (boxNumber + 1) * (slot + 1) * focalLength }
        }.flatten()
            .sum()
        return map
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day15/test")
    check(part1(testInput) == 1320)

    val input = readInput("day15/data")
    part1(input).println()

    val testInputPart2 = readInput("day15/test")
    val part2test = part2(testInputPart2)
    check(part2test == 145)

    part2(input).println()
}

fun List<Int>.hashCharacters() =
    fold(0) { init, op ->
        ((init + op) * 17).mod(256)
    }


sealed class Step(val input: String) {
    lateinit var label: String

    fun box() = label.chars().toList().hashCharacters()

    data class OpStep(
        val a: String
    ) : Step(a) {
        var focalLength: Int = -1

        init {
            val splits = input.split("=")
            label = splits[0]
            focalLength = splits[1].toInt()
        }
    }

    data class SwapStep(val a: String) : Step(a) {
        init {
            val split = input.split("-")
            label = split[0]
        }
    }
}

fun stepFactory(token: String) = when {
    token.contains("=") -> Step.OpStep(token)
    else -> Step.SwapStep(token)
}