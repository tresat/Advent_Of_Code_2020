package com.tomtresansky.aoc_2020.day_05

import com.tomtresansky.aoc_2020.day_05.bsp.BinarySeatPartitioner
import com.tomtresansky.aoc_2020.day_05.bsp.BinarySpec
import java.io.File

@Suppress("SameParameterValue")
class Day05 {
    companion object {
        const val INPUT_FILE_NAME = "day_05_input.txt"
        val specs = readSpecsFromFile(INPUT_FILE_NAME)

        private fun readSpecsFromFile(inputFileName: String): List<BinarySpec> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return inputFile.readLines()
        }
    }

    fun solvePart1(): Int {
        val bsp = BinarySeatPartitioner(128, 8)
        val seats = specs.map { bsp.findSeat(it)}
        return seats.maxOf { it.getId() }
    }

    fun solvePart2(): Int {
        return 2
    }
}