package com.tomtresansky.aoc_2020.day_14

import com.tomtresansky.aoc_2020.day_14.cpu.Cpu
import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test: BaseTest(Day14()) {
    @Test
    fun testSample() {
        val problem = Day14()
        val commands = problem.loadInput("day_14_sample.txt")
        val cpu = Cpu()
        commands.forEach { cpu.execute(it) }
        assertThat(cpu.sumMemory()).isEqualTo(165)
    }

    @Test
    fun testLongNum() {
        val mask = Bitmask("0111X10100100X1111X10010X000X1000001")
        val result = mask.applyTo(468673978)
        println ("Result: $result")
    }
}
