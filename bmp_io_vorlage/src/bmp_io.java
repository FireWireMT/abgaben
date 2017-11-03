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
		
		PrintWriter writer = new PrintWriter(new FileWriter("doc/output/out_grating_V3.txt"));
		
		// BGR schreiben horizontal 2.1.	
    	for(int x = 0; x < bmp.image.getWidth(); x++) {
    		int y = 0;
    		writer.printf("x:%3d, y:%3d, r:%5d, g:%5d, b:%5d\n", x, y, bmp.image.getRgbPixel(x, y).r, bmp.image.getRgbPixel(x, y).g, bmp.image.getRgbPixel(x, y).b);
    		//writer.println("X: " + x + ", y: " + y + ", R: " + bmp.image.getRgbPixel(x, y).r + " G: " + bmp.image.getRgbPixel(x, y).g + " B: " + bmp.image.getRgbPixel(x, y).b);
		}

		// BGR schreiben vertikal 2.1.	
    	for(int y = 0; y < bmp.image.getHeight(); y++) {
    		int x = 0;
    		writer.printf("x:%3d, y:%3d, r:%5d, g:%5d, b:%5d\n", x, y, bmp.image.getRgbPixel(x, y).r, bmp.image.getRgbPixel(x, y).g, bmp.image.getRgbPixel(x, y).b);
    		//writer.println("X: " + x + ", y: " + y + ", R: " + bmp.image.getRgbPixel(x, y).r + " G: " + bmp.image.getRgbPixel(x, y).g + " B: " + bmp.image.getRgbPixel(x, y).b);
    		
    	}
    	
	    if (args.length == 1) 
			System.exit(0);

		outFilename = "doc/output/out_grating_V3.bmp";//args[1];
		OutputStream out = new FileOutputStream(outFilename);
		
		// erzeuge graustufenbild
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
			
				// ********* ToDo ***************
				
			}
		}
		
		// downsampling
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0; x < bmp.image.getWidth(); x++) {
		
				// ********* ToDo ***************
				
			}
		}
		
		// bitreduzierung
		int reduced_bits = 1;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
		
				// ********* ToDo ***************
			
			}
		}
		
		// bitreduzierung differenz
		reduced_bits = 1;
		int bitsPerColor = 8;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {

				// ********* ToDo ***************
				
			}
		}
		
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
