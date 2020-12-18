package com.tomtresansky.aoc_2020.day_12.command

import com.tomtresansky.aoc_2020.day_12.geometry.*

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
}

abstract class OrthogonalMoveCommand(val distance: Distance): ICommand {
    abstract fun getDeltas(): Coord
}

class NorthCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun getDeltas() = (0.0 to distance.toDouble())
    override fun toString() = "North $distance"
}
class SouthCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun getDeltas() = (0.0 to -distance.toDouble())
    override fun toString() = "South $distance"
}
class EastCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun getDeltas() = (distance.toDouble() to 0.0)
    override fun toString() = "East $distance"
}
class WestCommand(distance: Distance): OrthogonalMoveCommand(distance) {
    override fun getDeltas() = (-distance.toDouble() to 0.0)
    override fun toString() = "West $distance"
}

abstract class RotationCommand(protected val degree: Degree): ICommand {
    init {
        assert(degree <= 360) // Can't handle left rotation > 360, but don't think they'll provide any in the input, ensure that
    }
    abstract fun getHeadingChange(): Int
}

class LeftCommand(degree: Degree): RotationCommand(degree) {
    override fun getHeadingChange() = -degree
    override fun toString() = "Left $degree"
}
class RightCommand(degree: Degree): RotationCommand(degree) {
    override fun getHeadingChange() = degree
    override fun toString() = "Right $degree"
}

class ForwardCommand(val units: Int): ICommand {
    override fun toString() = "FORWARD $units"
}
