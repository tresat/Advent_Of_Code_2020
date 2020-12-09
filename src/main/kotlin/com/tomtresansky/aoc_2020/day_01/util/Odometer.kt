package com.tomtresansky.aoc_2020.day_01.util

import java.lang.RuntimeException
import java.util.*

class Odometer(numCounters: Int, private val maxCounterValue: Int) {
    private val counters: Array<Int> = Array(numCounters) { 0 }
    private var ticks = 0

    fun getValue() = counters.copyOf()
    fun getTicks() = ticks
    override fun toString() = "${Arrays.deepToString(counters)} (${getTicks()} ticks)"

    fun canIncrement() = (!counters.all { it == maxCounterValue })

    fun increment(): Array<Int> {
        if (!canIncrement()) {
            throw CounterAtMaxValueException(this)
        } else {
            ticks++
        }

        for (i in counters.indices.reversed()) {
            val needToCarry = (counters[i] == maxCounterValue)
            counters[i] = (counters[i] + 1).rem(maxCounterValue + 1)
            if (!needToCarry) {
                break
            }
        }

        return counters.copyOf()
    }

    class CounterAtMaxValueException(odo: Odometer) : RuntimeException("Odometer is already at max value: $odo")
}