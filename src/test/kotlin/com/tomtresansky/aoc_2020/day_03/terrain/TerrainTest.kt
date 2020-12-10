package com.tomtresansky.aoc_2020.day_03.terrain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TerrainTest {
    @DisplayName("Test that the getting can handle retrieval of coordinates past the natural end of the spec string")
    @Test
    fun testTerrainRowGetter() {
        val spec = "..#"
        val row = TerrainRow.fromSpec(spec)

        assertThat(row.getTerrainTypeAt(1)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(2)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(3)).isEqualTo(TerrainType.TREE)
        assertThat(row.getTerrainTypeAt(4)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(5)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(6)).isEqualTo(TerrainType.TREE)
        assertThat(row.getTerrainTypeAt(7)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(8)).isEqualTo(TerrainType.OPEN)
        assertThat(row.getTerrainTypeAt(9)).isEqualTo(TerrainType.TREE)
    }
}
