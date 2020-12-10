package com.tomtresansky.aoc_2020.day_03.terrain

typealias Coords = Pair<Int, Int>

enum class TerrainType(val representation: Char) {
    OPEN('.'),
    TREE('#');

    companion object {
        fun fromSpec(spec: Char)= when(spec) {
            OPEN.representation -> OPEN
            TREE.representation -> TREE
            else -> throw IllegalTerrainTypeException(spec)
        }
    }

    class IllegalTerrainTypeException(spec: Char): RuntimeException("Unexpected char: $spec")
}

class TerrainRow(private val pattern: List<TerrainType>) {
    companion object {
        fun fromSpec(spec: String) = TerrainRow(spec.toCharArray().map { TerrainType.fromSpec(it) })
    }

    fun getTerrainTypeAt(coord: Int) = pattern[coord.rem(pattern.size)]
}

class Terrain(private val rows: List<TerrainRow>) {
    companion object {
        fun fromSpec(spec: List<String>) = Terrain(spec.map { TerrainRow.fromSpec (it) })
    }

    fun getTerrainTypeAt(coords: Coords): TerrainType {
        if (isOnMap(coords)) {
            return rows[coords.second].getTerrainTypeAt(coords.first)
        } else {
            throw IllegalCoordiinatesException(coords, rows.size)
        }
    }

    fun isOnMap(coords: Coords) = ((coords.first >= 0) && (coords.second in rows.indices))

    class IllegalCoordiinatesException(coords: Coords, rowCount: Int): RuntimeException("Coords: $coords are past the bottom of the map which is $rowCount high!")
}
