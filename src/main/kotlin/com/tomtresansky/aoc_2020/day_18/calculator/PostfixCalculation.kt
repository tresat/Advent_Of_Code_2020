package com.tomtresansky.aoc_2020.day_18.calculator

class PostfixCalculation(input: List<Token>) {
    private val data = ArrayList(input)

    fun evaluate(): Long {
        var result = 0L
        val remaining = data.toMutableList()
        while (remaining.size > 1) {
            val operandIndex = remaining.indexOfFirst { it is Token.Operator }
            val lhsIndex = operandIndex - 2
            val rhsIndex = operandIndex - 1
            result = (remaining[operandIndex] as Token.Operator).operate(remaining[lhsIndex] as Token.Number, remaining[rhsIndex] as Token.Number)
            remaining.removeAt(operandIndex)
            remaining.removeAt(rhsIndex)
            remaining[lhsIndex] = Token.Number(result)
        }
        return result
    }

    override fun toString() = data.joinToString(" ") { it.toString() }
}
