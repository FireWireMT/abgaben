import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public final class bmp_io {
	
	// Run with zero command-line arguments. This program reads Demo.bmp and writes Demo2.bmp in the current directory.
	public static void main(String[] args) throws IOException {
		String inFilename = null;
		String outFilename = null;
		PixelColor pc = null;
		BmpImage bmp = null;
		
		if (args.length < 1) 
			System.out.println("At least one filename specified  (" + args.length + ")"); 
		
		inFilename = "doc/grating_V3.bmp";//args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);
		
		String file = "doc/verb_output/grating_V3_down1.txt";
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter writer = null;
		
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		writer = new PrintWriter(bw);
		
		// BGR schreiben horizontal 2.1.	
    	for(int x = 0; x < bmp.image.getWidth(); x++) {
    		pc= bmp.image.getRgbPixel(x, 0);
    		writer.println("" + pc.r + " ");
    		
		}

		// BGR schreiben vertikal 2.1.	
    	for(int y = 0; y < bmp.image.getHeight(); y++) {
    		pc= bmp.image.getRgbPixel(0, y);
    		writer.println("" + pc.r + " ");
    		
    	}
    	writer.close();
    	
	    if (args.length == 1) 
			System.exit(0);

		outFilename = "doc/verb_output/grating_V3_down1.bmp";//args[1];
		OutputStream out = new FileOutputStream(outFilename);
	
		PixelColor pixc = null;
		
		// erzeuge graustufenbild
	/*	for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
				pixc = bmp.image.getRgbPixel(x, y);
				int gp = (pixc.r+pixc.g+pixc.b)/3;
				PixelColor gpx = new PixelColor(gp,gp,gp);
				bmp.image.setRgbPixel(x, y, gpx);			
			}
		}*/
		// downsampling
		int xpre1 = 0;
	    int ypre1 = 0;
	    for(int y = 0; y < bmp.image.getHeight(); y++) {
	      for(int x = 0; x < bmp.image.getWidth(); x++) {
	        if(y % 2 !=0){
	          ypre1 = y -1;
	          PixelColor ynew = new PixelColor(bmp.image.getRgbPixel(x, ypre1).r, bmp.image.getRgbPixel(x, ypre1).g, bmp.image.getRgbPixel(x, ypre1).b);
	          bmp.image.setRgbPixel(x, y, ynew);
	          if(x % 2 !=0){
	            xpre1 = x - 1;
	            PixelColor xnew = new PixelColor(bmp.image.getRgbPixel(xpre1, y).r, bmp.image.getRgbPixel(xpre1, y).g, bmp.image.getRgbPixel(xpre1, y).b);
	            bmp.image.setRgbPixel(x, y, xnew);  
	          }
	        }
	      }
	    }
					
		// bitreduzierung
	/*	PixelColor p2 = null;
		int reduced_bits = 5;
		for(int y = 0; y < bmp.image.getHeight(); y++){
			for(int x = 0; x < bmp.image.getWidth(); x++){
				p2 = bmp.image.getRgbPixel(x, y);
				p2.r = (int)(p2.r/Math.pow(2,  reduced_bits));
				p2.g = (int)(p2.g/Math.pow(2,  reduced_bits));
				p2.b = (int)(p2.b/Math.pow(2,  reduced_bits));
				p2.r = (int)(p2.r*Math.pow(2,  reduced_bits));
				p2.g = (int)(p2.g*Math.pow(2,  reduced_bits));
				p2.b = (int)(p2.b*Math.pow(2,  reduced_bits));
				
				bmp.image.setRgbPixel(x, y, p2);
			}
		}
				
		// bitreduzierung differenz
		int reduced_bits = 1;
		PixelColor p2 = null;
		int bitsPerColor = 8;
		PixelColor p3 = null;
		PixelColor p4 = null;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				p2 = new PixelColor(bmp.image.getRgbPixel(x, y).g,
									bmp.image.getRgbPixel(x, y).b,
									bmp.image.getRgbPixel(x, y).r);;
				p3 = new PixelColor(bmp.image.getRgbPixel(x, y).g,
									bmp.image.getRgbPixel(x, y).b,
									bmp.image.getRgbPixel(x, y).r);;
				
				p2.r = (int)(p2.r/Math.pow(2,  reduced_bits));
				p2.g = (int)(p2.g/Math.pow(2,  reduced_bits));
				p2.b = (int)(p2.b/Math.pow(2,  reduced_bits));
				p2.r = (int)(p2.r*Math.pow(2,  reduced_bits));
				p2.g = (int)(p2.g*Math.pow(2,  reduced_bits));
				p2.b = (int)(p2.b*Math.pow(2,  reduced_bits));					
									
				bmp.image.setRgbPixel(x,y, new PixelColor((int)(((p3.g-p2.g)/2+127)*Math.pow(2,  8-reduced_bits-1)),
														  (int)(((p3.r-p2.r)/2+127)*Math.pow(2,  8-reduced_bits-1)),
														  (int)(((p3.b-p2.b)/2+127)*Math.pow(2,  8-reduced_bits-1))));
			}
		}*/
				
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
		
		