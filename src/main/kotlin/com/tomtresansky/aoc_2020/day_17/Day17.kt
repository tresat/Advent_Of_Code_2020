package com.tomtresansky.aoc_2020.day_17

import com.tomtresansky.aoc_2020.day_16.ticket.FieldRule
import com.tomtresansky.aoc_2020.day_16.ticket.Rulebook
import com.tomtresansky.aoc_2020.day_16.ticket.Ticket
import com.tomtresansky.aoc_2020.day_17.game.EnergyGrid
import com.tomtresansky.aoc_2020.day_17.game.Slice
import java.io.File

class Day17 {
    companion object {
        const val INPUT_FILE_NAME = "day_17_input.txt"

        private fun readSliceFromFile(inputFileName: String): Slice {
            val inputFile = File(this::class.java.getResource(inputFileName)!!.toURI())
            return Slice(inputFile.readLines())
        }
    }

    fun solvePart1(): Long {
        val slice = readSliceFromFile(INPUT_FILE_NAME)
        val grid = EnergyGrid(slice)

        grid.print()

        TODO()
    }
}
