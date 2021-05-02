package com.tomtresansky.aoc_2020.day_18

import com.tomtresansky.aoc_2020.day_18.calculator.PostfixCalculation
import com.tomtresansky.aoc_2020.day_18.calculator.Shunter
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day18 : IDay<PostfixCalculation> {
    companion object {
        const val INPUT_FILE_NAME = "day_18_input.txt"
    }

    private val shunter = Shunter()

    override fun getDayNumber() = 18
    override fun loadSingleInputElement(rawData: String) = shunter.shunt(rawData)

    override fun solvePart1() = loadInput().sumOf { it.evaluate() }.toLong()
    override fun solvePart2() = loadInput().sumOf { it.evaluate() }.toLong()
}