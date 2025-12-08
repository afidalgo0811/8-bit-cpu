package com.cpu.sim

/**
 * Handles all console output and formatting for the CPU simulation.
 */
class Display {
    fun printHeader() {
        // Formatting a nice table
        println(
            "%-4s | %-4s | %-8s | %-4s | %-4s | %-4s".format(
                "STEP",
                "PC",
                "IR (CMD)",
                "A",
                "B",
                "OUT",
            ),
        )
        println("-".repeat(45))
    }

    fun printState(
        step: Int,
        cpu: Cpu,
    ) {
        // Convert IR to binary string for cool factor
        val irBinary = cpu.regIR.output().toString(2).padStart(8, '0')

        println(
            "%-4d | %-4d | %s | %-4d | %-4d | %-4d".format(
                step,
                cpu.regPC.output(),
                irBinary,
                cpu.regA.output(),
                cpu.regB.output(),
                cpu.regOut.output(),
            ),
        )
    }
}
