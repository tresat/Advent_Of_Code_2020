package com.tomtresansky.aoc_2020.day_11

import com.tomtresansky.aoc_2020.day_11.grid.Grid
import com.tomtresansky.aoc_2020.day_11.grid.VisibilityAlgorithm
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day11Test: BaseTest(Day11()) {
    @Disabled
    @Test
    fun testSample() {
        val problem = Day11()
        val rows = problem.loadInput("day_11_input_sample.txt")
        val grid = Grid(rows, VisibilityAlgorithm.VISIBLE_SEATS)

        println("Steps until stable: ${grid.stepUntilStable()}")
        println(grid)
        println ("Occupied: ${ grid.countOccupiedSeats() }")
    }
}