package com.tomtresansky.aoc_2020.day_14.cpu

import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import java.math.BigInteger

typealias Address = BigInteger
typealias Value = BigInteger

class Cpu {
    companion object {
        const val BITS = 36
    }

    private var mask: Bitmask? = null
    private val memory: MutableMap<Address, Value> = mutableMapOf()

    fun setMask(mask: Bitmask) {
        this.mask = mask
    }

    fun setMemory(address: Address, value: Value) {
        memory[address] = mask!!.applyTo(value)
    }

    fun execute(command: ICommand) {
        command.execute(this)
    }

    fun sumMemory() = memory.values.fold(BigInteger.ZERO) { acc, curr -> acc + curr }
}
