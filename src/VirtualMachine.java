import java.util.Scanner;

public class VirtualMachine {

    private int sp;
    private int pc;
//    private int[] stack;
//    private int[] code;
//    private int[] data;
    private Instruction[] instructionSet = Instruction.values();
    private PageTable pageTable;

    public VirtualMachine(int[] code, int[] data, int stackSize, PageTable pageTable){
        this.pageTable = pageTable;

        int memIndex = 0;
        for (int d : data) {
            addToMemory(d, memIndex++);
        }

        pc = memIndex;
        for (int c : code) {
            addToMemory(c, memIndex++);
        }

        sp = memIndex;
        int maxStackAddress = memIndex + stackSize - 1;
        while(memIndex <= maxStackAddress) {
            addToMemory(0, memIndex++);
        }
    }

    public int execute() {
        Instruction instruction = instructionSet[getFromMemory(pc++)];
        int[] operands = new int[instruction.getOperandCount()];
        for (int i = 0; i < instruction.getOperandCount(); i++) {
            operands[i] = getFromMemory(pc++);
        }
        int value;
        switch(instruction) {
            case ADD:
                value = getFromMemory(sp-1) + getFromMemory(sp);
                addToMemory(value, --sp);
                break;
            case SUB:
                value = getFromMemory(sp-1) - getFromMemory(sp);
                addToMemory(value, --sp);
                break;
            case MUL:
                value = getFromMemory(sp-1) * getFromMemory(sp);
                addToMemory(value, --sp);
                break;
            case DIV:
                value = getFromMemory(sp-1) / getFromMemory(sp);
                addToMemory(value, --sp);
                break;
            case HALT:
                return 1;
            case PUSH:
                addToMemory(operands[0], ++sp);
                break;
            case WRITE:
                System.out.println(getFromMemory(sp));
                sp--;
                break;
            case LD:
                value = getFromMemory(16*operands[0]+operands[1]);
                addToMemory(value, ++sp);
                break;
            case WD:
                addToMemory(getFromMemory(sp),16*operands[0]+operands[1]);
                break;
//            case SLD:v b
//                stack[++sp] = memory.getValueFromMemory(16*operands[0]+operands[1]);
//                sp++;
//                break;
//            case SWD:
//                memory.addValueToMemory(16*operands[0]+operands[1],stack[sp]);
//                break;
            case JMP:
                pc = operands[0];
                break;
            case JE:
                if (getFromMemory(sp) == 0) {
                    pc = operands[0];
                }
                break;
            case JG:
                if (getFromMemory(sp) == 2) {
                    pc = operands[0];
                }
                break;
            case JL:
                if (getFromMemory(sp) == 1) {
                    pc = operands[0];
                }
                break;
            case PRINT:
                System.out.println(getFromMemory(sp));
                break;
            case READ:
                Scanner obj = new Scanner(System.in);
                value = obj.nextInt();
                addToMemory(value, ++sp);
                break;
            case CMP:
                if (getFromMemory(sp) == getFromMemory(sp - 1)){
                    addToMemory(0, --sp);
                }
                else if (getFromMemory(sp) < getFromMemory(sp - 1)){
                    addToMemory(1, --sp);
                }
                else{
                    addToMemory(2, --sp);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + instruction);
        }
        return 0;
    }

    private void addToMemory(int value, int virtualAddress) {
        int realAddress = RealMachine.getCPU().getMmu().convertAddressToPhysical(virtualAddress, pageTable);
        RealMachine.addToMemory(realAddress, value);
    }

    private int getFromMemory(int virtualAddress) {
        int realAddress = RealMachine.getCPU().getMmu().convertAddressToPhysical(virtualAddress, pageTable);
        return RealMachine.getFromMemory(realAddress);
    }

}
