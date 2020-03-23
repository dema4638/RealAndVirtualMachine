import java.util.Scanner;

public class VirtualMachine {

    private int sp;
    private int pc;
    private int[] stack;
    private int[] code;
    private int[] data;
    private Memory memory;
    private Instruction[] instructionSet = Instruction.values();

    public VirtualMachine(int[] code, int[] data, int stackSize, Memory memory){
        sp = -1;
        this.code = code;
        this.data = data;
        this.pc = 0;
        stack = new int[stackSize];
        this.memory = memory;
    }

    public int execute() {
        Instruction instruction = instructionSet[code[pc++]];
        int[] operands = new int[instruction.getOperandCount()];
        for (int i = 0; i < instruction.getOperandCount(); i++) {
            operands[i] = code[pc++];
        }
        int value;
        switch(instruction) {
            case ADD:
                value = stack[sp-1] + stack[sp];
                stack[--sp] = value;
                break;
            case SUB:
                value = stack[sp-1] - stack[sp];
                stack[--sp] = value;
                break;
            case MUL:
                value = stack[sp-1] * stack[sp];
                stack[--sp] = value;
                break;
            case DIV:
                value = stack[sp-1] / stack[sp];
                stack[--sp] = value;
                break;
            case HALT:
                return 1;
            case PUSH:
                stack[++sp] = operands[0];
                break;
            case WRITE:
                System.out.println(stack[sp]);
                sp--;
                break;
            case LD:
                stack[++sp] = data[16*operands[0]+operands[1]];
                break;
            case WD:
                data[16*operands[0]+operands[1]] = stack[sp];
                break;
            case SLD:
                stack[++sp] = memory.getValueFromMemory(16*operands[0]+operands[1]);
                sp++;
                break;
            case SWD:
                memory.addValueToMemory(16*operands[0]+operands[1],stack[sp]);
                break;
            case JMP:
                pc = operands[0];
                break;
            case JE:
                if (stack[sp] == 0) {
                    pc = operands[0];
                }
                break;
            case JG:
                if (stack[sp] == 2) {
                    pc = operands[0];
                }
                break;
            case JL:
                if (stack[sp] == 1) {
                    pc = operands[0];
                }
                break;
            case PRINT:
                System.out.println(stack[sp]);
                break;
            case READ:
                Scanner obj = new Scanner(System.in);
                value = obj.nextInt();
                stack[++sp]= value;
                break;
            case CMP:
                if (stack[sp] == stack[sp-1]){
                    stack[--sp] = 0;
                }
                else if (stack[sp] < stack[sp-1]){
                    stack[--sp]=1;
                }
                else{
                    stack[--sp]=2;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + instruction);
        }
        return 0;
    }

}
