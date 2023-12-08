package day7

import println
import readInput

class Hand(private val cards: List<String>) : Comparable<Hand> {
    constructor(str: String) :
            this(str.split("").filter { it.isNotEmpty() })

    override fun compareTo(other: Hand): Int {
        val thisType = this.toType()
        val otherType = other.toType()

        return if (thisType > otherType) {
            -1
        } else if (thisType < otherType) {
            1
        } else {
            compareEqualType(other)
        }
    }

    fun toType(): Type =
        when {
            isFiveOfAKind() -> Type.FIVE_OF_A_KIND
            isFourOfAKind() -> Type.FOUR_OF_A_KIND
            isFullHouse() -> Type.FULL_HOUSE
            isThreeOfAKind() -> Type.THREE_OF_A_KIND
            isTwoPair() -> Type.TWO_PAIR
            isOnePair() -> Type.ONE_PAIR
            else -> Type.HIGH_CARD
        }

    private fun isFiveOfAKind() = cards.toSet().size == 1

    private fun isFourOfAKind(): Boolean {
        if (cards.toSet().size != 2) {
            return false
        }

        val list = cards.groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        return list[0].second == 4 && list[1].second == 1
    }

    private fun isFullHouse(): Boolean {
        if (cards.toSet().size != 2) {
            return false
        }

        val list = cards.groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        return list[0].second == 3 && list[1].second == 2
    }

    private fun isThreeOfAKind(): Boolean {
        val list = cards.groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        return list[0].second == 3 && list[1].second == 1
    }


    private fun isTwoPair(): Boolean {
        if (cards.toSet().size != 3) {
            return false
        }

        val list = cards.groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
        return list[0].second == 2 && list[1].second == 2
    }

    private fun isOnePair(): Boolean =
        cards.toSet().size == 4

    fun compareEqualType(other: Hand, index: Int = 0): Int {
        if (index == 5) {
            return 0
        }

        return if (this.cards[index] == other.cards[index]) {
            compareEqualType(other, index + 1)
        } else if (cardOrder[this.cards[index]]!! > cardOrder[other.cards[index]]!!) {
            1
        } else {
            -1
        }
    }

    override fun toString(): String {
        return cards.joinToString(separator = "") { it }
    }

    companion object {
        val cardOrder: Map<String, Int> = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")
            .mapIndexed { index, number -> number to index }
            .toMap()
    }

    enum class Type {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }
}

fun main() {
    fun part1(input: List<String>): Int =
        input.map { line ->
            val (a, b) = line.split(" ").filter { it.isNotEmpty() }
            Hand(a) to b.toInt()
        }
            .sortedByDescending { it.first }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum()


    fun part2(input: List<String>): Int {
        TODO()
    }

//    Hand("KTJJT").compareTo(Hand("KK677"))

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/test")
    check(part1(testInput) == 6440)

    val input = readInput("day7/data")
    part1(input).println()

    val testInputPart2 = readInput("day7/test")
    val part2test = part2(testInputPart2)
    check(part2test == 5905)
//
//    part2(input).println()
}
