There are 2 Add/Sub reservation stations (AdderBuffer.java), each station is (AdderR.java).
There are 2 Mul/Div reservation stations (MulBuffer.java), each station is (MulR.java).
There are 2 stations in the load buffer (LoadBuffer.java), each station (LoadR.java).
There are 2 stations in the store buffer (StoreBuffer.java), each station (StoreR.java).

There are 2 register files:
	Floating point reg. file for the instructions (RegisterFile.java).
	Integer reg. file for the loads and stores base address reg. (RegisterFile2.java).
(Inst.java) contains the instructions in the program and metadata.
(Main.java) contains the logic and where the simulation can be started.

The Add/Sub takes 2 cycles to execute.
The Mul takes 5 cycles to execute, the Div takes 10 cycles to execute.
The load takes 2 cycles to execute.
The store takes 1 cycle to execute.
If multiple instructions finish execution in the same cycle only 1 instruction will write back and the other instructions will write back later.

Inputs: the mips instructions (one by one).
Outputs: the cycle, the contents of each component in tomasulo.