package com.tomtresansky.aoc_2020.day_07

import com.tomtresansky.aoc_2020.day_07.rules.Bag
import com.tomtresansky.aoc_2020.day_07.rules.Checker
import com.tomtresansky.aoc_2020.day_07.rules.Rule
import java.io.File
import java.math.BigInteger

@Suppress("SameParameterValue")
class Day07 {
    companion object {
        const val INPUT_FILE_NAME = "day_07_input.txt"

        val rules = readRulesFromFile(INPUT_FILE_NAME)

        private fun readRulesFromFile(inputFileName: String): List<Rule> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return inputFile.readLines().map { Rule.deserialize(it) }
        }
    }

    fun solvePart1(): Int {
        val checker = Checker.fromRules(rules)
        val startBag = Bag.get("shiny", "gold")
        val paths = checker.getStorageChainsFor(startBag)
        return (paths.flatten().toSet() - startBag).size
    }

    fun solvePart2(): Long {
        val checker = Checker.fromRules(rules)
        val container = Bag.get("shiny", "gold")
        return checker.countContentsOf(container)
    }
}