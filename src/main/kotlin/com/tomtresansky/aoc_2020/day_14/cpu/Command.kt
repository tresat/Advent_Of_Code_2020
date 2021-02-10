package com.tomtresansky.aoc_2020.day_14.cpu

import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import java.math.BigInteger

interface ICommand {
    fun execute(cpu: AbstractCpu)
}

class SetMemoryCommand(private val address: Address, private val value: Value): ICommand {
    companion object {
        const val PREFIX = "mem"
        private val COMMAND_REGEX = """mem\[(\d+)]\s=\s(\d+)""".toRegex()
        private const val ADDRESS_INDEX = 1
        private const val VALUE_INDEX = 2

        fun deserialize(rawData: String): SetMemoryCommand {
            val matchResult = COMMAND_REGEX.matchEntire(rawData)
            with (matchResult?.groupValues!!) {
                val address = BigInteger.valueOf(get(ADDRESS_INDEX).toLong())
                val value = BigInteger.valueOf(get(VALUE_INDEX).toLong())
                return SetMemoryCommand(address, value)
            }
        }
    }

    override fun execute(cpu: AbstractCpu) {
        cpu.setMemory(address, value)
    }
}

class SetBitmaskCommand(private val mask: Bitmask): ICommand {
    companion object {
        fun deserialize(rawData: String) = SetBitmaskCommand(Bitmask(rawData))
    }

    override fun execute(cpu: AbstractCpu) {
        cpu.setMask(mask)
    }
}
