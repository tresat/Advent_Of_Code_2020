package com.tomtresansky.aoc_2020.day_08.computer

enum class OpCode { nop, acc, jmp }

data class Instruction(val lineNumber: Int, val code: OpCode, val operand: Int) {
    companion object {
        private val INSTRUCTION_REGEX = """(acc|nop|jmp)\s([-+])(\d+)""".toRegex()
        private const val OPCODE_INDEX = 1
        private const val OPERAND_SIGN_INDEX = 2
        private const val OPERAND_VALUE_INDEX = 3

        fun deserialize(lineNumber: Int, data: String): Instruction {
            val matchResult = INSTRUCTION_REGEX.matchEntire(data)
            with (matchResult?.groupValues!!) {
                val opCode = OpCode.valueOf(get(OPCODE_INDEX))
                val positive: Boolean = (get(OPERAND_SIGN_INDEX) == "+")
                val operandRaw = get(OPERAND_VALUE_INDEX).toInt()

                return Instruction(lineNumber, opCode, (if (positive) operandRaw else -operandRaw))
            }
        }
    }
}
typealias Program = List<Instruction>

interface Result {
    fun completed(): Boolean
    fun finalValue(): Int
}
class NormalExit(private val accumulator: Int): Result {
    override fun completed() = true
    override fun finalValue() = accumulator
}
class AbortedDueToInfiniteLoop(private val accumulator: Int, @Suppress("unused") val duplicateInstruction: Instruction): Result {
    override fun completed() = false
    override fun finalValue() = accumulator
}

class Computer {
    private var accumulator = 0

    fun execute(program: Program): Result {
        val executed = mutableSetOf<Instruction>()
        var instructionPointer = 0

        while(instructionPointer < program.size) {
            val nextInstruction = program[instructionPointer]
            if (executed.contains(nextInstruction)) {
                return AbortedDueToInfiniteLoop(accumulator, nextInstruction)
            } else {
                instructionPointer = executeInstruction(nextInstruction)
                executed.add(nextInstruction)
            }
        }

        return NormalExit(accumulator)
    }

    private fun executeInstruction(instruction: Instruction): Int {
        return when (instruction.code) {
            OpCode.acc -> {
                accumulator += instruction.operand
                instruction.lineNumber + 1
            }
            OpCode.jmp -> instruction.lineNumber + instruction.operand
            OpCode.nop -> instruction.lineNumber + 1
        }
    }

    fun reset() {
        accumulator = 0
    }
}
