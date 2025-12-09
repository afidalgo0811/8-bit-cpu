package com.cpu.sim

fun main() {
    val cpu = Cpu()
    val display = Display()
    val assembler = Assembler()

    println("--- 8-BIT CPU SIMULATOR ---")
    println("Initializing hardware...")

    /*
    Fibonacci Sequence program (1, 1, 2, 3, 5, 8, 13...)
     */

    val sourceCode = """
        ; --- SETUP ---
        LDI 1    ; A = 1
        STA 14   ; x = 1 (We assume y at 15 is already 0)
        
        ; --- LOOP (Address 2) ---
        LDA 14   ; Load x
        OUT      ; Display x
        ADD 15   ; A = x + y
        STA 13   ; temp = A  (Address 13 is now safe!)
        
        LDA 14   ; Load x
        STA 15   ; y = x
        
        LDA 13   ; Load temp
        STA 14   ; x = temp
        
        JC 12    ; If Carry, Jump to End
        JMP 2    ; Jump to Loop Start (Address 2)
        
        ; --- END (Address 12) ---
        HLT      ; Stop
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
        if (stepCount > 1000) {
            println("ERR: Cycle limit reached!")
            break
        }

        // Optional: Slow it down, so you can watch it (100ms)
        Thread.sleep(100)
    }

    display.printState(stepCount, cpu) // Print final state
    println("--- CPU HALTED ---")
}
