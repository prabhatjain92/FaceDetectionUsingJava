package pack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteLastRecord {

	static StringBuilder sBuilder=new StringBuilder();
	public void DeleteLast() throws IOException
	{
		File f=new File("Intermediate.txt");
		
		BufferedReader br=new BufferedReader(new FileReader(f));
		String value;
	
		while((value = br.readLine()) != null)
		{
			sBuilder.append(value);
			sBuilder.append("\n");
			
		}
		sBuilder.setLength(sBuilder.length()-1);
		
		
		PrintWriter pw=new PrintWriter("Intermediate.txt");
		
		pw.append(sBuilder);
		pw.close();
		
	}
}
