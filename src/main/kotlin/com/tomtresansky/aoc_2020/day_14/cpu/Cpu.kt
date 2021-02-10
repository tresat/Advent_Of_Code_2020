package com.tomtresansky.aoc_2020.day_14.cpu

import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import java.math.BigInteger

typealias Address = BigInteger
typealias Value = BigInteger

abstract class AbstractCpu {
    companion object {
        const val BITS = 36
    }

    protected var _mask: Bitmask? = null
    protected val _memory: MutableMap<Address, Value> = mutableMapOf()

    fun setMask(mask: Bitmask) {
        this._mask = mask
    }

    abstract fun setMemory(address: Address, value: Value)

    fun execute(command: ICommand) {
        command.execute(this)
    }

    fun sumMemory() = _memory.values.fold(BigInteger.ZERO) { acc, curr -> acc + curr }
}

class BasicCpu: AbstractCpu() {
    override fun setMemory(address: Address, value: Value) {
        _memory[address] = _mask!!.applyTo(value)
    }
}

class MemAddressDecoderCpu: AbstractCpu() {
    override fun setMemory(address: Address, value: Value) {
        val addressPattern = _mask!!.applyDecodingTo(address)

        val allAddresses = addressPattern.float()
        allAddresses.forEach { _memory[it] = value }
    }
}
