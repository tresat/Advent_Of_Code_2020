package com.tomtresansky.aoc_2020.day_10.adapter

typealias Voltage = Int

@Suppress("DataClassPrivateConstructor")
data class Adapter private constructor(val rating: Voltage) {
    companion object Registry {
        private val adapters = mutableMapOf<Voltage, Adapter>()
        fun get(rating: Voltage) = adapters.computeIfAbsent(rating) { Adapter(rating) }
    }

    private val acceptableInputs = setOf(rating - 3, rating - 2, rating - 1)
    fun canAcceptInput(input: Voltage) = acceptableInputs.contains(input)
    override fun toString() = rating.toString()
}

fun List<Adapter>.findDifferences(difference: Int): List<Pair<Adapter, Adapter>> = this.windowed(2).filter { it[1].rating - it[0].rating == difference }.map { it[0] to it[1] }
