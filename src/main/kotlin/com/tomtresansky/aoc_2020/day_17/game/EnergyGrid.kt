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
        val activeNeighbors = neighbors.count { it.state == State.ACTIVE }
        newState = if (state == State.ACTIVE) {
            if (activeNeighbors == 2 || activeNeighbors == 3) {
                State.ACTIVE
            } else {
                State.INACTIVE
            }
        } else {
            if (activeNeighbors == 3) {
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

class EnergyGrid(initState: Slice) {
    companion object {
        private const val DEFAULT_NUM_CYCLES_TO_RUN = 3
    }

    private val cells: MutableMap<Coord, ConwayCube?>
    private val minX: Int; private val maxX: Int
    private val minY: Int; private val maxY: Int
    private val minZ: Int; private val maxZ: Int

    init {
        val totalWidth = initState.size() + DEFAULT_NUM_CYCLES_TO_RUN + 1
        val totalNumCubes = (totalWidth).pow(3)
        cells = HashMap(totalNumCubes)

        val halfMax = totalWidth / 2
        minX = -halfMax; maxX = halfMax
        minY = -halfMax; maxY = halfMax
        minZ = -halfMax; maxZ = halfMax

        val halfInit = initState.size() / 2
        for (y in 0 until initState.size()) {
            for (x in 0 until initState.size()) {
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
        for (z in minZ..maxZ) {
            for (y in minY..maxY) {
                for (x in minX..maxX) {
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
                        println("Ignoring [x: $nx, y: $ny, z: $nz] - same cell")
                    } else if (nx !in minX..maxX) {
                        println("Ignoring [x: $nx, y: $ny, z: $nz] - x out of bounds")
                    } else if (ny !in minY..maxY) {
                        println("Ignoring [x: $nx, y: $ny, z: $nz] - y out of bounds")
                    } else if (nz !in minZ..maxZ) {
                        println("Ignoring [x: $nx, y: $ny, z: $nz] - z out of bounds")
                    } else {
                        val neighbor = cells[Coord(nx, ny, nz)]!!
                        cell.addNeighbor(neighbor)
                        println("Linked [x: $x, y: $y, z: $z] to [x: $nx, y: $ny, z: $nz]")
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
        everywhere { x, y, z -> cells[Coord(x, y, z)]!!.calcNewStatus() }
    }

    private fun applyNewStatus() {
        everywhere { x, y, z -> cells[Coord(x, y, z)]!!.applyNewStatus() }
    }

    override fun toString(): String {
        val sbOverall = StringBuilder()
        for (z in minZ..maxZ) {
            val sbFrame = StringBuilder()
            sbFrame.append("Z index: $z:\n")
            var hadActiveCell = false

            for (y in minY..maxY) {
                for (x in minX..maxX) {
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
        return sbOverall.toString()
    }

    fun numActiveCubes(): Long {
        var count = 0L
        everywhere { x, y, z -> if (cells[Coord(x, y, z)]!!.state == State.ACTIVE) count++ }
        return count
    }
}