package com.tomtresansky.aoc_2020.day_10

import com.tomtresansky.aoc_2020.day_10.adapter.Adapter
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day10: IDay<Adapter> {
    override fun getDayNumber() = 10
    override fun loadSingleInputElement(rawData: String) = Adapter(rawData.toInt())

    override fun solvePart1(): Int {
        val adapters = loadInput()

        TODO()
    }

    override fun solvePart2() = TODO()
}
