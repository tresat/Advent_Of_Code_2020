package com.tomtresansky.aoc_2020.day_18

import com.tomtresansky.aoc_2020.day_18.calculator.*
import com.tomtresansky.aoc_2020.day_18.calculator.Shunter
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day18 : IDay<String> {
    companion object {
        const val INPUT_FILE_NAME = "day_18_input.txt"
    }

    override fun getDayNumber() = 18
    override fun loadSingleInputElement(rawData: String) = rawData

    override fun solvePart1(): Long {
        val shunter = Shunter(SamePrecedence)
        val result = loadInput().sumOf { shunter.shunt(it).evaluate() }
        assert(result == 31142189909908)
        return result
    }
    override fun solvePart2(): Long {
        val shunter = Shunter(AdditionFirst)
        val result = loadInput().sumOf { shunter.shunt(it).evaluate() }
        assert(result == 323912478287549)
        return result
    }
}
