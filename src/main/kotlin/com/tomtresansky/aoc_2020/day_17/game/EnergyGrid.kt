package com.tomtresansky.aoc_2020.day_17.game

import com.tomtresansky.aoc_2020.util.pow
import java.util.*
import kotlin.collections.HashMap

data class Coord(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y, z + other.z)
}

enum class State(private val symbol: Char) {
    ACTIVE('#'),
    INACTIVE('.');

    companion object {
        fun from(c: Char) = values().find { it.symbol == c }!!
    }

    override fun toString() = symbol.toString()
}

class ConwayCube(private val coord: Coord, initState: State) {
    private val neighbors: MutableSet<ConwayCube> = HashSet(26)
    var state: State = initState
    private var newState: State? = null

    constructor(other: ConwayCube): this(other.coord, other.state)

    fun addNeighbor(neighbor: ConwayCube) {
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
    override fun equals(other: Any?) = if (other is ConwayCube) Objects.equals(coord, other.coord) else false
}

class Slice(text: List<String>) {
    private val cells: MutableList<MutableList<State>> = mutableListOf()
    init {
        text.forEach {
            val row = it.toCharArray().map { c-> State.from(c) }.toMutableList()
            cells.add(row)
        }
        assert(cells.all { r -> r.size == cells.size })
    }

    fun get(x: Int, y: Int) = cells[y][x]
    fun size() = cells.size
}

class EnergyGrid(initState: Slice, cyclesToRun: Int) {
    private val cells: MutableMap<Coord, ConwayCube?>
    private val extreme: Int

    init {
        val initSize = initState.size()
        val totalWidth = initSize + (cyclesToRun * 2)
        val totalNumCubes = (totalWidth).pow(3)
        cells = HashMap(totalNumCubes)
        extreme = (totalWidth / 2) + 1

        val halfInit = initSize / 2
        for (y in 0 until initSize) {
            for (x in 0 until initSize) {
                val coord = Coord(x - halfInit, y - halfInit, 0)
                cells[coord] = ConwayCube(coord, initState.get(x, y))
            }
        }

        everywhere { x, y, z ->
            val coord = Coord(x, y, z)
            if (!cells.contains(coord)) cells[coord] = ConwayCube(coord, State.INACTIVE)
        }

        linkNeighbors()
    }

    private fun everywhere(action: (x: Int, y: Int, z: Int) -> Any) {
        for (z in -extreme..extreme) {
            for (y in -extreme..extreme) {
                for (x in -extreme..extreme) {
                    action(x, y, z)
                }
            }
        }
    }

    private fun linkNeighbors() {
        everywhere(this::linkNeighbors)
    }

    private fun linkNeighbors(x: Int, y: Int, z: Int) {
        val cell = cells[Coord(x, y, z)]!!
        for (nz in z-1..z+1) {
            for (ny in y-1..y+1) {
                for (nx in x-1..x+1) {
                    if (nx == x && ny == y && nz == z) {
                        //println("Ignoring [x: $nx, y: $ny, z: $nz] - same cell")
                    } else if (nx !in -extreme..extreme) {
                        //println("Ignoring [x: $nx, y: $ny, z: $nz] - x out of bounds")
                    } else if (ny !in -extreme..extreme) {
                        //println("Ignoring [x: $nx, y: $ny, z: $nz] - y out of bounds")
                    } else if (nz !in -extreme..extreme) {
                        //println("Ignoring [x: $nx, y: $ny, z: $nz] - z out of bounds")
                    } else {
                        val neighbor = cells[Coord(nx, ny, nz)]!!
                        cell.addNeighbor(neighbor)
                        //println("Linked [x: $x, y: $y, z: $z] to [x: $nx, y: $ny, z: $nz]")
                    }
                }
            }
        }

        //val percentDone = (((maxZ + z).toFloat() / (maxZ*2+1).toFloat()) * 100).toBigDecimal()
        //println("Linking: $percentDone%")

        //println("Cube at [x: $x, y: $y, z: $z] has ${cell.getNeighbors().size} neighbors")
    }

    fun get(target: Coord) = ConwayCube(cells[target]!!)
    fun set(target: Coord, state: State) {
        cells[target]!!.state = state
    }

    fun step() {
        calcNewStatus()
        applyNewStatus()
    }

    private fun calcNewStatus() {
        //printActives("Current actives: ")
        everywhere { x, y, z -> cells[Coord(x, y, z)]!!.calcNewStatus() }
    }

    private fun applyNewStatus() {
        everywhere { x, y, z -> cells[Coord(x, y, z)]!!.applyNewStatus() }
        //printActives("New actives: ")
    }

    private fun printActives(msg: String) {
        println(msg)
        everywhere { x, y, z ->
            val cell = cells[Coord(x, y, z)]!!
            if (cell.state == State.ACTIVE) {
                println("[x: $x, y: $y, z: $z]")
            }
        }
        println()
    }

    override fun toString(): String {
        val sbOverall = StringBuilder()
        for (z in -extreme..extreme) {
            val sbFrame = StringBuilder()
            sbFrame.append("Z index: $z:\n")
            var hadActiveCell = false

            for (y in -extreme..extreme) {
                for (x in -extreme..extreme) {
                    val cell = cells[Coord(x, y, z)]!!
                    sbFrame.append(cell)
                    if (cell.state == State.ACTIVE) {
                        hadActiveCell = true
                    }
                }
                sbFrame.appendLine()
            }
            sbFrame.appendLine()

            if (hadActiveCell) {
                sbOverall.append(sbFrame)
            }
        }

        sbOverall.append("\nActive Cells: ${numActiveCubes()}\n\n")
        return sbOverall.toString()
    }

    fun numActiveCubes(): Long {
        var count = 0L
        everywhere { x, y, z -> if (cells[Coord(x, y, z)]!!.state == State.ACTIVE) count++ }
        return count
    }
}