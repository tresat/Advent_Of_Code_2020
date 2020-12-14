@file:Suppress("UnstableApiUsage")

package com.tomtresansky.aoc_2020.day_10.adapter

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder

typealias Path = List<Adapter>

class Stacker(private val adapters: List<Adapter>) {
    fun inclusiveStack(): List<Adapter> {
        return adapters.sortedBy { it.rating }
    }

    fun exclusiveStacks(): List<Path> {
        val inclusiveStack = inclusiveStack()
        val graph = connectivityGraph(inclusiveStack)
        return graph.findAllPathsBetween(listOf(inclusiveStack[0]), inclusiveStack[inclusiveStack.lastIndex])
    }

    fun countExclusiveStacks(): Int {
        val inclusiveStack = inclusiveStack()
        val graph = connectivityGraph(inclusiveStack)
        return graph.countAllPathsBetween(listOf(inclusiveStack[0]), inclusiveStack[inclusiveStack.lastIndex])
    }

    private fun connectivityGraph(data: List<Adapter>): Graph<Adapter> {
        val addedEdges = mutableSetOf<Pair<Adapter, Adapter>>()
        val builder = GraphBuilder.directed().immutable<Adapter>()
        val windows = data.windowed(4)
        (windows + buildRemainderWindows(windows[windows.lastIndex])).forEach { window ->
            val sourceAdapter = window[0]
            val targetAdapters = window.subList(1, window.size).filter { it.rating - sourceAdapter.rating <= 3 }
            targetAdapters.forEach { targetAdapter ->
                if (addedEdges.add(sourceAdapter to targetAdapter)) {
                    builder.putEdge(sourceAdapter, targetAdapter)
                }
            }
        }
        return builder.build()
    }

    private fun buildRemainderWindows(path: Path): List<Path> {
        val starts = path.subList(1, path.lastIndex)
        return starts.map { path.subList(path.indexOf(it), path.lastIndex + 1) }
    }
}

// Uses lots of memory creating many, many lists
private fun Graph<Adapter>.findAllPathsBetween(currentPath: Path, target: Adapter): List<Path> =
    if (currentPath[currentPath.lastIndex] == target) {
        // println("FOUND: ${currentPath.joinToString(" -> ")}")
        listOf(currentPath)
    } else {
        // println("SEARCHING: ${currentPath[currentPath.lastIndex]}")
        val nexts = this.successors(currentPath[currentPath.lastIndex])
        nexts.map { next -> findAllPathsBetween(currentPath + next, target) }.flatten()
    }

private fun Graph<Adapter>.countAllPathsBetween(currentPath: Path, target: Adapter): Int =
    if (currentPath[currentPath.lastIndex] == target) {
        println("FOUND: ${currentPath.joinToString(" -> ")}")
        1
    } else {
        println("SEARCHING: ${currentPath[currentPath.lastIndex]}")
        val nexts = this.successors(currentPath[currentPath.lastIndex])
        nexts.map { next -> findAllPathsBetween(currentPath + next, target) }.size
    }

