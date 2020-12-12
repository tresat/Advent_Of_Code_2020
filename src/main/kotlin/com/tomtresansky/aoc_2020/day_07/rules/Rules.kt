package com.tomtresansky.aoc_2020.day_07.rules

typealias SerializedRule = String
typealias Adjective = String
typealias Color = String
typealias SerializedContents = String
typealias SerializedContent = String

@Suppress("DataClassPrivateConstructor")
data class Bag private constructor(val adjective: Adjective, val color: Color) {
    companion object Registry {
        private val bags = mutableMapOf<Adjective, MutableMap<Color, Bag>>()

        fun get(adjective: Adjective, color: Color) = bags.computeIfAbsent(adjective) { mutableMapOf() }
                                                          .computeIfAbsent(color) { Bag(adjective, color) }
    }

    override fun toString() = "[$adjective $color]"
}

data class Rule(val container: Bag, val contents: Map<Bag, Int>) {
    companion object {
        private val RULE_REGEX = """(\w+)\s(\w+)\sbags\scontain\s(.*)\.""".toRegex()
        private const val ADJECTIVE_IDX = 1
        private const val COLOR_IDX = 2
        private const val CONTENTS_IDX = 3

        private val SINGLE_CONTENT_NO_OTHER_BAGS_MARKER = "no other bags"
        private val SINGLE_CONTENT_REGEX = """(\d+)\s(\w+)\s(\w+)\sbags?""".toRegex()
        private const val CONTENT_COUNT_IDX = 1
        private const val CONTENT_ADJECTIVE_IDX = 2
        private const val CONTENT_COLOR_IDX = 3

        fun deserialize(data: SerializedRule): Rule {
            val matchResult = RULE_REGEX.matchEntire(data)
            with (matchResult?.groupValues!!) {
                val bag = Bag.get(get(ADJECTIVE_IDX), get(COLOR_IDX))
                val contents = deserializeContents(get(CONTENTS_IDX))
                return Rule(bag, contents)
            }
        }

        private fun deserializeContents(data: SerializedContents) = data.split(", ").filter { SINGLE_CONTENT_NO_OTHER_BAGS_MARKER != it }.associate { deserializeSingleContent(it) }

        private fun deserializeSingleContent(data: SerializedContent): Pair<Bag, Int> {
            val matchResult = SINGLE_CONTENT_REGEX.matchEntire(data)
            with(matchResult?.groupValues!!) {
                val bag = Bag.get(get(CONTENT_ADJECTIVE_IDX), get(CONTENT_COLOR_IDX))
                val count = get(CONTENT_COUNT_IDX).toInt()
                return (bag to count)
            }
        }
    }
}
