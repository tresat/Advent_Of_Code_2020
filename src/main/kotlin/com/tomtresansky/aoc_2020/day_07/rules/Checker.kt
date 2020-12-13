@file:Suppress("unused")

package com.tomtresansky.aoc_2020.day_07.rules

import com.google.common.graph.ValueGraph
import com.google.common.graph.ValueGraphBuilder

typealias StoragePossibility = List<Bag>
fun StoragePossibility.customToString() = joinToString(" -> ")

// Edge from A -> B represents A "is contained by" B
@Suppress("UnstableApiUsage")
class Checker(private val graph: ValueGraph<Bag, Int>) {
    companion object {
        fun fromRules(rules: List<Rule>): Checker {
            val builder = ValueGraphBuilder.directed().immutable<Bag, Int>()
            rules.forEach { rule ->
                rule.contents.forEach { (contained, count) -> builder.putEdgeValue(contained, rule.container, count) }
            }
            return Checker(builder.build())
        }
    }

    fun getStorageChainsFor(bag: Bag): List<StoragePossibility> {
        val startNode = graph.nodes().find { it == bag }!!
        return exhaustStorageChainFor(mutableListOf(startNode))
    }

    private fun exhaustStorageChainFor(currentPath: StoragePossibility): List<StoragePossibility> {
        val outNodes = graph.successors(currentPath.last())
        return if (outNodes.isEmpty()) {
            listOf(currentPath)
        } else {
            val resultPaths = mutableListOf<StoragePossibility>()
            outNodes.forEach { outNode ->
                resultPaths.addAll(exhaustStorageChainFor(currentPath + outNode))
            }
            resultPaths
        }
    }

    fun countContentsOf(bag: Bag): Long {
        val startNode = graph.nodes().find { it == bag }!!
        return exhaustContentsOf(startNode)
    }

    private fun exhaustContentsOf(bag: Bag): Long {
        val inNodes = graph.predecessors(bag)
        return if (inNodes.isEmpty()) {
            return 0
        } else {
            var childContentsCount: Long = 0
            inNodes.forEach { inNode ->
                val numContained = graph.edgeValue(inNode, bag)!!.get().toLong()
                childContentsCount += numContained * (1 + countContentsOf(inNode))
            }
            childContentsCount
        }
    }
}
