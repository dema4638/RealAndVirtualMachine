import javax.print.attribute.standard.PrinterStateReason;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RealMachine {

   // private static CPU cpu = new CPU();
    private static Memory memory = new Memory(16, 16);
    private static CPU cpu = new CPU(memory);
    private static Compiler compiler = new Compiler();
    //private static VirtualMachine currentVm = new VirtualMachine(code, data, stackSize, memory);

    public static void main(String[] args) {

        memory.randomBusyFrames(6); //Simulate frames that cannot be allocated to the VM
        while (true) {
            final int[] data = new int[10];
            final int stackSize = 100;
            boolean runProgram = true;

            System.out.println("Please specify program file:");
            Scanner sc = new Scanner(System.in);
            ArrayList<String> program = compiler.readFile(sc.nextLine());
            //sc.close();

            // GUI gui = new GUI();
            //gui.createGUI();
            PageTable pageTable = new PageTable(memory.getFreeFrames(10));
            compiler.setCPU(cpu);

            int index;
            int[] code = compiler.getCode(program);
            if ((index = cpu.getPI()) != 0) {
                handlePIInterrupt(index);
                cpu.setPI(0);
                runProgram = false;
            }

            VirtualMachine currentVm = new VirtualMachine(code, data, stackSize, pageTable);

            if (runProgram) {
                while (true) {
                    if (currentVm.execute() != 0) {
                        break;
                    }
                    if ((index = cpu.getPI()) != 0) {
                        handlePIInterrupt(index);
                        break;
                    }
                    //TODO: handle SI interrupt
                }
                currentVm.exit();
            }
        }
    }

    /*PI = 1: Wrong command found in program's code
     */
    public static void handlePIInterrupt(int index){
        switch (index) {
            case 1:
                System.out.println("Wrong command found in program's code");
                cpu.resetInterrupts();
                break;
        }
    }

    /* SI = 1: HALT interrupt
     */


    public static void handleSIInterrupt(int index){
        switch (index) {
            case 1:
                cpu.resetInterrupts();
                break;
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
