package com.tomtresansky.aoc_2020.day_16

import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun solvePart1() {
        val problem = Day16()
        val solution = problem.solvePart1()
        println ("Day 16, Part 1: $solution")
        assert(solution == 26869)
    }

    @Test
    fun solvePart2() {
        val problem = Day16()
        val solution = problem.solvePart2()
        println ("Day 16, Part 2: $solution")
        assert(solution == 855275529001L)
    }
}
