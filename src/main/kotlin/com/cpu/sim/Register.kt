package com.cpu.sim

/**
 * Represents an 8-bit Register.
 *
 * In our architecture, we will have specific instances of this class:
 * - Register A: For math.
 * - Register B: For math.
 * - IR (Instruction Register): Holds the current command.
 * - PC (Program Counter): Holds the line number.
 */
class Register(val name: String) {
    // The internal storage. In hardware, this is a set of 8 Flip-Flops.
    var value: Int = 0
        private set // We make the setter private so only load() can change it.

    /**
     * Simulates the "Load Enable" line.
     * When this is called, the register grabs the value from the bus.
     *
     * @param input The value currently on the bus.
     */
    fun load(input: Int) {
        // We use bitwise AND 0xFF to force the value to be 8-bit.
        // If input is 256 (100000000), 'and 0xFF' cuts off the top, leaving 0.
        // This effectively simulates an 8-wire limit.
        value = input and 0xFF
    }

    /**
     * Simulates the "Output Enable" line.
     * Places the stored value onto the bus (or returns it, in software terms).
     */
    fun output(): Int {
        return value
    }
}
