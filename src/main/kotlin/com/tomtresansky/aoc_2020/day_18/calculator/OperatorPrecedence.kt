package com.tomtresansky.aoc_2020.day_18.calculator


sealed class OperatorPrecedence: Comparator<Token> {
    companion object {
        const val LESSER = -1
        const val EQUAL = 0
        const val GREATER = 1
    }
}

object SamePrecedence: OperatorPrecedence() {
    override fun compare(o1: Token?, o2: Token?) = EQUAL
}

object AdditionFirst: OperatorPrecedence() {
    override fun compare(o1: Token?, o2: Token?): Int {
        return if (o1 is Token.Addition && o2 is Token.Multiplication) {
            GREATER
        } else if (o1 is Token.Multiplication && o2 is Token.Addition) {
            LESSER
        } else {
            EQUAL
        }
    }
}
