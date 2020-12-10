package com.tomtresansky.aoc_2020.day_03

import com.tomtresansky.aoc_2020.day_03.terrain.Terrain
import com.tomtresansky.aoc_2020.day_03.traversal.Motion
import com.tomtresansky.aoc_2020.day_03.traversal.Traversal
import com.tomtresansky.aoc_2020.day_03.traversal.countTrees
import java.io.File

@Suppress("SameParameterValue")
class Day03 {
    companion object {
        const val INPUT_FILE_NAME = "day_03_input.txt"
        val terrain = Terrain.fromSpec(readSpecFromFile(INPUT_FILE_NAME))

        private fun readSpecFromFile(inputFileName: String): List<String> {
            val inputFile = File(this::class.java.getResource(inputFileName).toURI())
            return inputFile.readLines()
        }
    }

    fun solvePart1(): Int {
        val motion = Motion(3, 1)
        val path = Traversal.fullFromTopLeft(terrain, motion)

        return path.countTrees(terrain)
    }

    fun solvePart2(): Int {
        val terrain = Terrain.fromSpec(readSpecFromFile(INPUT_FILE_NAME))
        val motions = arrayOf(Motion(1, 1),
                              Motion(3, 1),
                              Motion(5, 1),
                              Motion(7, 1),
                              Motion(1, 2))

        return motions.map { Traversal.fullFromTopLeft(terrain, it) }
                      .map { it.countTrees(terrain) }
                      .fold(1) { curr, acc -> curr * acc }
    }
}
