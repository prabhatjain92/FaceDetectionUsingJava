package pack;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.Transforms;
import org.openimaj.image.processing.face.detection.keypoints.FKEFaceDetector;
import org.openimaj.image.processing.face.detection.keypoints.KEDetectedFace;
import org.openimaj.image.processing.face.util.KEDetectedFaceRenderer;

public class ExtractFace {
	static int image_num=1;
	public static void main(String args[]) throws IOException
	{
	
		DeleteLastRecord dlRecord=new DeleteLastRecord();
		dlRecord.DeleteLast();
	
		
		
		
		BufferedReader br1=new BufferedReader(new FileReader(new File("Intermediate.txt")));
		String line;
		while((line = br1.readLine()) !=null)
		{
			
		String v=line.toString();
		String number_of_faces[]=line.split("\t");
		int number_of_faces1=number_of_faces.length;
		number_of_faces1=number_of_faces1-1;
		String imagename[]=line.split("\t");
		
		System.out.println("Number_OF_Faces in "+imagename[0]+"   "+number_of_faces1);
		if(number_of_faces1==1)
		{
			String SUB_STR=v.substring(v.lastIndexOf("[")+1, v.length()-1);
			System.out.println(SUB_STR);
			String OutputImageName=imagename[0];
			MBFImage image=ImageUtilities.readMBF(new File("images1/"+OutputImageName));
			CreateFaceImages(imagename[0],SUB_STR,OutputImageName);
		}
	else
		{
			
			String multipleFaceInImage=null;
			String f1[]=v.split("\t");
		
		
			for(int i=1;i<f1.length;i++)
			{
				
				multipleFaceInImage=f1[i];
				String SUB_STR=multipleFaceInImage.substring(multipleFaceInImage.lastIndexOf("[")+1, multipleFaceInImage.length()-1);
				System.out.println(SUB_STR);
				
				String out_imgname=f1[0].substring(0, f1[0].lastIndexOf("."));
				out_imgname+=i;
				System.out.println(out_imgname);
				String imageNameWithExt=f1[0].substring(f1[0].lastIndexOf(".")+1,f1[0].length());
				CreateFaceImages(imagename[0], SUB_STR, out_imgname+"."+imageNameWithExt);
			}
			
		
		}
		
		}
	}	
	
	
	public static void CreateFaceImages(String imagename,String SUB_STR, String OutputImageName) throws IOException
	{
		String imgname=imagename;
		
		MBFImage image = ImageUtilities.readMBF(new File("images1/"+imgname));
		BufferedImage bi1=ImageIO.read(new File("images1/"+imgname));
		String[] fields=SUB_STR.split(",");
		String x_axis= fields[0].substring(fields[0].lastIndexOf("=")+1, fields[0].length());
		String y_axis=fields[1].substring(fields[1].lastIndexOf("=")+1, fields[1].length());
		String width=fields[2].substring(fields[2].lastIndexOf("=")+1, fields[2].length());
		String height=fields[3].substring(fields[3].lastIndexOf("=")+1, fields[3].length()-1);
	
		Float w1=Float.parseFloat(width);
		int w=w1.intValue();
		
		Float h1=Float.parseFloat(height);
		int h=h1.intValue();
		
		Float x_cor1=Float.parseFloat(x_axis);
		int x_cor=x_cor1.intValue();
		
		Float y_cor1=Float.parseFloat(y_axis);
		int y_cor=y_cor1.intValue();
		
		int [] data=new int [w*h];
        int i = 0;
        
        int  a=y_cor+h, b=x_cor+w;
        
        for (int y=y_cor; y<a; y++)
        {
        	for(int x=x_cor; x<b; x++)
        	{
        			BufferedImage img = ImageUtilities.createBufferedImage(image, bi1);
        			data[i++] = img.getRGB(x, y);
        	}
        }
       
        
        String filename=imgname;
        
      
        	
        String imageNameWithExt=filename.substring(filename.lastIndexOf(".")+1,filename.length());
        
        BufferedImage image1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    image1.setRGB(0, 0, w, h, data, 0, w);
	    File outputfile = new File(OutputImageName);
	    ImageIO.write(image1, imageNameWithExt, outputfile);
	    
		MBFImage extracted_image = ImageUtilities.readMBF(outputfile);
	    FKEFaceDetector det2 = new FKEFaceDetector();
    	List<KEDetectedFace> face2 = det2.detectFaces(Transforms.calculateIntensity(extracted_image));
    	for( KEDetectedFace face : face2 ) {
    		new KEDetectedFaceRenderer()
    		.drawDetectedFace(extracted_image,5,face);
    	}
    	DisplayUtilities.display(extracted_image);
    	    
	    	System.out.println("Image Created " +OutputImageName);
	
}
}
