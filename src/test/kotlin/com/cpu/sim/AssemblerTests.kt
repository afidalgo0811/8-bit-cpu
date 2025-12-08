package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AssemblerTests {
    private val assembler = Assembler()

    @Test
    fun `test simple instructions`() {
        val source =
            """
            LDI 5
            ADD 10
            HLT
            """.trimIndent()

        val binary = assembler.assemble(source)

        // LDI (5) + 5 -> 0x55
        assertEquals(0x55, binary[0])
        // ADD (2) + 10 -> 0x2A
        assertEquals(0x2A, binary[1])
        // HLT (15) + 0 -> 0xF0
        assertEquals(0xF0, binary[2])
    }

    @Test
    fun `test comments and whitespace`() {
        val source =
            """
            LDI 5    ; Load the number 5
                     ; This is a full line comment
            STA 15   ; Store it
            """.trimIndent()

        val binary = assembler.assemble(source)

        // Should ignore whitespace and comments
        assertEquals(0x55, binary[0]) // LDI 5
        assertEquals(0x4F, binary[1]) // STA 15
        assertEquals(0x00, binary[2]) // Empty memory should be 0
    }

    @Test
    fun `test case insensitivity`() {
        val source = "ldi 5"
        val binary = assembler.assemble(source)
        assertEquals(0x55, binary[0])
    }

    @Test
    fun `test invalid opcode throws error`() {
        val source = "XYZ 5" // Unknown command

        assertFailsWith<IllegalArgumentException> {
            assembler.assemble(source)
        }
    }

    @Test
    fun `test invalid operand throws error`() {
        val source = "LDI Hello" // "Hello" is not a number

        assertFailsWith<IllegalArgumentException> {
            assembler.assemble(source)
        }
    }

    @Test
    fun `test program limit`() {
        // Generate 17 instructions (limit is 16)
        val source = "NOP\n".repeat(17)

        val binary = assembler.assemble(source)

        // Should only assemble the first 16, ignoring the rest
        assertEquals(16, binary.size)
    }
}
