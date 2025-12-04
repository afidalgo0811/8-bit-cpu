package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals

class AluTests {
    private val alu = Alu()

    @Test
    fun `test simple addition`() {
        val result = alu.add(10, 5)
        assertEquals(15, result)
    }

    @Test
    fun `test simple subtraction`() {
        val result = alu.subtract(10, 5)
        assertEquals(5, result)
    }

    @Test
    fun `test addition overflow`() {
        // 255 + 1 should wrap to 0, not 256
        val result = alu.add(255, 1)
        assertEquals(0, result, "ALU should wrap 256 back to 0")
    }

    @Test
    fun `test subtraction underflow`() {
        // 0 - 1 should wrap to 255 (binary 11111111), not -1
        val result = alu.subtract(0, 1)
        assertEquals(255, result, "ALU should wrap -1 back to 255")
    }
}
