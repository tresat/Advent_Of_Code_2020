package com.tomtresansky.aoc_2020.day_13.crt

typealias Offset = Int
typealias Id = Int
typealias Element = Pair<Offset, Id>

fun Element.offset() = this.first
fun Element.id() = this.second

class Generator(val constant: Long, val multiplicand: Long) {
    operator fun invoke(n: Long) = constant + (multiplicand * n)
}

class CRTer {
    fun buildGenerator(e1: Element, e2: Element): Generator {
        val constant = if (e1.offset() <= e2.offset()) {
            findConstant(e1, e2)
        } else {
            findConstant(e2, e1)
        }

        return Generator(constant, (e1.id() * e2.id()).toLong())
    }

    private fun findConstant(lowElem: Element, highElem: Element): Long {
        assert(lowElem.offset() <= highElem.offset())
        var candidate = lowElem.id().toLong()
        while (!isSolution(candidate, lowElem, highElem)) {
            candidate += lowElem.id()
        }
        return candidate
    }

    private fun isSolution(candidate: Long, vararg tests: Element): Boolean {
        return tests.all {
            (candidate + it.offset()).rem(it.id()) == 0L
        }
    }

    fun expandGenerator(generator: Generator, nextElem: Element): Generator {
        var candidateIndex = 0L
        var candidate = generator(candidateIndex)
        while (!isSolution(candidate, nextElem)) {
            candidate = generator(++candidateIndex)
        }
        return Generator(candidate, generator.multiplicand * nextElem.id())
    }

    fun buildGenerator(vararg elems: Element): Generator = buildGenerator(elems.toList())

    fun buildGenerator(elems: List<Element>): Generator {
        return if (elems.size == 1) {
            Generator(elems[0].offset().toLong(), elems[0].id().toLong())
        } else {
            var generator = buildGenerator(elems[0], elems[1])
            elems.toList().subList(2, elems.size).forEach {
                generator = expandGenerator(generator, it)
            }
            generator
        }
    }
}