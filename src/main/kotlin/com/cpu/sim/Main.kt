package com.cpu.sim

fun main() {
    val cpu = Cpu()
    val display = Display()
    val assembler = Assembler()

    println("--- 8-BIT CPU SIMULATOR ---")
    println("Initializing hardware...")

    // PROGRAM: Calculate 5 + 3
    // We can now write comments and use names!
    val sourceCode = """
        LDI 5    ; Load 5 into Register A
        STA 15   ; Store it at address 15 (bottom of memory)
        LDI 3    ; Load 3 into Register A
        ADD 15   ; Add value at address 15 (which is 5)
        OUT      ; Show the result (should be 8)
        HLT      ; Stop the clock
    """
    val program = assembler.assemble(sourceCode)
    cpu.ram.loadProgram(program)

    println("Program loaded. Starting Clock...")
    display.printHeader()

    // The Clock Loop
    var stepCount = 0
    while (!cpu.halted) {
        // Print state BEFORE the step
        display.printState(stepCount, cpu)

        // Tick the clock
        cpu.step()

        stepCount++

        // Safety break for infinite loops
        if (stepCount > 100) {
            println("ERR: Cycle limit reached!")
            break
        }

        // Optional: Slow it down, so you can watch it (100ms)
        Thread.sleep(1000)
    }

    display.printState(stepCount, cpu) // Print final state
    println("--- CPU HALTED ---")
}
