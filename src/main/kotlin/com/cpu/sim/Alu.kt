package com.cpu.sim

/**
 * Arithmetic Logic Unit (ALU).
 *
 * This component performs mathematical operations.
 * In the SAP-1 architecture, it sits between Register A and Register B.
 * It is "combinational logic," meaning it calculates instantly based on inputs.
 */
class Alu {
    /**
     * Adds two 8-bit integers.
     * Simulates wrapping behavior (overflow) using 0xFF mask.
     */
    fun add(
        val1: Int,
        val2: Int,
    ): Int {
        val sum = val1 + val2
        // If sum is 256, 'and 0xFF' makes it 0.
        return sum and 0xFF
    }

    /**
     * Subtracts val2 from val1.
     * Simulates wrapping behavior (underflow).
     */
    fun subtract(
        val1: Int,
        val2: Int,
    ): Int {
        val diff = val1 - val2
        // In Kotlin, -1 and 0xFF results in 255 (Try it!)
        // This perfectly mimics binary 2's complement wrapping.
        return diff and 0xFF
    }
}
