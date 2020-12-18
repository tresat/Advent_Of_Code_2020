package com.tomtresansky.aoc_2020.day_13.bus

typealias Timestamp = Int

data class Bus(val number: Long) {
    companion object {
        fun deserialize(data: String) = data.split(",").mapNotNull { it.toLongOrNull() }.map { Bus(it) }
    }

    fun nextDeparturePast(earliestPossible: Timestamp): Long {
        val departureTimes = generateSequence(0L) { it + number }
        return departureTimes.find { it > earliestPossible }!!
    }
}
