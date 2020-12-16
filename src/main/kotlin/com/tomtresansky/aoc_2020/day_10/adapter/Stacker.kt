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

    fun countExclusiveStacks(): Long {
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
fun Graph<Adapter>.findAllPathsBetween(currentPath: Path, target: Adapter) = this.findAllPathsBetween(currentPath, target, mutableMapOf())
fun Graph<Adapter>.findAllPathsBetween(currentPath: Path, target: Adapter, cache: MutableMap<Adapter, List<Path>>): List<Path> {
    val end = currentPath[currentPath.lastIndex]
    if (!cache.containsKey(end)) {
        if (end == target) {
            println("FOUND: ${currentPath.joinToString(" -> ")}")
            cache[end] = listOf(currentPath)
        } else {
            println("SEARCHING: ${currentPath[currentPath.lastIndex]}")
            val nexts = this.successors(currentPath[currentPath.lastIndex])
            cache[end] = nexts.flatMap { next -> findAllPathsBetween(currentPath + next, target, cache) }
        }
    } else { println("CACHE HIT: $end") }
    return cache[end]!!
}

fun Graph<Adapter>.countAllPathsBetween(currentPath: Path, target: Adapter) = this.countAllPathsBetween(currentPath, target, mutableMapOf())
fun Graph<Adapter>.countAllPathsBetween(currentPath: Path, target: Adapter, cache: MutableMap<Adapter, Long>): Long {
    val end = currentPath[currentPath.lastIndex]
    if (!cache.containsKey(end)) {
        if (currentPath[currentPath.lastIndex] == target) {
            println("FOUND: ${currentPath.joinToString(" -> ")}")
            cache[end] = 1
        } else {
            println("SEARCHING: ${currentPath[currentPath.lastIndex]}")
            val nexts = this.successors(currentPath[currentPath.lastIndex])
            cache[end] = nexts.map { next -> countAllPathsBetween(currentPath + next, target, cache) }.sum()
            println("STORED: $end = ${cache[end]}")
        }
    } else { println("CACHE HIT: $end = ${cache[end]}") }
    return cache[end]!!
}
