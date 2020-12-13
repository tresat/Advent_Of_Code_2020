package com.tomtresansky.aoc_2020.day_10.adapter

class Stacker(private val adapters: List<Adapter>) {
    fun stack(): List<Adapter> {
        return adapters.sortedBy { it.rating }
    }
}
