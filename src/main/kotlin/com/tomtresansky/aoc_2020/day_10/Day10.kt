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
    override fun loadSingleInputElement(rawData: String) = Adapter.get(rawData.toInt())

    override fun solvePart1(): Long {
        val adapters = with(loadInput()) { this + Adapter.get(0) + Adapter.get(this.maxOf { it.rating } + BUILT_IN_ADAPTER_DIFFERENCE) }
        val stack = Stacker(adapters).inclusiveStack()
        val volt1differences = stack.findDifferences(1)
        val volt3differences = stack.findDifferences(3)
        return (volt1differences.size * volt3differences.size).toLong()
    }

    override fun solvePart2(): Long {
        val adapters = with(loadInput()) { this + Adapter.get(0) + Adapter.get(this.maxOf { it.rating } + BUILT_IN_ADAPTER_DIFFERENCE) }
        val stacker = Stacker(adapters)
        println("In Order: ${stacker.inclusiveStack().joinToString(" -> ") }}")
        return stacker.countExclusiveStacks()
    }
}
// 1939005440 is too low