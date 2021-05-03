package com.tomtresansky.aoc_2020.day_18.calculator

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ShunterTest {
    private val shunter = Shunter(SamePrecedence)

    @Test
    fun simpleTest() {
        val postfix = shunter.shunt("1 + 2")
        assertEquals("1 2 +", postfix.toString())
    }

    @Test
    fun complexTest() {
        val postfix = shunter.shunt("1 + 2 * 3 + (4 + 5) * 6")
        assertEquals("1 2 + 3 * 4 5 + + 6 *", postfix.toString())
    }
}