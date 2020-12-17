package com.tomtresansky.aoc_2020.day_12

import com.tomtresansky.aoc_2020.day_12.command.*
import com.tomtresansky.aoc_2020.day_12.geometry.EAST
import com.tomtresansky.aoc_2020.day_12.geometry.ORIGIN
import com.tomtresansky.aoc_2020.day_12.geometry.Vector
import com.tomtresansky.aoc_2020.day_12.geometry.calcManhattanDistanceTo
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day12: IDay<ICommand> {
    override fun getDayNumber() = 12

    override fun loadSingleInputElement(rawData: String) = ICommand.deserialize(rawData)

    override fun solvePart1(): Long {
        val commands = loadInput()
        var curr = Vector(ORIGIN, EAST)

        println(curr)
        commands.forEach { command ->
            println(command)
            curr = command.execute(curr)
            println(curr)
        }
        return curr.location.calcManhattanDistanceTo(ORIGIN).toLong()
    }

    override fun solvePart2(): Long {
        TODO()
    }
}