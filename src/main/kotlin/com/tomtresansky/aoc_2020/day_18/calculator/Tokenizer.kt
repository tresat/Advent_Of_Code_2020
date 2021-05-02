package com.tomtresansky.aoc_2020.day_18.calculator

import com.sun.jmx.remote.internal.ArrayQueue
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*

private const val LESSER = -1
private const val EQUAL = 0
private const val GREATER = 1

sealed class Token(private val valString: String): Comparable<Token> {
    abstract fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>)
    override fun compareTo(other: Token) = EQUAL
    override fun toString() = valString

    data class Number(val value: Int): Token(value.toString()) {
        constructor(valString: String): this(valString.toInt())

        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>) {
            outputQueue.add(this)
        }
    }

    abstract class Operator(symbol: String) : Token(symbol) {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>) {
            while (operatorStack.isNotEmpty() && operatorStack.peek() !is OpenParen && operatorStack.peek().compareTo(this) != LESSER) {
                outputQueue.add(operatorStack.pop())
            }
            operatorStack.push(this)
        }
    }

    object Addition: Operator("+")
    object Multiplication: Operator("*")

    object OpenParen: Token("(") {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>) {
            operatorStack.push(this)
        }
    }

    object CloseParen: Token(")") {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>) {
            while (operatorStack.isNotEmpty() && operatorStack.peek() !is OpenParen) {
                outputQueue.add(operatorStack.pop())
            }
            if (operatorStack.isNotEmpty() && operatorStack.peek() !is OpenParen) {
                operatorStack.pop()
            }
        }
    }
}

internal class Tokenizer(private var input: String) {
    fun hasNextToken() = input.isNotEmpty()

    fun nextToken(): Token {
        do {
            when (val nextChar = peekNextChar()) {
                in '0'..'9' -> return popNumber()
                ' ' -> popNextChar()
                '(' -> return popToken(Token.OpenParen)
                ')' -> return popToken(Token.CloseParen)
                '+' -> return popToken(Token.Addition)
                '*' -> return popToken(Token.Multiplication)
                else -> throw IllegalArgumentException(nextChar.toString())

            }
        } while (input.isNotEmpty())

        throw IllegalStateException()
    }

    private fun popToken(type: Token): Token {
        popNextChar()
        return type
    }

    private fun popNumber(): Token {
        var numStr = ""
        do {
            numStr += popNextChar()
        } while (peekNextChar().isDigit())
        return Token.Number(numStr)
    }

    private fun peekNextChar() = input[0]

    private fun popNextChar(): Char {
        val nextChar = input[0]
        input = input.substring(1)
        return nextChar
    }

    fun allTokens(): List<Token> {
        val tokens = mutableListOf<Token>()

        while (hasNextToken()) {
            tokens.add(nextToken())
        }

        return tokens
    }
}
