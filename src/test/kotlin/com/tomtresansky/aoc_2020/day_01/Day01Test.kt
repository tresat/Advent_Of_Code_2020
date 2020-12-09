package com.tomtresansky.aoc_2020.day_01

import org.junit.jupiter.api.Test

internal class Day01Test {
    @Test
    fun solvePart1() {
        val problem = Day01()
        val solution = problem.solve(2)
        println ("Day 1, Part 1: $solution")
    }

    @Test
    fun solvePart2() {
        val problem = Day01()
        val solution = problem.solve(3)
        println ("Day 1, Part 2: $solution")
    }
}