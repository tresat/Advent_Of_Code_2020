package com.tomtresansky.aoc_2020.day_18.calculator

import com.sun.jmx.remote.internal.ArrayQueue
import com.tomtresansky.aoc_2020.day_18.calculator.OperatorPrecedence.Companion.LESSER
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.*

sealed class Token(private val valString: String) {
    abstract fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>, precedence: OperatorPrecedence)
    override fun toString() = valString

    data class Number(val value: Long): Token(value.toString()) {
        constructor(valString: String): this(valString.toLong())

        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>, precedence: OperatorPrecedence) {
            outputQueue.add(this)
        }

        override fun toString() = value.toString()
    }

    abstract class Operator(symbol: String) : Token(symbol) {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>, precedence: OperatorPrecedence) {
            while (operatorStack.isNotEmpty() && operatorStack.peek() !is OpenParen && precedence.compare(operatorStack.peek(), this) != OperatorPrecedence.LESSER) {
                outputQueue.add(operatorStack.pop())
            }
            operatorStack.push(this)
        }

        abstract fun operate(lhs: Number, rhs: Number): Long
    }

    object Addition: Operator("+") {
        override fun operate(lhs: Number, rhs: Number) = (lhs.value + rhs.value)
    }
    object Multiplication: Operator("*") {
        override fun operate(lhs: Number, rhs: Number) = (lhs.value * rhs.value)
    }

    object OpenParen: Token("(") {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>, precedence: OperatorPrecedence) {
            operatorStack.push(this)
        }
    }

    object CloseParen: Token(")") {
        override fun shunt(outputQueue: ArrayQueue<Token>, operatorStack: Stack<Token>, precedence: OperatorPrecedence) {
            while (operatorStack.isNotEmpty() && operatorStack.peek() !is OpenParen) {
                outputQueue.add(operatorStack.pop())
            }
            if (operatorStack.isNotEmpty() && operatorStack.peek() is OpenParen) {
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
        } while (input.isNotEmpty() && peekNextChar().isDigit())
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
