package com.tomtresansky.aoc_2020.day_15

import com.tomtresansky.aoc_2020.day_15.game.Game
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.junit.jupiter.api.Test

class Day15Test: BaseTest(Day15()) {
    @Test
    fun testSampleInput() {
        val game = Game(listOf(0,3,6))
        /*for (turnNum in 1..10) {
            println ("Turn: $turnNum, Spoken: ${game.getSpokenValueOnTurn(turnNum)}")
        }*/
        assert(game.getSpokenValueOnTurn(10) == 0)
    }
}
