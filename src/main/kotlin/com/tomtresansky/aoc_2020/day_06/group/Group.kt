package com.tomtresansky.aoc_2020.day_06.group

typealias Answer = Char

typealias SerializedPerson = String
class Person(val answers: Set<Answer>) {
    companion object {
        fun deserialize(data: SerializedPerson) = Person(data.toCharArray().toSet())
    }
}

typealias SerializedGroup = List<SerializedPerson>
class Group(val people: List<Person>) {
    companion object {
        fun deserialize(data: SerializedGroup) = Group(data.map { Person.deserialize(it) })
    }

    fun totalUniqueAnswers() = people.map { it.answers }.fold(mutableSetOf<Answer>()) { curr, acc -> curr.addAll(acc); curr }.size
    fun universallyPresentAnswers() = people.flatMap { it.answers }.groupBy { it }.values.count { it.size == people.size }
}
