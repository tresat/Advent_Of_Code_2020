package com.tomtresansky.aoc_2020.day_03

import com.tomtresansky.aoc_2020.day_03.terrain.Coords
import com.tomtresansky.aoc_2020.day_03.terrain.Terrain
import com.tomtresansky.aoc_2020.day_03.terrain.TerrainType
import java.io.File

@Suppress("SameParameterValue")
class Day03 {
    companion object {
        const val INPUT_FILE_NAME = "day_03_input.txt"
    }

    fun solve(): Int {
        val terrain = Terrain.fromSpec(readSpecFromFile(INPUT_FILE_NAME))

        var coords = (0 to 0).move()
        var treeCount = 0

        while (terrain.isOnMap(coords)) {
            if (terrain.getTerrainTypeAt(coords) == TerrainType.TREE) {
                treeCount++
            }
            coords = coords.move()
        }

        return treeCount
    }

    private fun Coords.move(): Coords = Coords(this.first + 3, this.second + 1)

    private fun readSpecFromFile(inputFileName: String): List<String> {
        val inputFile = File(this::class.java.getResource(inputFileName).toURI())
        return inputFile.readLines()
    }
}