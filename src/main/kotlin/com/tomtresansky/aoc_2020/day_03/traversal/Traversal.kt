package com.tomtresansky.aoc_2020.day_03.traversal

import com.tomtresansky.aoc_2020.day_03.terrain.Coords
import com.tomtresansky.aoc_2020.day_03.terrain.Terrain
import com.tomtresansky.aoc_2020.day_03.terrain.TerrainType

typealias Motion = Pair<Int, Int>
typealias Path = List<Pair<Int, Int>>

private fun Coords.move(motion: Motion): Coords = Coords(this.first + motion.first, this.second + motion.second)
fun Path.countTrees(terrain: Terrain) = this.map { terrain.getTerrainTypeAt(it) }.count { it == TerrainType.TREE }

class Traversal {
    companion object {
        fun fullFromTopLeft(terrain: Terrain, motion: Motion): Path {
            val path = mutableListOf<Coords>()

            var pos = (0 to 0)
            while (true) {
                pos = pos.move(motion)

                if (terrain.isOnMap(pos)) {
                    path += pos
                } else {
                    break
                }
            }

            return path
        }
    }
}