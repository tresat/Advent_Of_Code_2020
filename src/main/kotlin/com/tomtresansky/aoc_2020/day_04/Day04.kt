package com.tomtresansky.aoc_2020.day_04

import com.tomtresansky.aoc_2020.day_04.passport.Passport
import com.tomtresansky.aoc_2020.day_04.passport.SimplePassportValidator
import com.tomtresansky.aoc_2020.day_04.passport.ThoroughPassportValidator
import java.io.File

@Suppress("SameParameterValue")
class Day04 {
    companion object {
        const val INPUT_FILE_NAME = "day_04_input.txt"

        val passports = readPassportData(INPUT_FILE_NAME)

        private fun readPassportData(inputFileName: String): List<Passport> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            val lineGroups = splitLineGroups(inputFile.readLines())
            return lineGroups.map { Passport.deserialize(it) }
        }

        private fun splitLineGroups(rawLines: List<String>): MutableList<List<String>> {
            val dividerIndices = rawLines.indices.filter { rawLines[it].isEmpty() } + rawLines.size

            val lineGroups = mutableListOf<List<String>>()
            var startIdx = 0
            for (endIdx in dividerIndices) {
                lineGroups.add(rawLines.subList(startIdx, endIdx))
                startIdx = endIdx + 1
            }
            return lineGroups
        }
    }

    fun solvePart1(): Int {
        val validator = SimplePassportValidator()
        return passports.count { validator.isValid(it) }
    }

    fun solvePart2(): Int {
        val validator = ThoroughPassportValidator()
        return passports.count { validator.isValid(it) }
    }
}
