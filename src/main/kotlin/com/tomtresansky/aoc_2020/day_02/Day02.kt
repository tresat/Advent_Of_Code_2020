package com.tomtresansky.aoc_2020.day_02

import java.io.File

@Suppress("SameParameterValue")
class Day02 {
    companion object {
        const val INPUT_FILE_NAME = "day_02_input.txt"
        const val MIN_OCCURS_IDX = 1
        const val MAX_OCCURS_IDX = 2
        const val KEY_CHAR_IDX = 3
        const val PASSWORD_IDX = 4
    }

    fun solve(): Int {
        val inputLines = readPasswordDataFile(INPUT_FILE_NAME)
        return inputLines.count { hasValidPassword(it) }
    }

    private fun readPasswordDataFile(inputFileName: String): List<String> {
        val inputFile = File(this::class.java.getResource(inputFileName).toURI())
        return inputFile.readLines()
    }

    private fun hasValidPassword(line: String): Boolean {
        val regex = """(\d+)-(\d+)\s(\w):\s(\w+)""".toRegex()
        val matchResult = regex.matchEntire(line)

        with (matchResult?.groupValues!!) {
            val minOccurs = get(MIN_OCCURS_IDX).toInt()
            val maxOccurs = get(MAX_OCCURS_IDX).toInt()
            val keyChar = get(KEY_CHAR_IDX)[0]

            val password = get(PASSWORD_IDX)
            val policy = PasswordPolicy(minOccurs, maxOccurs, keyChar)
            return policy.isValid(password)
        }
    }

    private class PasswordPolicy(private val minOccurs: Int, private val maxOccurs: Int, private val key: Char) {
        fun isValid(password: String): Boolean {
            val keyCount = password.toCharArray().count { it == key }
            return (keyCount in minOccurs..maxOccurs)
        }
    }
}