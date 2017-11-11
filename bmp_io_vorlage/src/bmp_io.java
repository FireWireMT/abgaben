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
		
		inFilename = "doc/a2_detail1.bmp";//args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);
		
		PrintWriter writer = new PrintWriter(new FileWriter("doc/output/a2_detail1_3.3.txt"));
		
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

		outFilename = "doc/output/a2_detail_3.3.bmp";//args[1];
		OutputStream out = new FileOutputStream(outFilename);
		
		// erzeuge graustufenbild
		
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
				int r = bmp.image.getRgbPixel(x, y).r;
				int g = bmp.image.getRgbPixel(x,y).g;
				int b = bmp.image.getRgbPixel(x,y).b;
				double gp = Math.sqrt(0.299*Math.pow(r,2) + 0.587*Math.pow(g,2) + 0.114*Math.pow(b,2));
				PixelColor gpx = new PixelColor((int)gp,(int)gp,(int)gp);
				bmp.image.setRgbPixel(x, y, gpx);
						
			}
		}
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
		int reduced_bits = 1;
		int mask = (1<<reduced_bits); // 00001000
		mask -= 1; // 00000111
		mask = ~mask; // 11111000
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				int r = (int)((bmp.image.getRgbPixel(x, y).r)/reduced_bits)*reduced_bits;
				int g = (int)((bmp.image.getRgbPixel(x, y).g)/reduced_bits)*reduced_bits;
				int b = (int)((bmp.image.getRgbPixel(x, y).b)/reduced_bits)*reduced_bits;
				PixelColor pRed = new PixelColor(r,g,b);
				bmp.image.setRgbPixel(x, y, pRed);
			}
		}
				
		// bitreduzierung differenz
		reduced_bits = 4;
		int bitsPerColor = 8;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for (int x = 0; x < bmp.image.getWidth(); x++) {
				PixelColor color = bmp.image.getRgbPixel(x,y);
				int r = color.r;
				int g = color.g;
				int b = color.b;

				color.r = color.r/(int)Math.pow(2,reduced_bits);
				color.g = color.g/(int)Math.pow(2,reduced_bits);
				color.b = color.b/(int)Math.pow(2,reduced_bits);

				color.r = color.r*(int)Math.pow(2,reduced_bits);
				color.g = color.g*(int)Math.pow(2,reduced_bits);
				color.b = color.b*(int)Math.pow(2,reduced_bits);

				color.r -= r;
				color.g -= g;
				color.b -= b;

				color.r *=Math.pow(2, bitsPerColor-reduced_bits-1);
				color.g *=Math.pow(2, bitsPerColor-reduced_bits-1);
				color.b *=Math.pow(2, bitsPerColor-reduced_bits-1);

				color.r = 127+color.r/2;
				color.g = 127+color.g/2;
				color.b = 127+color.b/2;

				bmp.image.setRgbPixel(x,y,color);
			}
		}
				
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
		
		