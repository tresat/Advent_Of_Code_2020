package com.tomtresansky.aoc_2020.day_17.game

enum class State(private val symbol: Char) {
    ACTIVE('#'),
    INACTIVE('.');

    companion object {
        fun from(c: Char) = values().find { it.symbol == c }!!
    }

    override fun toString() = symbol.toString()
}
