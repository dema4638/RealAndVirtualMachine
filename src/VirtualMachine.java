public class VirtualMachine {

    private Instruction[] instructionSet = Instruction.values();

    public VirtualMachine(int[] code, int[] data, int stackSize) {
        //loading virtual machine into memory
        int memIndex = 0;
        for (int d : data) {
            getCPU().getMmu().addToMemory(d, memIndex++, getCPU().getPTR());
        }

        getCPU().setPC(memIndex);
        for (int c : code) {
            getCPU().getMmu().addToMemory(c, memIndex++, getCPU().getPTR());
        }

        getCPU().setSP(memIndex);
        int maxStackAddress = memIndex + stackSize - 1;
        while(memIndex <= maxStackAddress) {
            getCPU().getMmu().addToMemory(0, memIndex++, getCPU().getPTR());
        }
    }

    public void execute() {
        Instruction instruction = instructionSet[getCPU().getMmu().
                getFromMemory(getCPU().getAndIncrementPC(), getCPU().getPTR(), getCPU().getMODE())];
        int[] operands = new int[instruction.getOperandCount()];
        for (int i = 0; i < instruction.getOperandCount(); i++) {
            operands[i] = getCPU().getMmu().
                    getFromMemory(getCPU().getAndIncrementPC(), getCPU().getPTR(), getCPU().getMODE());
        }
        getCPU().execute(instruction ,operands);

    }

    private CPU getCPU() {
        return RealMachine.getCPU();
    }

    public void exit() {
        getCPU().getMmu().freePagesFromMemory(getCPU().getPTR());
        instructionSet = null;
    }
}
