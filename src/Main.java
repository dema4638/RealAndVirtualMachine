import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	private static RealMachine rm = new RealMachine();
	private static GUI gui = new GUI(rm);
	
	private static boolean debugEnabled = false;
	private static InputDevice inputDevice = new InputDevice();
	private static OutputDevice outputDevice = new OutputDevice();
	private static int[] programs = new int[0];
	private static String[] commands = {"RUN", "STOP", "LOAD", "UNLOAD", "DEBUG", "?"};

	
	private static ArrayList<String> program;
	
    public static void main(String[] args) {
    	printCommands();
    	String command = new String(inputDevice.getInput()).toUpperCase();
    	
    	while(!command.contentEquals("STOP"))
    	{
    		switch(command)
    		{
    			case "LOAD":
    				load();
    				break;
    			
    			case "UNLOAD":
    				unload();
    				break;
    			
    			case "DEBUG":
    				debug();
    				break;
    		
    			case "RUN":
    				run();
    				break;
    				
    			case "?":
    				printCommands();
    				outputDevice.println();
    				printPrograms();
    				break;
    			
    			default:
    				outputDevice.println("Command not found!");
    				printCommands();
    				break;
    		}

    		command = new String(inputDevice.getInput()).toUpperCase();
    	}
    	
    	outputDevice.println("Shutting down OS");
    }
    
    private static void load()
    {
		outputDevice.print("Please specify a file: ");
		String filePath = new String(inputDevice.getInput()).toUpperCase();
		try
		{
			program = rm.loadProgram(filePath);
        	gui.setStepButtonEnabled(true);
			outputDevice.println("Program has been sucessfully loaded. Type RUN to run it.");
		}
		catch(Exception ex) {}
    }
    
    private static void unload()
    {
		outputDevice.print("Please specify a program: ");
		Integer parsedIndex = tryParseInt(inputDevice.getInput());
		if (parsedIndex != null)
		{
			//rm.unloadProgram(parsedIndex);
		}
		else
		{
			outputDevice.println("Could not parse integer value");
		}
    }

    private static void debug()
    {
    	debugEnabled = !debugEnabled;
    	
    	if (debugEnabled)
    		outputDevice.println("Debug enabled.");
    	else 
    		outputDevice.println("Debug disabled.");
    	
    	if (program == null)
    		outputDevice.println("Program has not been loaded. Type LOAD to load a program.");
    	
    	gui.setVisible(debugEnabled);
    	rm.initDebugGUI(gui);
    }
    
    private static void run()
    {
		rm.runProgram(program, debugEnabled);
		
		while(true)
		{
			if (debugEnabled)
			{
					synchronized(rm)
					{
						try {
							rm.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					if (rm.doNextStep() == 0)
						break;
			}
			else if (rm.doNextStep() == 0)
				break;
			
		}
    }
    
    private static void printCommands()
    {
    	outputDevice.println("Available commands: ");
    	for (int i = 0; i < commands.length; i++)
    	{
    		outputDevice.print(commands[i] + " ");
    	}
    	outputDevice.println();
    }
    
    private static void printPrograms()
    {
    	outputDevice.println("Available programs: ");
    	if (programs.length > 0)
    	{
        	for (int i = 0; i < programs.length; i++)
        	{
        		outputDevice.println((byte)programs[i]);
        	}
    	}
    	else
    	{
    		outputDevice.println("None. Use LOAD to insert new program");
    	}
    	outputDevice.println();
    }
    
    private static Integer tryParseInt(byte[] input)
    {
    	String inputString = new String(input);
    	
    	try
    	{
    		return new Integer(Integer.parseInt(inputString));
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    }
}
