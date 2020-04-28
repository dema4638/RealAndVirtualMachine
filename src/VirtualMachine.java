import java.util.Scanner;

public class VirtualMachine {

//    private int sp;
//    private int pc;
//    private int[] stack;
//    private int[] code;
//    private int[] data;
    private Instruction[] instructionSet = Instruction.values();
    private PageTable pageTable;

    public VirtualMachine(int[] code, int[] data, int stackSize, PageTable pageTable){
        //loading virtual machine into memory
        this.pageTable = pageTable;

        int memIndex = 0;
        for (int d : data) {
            getCPU().getMmu().addToMemory(d, memIndex++,pageTable);
        }

        getCPU().setPC(memIndex);
        //RealMachine.getCPU().setPC(pc);
        for (int c : code) {
            getCPU().getMmu().addToMemory(c, memIndex++,pageTable);
        }

        getCPU().setSP(memIndex);
        //RealMachine.getCPU().setSP(sp);
        int maxStackAddress = memIndex + stackSize - 1;
        while(memIndex <= maxStackAddress) {
            getCPU().getMmu().addToMemory(0, memIndex++,pageTable);
        }
    }

    public void execute() {
        Instruction instruction = instructionSet[getCPU().getMmu().
                getFromMemory(getCPU().getAndIncrementPC(),pageTable, getCPU().getMODE())];
        int[] operands = new int[instruction.getOperandCount()];
        for (int i = 0; i < instruction.getOperandCount(); i++) {
            operands[i] = getCPU().getMmu().
                    getFromMemory(getCPU().getAndIncrementPC(),pageTable, getCPU().getMODE());
        }
        getCPU().execute(instruction,pageTable,operands);

    }

    private CPU getCPU() {
        return RealMachine.getCPU();
    }

    public void exit() {
        getCPU().getMmu().freePagesFromMemory(pageTable);
        instructionSet = null;
        pageTable = null;
    }
}
