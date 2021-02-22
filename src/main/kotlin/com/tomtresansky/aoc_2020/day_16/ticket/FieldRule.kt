package com.tomtresansky.aoc_2020.day_16.ticket

class FieldRule(private val name: String, private val ranges: List<ValidRange>) {
    companion object {
        private val REGEX = """([a-zA-Z\s]+):\s(\d+)\-(\d+)\sor\s(\d+)\-(\d+)""".toRegex()
        private const val NAME_IDX = 1
        private const val FIRST_LOW_IDX = 2
        private const val FIRST_HIGH_IDX = 3
        private const val SECOND_LOW_IDX = 4
        private const val SECOND_HIGH_IDX = 5

        fun deserialize(line: String): FieldRule {
            val matchResult = REGEX.matchEntire(line)
            with (matchResult?.groupValues!!) {
                val name = get(NAME_IDX)
                val firstLow = get(FIRST_LOW_IDX).toInt()
                val firstHigh = get(FIRST_HIGH_IDX).toInt()
                val secondLow = get(SECOND_LOW_IDX).toInt()
                val secondHigh = get(SECOND_HIGH_IDX).toInt()
                return FieldRule(name, listOf(ValidRange(firstLow, firstHigh), ValidRange(secondLow, secondHigh)))
            }
        }
    }

    fun isValid(target: Int) = ranges.any { it.contains(target) }
}
