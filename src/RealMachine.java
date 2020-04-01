import javax.print.attribute.standard.PrinterStateReason;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RealMachine {

   // private static CPU cpu = new CPU();
    private static Memory memory = new Memory(16, 16);
    private static Compiler compiler = new Compiler();

    private static final int[] data = new int[10];
    private static final int stackSize = 100;

    //private static VirtualMachine currentVm = new VirtualMachine(code, data, stackSize, memory);
    private static VirtualMachine currentVm;
    private static CPU cpu = new CPU(memory);

    public static void main(String[] args) {
        ArrayList<String> program = compiler.readFile();

        memory.randomBusyFrames(6); //Simulate frames that cannot be allocated to the VM

        PageTable pageTable = new PageTable(memory.getFreeFrames(10));
        currentVm = new VirtualMachine(compiler.getCode(program), data, stackSize, pageTable);
        while(true) {
            if (currentVm != null) {
                if (currentVm.execute() != 0) {
                    currentVm = null;
                }
            }
        }
    }

    public static CPU getCPU() {
        return cpu;
    }

    public static void addToMemory(int address, int value) {
        memory.addValueToMemory(address, value);
    }

    public static int getFromMemory(int address) {
        return memory.getValueFromMemory(address);
    }

}
