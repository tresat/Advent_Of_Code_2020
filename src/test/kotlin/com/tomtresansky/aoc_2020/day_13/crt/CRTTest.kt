package com.tomtresansky.aoc_2020.day_13.crt

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CRTTest {
    private val solver: CRTer = CRTer()

    @Test
    fun test() {
        val e1 = (0 to 7)
        val e2 = (1 to 13)

        val solution: (Long) -> Long = solver.buildGenerator(e1, e2)

        assertThat(solution(0L)).isEqualTo(14L)
        assertThat(solution(1L)).isEqualTo(105L)
        assertThat(solution(2L)).isEqualTo(196L)
    }
}