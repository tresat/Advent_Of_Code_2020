package com.tomtresansky.aoc_2020.day_16.ticket

typealias Value = Int
typealias Index = Int

data class Ticket(val values: List<Value>) {
    companion object {
        fun deserialize(line: String) = Ticket(line.split(',').map { it.toInt() })
    }
}
