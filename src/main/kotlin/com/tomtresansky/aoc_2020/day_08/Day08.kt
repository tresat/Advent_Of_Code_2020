package com.tomtresansky.aoc_2020.day_08

import com.tomtresansky.aoc_2020.day_08.computer.Computer
import com.tomtresansky.aoc_2020.day_08.computer.Debugger
import com.tomtresansky.aoc_2020.day_08.computer.Instruction
import com.tomtresansky.aoc_2020.day_08.computer.Program
import java.io.File

class Day08 {
    companion object {
            const val INPUT_FILE_NAME = "day_08_input.txt"

            val program = readProgramFromFile(INPUT_FILE_NAME)

            private fun readProgramFromFile(inputFileName: String): Program {
                val inputFile = File(this::class.java.getResource(inputFileName).toURI())
                return inputFile.readLines().mapIndexed { lineNumber, data -> Instruction.deserialize(lineNumber, data) }
            }
    }

    fun solvePart1(): Int {
        val computer = Computer()
        return computer.execute(program).finalValue()
    }

    fun solvePart2(): Int {
        val computer = Computer()
        val debugger = Debugger()
        val debuggedProgram = debugger.debug(program, computer)!!
        computer.reset()
        return computer.execute(debuggedProgram).finalValue()
    }
}
