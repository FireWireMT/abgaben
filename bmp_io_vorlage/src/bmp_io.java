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
		
		inFilename = "doc/a2_fläche1.bmp";//args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);
		
		String file = "doc/output/a2_fläche_down5.txt";
		
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

		outFilename = "doc/output/a2_fläche_down5.bmp";//args[1];
		OutputStream out = new FileOutputStream(outFilename);
	
		PixelColor pixc = null;
		
		// erzeuge graustufenbild
		/*for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
				pixc = bmp.image.getRgbPixel(x, y);
				int gp = (pixc.r+pixc.g+pixc.b)/3;
				PixelColor gpx = new PixelColor(gp,gp,gp);
				bmp.image.setRgbPixel(x, y, gpx);			
			}
		}*/
		// downsampling
		int n = 1;
		int m = 5;
	    for(int y = 0; y < bmp.image.getHeight(); y++) {
	      for(int x = 0; x < bmp.image.getWidth(); x++) {
	        if((x) % m != 0 && x != 0 && y != 0 ){
	        	PixelColor downsampling = bmp.image.getRgbPixel(x-n,y-n);
	        	bmp.image.setRgbPixel(x,y,downsampling);
			}
	      }
	    }
					
		// bitreduzierung
		/*PixelColor p2 = null;
		int reduced_bits = 2;
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
		}*/
				
		// bitreduzierung differenz
		/*int reduced_bits = 8;
		int bitsPerColor = 8;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				PixelColor color = bmp.image.getRgbPixel(x,y);

				int r = color.r;
				int g = color.g;
				int b = color.b;


				color.r = (int)(color.r/Math.pow(2,  reduced_bits));
				color.g = (int)(color.g/Math.pow(2,  reduced_bits));
				color.b = (int)(color.b/Math.pow(2,  reduced_bits));

				color.r = (int)(color.r*Math.pow(2,  reduced_bits));
				color.g = (int)(color.g*Math.pow(2,  reduced_bits));
				color.b = (int)(color.b*Math.pow(2,  reduced_bits));

				color.r -= r;
				color.g -= g;
				color.b -= b;

				color.r *= Math.pow(2, bitsPerColor-reduced_bits-1);
				color.g *= Math.pow(2, bitsPerColor-reduced_bits-1);
				color.b *= Math.pow(2, bitsPerColor-reduced_bits-1);

				color.r = 127 + color.r/2;
				color.g = 127 + color.g/2;
				color.b = 127 + color.b/2;
									
				bmp.image.setRgbPixel(x,y, color);
			}
		}*/
				
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
		
		