# 8-Bit CPU Simulator (SAP-1 Architecture)

A step-by-step implementation of an 8-bit computer architecture, built from scratch in Kotlin. This project simulates the hardware logic of the SAP-1 (Simple As Possible) computer, including registers, ALU, RAM, and the system bus.

Project Goal

To demystify how computers work by building one in software, component by component, following the flow of data through logic gates and registers.

Architecture

The project follows a standard Bus Architecture where components communicate via a shared 8-bit bus.

classDiagram
class CPU {
-bus : Int
-halted : Boolean
+start()
+step()
-fetch()
-decode()
-execute()
}

    class Register {
        +value : Int
        +name : String
        +load(val)
        +output() : Int
    }

    class ALU {
        +add(a, b) : Int
        +subtract(a, b) : Int
    }

    class RAM {
        -data : IntArray
        +read(address) : Int
        +write(address, value)
    }

    %% Relationships
    CPU *-- Register : contains
    CPU *-- RAM : contains
    CPU *-- ALU : contains


Components Checklist

* [x] Register: 8-bit storage (A, B, IR, PC, OUT).

* [ ] ALU: Arithmetic Logic Unit (Add/Subtract).

* [ ] RAM: 16-byte memory bank.

* [ ] Bus: The communication pathway.

* [ ] Control Unit: The logic that decodes instructions.

* [ ] Clock: The cycle stepper.

Tech Stack

* Language: Kotlin

* Type: Console Application / Simulation