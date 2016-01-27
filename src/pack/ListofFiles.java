package pack;

import java.io.File;

public class ListofFiles {

	static String[] filenames;
	public int ListofImages(int ImageNum)
	{
		
		File folder=new File("images1/");
		
		File[] listoffiles=folder.listFiles();
		
		filenames=new String[listoffiles.length];
		for(int i=0;i<listoffiles.length;i++)
		{
			if(listoffiles[i].isFile())
			{
				filenames[i]=listoffiles[i].getName();
			}
			else if(listoffiles[i].isDirectory())
			{
				System.out.println("Directory"+listoffiles[i].getName());
			}
		}
		return filenames.length;
	}	
		public String ImageName(int Imagenum)
		{
			
			return filenames[Imagenum];
			
		}
		
	

}
