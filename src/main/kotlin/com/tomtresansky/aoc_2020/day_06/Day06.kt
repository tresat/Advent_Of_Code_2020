package com.tomtresansky.aoc_2020.day_06

import java.io.File

@Suppress("SameParameterValue")
class Day06 {
    companion object {
        const val INPUT_FILE_NAME = "day_06_input.txt"
        val specs = readSpecsFromFile(INPUT_FILE_NAME)

        private fun readSpecsFromFile(inputFileName: String): List<String> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return inputFile.readLines()
        }
    }

    fun solvePart1(): Int {
        return 1
    }

    fun solvePart2(): Int {
        return 2
    }
}
