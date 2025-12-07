package com.cpu.sim

/**
 * The Program Counter (PC).
 *
 * This component keeps track of which line of code (memory address)
 * the computer is currently executing.
 *
 * In SAP-1, this is a 4-bit counter (Values 0-15).
 */
class ProgramCounter {
    var value: Int = 0
        private set

    /**
     * Increment the counter by 1.
     * This happens automatically at the start of every instruction cycle.
     * Wraps around from 15 (1111) to 0 (0000).
     */
    fun increment() {
        value = (value + 1) and 0x0F
    }

    /**
     * Load a specific address (used for JUMP instructions).
     */
    fun load(input: Int) {
        value = input and 0x0F
    }

    /**
     * Output the current count to the bus.
     */
    fun output(): Int {
        return value
    }

    fun reset() {
        value = 0
    }
}
