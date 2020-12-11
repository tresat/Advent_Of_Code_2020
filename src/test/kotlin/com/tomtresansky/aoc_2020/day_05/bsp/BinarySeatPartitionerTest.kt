package com.tomtresansky.aoc_2020.day_05.bsp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BinarySeatPartitionerTest {
    @Test
    fun test() {
        val bsp = BinarySeatPartitioner(128, 8)
        val seat = bsp.findSeat("FBFBBFFRLR")

        assertThat(seat.rowNum).isEqualTo(44)
        assertThat(seat.seatNum).isEqualTo(5)
    }
}
