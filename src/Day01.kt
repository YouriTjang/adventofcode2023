fun main() {
    fun part1(input: List<String>): Int {
        val firstAndLastDigit = input
            .map { line ->
                val numbers = line
                    .split("")
                    .filter { it.matches(Regex("\\d")) }
                val calibrationValue = (numbers.first() + numbers.last()).toInt()
                println(calibrationValue.toString() + "\t" + line)
                calibrationValue
            }

        return firstAndLastDigit.sum()
    }

    fun part2(input: List<String>): Int {
        val digitized = input.map { line ->
            var restOfLine = line
            var result = ""
            while (restOfLine.isNotEmpty()) {
                replace@ for(it in replacements) {
                    if (restOfLine.matches(Regex("^" + it.regex + ".*"))) {
                        result += it.replacement
                        restOfLine = restOfLine.substring(it.move)
                        break@replace
                    }
                }
            }
            result
        }

        return part1(digitized)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    val testInputPart2 = readInput("Day01-part2_test")
    val part2test = part2(testInputPart2)
    check(part2test == 281)

    part2(input).println()
}

data class Replacer(
    val regex: String,
    val replacement: String,
    val move: Int = 1,
)

val replacements = listOf(
    Replacer("one", "1"),
    Replacer("two", "2"),
    Replacer("three", "3"),
    Replacer("four", "4"),
    Replacer("five", "5"),
    Replacer("six", "6"),
    Replacer("seven", "7"),
    Replacer("eight", "8"),
    Replacer("nine", "9"),
    Replacer("1", "1"),
    Replacer("2", "2"),
    Replacer("3", "3"),
    Replacer("4", "4"),
    Replacer("5", "5"),
    Replacer("6", "6"),
    Replacer("7", "7"),
    Replacer("8", "8"),
    Replacer("9", "9"),
    Replacer("0", "0"),
    Replacer("\\w", ".", 1),
)