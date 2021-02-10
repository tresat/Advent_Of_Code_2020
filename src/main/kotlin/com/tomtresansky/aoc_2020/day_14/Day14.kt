package com.tomtresansky.aoc_2020.day_14

import com.tomtresansky.aoc_2020.day_14.cpu.*
import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import com.tomtresansky.aoc_2020.util.problem.IDay
import java.lang.IllegalStateException

class Day14: IDay<ICommand> {
    companion object {
        const val INPUT_FILE_NAME = "day_14_input.txt"
        const val MASK_IDX = 2
    }

    override fun getDayNumber() = 14

    override fun loadSingleInputElement(rawData: String): ICommand =
        when {
            rawData.startsWith(Bitmask.PREFIX) -> {
                SetBitmaskCommand.deserialize(rawData.split(" ")[MASK_IDX])
            }
            rawData.startsWith(SetMemoryCommand.PREFIX) -> {
                SetMemoryCommand.deserialize(rawData)
            }
            else -> {
                throw IllegalStateException("Unknown pattern: $rawData")
            }
        }

    override fun solvePart1(): Long {
        val cpu = BasicCpu()
        loadInput().forEach { cpu.execute(it) }
        val result = cpu.sumMemory().toLong()
        assert(result == 9628746976360L)
        return result
    }

    override fun solvePart2(): Long {
        val cpu = MemAddressDecoderCpu()
        loadInput().forEach { cpu.execute(it) }
        val result = cpu.sumMemory().toLong()
        assert(result == 4574598714592L)
        return result
    }
}
