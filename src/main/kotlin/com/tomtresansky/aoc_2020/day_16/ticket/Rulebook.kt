package com.tomtresansky.aoc_2020.day_16.ticket

class Rulebook(val rules: List<FieldRule>) {
    fun invalidValues(ticket: Ticket) = ticket.values.filter { !isValid(it) }
    private fun isValid(value: Value) = rules.any { it.isValid(value) }
    fun isValid(ticket: Ticket) = ticket.values.all { isValid(it) }

    fun potentiallyMatchingRules(ticket: Ticket): Map<Index, Set<FieldRule>> {
        val result = mutableMapOf<Index, Set<FieldRule>>()

        ticket.values.forEachIndexed { index, value ->
            result[index] = rules.filter { it.isValid(value) }.toSet()
        }

        return result
    }
}
