package com.tomtresansky.aoc_2020.day_15.game

typealias Turn = Int
typealias Spoken = Int

class Game(startNums: List<Spoken>) {
    private val turns = mutableListOf<Spoken>()
    private val lastAppearances = mutableMapOf<Spoken, Turn>()
    private val secondToLastAppearances = mutableMapOf<Spoken, Turn>()

    init {
        startNums.forEach { setNextSpokenValue(it) }
    }

    private fun getLastTurnNum() = turns.size

    private fun setNextSpokenValue(spoken: Spoken) {
        turns.add(spoken)

        if (lastAppearances.containsKey(spoken)) {
            secondToLastAppearances[spoken] = lastAppearances[spoken]!!
        }

        lastAppearances[spoken] = getLastTurnNum()
    }

    private fun calcNextSpokenValue() {
        val lastSpoken = turns.last()
        val previousTurn = lastAppearances.getOrDefault(lastSpoken, 0)
        val secondToPreviousTurn = secondToLastAppearances.getOrDefault(lastSpoken, 0)
        val nextSpokenValue = (if (0 == secondToPreviousTurn) 0 else (previousTurn - secondToPreviousTurn))
        setNextSpokenValue(nextSpokenValue)
    }

    fun getSpokenValueOnTurn(requestedTurnNum: Turn): Spoken {
        while (requestedTurnNum > getLastTurnNum()) {
            calcNextSpokenValue()
        }

        return turns[requestedTurnNum - 1]
    }
}
