package com.tomtresansky.aoc_2020.day_04

import com.tomtresansky.aoc_2020.day_04.passport.Passport
import com.tomtresansky.aoc_2020.day_04.passport.SerializedField
import com.tomtresansky.aoc_2020.day_04.passport.SerializedPassport
import java.io.File

@Suppress("SameParameterValue")
class Day04 {
    companion object {
        const val INPUT_FILE_NAME = "day_04_input.txt"

        private fun readSerializedPassportDataFromFile(inputFileName: String): List<SerializedPassport> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            val rawLines = inputFile.readLines()

            val lineGroups = buildLineGroups(rawLines)
            return buildSerializedPassports(lineGroups)
        }

        private fun buildLineGroups(rawLines: List<String>): MutableList<List<String>> {
            val dividerIndices = rawLines.indices.filter { rawLines[it].isEmpty() } + rawLines.size

            val lineGroups = mutableListOf<List<String>>()
            var startIdx = 0
            for (endIdx in dividerIndices) {
                lineGroups.add(rawLines.subList(startIdx, endIdx))
                startIdx = endIdx + 1
            }
            return lineGroups
        }

        private fun buildSerializedPassports(lineGroups: MutableList<List<String>>): MutableList<SerializedPassport> {
            val serializedPassports = mutableListOf<SerializedPassport>()
            for (lineGroup in lineGroups) {
                val serializedFields = mutableListOf<SerializedField>()
                for (line in lineGroup) {
                    line.split(' ').forEach { serializedFields.add(it) }
                }
                serializedPassports.add(serializedFields)
            }
            return serializedPassports
        }
    }

    fun solvePart1(): Int {
        val serializedPassports = readSerializedPassportDataFromFile(INPUT_FILE_NAME)
        val passports = serializedPassports.map { Passport.deserialize(it) }
        return passports.count { it.isValid() }
    }
}