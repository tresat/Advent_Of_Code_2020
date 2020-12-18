package com.tomtresansky.aoc_2020.day_12.command

import com.tomtresansky.aoc_2020.day_12.geometry.*
import kotlin.math.cos
import kotlin.math.sin

abstract class AbstractCaptain(var ship: Vector) {
    abstract fun execute(command: ICommand)

    open fun executeAll(commands: List<ICommand>, print: Boolean) {
        if (print) println("Ship: $ship")
        commands.forEach { command ->
            if (print) println(command)
            execute(command)
            if (print) println("Ship: $ship")
        }
    }
}

class StandardCaptain(ship: Vector): AbstractCaptain(ship) {
    override fun execute(command: ICommand) =
        when (command) {
            is OrthogonalMoveCommand -> ship += command.getDeltas()
            is ForwardCommand -> ship += getForwardDeltas(ship, command)
            is RotationCommand -> ship = Vector(ship.location, ship.heading.rotate(command.getHeadingChange()))
            else -> throw IllegalStateException("Unknown command: $command")
        }

    private fun getForwardDeltas(initial: Vector, command: ForwardCommand): Coord {
        val deltaX: Double
        val deltaY: Double
        when (initial.heading) {
            0 -> {
                deltaX = 0.0
                deltaY = command.units.toDouble()
            }
            90 -> {
                deltaX = command.units.toDouble()
                deltaY = 0.0
            }
            180 -> {
                deltaX = 0.0
                deltaY = -command.units.toDouble()
            }
            270 -> {
                deltaX = -command.units.toDouble()
                deltaY = 0.0
            }
            else -> when (initial.heading.quadarant()) {
                Quadrant.I -> {
                    deltaY = (command.units * sin((90 - initial.heading).toDouble()))
                    deltaX = (command.units / cos((90 - initial.heading).toDouble()))
                }
                Quadrant.II -> {
                    deltaY = (command.units * sin((initial.heading - 270).toDouble()))
                    deltaX = -(command.units / cos((initial.heading - 270).toDouble()))
                }
                Quadrant.III -> {
                    deltaX = -(command.units * sin((initial.heading - 270).toDouble()))
                    deltaY = -(command.units / cos((initial.heading - 270).toDouble()))
                }
                Quadrant.IV -> {
                    deltaY = -(command.units * sin((initial.heading - 90).toDouble()))
                    deltaX = (command.units / cos((initial.heading - 90).toDouble()))
                }
            }
        }
        return (deltaX to deltaY)
    }
}

class RelativeToWaypointCaptain(ship: Vector, private var waypoint: Vector): AbstractCaptain(ship) {
    override fun execute(command: ICommand) =
        when (command) {
            is OrthogonalMoveCommand -> waypoint += command.getDeltas()
            is ForwardCommand -> ship = moveRelative(ship, waypoint, command.units)
            is RotationCommand -> waypoint = rotateAround(waypoint, ship, command.getHeadingChange())
            else -> throw IllegalStateException("Unknown command: $command")
        }

    private fun moveRelative(start: Vector, relative: Vector, units: Int): Vector {
        val deltaX = relative.x() * units
        val deltaY = relative.y() * units
        return start + (deltaX to deltaY)
    }

    private fun rotateAround(start: Vector, center: Vector, headingChange: Int): Vector {
        assert(headingChange.rem(90) == 0) // Can only handle 90 degree rotations

        // Special case, same location = no change
        if (start.location == center.location) {
            return Vector(start.location, start.heading)
        }

        var current = start
        val rotations = headingChange / 90
        if (rotations > 0) {
            repeat(rotations) { current = Vector((current.y() to -current.x()), current.heading) }
        } else {
            repeat(-rotations) { current = Vector((-current.y() to current.x()), current.heading) }
        }

        return current
    }
}
