package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals

class RamTests {
    @Test
    fun `test read and write`() {
        val ram = Ram()
        ram.write(5, 42)
        assertEquals(42, ram.read(5))
    }

    @Test
    fun `test address masking (4-bit limit)`() {
        val ram = Ram()

        // Address 16 (binary 10000) should wrap to Address 0 (binary 0000)
        // because we only have 4 address wires.
        ram.write(16, 99)

        assertEquals(99, ram.read(0), "Address 16 should alias to Address 0")
    }

    @Test
    fun `test data masking (8-bit limit)`() {
        val ram = Ram()

        // Value 257 (100000001) should store as 1
        ram.write(2, 257)

        assertEquals(1, ram.read(2), "Data should be truncated to 8 bits")
    }

    @Test
    fun `test bulk load program`() {
        val ram = Ram()
        val program = IntArray(3)
        program[0] = 10
        program[1] = 20
        program[2] = 30

        ram.loadProgram(program)

        assertEquals(10, ram.read(0))
        assertEquals(20, ram.read(1))
        assertEquals(30, ram.read(2))
    }
}
