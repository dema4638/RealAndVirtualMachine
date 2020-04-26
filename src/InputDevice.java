import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputDevice {
	
	private BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	
	public byte[] getInput()
	{
		try
		{
			String tmp = bufferRead.readLine();
			return tmp.getBytes();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			return new byte[0];
		}
	}
}
