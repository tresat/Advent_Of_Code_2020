package com.tomtresansky.aoc_2020.day_12

import com.tomtresansky.aoc_2020.day_12.command.*
import com.tomtresansky.aoc_2020.day_12.geometry.*
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day12: IDay<ICommand> {
    override fun getDayNumber() = 12

    override fun loadSingleInputElement(rawData: String) = ICommand.deserialize(rawData)

    override fun solvePart1(): Long {
        val commands = loadInput()
        val ship = Vector(ORIGIN, EAST)
        val captain = StandardCaptain(ship)
        captain.executeAll(commands, false)
        return captain.ship.location.calcManhattanDistanceTo(ORIGIN).toLong()
    }

    override fun solvePart2(): Long {
        val commands = loadInput()
        val ship = Vector(ORIGIN, EAST)
        val waypoint = Vector(ORIGIN.x() + 10 to ORIGIN.y() + 1, 0)
        val captain = RelativeToWaypointCaptain(ship, waypoint)
        captain.executeAll(commands, false)
        return captain.ship.location.calcManhattanDistanceTo(ORIGIN).toLong()
    }
}
