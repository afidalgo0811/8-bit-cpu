package com.cpu.sim

/**
 * Holds the result of a math operation plus the status flags.
 * @param value The 8-bit result (0-255).
 * @param zero True if the result is 0.
 * @param carry True if the operation caused an overflow (sum > 255).
 */
data class AluResult(
    val value: Int,
    val zero: Boolean,
    val carry: Boolean,
)

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
    ): AluResult {
        val sum = val1 + val2
        // If sum is 256, 'and 0xFF' makes it 0.
        val result = sum and 0xFF
        // Zero Flag: Did we get 0?
        val zero = (result == 0)
        // Carry Flag: Did we exceed 8 bits?
        val carry = (sum > 255)

        return AluResult(result, zero, carry)
    }

    /**
     * Subtracts val2 from val1.
     * Simulates wrapping behavior (underflow).
     */
    fun subtract(
        val1: Int,
        val2: Int,
    ): AluResult {
        val diff = val1 - val2

        val zero = (diff == 0)
        // In Kotlin, -1 and 0xFF results in 255 (Try it!)
        // This perfectly mimics binary 2's complement wrapping.
        val result = diff and 0xFF
        // For SAP-1 simple subtraction, we typically don't use the Carry flag
        // in the same way (it depends on 2's complement logic).
        // We will leave it false for now to keep it simple.
        val carry = false
        return AluResult(result, zero, carry)
    }
}
