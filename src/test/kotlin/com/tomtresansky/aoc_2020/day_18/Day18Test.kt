package com.tomtresansky.aoc_2020.day_18

import com.tomtresansky.aoc_2020.day_18.calculator.AdditionFirst
import com.tomtresansky.aoc_2020.day_18.calculator.SamePrecedence
import com.tomtresansky.aoc_2020.day_18.calculator.Shunter
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class Day18Test: BaseTest(Day18()) {
    @Test
    fun testSampleSamePrecedence() {
        val problem = Day18()
        val rawData = problem.loadInput("day_18_sample.txt")
        val shunter = Shunter(SamePrecedence)
        val results = rawData.map { shunter.shunt(it) }.map { it.evaluate() }

        assertEquals(listOf(71L, 51L, 26L, 437L, 12240L, 13632L), results)
    }

    @Test
    fun testSampleAdditionFirst() {
        val problem = Day18()
        val rawData = problem.loadInput("day_18_sample.txt")
        val shunter = Shunter(AdditionFirst)
        val results = rawData.map { shunter.shunt(it) }.map { it.evaluate() }

        assertEquals(listOf(231L, 51L, 46L, 1445L, 669060L, 23340L), results)
    }
}
