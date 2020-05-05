public class OutputDevice {
	
	public void println(byte b)
	{
		System.out.println((char)b);
	}
	public void println(byte[] b)
	{
		for (int i = 0; i < b.length; i++)
		{
			System.out.print((char)b[i]);
		}
		System.out.println();
	}
	
	public void println(String s)
	{
		System.out.println(s);
	}
	
	public void print(String s)
	{
		System.out.print(s);
	}
	
	public void print(int d)
	{
		System.out.print(d);
	}
	
	public void println(int d)
	{
		System.out.println(d);
	}
	
	public void println()
	{
		System.out.println();
	}
}
