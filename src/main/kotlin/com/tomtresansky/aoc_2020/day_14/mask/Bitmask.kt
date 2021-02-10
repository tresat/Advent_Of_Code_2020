package com.tomtresansky.aoc_2020.day_14.mask

import com.tomtresansky.aoc_2020.day_14.cpu.AbstractCpu
import com.tomtresansky.aoc_2020.day_14.cpu.Address
import com.tomtresansky.aoc_2020.day_14.cpu.Value
import com.tomtresansky.aoc_2020.util.Odometer
import java.lang.IllegalStateException
import java.lang.Long.toBinaryString
import java.lang.StringBuilder
import java.math.BigInteger

typealias BitFlag = Char
fun BitFlag.applyTo(digit: Char) = when(this) {
    '0' -> 0
    '1' -> 1
    'X' -> digit.toString().toInt()
    else -> throw IllegalStateException("Bad value: $this")
}
fun BitFlag.applyDecodingTo(digit: Char) = when(this) {
    '0' -> digit
    '1' -> '1'
    'X' -> 'X'
    else -> throw IllegalStateException("Bad value: $this")
}

fun List<Int>.convertToNumber(): Value {
    var result = BigInteger.ZERO
    this.forEachIndexed { index, digit -> result += (BigInteger.valueOf(2).pow(AbstractCpu.BITS - (index + 1)) * BigInteger.valueOf(digit.toLong())) }
    return result
}

fun String.convertToNumber(): Value {
    val binaryList = this.map { it.toString().toInt() }.toCollection(mutableListOf())
    return binaryList.convertToNumber()
}

fun Value.convertToBinary(): String {
    val valString = toBinaryString(this.toLong())
    return valString.padStart(AbstractCpu.BITS, '0')
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

    fun applyDecodingTo(value: Value): Bitmask {
        val inputBinaryValue = value.convertToBinary()

        val resultBinaryValue = mutableListOf<Char>()
        inputBinaryValue.forEachIndexed { index, digit -> resultBinaryValue.add(pattern[index].applyDecodingTo(digit)) }

        val sb = StringBuilder()
        resultBinaryValue.forEach(sb::append)
        return Bitmask(sb.toString())
    }

    fun float(): List<Address> {
        val indiciesToModify = mutableListOf<Int>()
        this.pattern.toCharArray().forEachIndexed { index, c -> if (c == 'X') indiciesToModify.add(index) }

        val results = mutableListOf<String>()
        val odo = Odometer(indiciesToModify.size, 1)

        while (odo.canIncrement()) {
            results.add(apppyFloat(this.pattern.toCharArray(), odo.getValue()))

            odo.increment()
        }
        results.add(apppyFloat(this.pattern.toCharArray(), odo.getValue()))

        return results.map { it.convertToNumber() }
    }

    private fun apppyFloat(toCharArray: CharArray, value: Array<Int>): String {
        val result = toCharArray.clone()

        for (v in value) {
            val index = result.indexOfFirst { it == 'X' }
            result[index] = v.toString().toCharArray()[0]
        }

        val sb = StringBuilder()
        result.forEach { sb.append(it) }
        return sb.toString()
    }

    override fun toString() = pattern

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bitmask

        if (pattern != other.pattern) return false

        return true
    }
}
