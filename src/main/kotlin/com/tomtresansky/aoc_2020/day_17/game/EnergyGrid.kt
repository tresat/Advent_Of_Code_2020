package com.tomtresansky.aoc_2020.day_17.game

data class Coord(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y, z + other.z)
}

enum class State(private val char: Char) {
    ACTIVE('#'),
    INACTIVE('.');

    companion object {
        fun from(c: Char): State {

        }
    }

    override fun toString() = char.toString()
}

class ConwayCube(var state: State)

class Slice(text: List<String>) {
    private val cells: MutableList<MutableList<ConwayCube>> = mutableListOf()
    init {
        text.forEach {
            val row = it.toCharArray().map { c-> ConwayCube(State.from(c)) }.toMutableList()
            cells.add(row)
        }
        assertSquare()
    }

    private fun assertSquare() = cells.all { r -> r.size == cells.size }
    fun center() = cells.indices.last / 2
    fun get(x: Int, y: Int) = cells[x][y]
    fun size() = cells.size
}

class EnergyGrid(initState: Slice) {
    private val cells: MutableList<MutableList<MutableList<ConwayCube?>>> = mutableListOf()
    private val center: Coord

    init {
        val centerIdx = initState.center()
        center = Coord(centerIdx, centerIdx, 0)

        for (x in 0..initState.size()) {
            for (y in 0..initState.size()) {
                if (cells.size < x) {
                    cells.add(mutableListOf())
                }
                if (cells[x].size < y) {
                    cells.add(mutableListOf())
                }
                cells[x][y].add(initState.get(x, y))
            }
        }
    }

    fun get(target: Coord): ConwayCube {
        val adjusted = target + center
        val xSlice = cells[adjusted.x]
        val yLine = xSlice[adjusted.y]
        return yLine[adjusted.z]!!
    }

    fun set(target: Coord, state: State) {
        val adjusted = target + center
        val xSlice = cells[adjusted.x]
        val yLine = xSlice[adjusted.y]
        val cube = yLine[adjusted.z]

        if (null == cube) {
            yLine[adjusted.z] = ConwayCube(state)
        } else {
            cube.state = state
        }
    }

    fun print() {
        TODO()
    }
}