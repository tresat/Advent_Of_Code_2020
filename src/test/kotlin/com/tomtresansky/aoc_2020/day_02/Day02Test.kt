package com.tomtresansky.aoc_2020.day_02

import com.tomtresansky.aoc_2020.day_02.policy.PasswordPolicyType
import org.junit.jupiter.api.Test

internal class Day02Test {
    @Test
    fun solvePart1() {
        val problem = Day02()
        val solution = problem.solve(PasswordPolicyType.PREVIOUS)
        println ("Day 2, Part 1: $solution")
    }

    @Test
    fun solvePart2() {
        val problem = Day02()
        val solution = problem.solve(PasswordPolicyType.OFFICIAL)
        println ("Day 2, Part 2: $solution")
    }
}