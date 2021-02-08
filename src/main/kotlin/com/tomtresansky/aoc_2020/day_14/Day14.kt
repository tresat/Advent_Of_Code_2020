package com.tomtresansky.aoc_2020.day_14

import com.tomtresansky.aoc_2020.day_14.cpu.Cpu
import com.tomtresansky.aoc_2020.day_14.cpu.ICommand
import com.tomtresansky.aoc_2020.day_14.cpu.SetBitmaskCommand
import com.tomtresansky.aoc_2020.day_14.cpu.SetMemoryCommand
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
        val commands = loadInput()
        val cpu = Cpu()
        commands.forEach { cpu.execute(it) }
        return cpu.sumMemory().toLong() // 2202748520763 is too low?
    }

    override fun solvePart2(): Long {
        TODO()
    }
}