package com.tomtresansky.aoc_2020.day_16.ticket

class Rulebook(private val rules: List<FieldRule>) {
    fun invalidValues(ticket: Ticket) = ticket.values.filter { !isValid(it) }
    private fun isValid(value: Value) = rules.any { it.isValid(value) }
}
