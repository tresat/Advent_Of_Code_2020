package com.tomtresansky.aoc_2020.day_17.game.grid4

import com.tomtresansky.aoc_2020.day_17.game.ConwayCube
import com.tomtresansky.aoc_2020.day_17.game.Coord
import com.tomtresansky.aoc_2020.day_17.game.Slice
import com.tomtresansky.aoc_2020.day_17.game.State
import com.tomtresansky.aoc_2020.util.pow

data class Coord4(val x: Int, val y: Int, val z: Int, val w: Int): Coord {
    operator fun plus(other: Coord4) = Coord4(x + other.x, y + other.y, z + other.z, w + other.w)
}

class EnergyGrid4(initState: Slice, cyclesToRun: Int) {
    private val cells: MutableMap<Coord4, ConwayCube<Coord4>>
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
                val coord = Coord4(x - halfInit, y - halfInit, 0, 0)
                cells[coord] = ConwayCube(coord, initState.get(x, y))
            }
        }

        everywhere { x, y, z, w ->
            val coord = Coord4(x, y, z, w)
            if (!cells.contains(coord)) cells[coord] = ConwayCube(coord, State.INACTIVE)
        }

        linkNeighbors()
    }

    private fun everywhere(action: (x: Int, y: Int, z: Int, w: Int) -> Any) {
        for (w in -extreme..extreme) {
            for (z in -extreme..extreme) {
                for (y in -extreme..extreme) {
                    for (x in -extreme..extreme) {
                        action(x, y, z, w)
                    }
                }
            }
        }
    }

    private fun linkNeighbors() {
        everywhere(this::linkNeighbors)
    }

    private fun linkNeighbors(x: Int, y: Int, z: Int, w: Int) {
        val cell = cells[Coord4(x, y, z, w)]!!
        for (nw in w - 1..w + 1) {
            for (nz in z - 1..z + 1) {
                for (ny in y - 1..y + 1) {
                    for (nx in x - 1..x + 1) {
                        if (nx == x && ny == y && nz == z && nw == w) {
                            //println("Ignoring [x: $nx, y: $ny, z: $nz] - same cell")
                        } else if (nx !in -extreme..extreme) {
                            //println("Ignoring [x: $nx, y: $ny, z: $nz] - x out of bounds")
                        } else if (ny !in -extreme..extreme) {
                            //println("Ignoring [x: $nx, y: $ny, z: $nz] - y out of bounds")
                        } else if (nz !in -extreme..extreme) {
                            //println("Ignoring [x: $nx, y: $ny, z: $nz] - z out of bounds")
                        } else if (nw !in -extreme..extreme) {
                            //println("Ignoring [x: $nx, y: $ny, z: $nz] - z out of bounds")
                        } else {
                            val neighbor = cells[Coord4(nx, ny, nz, nw)]!!
                            cell.addNeighbor(neighbor)
                            //println("Linked [x: $x, y: $y, z: $z] to [x: $nx, y: $ny, z: $nz]")
                        }
                    }
                }
            }
        }
    }

    fun get(target: Coord4) = ConwayCube(cells[target]!!)
    fun set(target: Coord4, state: State) {
        cells[target]!!.state = state
    }

    fun step() {
        calcNewStatus()
        applyNewStatus()
    }

    private fun calcNewStatus() {
        //printActives("Current actives: ")
        everywhere { x, y, z, w -> cells[Coord4(x, y, z, w)]!!.calcNewStatus() }
    }

    private fun applyNewStatus() {
        everywhere { x, y, z, w -> cells[Coord4(x, y, z, w)]!!.applyNewStatus() }
        //printActives("New actives: ")
    }

    private fun printActives(msg: String) {
        println(msg)
        everywhere { x, y, z, w ->
            val cell = cells[Coord4(x, y, z, w)]!!
            if (cell.state == State.ACTIVE) {
                println("[x: $x, y: $y, z: $z, w: $w]")
            }
        }
        println()
    }

    override fun toString(): String {
        val sbOverall = StringBuilder()
        for (w in -extreme..extreme) {
            val sbFrame = StringBuilder()
            sbFrame.append("W index: $w:\n")
            var hadActiveCell = false
            for (z in -extreme..extreme) {
                sbFrame.append("Z index: $z:\n")

                for (y in -extreme..extreme) {
                    for (x in -extreme..extreme) {
                        val cell = cells[Coord4(x, y, z, w)]!!
                        sbFrame.append(cell)
                        if (cell.state == State.ACTIVE) {
                            hadActiveCell = true
                        }
                    }
                    sbFrame.appendLine()
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
        everywhere { x, y, z, w -> if (cells[Coord4(x, y, z, w)]!!.state == State.ACTIVE) count++ }
        return count
    }
}
