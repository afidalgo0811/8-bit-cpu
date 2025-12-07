package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CpuTests {
    @Test
    fun `test simple addition program`() {
        val cpu = Cpu()

        // PROGRAM: Calculate 5 + 3
        // ------------------------------------------------
        // 0: LDI 5   (Load 5 into A)        -> Op 5, Val 5 -> 0x55 -> 01010101
        // 1: STA 15  (Store A to addr 15)   -> Op 4, Val 15-> 0x4F -> 01001111
        // 2: LDI 3   (Load 3 into A)        -> Op 5, Val 3 -> 0x53 -> 11110011
        // 3: ADD 15  (Add RAM[15] to A)     -> Op 2, Val 15-> 0x2F -> 00101111
        // 4: OUT     (Output A)             -> Op 14       -> 0xE0 -> 11100000
        // 5: HLT     (Stop)                 -> Op 15       -> 0xF0 -> 11110000

        val program =
            intArrayOf(
                0x55,
                0x4F,
                0x53,
                0x2F,
                0xE0,
                0xF0,
            )

        cpu.ram.loadProgram(program)

        // Run the CPU
        // We limit it to 100 steps to prevent infinite loops if something breaks
        var steps = 0
        while (!cpu.halted && steps < 100) {
            cpu.step()
            steps++
        }

        // Assertions
        assertTrue(cpu.halted, "CPU should have halted")
        assertEquals(8, cpu.regOut.value, "Output should be 8 (5 + 3)")
        assertEquals(8, cpu.regA.value, "Register A should hold the result")
    }

    @Test
    fun `test jump instruction`() {
        val cpu = Cpu()

        // PROGRAM: Infinite Loop check
        // 0: JMP 2   (Jump to address 2) -> 0x62 -> 01100010
        // 1: HLT     (Should skip this)  -> 0xF0 -> 11110000
        // 2: LDI 9   (Load 9)            -> 0x59 -> 01011001
        // 3: HLT                         -> 0xF0 -> 11110000

        val program = intArrayOf(0x62, 0xF0, 0x59, 0xF0)
        cpu.ram.loadProgram(program)

        cpu.step() // Execute JMP 2
        assertEquals(2, cpu.regPC.output(), "PC should be 2 after Jump")

        cpu.step() // Execute LDI 9
        assertEquals(9, cpu.regA.value, "Should have executed instruction at address 2")
    }
}
