package com.tomtresansky.aoc_2020.day_08.computer

class Debugger {
    fun debug(program: Program, computer: Computer): Program? {
        var currentCandidate: Program
        var nextModCheckStartLineNum = 0
        do {
            computer.reset()
            currentCandidate = program.toMutableList()
            val instructionToModify = nextInstructionWithType(currentCandidate, nextModCheckStartLineNum++, OpCode.jmp, OpCode.nop)
            if (instructionToModify == null) {
                return null
            } else {
                currentCandidate = modifyProgram(program, instructionToModify)
            }
        } while (!computer.execute(currentCandidate).completed())
        return currentCandidate
    }

    private fun nextInstructionWithType(program: Program, modificationCheckStartIdx: Int, vararg opcodes: OpCode): Instruction? {
        for (lineNumber in modificationCheckStartIdx..program.size) {
            if (program[lineNumber].code in opcodes) {
                return program[lineNumber]
            }
        }
        return null
    }

    private fun modifyProgram(program: Program, instructionToModify: Instruction): Program {
        val result = program.toMutableList()
        result[instructionToModify.lineNumber] = modifyInstruction(instructionToModify)
        return result
    }

    private fun modifyInstruction(instruction: Instruction) =
        when (instruction.code) {
            OpCode.jmp -> Instruction(instruction.lineNumber, OpCode.nop, instruction.operand)
            OpCode.nop -> Instruction(instruction.lineNumber, OpCode.jmp, instruction.operand)
            else -> throw IllegalModificationException(instruction)
        }

    class IllegalModificationException(instruction: Instruction): RuntimeException("Can't modify: $instruction")
}
