package com.tomtresansky.aoc_2020.day_13.crt

typealias Offset = Int
typealias Id = Int
typealias Element = Pair<Offset, Id>
typealias Generator = (Long) -> Long

fun Element.offset() = this.first
fun Element.id() = this.second

class CRTer {
    fun buildGenerator(lowMod: Element, highMod: Element): Generator {
        assert(lowMod.id() > highMod.offset())
        val constant = findConstant(lowMod, highMod)
        return { n: Long -> constant + ((lowMod.id() * highMod.id())) * n }
    }

    private fun findConstant(lowMod: Element, highMod: Element): Long {
        var candidate = lowMod.id().toLong()
        while (!isSolution(lowMod, highMod, candidate)) {
            candidate += lowMod.id()
        }
        return candidate
    }

    private fun isSolution(lowMod: Element, highMod: Element, candidate: Long): Boolean {
        return ((candidate).rem(lowMod.id()) == lowMod.offset().toLong())
                && ((candidate).rem(highMod.id()) == highMod.offset().toLong())
    }
}