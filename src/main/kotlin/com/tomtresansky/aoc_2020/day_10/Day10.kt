package com.tomtresansky.aoc_2020.day_10

import com.tomtresansky.aoc_2020.day_10.adapter.Adapter
import com.tomtresansky.aoc_2020.day_10.adapter.Stacker
import com.tomtresansky.aoc_2020.day_10.adapter.findDifferences
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day10: IDay<Adapter> {
    companion object {
        private const val BUILT_IN_ADAPTER_DIFFERENCE = 3
    }

    override fun getDayNumber() = 10
    override fun loadSingleInputElement(rawData: String) = Adapter(rawData.toInt())

    override fun solvePart1(): Int {
        val adapters = with(loadInput()) { this + Adapter(0) + Adapter(this.maxOf { it.rating } + BUILT_IN_ADAPTER_DIFFERENCE) }
        val stack = Stacker(adapters).stack()
        val volt1differences = stack.findDifferences(1)
        val volt3differences = stack.findDifferences(3)
        return volt1differences.size * volt3differences.size
    }

    override fun solvePart2() = 2
}
