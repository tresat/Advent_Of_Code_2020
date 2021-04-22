package com.tomtresansky.aoc_2020.day_17.game.grid3

import com.tomtresansky.aoc_2020.day_17.game.ConwayCube
import com.tomtresansky.aoc_2020.day_17.game.Coord
import com.tomtresansky.aoc_2020.day_17.game.Slice
import com.tomtresansky.aoc_2020.day_17.game.State
import com.tomtresansky.aoc_2020.util.pow
import kotlin.collections.HashMap

data class Coord3(val x: Int, val y: Int, val z: Int): Coord {
    operator fun plus(other: Coord3) = Coord3(x + other.x, y + other.y, z + other.z)
}

class EnergyGrid3(initState: Slice, cyclesToRun: Int) {
    private val cells: MutableMap<Coord3, ConwayCube<Coord3>>
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
                val coord = Coord3(x - halfInit, y - halfInit, 0)
                cells[coord] = ConwayCube(coord, initState.get(x, y))
            }
        }

        everywhere { x, y, z ->
            val coord = Coord3(x, y, z)
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
        val cell = cells[Coord3(x, y, z)]!!
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
                        val neighbor = cells[Coord3(nx, ny, nz)]!!
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

    fun get(target: Coord3) = ConwayCube(cells[target]!!)
    fun set(target: Coord3, state: State) {
        cells[target]!!.state = state
    }

    fun step() {
        calcNewStatus()
        applyNewStatus()
    }

    private fun calcNewStatus() {
        //printActives("Current actives: ")
        everywhere { x, y, z -> cells[Coord3(x, y, z)]!!.calcNewStatus() }
    }

    private fun applyNewStatus() {
        everywhere { x, y, z -> cells[Coord3(x, y, z)]!!.applyNewStatus() }
        //printActives("New actives: ")
    }

    private fun printActives(msg: String) {
        println(msg)
        everywhere { x, y, z ->
            val cell = cells[Coord3(x, y, z)]!!
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
                    val cell = cells[Coord3(x, y, z)]!!
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
        everywhere { x, y, z -> if (cells[Coord3(x, y, z)]!!.state == State.ACTIVE) count++ }
        return count
    }
}