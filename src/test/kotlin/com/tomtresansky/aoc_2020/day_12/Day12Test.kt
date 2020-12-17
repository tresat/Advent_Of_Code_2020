package com.tomtresansky.aoc_2020.day_12

import com.tomtresansky.aoc_2020.day_12.geometry.EAST
import com.tomtresansky.aoc_2020.day_12.geometry.ORIGIN
import com.tomtresansky.aoc_2020.day_12.geometry.Vector
import com.tomtresansky.aoc_2020.day_12.geometry.calcManhattanDistanceTo
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day12Test: BaseTest(Day12()) {
    @Disabled
    @Test
    fun testSample() {
        val problem = Day12()
        val commands = problem.loadInput("day_12_input_sample.txt")
        var curr = Vector(ORIGIN, EAST)

        println(curr)
        commands.forEach { command ->
            println(command)
            curr = command.execute(curr)
            println(curr)
        }

        val distance = curr.location.calcManhattanDistanceTo(ORIGIN).toLong()
        println("Result: $distance")
    }
}