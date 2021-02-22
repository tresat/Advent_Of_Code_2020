package com.tomtresansky.aoc_2020.day_16.ticket

data class ValidRange(private val low: Int, private val high: Int) {
    fun contains(target: Int) = (target in low..high)
}