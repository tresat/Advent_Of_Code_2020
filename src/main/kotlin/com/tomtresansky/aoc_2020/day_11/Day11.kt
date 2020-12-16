package com.tomtresansky.aoc_2020.day_11

import com.tomtresansky.aoc_2020.day_11.grid.Cell
import com.tomtresansky.aoc_2020.day_11.grid.Grid
import com.tomtresansky.aoc_2020.day_11.grid.Row
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day11: IDay<Row> {
    override fun getDayNumber() = 11

    override fun loadSingleInputElement(rawData: String) = rawData.map { Cell.deserialize(it) }

    override fun solvePart1(): Long {
        val rows = loadInput()
        val grid = Grid(rows)
        grid.stepUntilStable()
        return grid.countOccupiedSeats()
    }

    override fun solvePart2(): Long {
        TODO("Not yet implemented")
    }
}