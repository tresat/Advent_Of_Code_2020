package com.tomtresansky.aoc_2020.util.problem

import java.io.File

interface IDay<out T> {
    fun getDayNumber(): Int
    fun getInputFileName() = "day_${getDayNumber()}_input.txt"
    fun loadSingleInputElement(rawData: String): T

    fun loadInput(): List<T> {
        val inputFile = File(this::class.java.getResource(getInputFileName()).toURI())
        return inputFile.readLines().map { loadSingleInputElement(it) }
    }

    fun solvePart1(): Int
    fun solvePart2(): Int
}