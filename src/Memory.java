import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Memory {
    private int[] memory;
    private boolean[] busyFrames = new boolean[Configuration.FRAMES_IN_MEMORY];

    public Memory(int words, int wordLength) {
        memory = new int[words * wordLength];
        busyFrames[0] = true; // Reserved by OS
    }

    public void addValueToMemory(int address, int value) {
        memory[address] = value;
    }

    public int getValueFromMemory(int address) {
        return memory[address];
    }

    public void randomBusyFrames(int count) {
		
		  int i = 0;
		  while(i < count) 
		  {
			  boolean existsFreeFrame = false; 
			  for (boolean f: busyFrames) 
			  { 
				  if (!f)
				  { 
					  existsFreeFrame = true; 
					  break; 
				  } 
			  } 
			  if (!existsFreeFrame) 
			  {
				  return; 
			  }
		  
			  int nextBusyFrame = new Random().nextInt(busyFrames.length); 
			  if (!busyFrames[nextBusyFrame])
			  { 
				  busyFrames[nextBusyFrame] = true; 
				  for(int j = 0; j < 16; j++) 
				  { 
					  memory[16 * nextBusyFrame + j] = 255;
					  } i++; 
			  } 
		  }
		 
    }

    public int[] getFreeFrames (int number, boolean consecutive) {
        final int originalNumber = number;
        int[] frames = new int[number];
        int currentFrame = 0;
        try {
            while (number > 0) {
                if (!busyFrames[currentFrame]) {
                    frames[originalNumber - number] = currentFrame;
                    number--;
                } else if (consecutive) {
                    number = originalNumber;
                }
                currentFrame++;
            }
            for (int f : frames) {
                busyFrames[f] = true;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfMemoryError("Cannot allocate any more pages");
        }

        return frames;
    }

    public void freeFrame(int frameNumber) {
        busyFrames[frameNumber] = false;
    }

    
    public int[] viewData(){
        return memory;
    }

}