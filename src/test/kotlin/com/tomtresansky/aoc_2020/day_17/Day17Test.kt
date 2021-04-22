package com.tomtresansky.aoc_2020.day_17

import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun solvePart1() {
        val problem = Day17()
        val solution = problem.solvePart1()
        println ("Day 17, Part 1: $solution")
        assert(solution == 313L) // 112L for sample
    }

    @Test
    fun solvePart2() {
        val problem = Day17()
        val solution = problem.solvePart2()
        println ("Day 17, Part 2: $solution")
        assert(solution == 2640L) // 848L for sample
    }
}
