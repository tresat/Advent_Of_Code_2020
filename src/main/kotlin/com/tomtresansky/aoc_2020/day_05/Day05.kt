package com.tomtresansky.aoc_2020.day_05

import com.tomtresansky.aoc_2020.day_05.bsp.BinarySeatPartitioner
import com.tomtresansky.aoc_2020.day_05.bsp.BinarySpec
import java.io.File

@Suppress("SameParameterValue")
class Day05 {
    companion object {
        private const val NUM_ROWS = 128
        private const val NUM_SEATS = 8

        const val INPUT_FILE_NAME = "day_05_input.txt"
        val specs = readSpecsFromFile(INPUT_FILE_NAME)

        private fun readSpecsFromFile(inputFileName: String): List<BinarySpec> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return inputFile.readLines()
        }
    }

    fun solvePart1(): Int {
        val bsp = BinarySeatPartitioner(NUM_ROWS, NUM_SEATS)
        val seats = specs.map { bsp.findSeat(it)}
        return seats.maxOf { it.getId() }
    }

    fun solvePart2(): Int {
        val bsp = BinarySeatPartitioner(NUM_ROWS, NUM_SEATS)
        val seats = specs.map { bsp.findSeat(it) }
        val eligibleSeats = seats.filter { !(it.inFirstRow() || it.inLastRow(NUM_ROWS) )}
        val eligibleSeatsById = eligibleSeats.groupBy { it.getId() }
        val sortedIds = eligibleSeatsById.keys.sorted()
        val idGap = sortedIds.windowed(2, 1).find { it[0] + 1 == it[1] - 1 }
        return idGap?.get(0)!! + 1
    }
}
