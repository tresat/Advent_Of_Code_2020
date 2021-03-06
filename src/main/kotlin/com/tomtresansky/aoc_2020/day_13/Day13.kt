package com.tomtresansky.aoc_2020.day_13

import com.tomtresansky.aoc_2020.day_13.bus.Bus
import com.tomtresansky.aoc_2020.day_13.bus.Timestamp
import com.tomtresansky.aoc_2020.day_13.crt.CRTer
import java.io.File

class Day13 {
    companion object {
        const val INPUT_FILE_NAME = "day_13_input.txt"

        private fun readScheduleFromFile(@Suppress("SameParameterValue") inputFileName: String): Pair<Timestamp, List<Bus?>> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            val lines = inputFile.readLines()
            return (lines[0].toLong() to Bus.deserialize(lines[1]))
        }
    }

    fun solvePart1(): Long {
        val (desiredDepartureTime, buses) = readScheduleFromFile(INPUT_FILE_NAME)
        val busDepartureTimes = buses.filterNotNull().groupBy { it.nextDeparturePast(desiredDepartureTime) }
        val earliestDepartureTime = busDepartureTimes.keys.minOrNull()!!
        val earliestBus = busDepartureTimes[earliestDepartureTime]!![0]
        return earliestBus.number * (earliestDepartureTime - desiredDepartureTime)
    }

    fun solvePart2(): Long {
        val (_, buses) = readScheduleFromFile(INPUT_FILE_NAME)

        val busesWithOffests = buildOffsets(buses)
        val elements = busesWithOffests.map { (it.second.toInt() to it.first.number.toInt()) }

        return CRTer().solve(elements)
    }

    private fun buildOffsets(buses: List<Bus?>): List<Pair<Bus, Long>> {
        val result: MutableList<Pair<Bus, Long>> = mutableListOf()

        var currentOffset = 0L
        buses.forEach { bus: Bus? ->
            if (bus != null) {
                result.add(bus to currentOffset)
            }

            currentOffset++
        }

        return result
    }
}
