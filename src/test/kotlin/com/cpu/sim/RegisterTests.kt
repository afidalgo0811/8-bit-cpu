package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals

class RegisterTests {
    @Test
    fun `test initial state is zero`() {
        val register = Register("A")
        assertEquals(0, register.output(), "Register should initialize to 0")
    }

    @Test
    fun `test load and output valid 8-bit value`() {
        val register = Register("A")
        register.load(42)
        assertEquals(42, register.output(), "Register should store and output valid 8-bit integers")
    }

    @Test
    fun `test 8-bit overflow masking`() {
        val register = Register("A")

        // Load 257 (Binary: 1 0000 0001)
        // Expected behavior: The 9th bit is dropped, leaving 1.
        register.load(257)

        assertEquals(1, register.output(), "Register should mask values > 255 (0xFF)")
    }

    @Test
    fun `test max 8-bit value`() {
        val register = Register("A")
        register.load(255)
        assertEquals(255, register.output(), "Register should hold 255")
    }
}
