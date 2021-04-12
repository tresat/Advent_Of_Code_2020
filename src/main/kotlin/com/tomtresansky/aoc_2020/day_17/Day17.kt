package com.tomtresansky.aoc_2020.day_17

import com.tomtresansky.aoc_2020.day_17.game.EnergyGrid
import com.tomtresansky.aoc_2020.day_17.game.Slice
import java.io.File

class Day17 {
    companion object {
        const val INPUT_FILE_NAME = "day_17_sample.txt"

        private fun readSliceFromFile(inputFileName: String): Slice {
            val inputFile = File(this::class.java.getResource(inputFileName)!!.toURI())
            return Slice(inputFile.readLines())
        }
    }

    fun solvePart1(): Long {
        val slice = readSliceFromFile(INPUT_FILE_NAME)
        val grid = EnergyGrid(slice)

        println("\nBefore any cycles:\n")
        print(grid)

        for (cycle in 1..3) {
            grid.step()

            println("After $cycle cycle${ if (cycle == 1) "" else "s" }:\n")
            print(grid)
        }

        return grid.numActiveCubes()
    }
}
