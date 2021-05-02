package com.tomtresansky.aoc_2020.day_18.calculator

import org.junit.jupiter.api.Test

class TokenizerTest {
    @Test
    fun simpleTest() {
        val tokenizer = Tokenizer("1 + 2")
        val tokens = tokenizer.allTokens()
        assert(tokens[0] == Token.Number(1))
        assert(tokens[1] == Token.Addition)
        assert(tokens[2] == Token.Number(2))
    }

    @Test
    fun complexTest() {
        val tokenizer = Tokenizer("1 + (2 * 3) + (4 * (5 + 6))")
        val tokens = tokenizer.allTokens()
        assert(tokens[0] == Token.Number(1))
        assert(tokens[1] == Token.Addition)
        assert(tokens[2] == Token.OpenParen)
        assert(tokens[3] == Token.Number(2))
        assert(tokens[4] == Token.Multiplication)
        assert(tokens[5] == Token.Number(3))
        assert(tokens[6] == Token.CloseParen)
        assert(tokens[7] == Token.Addition)
        assert(tokens[8] == Token.OpenParen)
        assert(tokens[9] == Token.Number(4))
        assert(tokens[10] == Token.Multiplication)
        assert(tokens[11] == Token.OpenParen)
        assert(tokens[12] == Token.Number(5))
        assert(tokens[13] == Token.Addition)
        assert(tokens[14] == Token.Number(6))
        assert(tokens[15] == Token.CloseParen)
        assert(tokens[16] == Token.CloseParen)
    }
}