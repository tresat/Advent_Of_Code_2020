package com.tomtresansky.aoc_2020.day_14.mask

import com.tomtresansky.aoc_2020.day_14.cpu.Cpu
import com.tomtresansky.aoc_2020.day_14.cpu.Value
import java.lang.IllegalStateException
import java.lang.Long.toBinaryString
import java.math.BigInteger

typealias BitFlag = Char
fun BitFlag.applyTo(digit: Char) = when(this) {
    '0' -> 0
    '1' -> 1
    'X' -> digit.toString().toInt()
    else -> throw IllegalStateException("Bad value: $this")
}

fun List<Int>.convertToNumber(): Value {
    var result = BigInteger.ZERO
    this.forEachIndexed { index, digit -> result += (BigInteger.valueOf(2).pow(Cpu.BITS - (index + 1)) * BigInteger.valueOf(digit.toLong())) }
    return result
}

fun String.convertToNumber(): Value {
    val binaryList = this.map { it.toString().toInt() }.toCollection(mutableListOf())
    return binaryList.convertToNumber()
}

fun Value.convertToBinary(): String {
    val valString = toBinaryString(this.toLong())
    return valString.padStart(Cpu.BITS, '0')
}

class Bitmask(private val pattern: String) {
    companion object {
        const val PREFIX = "mask"
    }

    fun applyTo(value: Value): Value {
        val inputBinaryValue = value.convertToBinary()

        val resultBinaryValue = mutableListOf<Int>()
        inputBinaryValue.forEachIndexed { index, digit -> resultBinaryValue.add(pattern[index].applyTo(digit)) }

        return resultBinaryValue.convertToNumber()
    }

    override fun toString() = pattern
}