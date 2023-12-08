package day8

import println
import readInput



fun main() {
    fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

    fun part1(input: List<String>): Int {
        val directions = input[0].split("").filter { it.isNotEmpty() }
        val goLeft = { dir: Pair<String, String> -> dir.first }
        val goRight = { dir: Pair<String, String> -> dir.second }
        val rex = Regex("^(\\w+) = \\((\\w+), (\\w+)\\)")

        val map = input
            .drop(2)
            .associate { line ->
                val groups = rex.findAll(line).toList()
                groups[0].groups[1]!!.value to (groups[0].groups[2]!!.value to groups[0].groups[3]!!.value)
            }

        var nextDir = "FDA"
        var count = 0
        for(dir in directions.asSequence().repeat()){
            nextDir = when(dir) {
                "L" -> goLeft.invoke(map[nextDir]!!)
                else -> goRight.invoke(map[nextDir]!!)
            }
            count++

//            if(nextDir == "ZZZ") {
            if(nextDir.endsWith("Z")) {
                break
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val directions = input[0].split("").filter { it.isNotEmpty() }
        val goLeft = { dir: Pair<String, String> -> dir.first }
        val goRight = { dir: Pair<String, String> -> dir.second }
        val rex = Regex("^(\\w+) = \\((\\w+), (\\w+)\\)")

        val map = input
            .drop(2)
            .associate { line ->
                val groups = rex.findAll(line).toList()
                groups[0].groups[1]!!.value to (groups[0].groups[2]!!.value to groups[0].groups[3]!!.value)
            }

        var nextDirs = map.keys.filter { it.endsWith("A") }.toSet()
        var count = 0
        for(dir in directions.asSequence().repeat()){
            nextDirs = nextDirs.map {
                when (dir) {
                    "L" -> goLeft.invoke(map[it]!!)
                    else -> goRight.invoke(map[it]!!)
                }
            }.toSet()
            count++

            if(nextDirs.any { it.endsWith("Z") }) {
                println(count)
                nextDirs = nextDirs.filter { !it.endsWith("Z") }.toSet()
            }

            if(nextDirs.isEmpty()) {
                break
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("day8/test")
//    check(part1(testInput) == 2)
//
//    val testInput2 = readInput("day8/test2")
//    check(part1(testInput2) == 6)
//
    val input = readInput("day8/data")
//    part1(input).println()

//    val testInputPart2 = readInput("day8/test3")
//    val part2test = part2(testInputPart2)
//    check(part2test == 6)

    part2(input).println()
}
