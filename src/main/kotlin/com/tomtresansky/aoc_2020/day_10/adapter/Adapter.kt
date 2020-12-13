package com.tomtresansky.aoc_2020.day_10.adapter

typealias Voltage = Int

data class Adapter(val rating: Voltage) {
    private val acceptableInputs = setOf(rating - 3, rating - 2, rating - 1)
    fun canAcceptInput(input: Voltage) = acceptableInputs.contains(input)
}

fun List<Adapter>.findDifferences(difference: Int): List<Pair<Adapter, Adapter>> = this.windowed(2).filter { it[1].rating - it[0].rating == difference }.map { it[0] to it[1] }
