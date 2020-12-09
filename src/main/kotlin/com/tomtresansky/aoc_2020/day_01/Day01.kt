package com.tomtresansky.aoc_2020.day_01

import com.tomtresansky.aoc_2020.day_01.util.Odometer
import java.io.File

private fun List<Int>.getAll(indices: Array<Int>): List<Int> = indices.map { i -> this[i] }

@Suppress("SameParameterValue")
class Day01 {
    companion object {
        const val INPUT_FILE_NAME = "day_01_input.txt"
        const val TARGET_SUM = 2020
    }

    fun solve(numEntries: Int): Int {
        val inputData = readExpensesFromFile(INPUT_FILE_NAME)
        val summingEntriesTuple = findTupleThatSumsTo(inputData, TARGET_SUM, numEntries)
        return summingEntriesTuple.fold(1) { curr, acc -> curr * acc }
    }

    private fun readExpensesFromFile(inputFileName: String): List<Int> {
        val inputFile = File(this::class.java.getResource(inputFileName).toURI())
        return inputFile.readLines().map { it.toInt() }.toList()
    }

    private fun findTupleThatSumsTo(entriesData: List<Int>, targetSum: Int, numEntries: Int): List<Int> {
        val odo = Odometer(numEntries, entriesData.size - 1)

        do {
            val currSum = entriesData.getAll(odo.getValue()).fold(0) { curr, acc -> curr + acc }
            if (currSum == targetSum) {
                return entriesData.getAll(odo.getValue())
            } else {
                odo.increment()
            }
        } while (odo.canIncrement())

        throw NoSummingTupleFoundException()
    }

    class NoSummingTupleFoundException : RuntimeException()
}