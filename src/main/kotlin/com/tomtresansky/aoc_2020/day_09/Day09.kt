package com.tomtresansky.aoc_2020.day_09

import com.tomtresansky.aoc_2020.day_09.cipher.Cipher
import java.io.File

class Day09 {
    companion object {
        const val INPUT_FILE_NAME = "day_09_input.txt"

        val cipher = readCipherFromFile(INPUT_FILE_NAME)

        private fun readCipherFromFile(inputFileName: String): Cipher {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return Cipher(inputFile.readLines().map { it.toLong() })
        }
    }

    fun solvePart1() = cipher.firstInvalidEntry()!!.element

    fun solvePart2(): Long {
        val invalidEntry = cipher.firstInvalidEntry()!!
        val priorToInvalid = cipher.data.subList(0, invalidEntry.index)
        val summingEntries = findContiguousSublistSummingTo(priorToInvalid, invalidEntry.element)!!
        return summingEntries.minOrNull()!! + summingEntries.maxOrNull()!!
    }

    private fun findContiguousSublistSummingTo(priorToInvalid: List<Long>, element: Long): List<Long>? {
        for (windowSize in 1 until priorToInvalid.size) {
            val contiguousSublist = priorToInvalid.windowed(windowSize).find { it.sum() == element }
            if (null != contiguousSublist) {
                return contiguousSublist
            }
        }
        return null
    }
}