package pack;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.FaceDetector;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;



public class DetectFace {

	static int fcount=0;
	static int image_num=1;
	
	public static void main(String args[]) throws IOException
	{
		
		long lStartTime = new Date().getTime(); // start time
		System.out.println(lStartTime);
		
		ListofFiles lfiles=new ListofFiles();
		int total_image=lfiles.ListofImages(image_num);
		
		System.out.println("Total Number of Images in Dataset "+total_image);
		PrintWriter pwriter=new PrintWriter(new File("Intermediate.txt"));
		for(int i=0;i<total_image;i++)
		{
			String imagename=lfiles.ImageName(i);
			int fNum=Func(imagename, pwriter);
			fcount+=fNum;
			System.out.println("Image Number  "+ i);
		}
		pwriter.close();
	System.out.println("Total Face Counted in Dataset "+ fcount);
	

	long lEndTime = new Date().getTime(); // end time
	System.out.println(lEndTime+"\n");
	long difference = lEndTime - lStartTime; // check different
	
	System.out.println("Elapsed milliseconds: " + difference);

	
	
	}// End of Main Method
	
	
	public static int Func(String imagename, PrintWriter pwriter) throws IOException
		{
		int face_count=0;
		
		
		
		MBFImage image=ImageUtilities.readMBF(new File("images1/"+imagename.toString()));
		FaceDetector<DetectedFace, FImage> fd=new HaarCascadeDetector(20);
		
		List<DetectedFace> faces=fd.detectFaces(Transforms.calculateIntensity(image));
		face_count+=faces.size();
		
		StringBuilder sBuilder=new StringBuilder();
		
		if(!(faces.size()==0))
				sBuilder.append(imagename.toString()+"\t");
		
				for(DetectedFace face : faces)
					{
					image.drawShape(face.getBounds(), RGBColour.RED);
					sBuilder.append(face.getBounds()+"\t");
					}
	
				if(!(faces.size()==0))
					sBuilder.append("\n");
			
		
				pwriter.print(sBuilder);
				
				DisplayUtilities.display(image);
		
		
	//	System.gc();
		return face_count;
	}	


}
