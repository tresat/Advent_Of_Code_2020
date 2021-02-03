package com.tomtresansky.aoc_2020.day_13.bus

typealias Timestamp = Long

data class Bus(val number: Long, val offset: Int) {
    companion object {
        fun deserialize(data: String): List<Bus?> {
            return data.split(",").mapIndexed { index, entry ->
                val potentialNumber = entry.toLongOrNull()
                if (null != potentialNumber) {
                    Bus(potentialNumber, index)
                } else {
                    null
                }
            }
        }
    }

    fun nextDeparturePast(earliestPossible: Timestamp): Long {
        val seed = findSeed(earliestPossible)
        val departureTimes = generateSequence(seed) { it + number }
        return departureTimes.find { it > earliestPossible }!!
    }

    private fun findSeed(earliestPossible: Timestamp): Timestamp {
        var candidate = earliestPossible
        while (candidate.rem(number) != 0L) {
            candidate--
        }
        return candidate
    }
}
