package com.tomtresansky.aoc_2020.day_19

import com.tomtresansky.aoc_2020.day_19.messages.Message
import com.tomtresansky.aoc_2020.day_19.messages.Rule
import com.tomtresansky.aoc_2020.day_19.messages.Rulebook
import java.io.File

class Day19 {
    companion object {
        const val INPUT_FILE_NAME = "day_19_input.txt"

        private fun readInputFromFile(inputFileName: String): Input {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            val lines = inputFile.readLines()
            val rules = lines.takeWhile { it.isNotEmpty() }.map { Rule.from(it) }
            val messages = lines.takeLastWhile { it.isNotEmpty() }.map { Message.from(it) }
            return Input(Rulebook(rules), messages)
        }
    }

    private data class Input(val rulebook: Rulebook, val messages: List<Message>)

    fun solvePart1(): Int {
        val (rules, messages) = readInputFromFile(INPUT_FILE_NAME)
        TODO()
    }

    fun solvePart2(): Int {
        val (rules, messages) = readInputFromFile(INPUT_FILE_NAME)
        TODO()
    }
}