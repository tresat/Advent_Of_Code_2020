package com.tomtresansky.aoc_2020.day_12.command

import com.tomtresansky.aoc_2020.day_12.geometry.*
import kotlin.math.cos
import kotlin.math.sin


enum class Action {
    N, S, E, W, L, R, F
}

interface ICommand {
    companion object {
        private val COMMAND_REGEX = """([A-Z])(\d+)""".toRegex()
        private const val ACTION_IDX = 1
        private const val VALUE_IDX = 2

        fun deserialize(data: String): ICommand {
            val matchResult = COMMAND_REGEX.matchEntire(data)
            with(matchResult?.groupValues!!) {
                val action = Action.valueOf(get(ACTION_IDX))
                val value = get(VALUE_IDX).toInt()
                return when (action) {
                    Action.N -> NorthCommand(value)
                    Action.S -> SouthCommand(value)
                    Action.E -> EastCommand(value)
                    Action.W -> WestCommand(value)
                    Action.L -> LeftCommand(value)
                    Action.R -> RightCommand(value)
                    Action.F -> ForwardCommand(value)
                }
            }
        }
    }

    fun execute(current: Vector): Vector
}

abstract class OrthogonalMoveCommand(protected val distance: Distance): ICommand

class NorthCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun execute(current: Vector) = Vector(current.x() to current.y() + distance, current.heading)
    override fun toString() = "North $distance"
}
class SouthCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun execute(current: Vector) = Vector(current.x() to current.y() - distance, current.heading)
    override fun toString() = "South $distance"
}
class EastCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun execute(current: Vector) = Vector(current.x() + distance to current.y(), current.heading)
    override fun toString() = "East $distance"
}
class WestCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun execute(current: Vector) = Vector(current.x() - distance to current.y(), current.heading)
    override fun toString() = "West $distance"
}

abstract class RotationCommand(protected val degree: Degree): ICommand {
    init {
        assert(degree <= 360) // Can't handle left rotation > 360, but don't think they'll provide any in the input, ensure that
    }
}

class LeftCommand(degree: Degree): RotationCommand(degree) {
    override fun execute(current: Vector) = Vector(current.location, current.heading.rotate(-degree))
    override fun toString() = "Left $degree"
}
class RightCommand(degree: Degree): RotationCommand(degree) {
    override fun execute(current: Vector) = Vector(current.location, current.heading.rotate(degree))
    override fun toString() = "Right $degree"
}

class ForwardCommand(private val distance: Distance): ICommand {
    override fun execute(current: Vector): Vector {
        val deltaX: Double
        val deltaY: Double
        when (current.heading) {
            0 -> {
                deltaX = 0.0
                deltaY = distance.toDouble()
            }
            90 -> {
                deltaX = distance.toDouble()
                deltaY = 0.0
            }
            180 -> {
                deltaX = 0.0
                deltaY = -distance.toDouble()
            }
            270 -> {
                deltaX = -distance.toDouble()
                deltaY = 0.0
            }
            else -> when (current.heading.quadarant()) {
                Quadrant.I -> {
                    deltaY = (distance * sin((90 - current.heading).toDouble()))
                    deltaX = (distance / cos((90 - current.heading).toDouble()))
                }
                Quadrant.II -> {
                    deltaY = (distance * sin((current.heading - 270).toDouble()))
                    deltaX = -(distance / cos((current.heading - 270).toDouble()))
                }
                Quadrant.III -> {
                    deltaX = -(distance * sin((current.heading - 270).toDouble()))
                    deltaY = -(distance / cos((current.heading - 270).toDouble()))
                }
                Quadrant.IV -> {
                    deltaY = -(distance * sin((current.heading - 90).toDouble()))
                    deltaX = (distance / cos((current.heading - 90).toDouble()))
                }
            }
        }

        return Vector(current.x() + deltaX to current.y() + deltaY, current.heading)
    }

    override fun toString() = "* FORWARD $distance"
}
