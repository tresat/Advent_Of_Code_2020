package com.tomtresansky.aoc_2020.day_11.grid

enum class VisibilityAlgorithm(val visibilityThreshold: Int) {
    ADJACENT_SEATS(4){
        override fun getNeighbors(pos: Coord, grid: Grid): List<Cell> {
            val neighbors = mutableListOf<Cell>()
            for (r in pos.first - 1..pos.first + 1) {
                for (c in pos.second - 1..pos.second + 1) {
                    if (r to c != pos) {
                        grid.getCell(r to c)?.apply { neighbors.add(this) }
                    }
                }
            }
            return neighbors
        }
    },

    VISIBLE_SEATS(5) {
        override fun getNeighbors(pos: Coord, grid: Grid): List<Cell> {
            val upNeighbor = nextCellWithSeat(pos, grid) { (it.first - 1 to it.second)}
            val downNeighbor = nextCellWithSeat(pos, grid) { (it.first + 1 to it.second)}
            val leftNeighbor = nextCellWithSeat(pos, grid) { (it.first to it.second - 1)}
            val rightNeighbor = nextCellWithSeat(pos, grid) { (it.first to it.second + 1)}
            val ulNeighbor = nextCellWithSeat(pos, grid) { (it.first - 1 to it.second - 1)}
            val urNeighbor = nextCellWithSeat(pos, grid) { (it.first - 1 to it.second + 1)}
            val dlNeighbor = nextCellWithSeat(pos, grid) { (it.first + 1 to it.second - 1)}
            val drNeighbor = nextCellWithSeat(pos, grid) { (it.first + 1 to it.second + 1)}
            return listOfNotNull(upNeighbor, downNeighbor, leftNeighbor, rightNeighbor, ulNeighbor, urNeighbor, dlNeighbor, drNeighbor)
        }

        private fun nextCellWithSeat(pos: Coord, grid: Grid, mover: (Coord) -> Coord): Cell? {
            var cur = pos
            do {
                cur = mover.invoke(cur)
                val cell = grid.getCell(cur)
                if (cell?.hasSeat() == true) {
                    return cell
                }
            } while (cell != null)
            return null
        }
    };

    abstract fun getNeighbors(pos: Coord, grid: Grid): List<Cell>
}

enum class Status(private val icon: Char) {
    NO_SEAT('.'),
    EMPTY_SEAT('L'),
    OCCUPIED_SEAT('#');

    companion object {
        fun from(data: Char) = values().find { it.icon == data }!!
    }

    override fun toString() = icon.toString()
}

class Cell(private var currStatus: Status, private var newStatus: Status? = null) {
    companion object {
        fun deserialize(data: Char) = Cell(Status.from(data))
    }

    fun isOccupied() = (currStatus == Status.OCCUPIED_SEAT)
    fun hasSeat() = (currStatus != Status.NO_SEAT)

    fun calcNewStatus(neighbors: List<Cell>, visibilityThreshold: Int) {
        newStatus = when (currStatus) {
            Status.NO_SEAT -> Status.NO_SEAT
            Status.EMPTY_SEAT -> if (neighbors.none { it.isOccupied() }) Status.OCCUPIED_SEAT else Status.EMPTY_SEAT
            Status.OCCUPIED_SEAT -> if (neighbors.count { it.isOccupied() } >= visibilityThreshold) Status.EMPTY_SEAT else Status.OCCUPIED_SEAT
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

data class Snapshot(private val textRows: List<String>) {
    override fun toString() = textRows.joinToString("\n")
}

class Grid(private val rows: List<Row>, private val algorithm: VisibilityAlgorithm) {
    private fun takeSnapshot() = Snapshot(rows.map { row -> row.joinToString("") { it.toString() } })

    fun step() {
        rows.indices.forEach { r ->
            rows[r].indices.forEach { c ->
                val pos = r to c
                val cell = getCell(pos)!!
                val neighbors = algorithm.getNeighbors(r to c, this)
                cell.calcNewStatus(neighbors, algorithm.visibilityThreshold)
            }
        }

        rows.indices.forEach { r ->
            rows[r].indices.forEach { c ->
                getCell(r to c)?.applyNewStatus()
            }
        }
    }

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

    fun countOccupiedSeats() = rows.flatten().count { it.isOccupied() }

    private fun isValidPosition(pos: Coord) = ((pos.first in rows.indices) && pos.second in rows[pos.first].indices)
    fun getCell(pos: Coord) =
        if (isValidPosition(pos)) {
            rows[pos.first][pos.second]
        } else {
            null
        }

    override fun toString() = takeSnapshot().toString()
}
