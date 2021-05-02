package com.tomtresansky.aoc_2020.day_18.calculator

import com.sun.jmx.remote.internal.ArrayQueue
import java.util.*

internal class Shunter {
    companion object {
        private const val INIT_OUTPUT_QUEUE_SIZE = 25
    }

    private val outputQueue = ArrayQueue<Token>(INIT_OUTPUT_QUEUE_SIZE)
    private val operatorStack = Stack<Token>()

    fun shunt(input: String): PostfixCalculation {
        reset()
        process(input)
        return drainStack()
    }

    private fun reset() {
        outputQueue.clear()
        operatorStack.empty()
    }

    private fun process(input: String) {
        val tokenizer = Tokenizer(input)
        while (tokenizer.hasNextToken()) {
            val token = tokenizer.nextToken()
            token.shunt(outputQueue, operatorStack)
        }
    }

    private fun drainStack(): PostfixCalculation {
        while (operatorStack.isNotEmpty()) {
            outputQueue.add(operatorStack.pop())
        }

        return PostfixCalculation(outputQueue)
    }
}
