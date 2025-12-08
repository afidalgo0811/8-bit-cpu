package com.cpu.sim

/**
 * The Assembler converts human-readable Assembly code into Machine Code.
 *
 * Example Input:
 * LDI 5  ; Load 5
 * ADD 10 ; Add 10
 *
 * Example Output:
 * [0x55, 0x2A]
 */
class Assembler {
    // Map text names to the integer Opcodes defined in Cpu.kt
    private val instructionMap =
        mapOf(
            "NOP" to Cpu.NOP,
            "LDA" to Cpu.LDA,
            "ADD" to Cpu.ADD,
            "SUB" to Cpu.SUB,
            "STA" to Cpu.STA,
            "LDI" to Cpu.LDI,
            "JMP" to Cpu.JMP,
            "JC" to Cpu.JC,
            "JZ" to Cpu.JZ,
            "OUT" to Cpu.OUT,
            "HLT" to Cpu.HLT,
        )

    fun assemble(programCode: String): IntArray {
        val machineCode = IntArray(16) // Max 16 bytes of RAM

        // 1. Split input into lines
        val lines = programCode.lines()

        var address = 0

        for (line in lines) {
            // Stop if we run out of memory
            if (address >= 16) break

            // 2. Clean the line: Remove comments (after ;) and whitespace
            val cleanLine = line.substringBefore(";").trim()

            // Skip empty lines
            if (cleanLine.isEmpty()) continue

            // 3. Parse the Instruction
            val byte = parseLine(cleanLine)
            machineCode[address] = byte

            address++
        }

        return machineCode
    }

    private fun parseLine(line: String): Int {
        // Split "LDI 5" into ["LDI", "5"]
        val parts = line.split(Regex("\\s+"))

        val mnemonic = parts[0].uppercase()
        val operandStr = if (parts.size > 1) parts[1] else "0"

        // 1. Get Opcode
        val opcode =
            instructionMap[mnemonic]
                ?: throw IllegalArgumentException("Unknown Instruction: $mnemonic")

        // 2. Get Operand (Parse "5" or "15" to int)
        val operand =
            operandStr.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid Operand: $operandStr")

        // 3. Combine them (Opcode in top 4 bits, Operand in bottom 4 bits)
        // Example: LDI (0101) + 5 (0101) -> 01010101 (0x55)
        return (opcode shl 4) or (operand and 0x0F)
    }
}
