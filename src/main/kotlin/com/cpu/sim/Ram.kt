package com.cpu.sim

/**
 * Random Access Memory (RAM).
 *
 * The SAP-1 has a very small memory: 16 addresses (0-15).
 * Each address holds one 8-bit byte.
 */
class Ram {
    // 16 bytes of memory. Initialized to 0.
    private val data = IntArray(16)

    /**
     * Reads the 8-bit value at the specific address.
     *
     * @param address The 4-bit address (0-15).
     * High bits of the address are ignored (Address 16 becomes Address 0).
     */
    fun read(address: Int): Int {
        // Mask address to 4 bits (0x0F = 00001111)
        val safeAddress = address and 0x0F
        return data[safeAddress]
    }

    /**
     * Writes an 8-bit value to a specific address.
     *
     * @param address The 4-bit address (0-15).
     * @param value The 8-bit value to store.
     */
    fun write(
        address: Int,
        value: Int,
    ) {
        val safeAddress = address and 0x0F
        // Mask value to 8 bits (0xFF) before storing
        data[safeAddress] = value and 0xFF
    }

    /**
     * Helper to load a full program into memory at once.
     * Mimics "burning" an EEPROM or setting dip-switches.
     */
    fun loadProgram(program: IntArray) {
        for (i in program.indices) {
            if (i < 16) {
                // We reuse our write method to ensure safety
                write(i, program[i])
            }
        }
    }

    /**
     * Debug helper to print memory state
     */
    fun printDump() {
        println("--- RAM DUMP ---")
        for (i in data.indices) {
            // Print address and value in Hex
            println("ADDRESS $i: ${data[i]}")
        }
    }
}
