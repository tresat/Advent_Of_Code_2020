package com.tomtresansky.aoc_2020.day_17.game

class Slice(text: List<String>) {
    private val cells: MutableList<MutableList<State>> = mutableListOf()
    init {
        text.forEach {
            val row = it.toCharArray().map { c-> State.from(c) }.toMutableList()
            cells.add(row)
        }
        assert(cells.all { r -> r.size == cells.size })
    }

    fun get(x: Int, y: Int) = cells[y][x]
    fun size() = cells.size
}
