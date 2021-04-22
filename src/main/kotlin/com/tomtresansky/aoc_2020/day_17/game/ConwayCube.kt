package com.tomtresansky.aoc_2020.day_17.game

import java.util.*

class ConwayCube<C: Coord>(private val coord: C, initState: State) {
    private val neighbors: MutableSet<ConwayCube<C>> = HashSet(26)
    var state: State = initState
    private var newState: State? = null

    constructor(other: ConwayCube<C>): this(other.coord, other.state)

    fun addNeighbor(neighbor: ConwayCube<C>) {
        neighbors.add(neighbor)
    }

    fun calcNewStatus() {
        val activeNeighbors = neighbors.filter { it.state == State.ACTIVE }
        newState = if (state == State.ACTIVE) {
            if (activeNeighbors.size == 2 || activeNeighbors.size == 3) {
                State.ACTIVE
            } else {
                //println("Cell Inactivating [x: ${coord.x}, y: ${coord.y}, z: ${coord.z}]")
                //activeNeighbors.forEach { println(" - Active neighbor: [x: ${it.coord.x}, y: ${it.coord.y}, z: ${it.coord.z}]") }
                //println()
                State.INACTIVE
            }
        } else {
            if (activeNeighbors.size == 3) {
                //println("Cell Activating [x: ${coord.x}, y: ${coord.y}, z: ${coord.z}]")
                //activeNeighbors.forEach { println("- Active neighbor: [x: ${it.coord.x}, y: ${it.coord.y}, z: ${it.coord.z}]") }
                //println()
                State.ACTIVE
            } else {
                State.INACTIVE
            }
        }
    }

    fun applyNewStatus() {
        state = newState!!
        newState = null
    }

    fun getNeighbors() = neighbors.toCollection(mutableSetOf())

    override fun toString() = state.toString()
    override fun hashCode(): Int = coord.hashCode()
    override fun equals(other: Any?) = if (other is ConwayCube<*>) Objects.equals(coord, other.coord) else false
}