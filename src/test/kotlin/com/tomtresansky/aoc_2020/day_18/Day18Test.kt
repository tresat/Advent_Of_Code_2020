package com.tomtresansky.aoc_2020.day_18

import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class Day18Test: BaseTest(Day18()) {
    @Test
    fun testSample() {
        val problem = Day18()
        val calculations = problem.loadInput("day_18_sample.txt")

        assertEquals(71, calculations[0].evaluate())
        assertEquals(51, calculations[1].evaluate())
        assertEquals(26, calculations[2].evaluate())
        assertEquals(437, calculations[3].evaluate())
        assertEquals(12240, calculations[4].evaluate())
        assertEquals(13632, calculations[5].evaluate())
    }
}
