package com.tomtresansky.aoc_2020.day_05.bsp

import com.google.common.math.IntMath.log2
import java.math.RoundingMode

data class SeatNumber(val rowNum: Int, val seatNum: Int) {
    fun getId() = rowNum * 8 + seatNum
}

typealias BinarySpec = String
typealias NormalizedBinarySpec = String

class BinarySeatPartitioner(private val numRows: Int, private val numSeats: Int) {
    companion object {
        private const val UPPER = 'U'
        private const val LOWER = 'L'
    }

    private val numRowSteps = log2(numRows, RoundingMode.UNNECESSARY)

    fun findSeat(spec: BinarySpec): SeatNumber {
        val rowSpec = extractRowSpec(spec)
        val seatSpec = extractSeatSpec(spec)
        return SeatNumber(binSearch(rowSpec, numRows), binSearch(seatSpec, numSeats))
    }

    private fun extractRowSpec(spec: BinarySpec) = String(spec.substring(0, numRowSteps).map { normalizeSpecChar(it) }.toCharArray())
    private fun extractSeatSpec(spec: BinarySpec) = String(spec.substring(numRowSteps).map { normalizeSpecChar(it) }.toCharArray())

    private fun normalizeSpecChar(char: Char) =
        when(char) {
            'F', 'L' -> LOWER
            'B', 'R' -> UPPER
            else -> throw IllegalStateException()
        }

    private fun binSearch(spec: NormalizedBinarySpec, total: Int): Int = spec.fold(0 to total) { curr, acc -> searchStep(acc, curr) }.first

    private fun searchStep(spec: Char, curr: Pair<Int, Int>) =
        when (spec) {
            UPPER -> (curr.first + ((curr.second - curr.first) / 2) to curr.second)
            LOWER -> (curr.first to curr.first + ((curr.second - curr.first) / 2))
            else -> throw IllegalStateException()
        }
}
