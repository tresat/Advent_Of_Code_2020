package com.tomtresansky.aoc_2020.util

import kotlin.math.pow

infix fun Long.pow(exponent: Int): Long = toDouble().pow(exponent).toLong()