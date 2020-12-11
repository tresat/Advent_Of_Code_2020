package com.tomtresansky.aoc_2020.day_06

import com.tomtresansky.aoc_2020.day_06.group.Group
import com.tomtresansky.aoc_2020.day_06.group.SerializedGroup
import java.io.File

@Suppress("SameParameterValue")
class Day06 {
    companion object {
        const val INPUT_FILE_NAME = "day_06_input.txt"

        val groups = readGroupsFromFile(INPUT_FILE_NAME)

        private fun readGroupsFromFile(inputFileName: String): List<Group> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            val lineGroups = splitLineGroups(inputFile.readLines())
            return lineGroups.map { Group.deserialize(it) }
        }

        private fun splitLineGroups(rawLines: List<String>): MutableList<SerializedGroup> {
            val dividerIndices = rawLines.indices.filter { rawLines[it].isEmpty() } + rawLines.size

            val lineGroups = mutableListOf<SerializedGroup>()
            var startIdx = 0
            for (endIdx in dividerIndices) {
                lineGroups.add(rawLines.subList(startIdx, endIdx))
                startIdx = endIdx + 1
            }
            return lineGroups
        }
    }

    fun solvePart1() = groups.map { it.totalUniqueAnswers() }.sum()

    fun solvePart2(): Int {
        return 2
    }
}
