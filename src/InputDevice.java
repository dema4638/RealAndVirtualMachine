import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

public class InputDevice {
	private BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	
	public Word[] getInput(){
		try {
			String s = bufferRead.readLine();
			byte[] bytes = s.getBytes();
			Word[] words;
			if(bytes.length%4 != 0) {
				words = new Word[(bytes.length/4)+1];
			}
			else words = new Word[bytes.length/4];
			for (int i = 0; i < words.length; i++) {
				words[i] = new Word();
			}
			int i = 3, j = 0;
			for (byte b: bytes) {
				if (i >= 0) {
					words[j].setByte(i,b);
					i--;
				}
				else {
					j = 3;
					j++;
					words[j].setByte(i,b);
				}
			}
			
			return words;
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
}
