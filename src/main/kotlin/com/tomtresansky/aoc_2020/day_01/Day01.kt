package com.tomtresansky.aoc_2020.day_01

import java.io.File

@Suppress("SameParameterValue")
class Day01 {
    companion object {
        const val INPUT_FILE_NAME = "day_01_input.txt"
        const val TARGET_SUM = 2020
    }

    fun solve(): Number {
        val inputData = readExpensesFromFile(INPUT_FILE_NAME)
        val pair = findPairThatSumsTo(inputData, TARGET_SUM)
        return pair.first * pair.second
    }

    private fun readExpensesFromFile(inputFileName: String): List<Int> {
        val inputFile = File(Day01::class.java.getResource(inputFileName).toURI())
        return inputFile.readLines().map { it.toInt() }.toList()}

    private fun findPairThatSumsTo(data: List<Int>, targetSum: Int): Pair<Int, Int> {
        return data.flatMap { first -> data.map { second -> first to second } }
                   .find { it.first + it.second == targetSum }!!
    }
}