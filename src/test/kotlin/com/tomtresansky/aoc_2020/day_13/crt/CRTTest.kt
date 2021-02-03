package com.tomtresansky.aoc_2020.day_13.crt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CRTTest {
    private val solver: CRTer = CRTer()

    @Test
    fun test() {
        val e1 = (0 to 7)
        val e2 = (1 to 13)
        val gen1 = solver.buildGenerator(e1, e2)

        assertThat(gen1(0)).isEqualTo(77)

        val e3 = (4 to 15)
        val gen2 = solver.expandGenerator(gen1, e3)

        assertThat(gen2(0)).isEqualTo(896)
    }

    @Test
    fun testSample1() {
        val e1 = (0 to 17)
        val e2 = (2 to 13)
        val gen1 = solver.buildGenerator(e1, e2)

        assertThat(gen1(0)).isEqualTo(102)

        val e3 = (3 to 19)
        val gen2 = solver.expandGenerator(gen1, e3)

        assertThat(gen2(0)).isEqualTo(3417)
    }

    @Test
    fun testSample2() {
        val e1 = (0 to 67)
        val e2 = (1 to 7)
        val gen1 = solver.buildGenerator(e1, e2)

        //assertThat(gen1(0)).isEqualTo(102)

        val e3 = (2 to 59)
        val gen2 = solver.expandGenerator(gen1, e3)

        //assertThat(gen2(0)).isEqualTo(3417)

        val e4 = (3 to 61)
        val gen3 = solver.expandGenerator(gen2, e4)

        assertThat(gen3(0)).isEqualTo(754018)
    }
}