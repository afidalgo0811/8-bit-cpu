package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals

class ProgramCounterTests {
    @Test
    fun `test incrementing`() {
        val pc = ProgramCounter()
        assertEquals(0, pc.output())

        pc.increment()
        assertEquals(1, pc.output())

        pc.increment()
        assertEquals(2, pc.output())
    }

    @Test
    fun `test wrapping at 15`() {
        val pc = ProgramCounter()

        // Load the max value (15)
        pc.load(15)

        // Increment should wrap to 0
        pc.increment()
        assertEquals(0, pc.output(), "PC should wrap 15 -> 0")
    }

    @Test
    fun `test 4-bit masking`() {
        val pc = ProgramCounter()

        // Try to load a value larger than 15 (e.g., 17 = 10001 binary)
        pc.load(17)

        // Should keep only bottom 4 bits (0001 = 1)
        assertEquals(1, pc.output())
    }
}
