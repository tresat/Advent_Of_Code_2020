package com.tomtresansky.aoc_2020.day_11.grid

enum class Status(private val icon: Char) {
    NO_SEAT('.'),
    EMPTY_SEAT('L'),
    OCCUPIED_SEAT('#');

    companion object {
        fun from(data: Char) = values().find { it.icon == data }!!
    }

    override fun toString() = icon.toString()
}

class Cell(var currStatus: Status, var newStatus: Status? = null) {
    companion object {
        private const val TOO_CROWDED_THRESHOLD = 4
        fun deserialize(data: Char) = Cell(Status.from(data))
    }

    private val neighboors = mutableSetOf<Cell>()

    fun addNeighboor(neighboor: Cell) = neighboors.add(neighboor)

    fun isOccupied() = (currStatus == Status.OCCUPIED_SEAT)

    fun calcNewStatus() {
        newStatus = when (currStatus) {
            Status.NO_SEAT -> Status.NO_SEAT
            Status.EMPTY_SEAT -> if (neighboors.none { it.isOccupied() }) Status.OCCUPIED_SEAT else Status.EMPTY_SEAT
            Status.OCCUPIED_SEAT -> if (neighboors.count { it.isOccupied() } >= TOO_CROWDED_THRESHOLD) Status.EMPTY_SEAT else Status.OCCUPIED_SEAT
        }
    }

    fun applyNewStatus() {
        currStatus = newStatus!!
        newStatus = null
    }

    override fun toString() = currStatus.toString()
}

typealias Row = List<Cell>
typealias Coord = Pair<Int, Int>

class Grid(private val rows: List<Row>) {
    init {
        for (r in rows.indices) {
            val row = rows[r]
            for (c in row.indices) {
                val current = (r to c)
                linkIfPossible(current, (r - 1 to c - 1))
                linkIfPossible(current, (r - 1 to c))
                linkIfPossible(current, (r - 1 to c + 1))
                linkIfPossible(current, (r to c - 1))
                linkIfPossible(current, (r to c + 1))
                linkIfPossible(current, (r + 1 to c - 1))
                linkIfPossible(current, (r + 1 to c))
                linkIfPossible(current, (r + 1 to c + 1))
            }
        }
    }

    private fun linkIfPossible(from: Coord, to: Coord) {
        if (from.first in rows.indices && to.first in rows.indices
                && from.second in rows[from.first].indices && to.second in rows[to.first].indices) {
            val fromCell = rows[from.first][from.second]
            val toCell = rows[to.first][to.second]
            fromCell.addNeighboor(toCell)
        }
    }

    fun print() {
        takeSnapshot().textRows.forEach { println(it) }
    }

    private fun takeSnapshot() = Snapshot(rows.map { row -> row.joinToString("") { it.toString() } })

    fun step() {
        eachCell { it.calcNewStatus() }
        eachCell { it.applyNewStatus() }
    }

    private fun eachCell(action: (Cell) -> Unit ) = rows.flatten().forEach { action(it) }

    fun stepUntilStable(): Int {
        var numSteps = 0
        do {
            val current = takeSnapshot()
            step()
            numSteps++
            val next = takeSnapshot()
        } while (current != next)
        return numSteps
    }

    fun countOccupiedSeats(): Long {
        var count: Long = 0
        eachCell { if (it.isOccupied()) count++ }
        return count
    }
}

data class Snapshot(val textRows: List<String>) {
    override fun toString() = textRows.joinToString { "\n" }
}
