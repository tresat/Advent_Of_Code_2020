package com.tomtresansky.aoc_2020.day_12.geometry

import kotlin.math.abs

val ORIGIN: Coord = (0.0 to 0.0)
val EAST: Degree = 90

typealias Distance = Int

enum class Quadrant {
    I, II, III, IV
}

typealias Degree = Int
fun Degree.rotate(other: Degree) = (360 + ((this + other).rem(360))).rem(360)
fun Degree.quadarant() =
    when (this) {
        in 0 until 90 -> Quadrant.I
        in 90 until 180 -> Quadrant.IV
        in 180 until 270 -> Quadrant.III
        in 270 until 360 -> Quadrant.II
        else -> throw IllegalStateException("Illegal heading: $this")
    }

typealias Coord = Pair<Double, Double>
fun Coord.x() = this.first
fun Coord.y() = this.second
fun Coord.calcManhattanDistanceTo(other: Coord) = abs(x() - other.x()) + abs(y() - other.y())

data class Vector(val location: Coord, val heading: Degree) {
    fun x() = location.x()
    fun y() = location.y()
}
