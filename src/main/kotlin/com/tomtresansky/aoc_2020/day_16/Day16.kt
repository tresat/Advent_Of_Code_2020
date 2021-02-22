package com.tomtresansky.aoc_2020.day_16

import com.tomtresansky.aoc_2020.day_16.ticket.FieldRule
import com.tomtresansky.aoc_2020.day_16.ticket.Rulebook
import com.tomtresansky.aoc_2020.day_16.ticket.Ticket
import java.io.File

class Day16 {
    companion object {
        const val INPUT_FILE_NAME = "day_16_input.txt"
        const val YOUR_TICKET_DELIMITER = "your ticket:"
        const val NEARBY_TICKETS_DELIMITER = "nearby tickets:"

        private fun readLinesFromFile(inputFileName: String): List<String> {
            val inputFile = File(this::class.java.getResource(inputFileName)!!.toURI())
            return inputFile.readLines()
        }

        private fun loadRulebook(lines: List<String>): Rulebook {
            val endLineNo = lines.indexOf("")
            val rules = lines.subList(0, endLineNo).map { FieldRule.deserialize(it) }
            return Rulebook(rules)
        }

        private fun loadYourTicket(lines: List<String>): Ticket {
            val ticketLineNo = lines.indexOf(YOUR_TICKET_DELIMITER) + 1
            return Ticket.deserialize(lines[ticketLineNo])
        }

        private fun loadNearbyTickets(lines: List<String>): List<Ticket> {
            val nearbyTicketsStartLineNo = lines.indexOf(NEARBY_TICKETS_DELIMITER) + 1
            return lines.subList(nearbyTicketsStartLineNo, lines.size).map { Ticket.deserialize(it) }
        }
    }

    fun solvePart1(): Int {
        val lines = readLinesFromFile(INPUT_FILE_NAME)
        val rulebook = loadRulebook(lines)
        //val yourTicket = loadYourTicket(lines)
        val nearbyTickets = loadNearbyTickets(lines)
        return nearbyTickets.flatMap { rulebook.invalidValues(it) }.sum()
    }
}
