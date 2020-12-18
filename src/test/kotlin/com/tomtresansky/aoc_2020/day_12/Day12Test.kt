package com.tomtresansky.aoc_2020.day_12

import com.tomtresansky.aoc_2020.day_12.command.RelativeToWaypointCaptain
import com.tomtresansky.aoc_2020.day_12.command.StandardCaptain
import com.tomtresansky.aoc_2020.day_12.geometry.*
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day12Test: BaseTest(Day12()) {
    @Test
    @Disabled
    fun testSampleStandard() {
        val problem = Day12()
        val commands = problem.loadInput("day_12_input_sample.txt")
        val ship = Vector(ORIGIN, EAST)
        val captain = StandardCaptain(ship)
        captain.executeAll(commands, true)
        return println("Distance: ${captain.ship.location.calcManhattanDistanceTo(ORIGIN).toLong()}")
    }

    @Test
    @Disabled
    fun testSampleRelative() {
        val problem = Day12()
        val commands = problem.loadInput("day_12_input_sample.txt")
        val ship = Vector(ORIGIN, EAST)
        val waypoint = Vector(ORIGIN.x() + 10 to ORIGIN.y() + 1, 0)
        val captain = RelativeToWaypointCaptain(ship, waypoint)
        captain.executeAll(commands, true)
        return println("Distance: ${captain.ship.location.calcManhattanDistanceTo(ORIGIN).toLong()}")
    }
}