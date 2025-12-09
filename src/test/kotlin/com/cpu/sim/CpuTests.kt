package com.cpu.sim

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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

        runCpu(cpu)

        assertEquals(8, cpu.regOut.value)

        // Assertions
        assertTrue(cpu.halted, "CPU should have halted")
        assertEquals(8, cpu.regOut.value, "Output should be 8 (5 + 3)")
        assertEquals(8, cpu.regA.value, "Register A should hold the result")
    }

    @Test
    fun `test jump if zero (JZ)`() {
        val cpu = Cpu()

        // PROGRAM:
        // 0: LDI 5
        // 1: SUB 5   (Result 0, Zero Flag = True)
        // 2: JZ 5    (Should Jump to 5)
        // 3: LDI 99  (Should be skipped)
        // 4: HLT
        // 5: LDI 1   (Target)
        // 6: HLT

        // Manual assembly:
        // LDI 5 -> 0x55
        // LDI 5 -> 0x55 (Wait, we need SUB. LDI 5 into A... we need 5 in RAM for SUB)
        // Let's use Immediate values to make setup easier,
        // but wait, SUB only works with Memory addresses in SAP-1!

        // Setup: Put 5 in RAM[15]
        cpu.ram.write(15, 5)

        val program =
            intArrayOf(
                0x55,
                0x3F,
                0x85,
                0x59,
                0xF0,
                0x51,
                0xF0,
            )

        cpu.ram.loadProgram(program)
        runCpu(cpu)

        assertEquals(1, cpu.regA.value, "Should have jumped to address 5 and loaded 1")
    }

    @Test
    fun `test jump if carry (JC)`() {
        val cpu = Cpu()

        // Setup: Put 1 in RAM[15]
        cpu.ram.write(15, 1)

        val program =
            intArrayOf(
                0x1E,
                0x2F,
                0x74,
                0xF0,
                0x51,
                0xF0,
            )

        // Setup data
        cpu.ram.write(14, 255)
        cpu.ram.write(15, 1)

        cpu.ram.loadProgram(program)
        runCpu(cpu)

        assertEquals(1, cpu.regA.value, "Should have jumped to address 4 on carry")
        assertTrue(cpu.flagCarry, "Carry flag should remain set")
    }

    @Test
    fun `test flags reset`() {
        val cpu = Cpu()

        // 1. Trigger Zero Flag
        cpu.ram.write(15, 5)
        cpu.regA.load(5)
        // Execute SUB 15 manually
        // opcode 3 (SUB), operand 15
        // cpu.execute is private, so we run a mini program
        val program =
            intArrayOf(
                0x3F,
                0x2F,
                0xF0,
            )
        cpu.ram.loadProgram(program)
        runCpu(cpu)

        assertFalse(cpu.flagZero, "Zero flag should be cleared after non-zero result")
    }

    // Helper to run until halt or timeout
    private fun runCpu(cpu: Cpu) {
        var steps = 0
        while (!cpu.halted && steps < 100) {
            cpu.step()
            steps++
        }
    }
}
