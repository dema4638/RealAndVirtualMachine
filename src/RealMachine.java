import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RealMachine {

	private static DebugObject debugObject = new DebugObject(); 
   // private static CPU cpu = new CPU();
    private static Memory memory = new Memory(16, 16);
    private static CPU cpu = new CPU(memory);
    private static Compiler compiler = new Compiler();
    private static InputDevice inputDevice = new InputDevice();
    private static OutputDevice outputDevice = new OutputDevice();
    private static VirtualMachine currentVm;// = new VirtualMachine(code, data, stackSize, memory);
    
    private static GUI gui;
    
    public ArrayList<String> loadProgram(String filepath)
    {
        Scanner sc = new Scanner(filepath);
        return compiler.readFile(sc.nextLine());
    }
    
    public void runProgram(ArrayList<String> program, boolean isDebugMode)
    {
        memory.randomBusyFrames(6); //Simulate frames that cannot be allocated to the VM
        
        while (true) {
            final int[] data = new int[10];
            final int stackSize = 100;
            boolean runProgram = true;


            //sc.close();
            PageTable pageTable = new PageTable(memory.getFreeFrames(10));
            compiler.setCPU(cpu);

            int index;
            int[] code = compiler.getCode(program);
            if ((index = cpu.getPI()) != 0) {
                handlePIInterrupt(index);
                cpu.setPI(0);
                runProgram = false;
            }

            currentVm = new VirtualMachine(code, data, stackSize, pageTable);
            if (runProgram) {
                while (true) {
                    if (nextStep() != 0) {
                    	gui.updateRegisters();
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
            	outputDevice.println("Wrong command found in program's code");
                cpu.resetInterrupts();
                break;
                
            case 2:
            	
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
    
    public int[] viewRealMemory(){
        return memory.viewData();
    }
    
    public int nextStep() {
        cpu.setMODE(0);
        int result = currentVm.execute();
        cpu.setMODE(1);
    	outputDevice.println(result);
        return result;
        //return handlePIInterrupt();
    }
}
