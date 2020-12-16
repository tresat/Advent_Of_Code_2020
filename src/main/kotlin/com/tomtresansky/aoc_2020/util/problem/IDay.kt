package com.tomtresansky.aoc_2020.util.problem

import java.io.File

interface IDay<out T> {
    fun getDayNumber(): Int
    fun getInputFileName() = "day_${getDayNumber()}_input.txt" //"day_10_input_sample.txt"
    fun loadSingleInputElement(rawData: String): T

    fun loadInput() = loadInput(getInputFileName())
    fun loadInput(inputFileName: String): List<T> {
        val inputFile = File(this::class.java.getResource(inputFileName).toURI())
        return inputFile.readLines().map { loadSingleInputElement(it) }
    }

    fun solvePart1(): Long
    fun solvePart2(): Long
}
