package com.tomtresansky.aoc_2020.day_09.cipher

import com.tomtresansky.aoc_2020.util.Odometer
import com.tomtresansky.aoc_2020.util.pow
import java.util.*

class Cipher(private val data: List<Long>, private val windowLength: Int = DEFAULT_LENGTH) {
    companion object {
        private const val DEFAULT_LENGTH = 25
    }

    fun preamble() = data.subList(0, windowLength)

    fun firstInvalidEntry(): Long? {
        val lookup = ArrayDeque(preamble())
        for (currIdx in windowLength until data.size) {
            val entry = data[currIdx]
            if (!validateEntry(entry, lookup.toList())) {
                return entry
            } else {
                lookup.pop()
                lookup.add(entry)
            }
        }
        return null
    }

    private fun validateEntry(entry: Long, lookup: List<Long>): Boolean {
        val odo = Odometer(2, lookup.size - 1)

        while (odo.getTicks() < (lookup.size pow 2) - 1) {
            val a = lookup[odo.getValue()[0]]
            val b = lookup[odo.getValue()[1]]

            if (a != b && a + b == entry) {
                return true
            } else {
                odo.increment()
            }
        }

        return false
    }
}
