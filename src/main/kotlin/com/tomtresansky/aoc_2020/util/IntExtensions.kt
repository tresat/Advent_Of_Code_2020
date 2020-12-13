package com.tomtresansky.aoc_2020.util

import kotlin.math.pow

infix fun Int.pow(exponent: Int): Int = toDouble().pow(exponent).toInt()
