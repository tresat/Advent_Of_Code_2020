package com.tomtresansky.aoc_2020.day_10.adapter

typealias Voltage = Int

class Adapter(val rating: Voltage) {
    private val acceptableInputs = setOf(rating - 3, rating - 2, rating - 1)
    fun canAcceptInput(input: Voltage) = acceptableInputs.contains(input)
}
