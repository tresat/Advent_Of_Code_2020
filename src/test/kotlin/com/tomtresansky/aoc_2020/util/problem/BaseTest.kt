package com.tomtresansky.aoc_2020.util.problem

import org.junit.jupiter.api.Test

abstract class BaseTest(private val problem: IDay<Any>) {
    @Test
    fun solvePart1() {
        val solution = problem.solvePart1()
        println ("Day ${problem.getDayNumber()}, Part 1: $solution")
    }

    @Test
    fun solvePart2() {
        val solution = problem.solvePart2()
        println ("Day ${problem.getDayNumber()}, Part 2: $solution")
    }
}
