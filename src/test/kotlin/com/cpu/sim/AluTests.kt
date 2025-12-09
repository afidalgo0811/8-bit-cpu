package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AluTests {
    private val alu = Alu()

    @Test
    fun `test simple addition`() {
        val result = alu.add(10, 5)

        assertEquals(15, result.value)
        assertFalse(result.zero, "Zero flag should be false")
        assertFalse(result.carry, "Carry flag should be false")
    }

    @Test
    fun `test simple subtraction`() {
        val result = alu.subtract(10, 5)

        assertEquals(5, result.value)
        assertFalse(result.zero)
    }

    @Test
    fun `test addition overflow (Carry Flag)`() {
        // 255 + 1 = 256.
        // In 8-bit: Result is 0. Carry is True. Zero is True.
        val result = alu.add(255, 1)

        assertEquals(0, result.value, "Value should wrap to 0")
        assertTrue(result.carry, "Carry flag should be TRUE")
        assertTrue(result.zero, "Zero flag should be TRUE (since result is 0)")
    }

    @Test
    fun `test zero flag`() {
        // 5 - 5 = 0
        val result = alu.subtract(5, 5)

        assertEquals(0, result.value)
        assertTrue(result.zero, "Zero flag should be TRUE")
    }
}
