package com.tomtresansky.aoc_2020.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OdometerTest {
    @DisplayName("Test incrementing the Odometer to the maximum amount")
    @Test
    fun testMaxIncrement() {
        val numCounters = 3
        val maxCounterValue = 5

        val odo = Odometer(numCounters, maxCounterValue)

        val expectedIncrements = ((maxCounterValue + 1) pow (numCounters)) - 1
        while (odo.canIncrement()) {
            odo.increment()
        }

        assertThat(odo.getValue()).isNotNull
                                  .hasSize(numCounters)
                                  .containsOnly(maxCounterValue)
        assertThat(odo.getTicks()).isEqualTo(expectedIncrements)
    }
}