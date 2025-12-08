package com.cpu.sim

fun main() {
    val cpu = Cpu()
    val display = Display()

    println("--- 8-BIT CPU SIMULATOR ---")
    println("Initializing hardware...")

    // PROGRAM: Calculate 5 + 3
    // ------------------------------------------------
    // 0: LDI 5   (Load 5 into A)        -> 0x55
    // 1: STA 15  (Store A to addr 15)   -> 0x4F
    // 2: LDI 3   (Load 3 into A)        -> 0x53
    // 3: ADD 15  (Add RAM[15] to A)     -> 0x2F
    // 4: OUT     (Output A)             -> 0xE0
    // 5: HLT     (Stop)                 -> 0xF0

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

        // Optional: Slow it down so you can watch it (100ms)
        Thread.sleep(100)
    }

    display.printState(stepCount, cpu) // Print final state
    println("--- CPU HALTED ---")
}
