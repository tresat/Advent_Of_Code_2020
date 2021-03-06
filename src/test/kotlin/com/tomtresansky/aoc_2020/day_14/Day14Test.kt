package com.tomtresansky.aoc_2020.day_14

import com.tomtresansky.aoc_2020.day_14.cpu.BasicCpu
import com.tomtresansky.aoc_2020.day_14.cpu.Value
import com.tomtresansky.aoc_2020.day_14.mask.Bitmask
import com.tomtresansky.aoc_2020.day_14.mask.convertToBinary
import com.tomtresansky.aoc_2020.day_14.mask.convertToNumber
import com.tomtresansky.aoc_2020.util.problem.BaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day14Test: BaseTest(Day14()) {
    @Test
    fun testSample() {
        val problem = Day14()
        val commands = problem.loadInput("day_14_sample.txt")
        val cpu = BasicCpu()
        commands.forEach { cpu.execute(it) }
        assertThat(cpu.sumMemory()).isEqualTo(165)
    }

    @Test
    fun testSampleNum1() {
        val mask = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val input: Value = BigInteger.valueOf(11)
        val binaryInput = input.convertToBinary()
        val result = mask.applyTo(input)
        val binaryResult = result.convertToBinary()
        //println ("value:   $binaryInput")
        //println ("mask:    $mask")
        //println ("result:  $binaryResult")
        assertThat(binaryResult).isEqualTo("000000000000000000000000000001001001")
    }

    @Test
    fun testSampleNum2() {
        val mask = Bitmask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        val input: Value = BigInteger.valueOf(101)
        val binaryInput = input.convertToBinary()
        val result = mask.applyTo(input)
        val binaryResult = result.convertToBinary()
        //println ("value:   $binaryInput")
        //println ("mask:    $mask")
        //println ("result:  $binaryResult")
        assertThat(binaryResult).isEqualTo("000000000000000000000000000001100101")
    }

    @Test
    fun testLongNumConverters() {
        val input: Value = BigInteger.valueOf(468673978L)
        val binary = input.convertToBinary()
        val result = binary.convertToNumber()
        assertThat(result).isEqualTo(input)
    }

    @Test
    fun testApplyDecodingTo() {
        val mask = Bitmask("000000000000000000000000000000X1001X")
        val input: Value = BigInteger.valueOf(42)
        val binaryInput = input.convertToBinary()
        val result = mask.applyDecodingTo(input)
        //println ("value:   $binaryInput")
        //println ("mask:    $mask")
        //println ("result:  $result")
        assertThat(result).isEqualTo(Bitmask("000000000000000000000000000000X1101X"))
    }

    @Test
    fun testFloat() {
        val mask = Bitmask("000000000000000000000000000000X1101X")
        val float = mask.float()
        assertThat(float).containsExactlyInAnyOrder(BigInteger.valueOf(26),
                                                    BigInteger.valueOf(27),
                                                    BigInteger.valueOf(58),
                                                    BigInteger.valueOf(59))
    }
}
