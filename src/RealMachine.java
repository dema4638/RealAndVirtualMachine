import java.util.ArrayList;
import java.util.Scanner;

public class RealMachine {
	
	// C:\Users\kristupas.lunskas\Desktop\Universitetas\OS\RealAndVirtualMachine\power.txt

    private static Memory memory = new Memory(Configuration.FRAMES_IN_MEMORY, Configuration.FRAME_SIZE);
    private static Memory externalMemory = new Memory(16,16);
    private static Compiler compiler = new Compiler();
    private static InputDevice inputDevice = new InputDevice();
    private static OutputDevice outputDevice = new OutputDevice();
    private static VirtualMachine currentVm;
    private static GUI gui;
    private static MMU mmu = new MMU(memory);
    private static CPU cpu = new CPU(mmu);

    private static boolean isDebugMode;
    private final int[] data = new int[10];
    private final int stackSize = 100;

    public RealMachine() {
        memory.randomBusyFrames(Configuration.FAKE_BUSY_FRAMES_COUNT); //Simulate frames that cannot be allocated to the VM
    }

    public ArrayList<String> loadProgram(String filepath)
    {
        Scanner sc = new Scanner(filepath);
        return compiler.readFile(sc.nextLine());
    }
    
    public void runProgram(ArrayList<String> program, boolean isDebug)
    {
        isDebugMode = isDebug;
        compiler.setCPU(cpu);

        int index;
        int[] code = compiler.getCode(program);
        if ((index = cpu.getPI()) != 0) {
            processInterrupt();
            cpu.setPI(0);
        }

        createPageTable();
        currentVm = createVirtualMachine(code, data, stackSize);

        // new VirtualMachine(code, data, stackSize);
    }

    public int doNextStep()
    {
    	cpu.setMODE(0);
    	if (cpu.getPI() == 0 && cpu.getSI() == 0)
    		execute();

    	cpu.setMODE(1);
    	if (processInterrupt() == 0)
    		return 0;
 
    	if (isDebugMode)
    		gui.setStepButtonEnabled(true);
    	
    	return 1;
    }
    
    public void execute() {
        cpu.setMODE(0);
        currentVm.execute();
        cpu.setMODE(1);
    }
    
    public void exit()
    {
        currentVm.exit();
    }
    /*
    PI = 1: Wrong command found in program's code
    PI = 2: Wrong address
    PI = 3: Not enough external memory
    SI = 1: HALT interrupt
    SI = 2: Get input from input device
    SI = 3: Put output to output device
    */
    
    public int processInterrupt()
    {
    	int index = 0;
    	if ((index = cpu.getPI()) != 0)
    	{
            switch (index) {
            case 1:
            	outputDevice.println("Wrong command found in program's code");
                cpu.resetInterrupts();
                return 0;
                
            case 2:
            	outputDevice.println("Wrong address");
                cpu.resetInterrupts();
                return 0;
                
            case 3:
            	outputDevice.println("Not enough external memory");
                cpu.resetInterrupts();
                return 0;
            }
    	}
    	else if ((index = cpu.getSI()) != 0)
    	{
            switch (index) {
            case 1:
                cpu.resetInterrupts();
                return 0;
                
            case 2:
            	getInputData();
                cpu.resetInterrupts();
                break;
                
            case 3:
            	putOutputData();
                cpu.resetInterrupts();
                break;
            }
    	}
    	return 1;
    }
    
    
    public void getInputData()
    {
    	if (gui != null)
    		gui.setStepButtonEnabled(false);
    	byte[] input = inputDevice.getInput();
    	int value = Character.getNumericValue((char)input[0]);
    	cpu.setSP(cpu.getSP()+1);
        mmu.addToMemory(value, cpu.getSP(), cpu.getPTR());
    	if (gui != null)
    		gui.setStepButtonEnabled(true);
    }
    
    public void putOutputData()
    {
    	outputDevice.println(mmu.getFromMemory(cpu.getSP(), cpu.getPTR(), cpu.getMODE()));
    }

    public void initDebugGUI(GUI givenGUI)
    {
    	gui = givenGUI;
    	cpu.initGUIReference(gui);
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
    
    public int[] viewExternalMemory(){
        return externalMemory.viewData();
    }

    private VirtualMachine createVirtualMachine(int[] code, int[] data, int stackSize) {
        int requiredMemory = code.length + data.length + stackSize;
        int[] allocatedFrames = memory.getFreeFrames(
                (requiredMemory - 1) / Configuration.FRAME_SIZE + 1,
                false);

        mmu.createFrameMapping(allocatedFrames, cpu.getPTR());

        return new VirtualMachine(code, data, stackSize);
    }

    private void createPageTable() {
        int firstFrame = memory.getFreeFrames(
                (Configuration.FRAMES_IN_MEMORY - 1) / Configuration.FRAME_SIZE + 1,
                true)[0];
        cpu.setPTR(firstFrame);
    }
}
