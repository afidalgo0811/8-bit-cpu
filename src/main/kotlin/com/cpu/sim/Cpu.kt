package com.cpu.sim

/**
 * The Central Processing Unit.
 *
 * This class acts as the "Motherboard". It instantiates all the components
 * and wires them together. It also contains the Control Logic (the brain)
 * to move data between them.
 */
class Cpu {
    // --- Components ---
    val ram = Ram()
    val alu = Alu()

    // General Purpose Registers
    val regA = Register("A")
    val regB = Register("B")

    // Special Purpose Registers
    val regIR = Register("IR") // Instruction Register (Stores the current command)
    val regPC = ProgramCounter() // Keeps track of where we are
    val regOut = Register("OUT") // The display

    // --- State ---
    var halted = false

    /**
     * The Instruction Set Architecture (ISA).
     * These mappings define the "Language" of our CPU.
     */
    companion object OpCodes {
        const val NOP = 0x00 // No Operation
        const val LDA = 0x01 // Load RAM data into Register A
        const val ADD = 0x02 // Add RAM data to Register A
        const val SUB = 0x03 // Subtract RAM data from Register A
        const val STA = 0x04 // Store Register A into RAM
        const val LDI = 0x05 // Load Immediate (Hardcoded value) into A
        const val JMP = 0x06 // Jump to Address
        const val JC = 0x07 // Jump if Carry (Not implemented yet)
        const val JZ = 0x08 // Jump if Zero (Not implemented yet)

        const val OUT = 0x0E // Output Register A to the "Display"
        const val HLT = 0x0F // Halt the clock
    }

    /**
     * Runs the Fetch-Decode-Execute cycle once.
     */
    fun step() {
        if (halted) return
        // --- 1. FETCH ---
        // Get the address from PC
        val address = regPC.output()
        // Read from RAM
        val instructionByte = ram.read(address)
        // Store in Instruction Register
        regIR.load(instructionByte)
        // Increment PC for next time
        regPC.increment()

        // --- 2. DECODE ---
        // Upper 4 bits = Opcode
        val opcode = (regIR.value and 0xF0) shr 4
        // Lower 4 bits = Operand (Address or Data)
        val operand = regIR.value and 0x0F

        // --- 3. EXECUTE ---
        execute(opcode, operand)
    }

    private fun execute(
        opcode: Int,
        operand: Int,
    ) {
        when (opcode) {
            NOP -> { // Do nothing
            }
            LDA -> {
                // Load RAM[operand] into A
                val data = ram.read(operand)
                regA.load(data)
            }

            ADD -> {
                // 1. Load RAM[operand] into B
                val data = ram.read(operand)
                regB.load(data)
                // 2. Add A + B -> A
                val result = alu.add(regA.value, regB.value)
                regA.load(result)
            }

            SUB -> {
                // 1. Load RAM[operand] into B
                val data = ram.read(operand)
                regB.load(data)
                // 2. Subtract A - B -> A
                val result = alu.subtract(regA.value, regB.value)
                regA.load(result)
            }

            STA -> {
                // Store Register A into RAM[operand]
                ram.write(operand, regA.value)
            }

            LDI -> {
                // Load Immediate: Load the operand itself into A
                regA.load(operand)
            }

            JMP -> {
                // Set PC to operand
                regPC.load(operand)
            }

            OUT -> {
                regOut.load(regA.value)
                // In a real app we might use a callback here to update UI
            }

            HLT -> {
                halted = true
            }
        }
    }
}
