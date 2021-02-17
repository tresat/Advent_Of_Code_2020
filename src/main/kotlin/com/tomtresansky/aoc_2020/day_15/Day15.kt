package com.tomtresansky.aoc_2020.day_15

import com.tomtresansky.aoc_2020.day_15.game.Game
import com.tomtresansky.aoc_2020.day_15.game.Spoken
import com.tomtresansky.aoc_2020.util.problem.IDay

class Day15: IDay<List<Spoken>> {
    override fun getDayNumber() = 15

    override fun loadSingleInputElement(rawData: String) = rawData.split(',').map { it.toInt() }.toList()

    override fun solvePart1(): Long {
        val game = Game(loadInput()[0])
        return game.getSpokenValueOnTurn(2020).toLong()
    }

    override fun solvePart2(): Long {
        TODO("Not yet implemented")
    }
}
