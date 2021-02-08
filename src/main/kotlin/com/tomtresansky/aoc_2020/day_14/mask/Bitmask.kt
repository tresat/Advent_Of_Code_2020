package com.tomtresansky.aoc_2020.day_14.mask

import com.tomtresansky.aoc_2020.day_14.cpu.Cpu
import com.tomtresansky.aoc_2020.day_14.cpu.Value
import com.tomtresansky.aoc_2020.util.pow
import java.lang.IllegalStateException

typealias BitFlag = Char
fun BitFlag.applyTo(digit: Char) = when(this) {
    '0' -> 0
    '1' -> 1
    'X' -> digit.toString().toInt()
    else -> throw IllegalStateException("Bad value: $this")
}

class Bitmask(private val pattern: String) {
    companion object {
        const val PREFIX = "mask"
    }

    fun applyTo(value: Value): Value {
        val inputBinaryValue = convertToBinary(value)

        val resultBinaryValue = mutableListOf<Int>()
        inputBinaryValue.forEachIndexed { index, digit -> resultBinaryValue.add(pattern[index].applyTo(digit)) }

        return convertToNumber(resultBinaryValue)
    }

    private fun convertToNumber(inputBinaryValue: List<Int>) =
        inputBinaryValue.foldRightIndexed(0L) { index: Int, digit: Int, acc: Long -> (2.pow(Cpu.BITS - (index + 1)) * digit) + acc }

    private fun convertToBinary(value: Value): String {
        assert(value < Int.MAX_VALUE)
        val valString = Integer.toBinaryString(value.toInt())
        return valString.padStart(Cpu.Companion.BITS, '0')
    }

    override fun toString() = pattern.toString()
}