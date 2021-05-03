package com.tomtresansky.aoc_2020.day_18.calculator

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PostfixCalculationTest {
    @Test
    fun simpleToStringTest() {
        val postfix = PostfixCalculation(listOf(Token.Number(1), Token.Number(2), Token.Addition))
        assertEquals("1 2 +", postfix.toString())
    }

    @Test
    fun simpleEvalTest() {
        val postfix = PostfixCalculation(listOf(Token.Number(1), Token.Number(2), Token.Addition))
        assertEquals(3, postfix.evaluate())
    }

    @Test
    fun complexEvalTest() {
        val postfix = PostfixCalculation(listOf(Token.Number(1), Token.Number(2), Token.Addition,
                                                Token.Number(4), Token.Multiplication))
        assertEquals(12, postfix.evaluate())
    }
}
