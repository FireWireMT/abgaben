package u4;
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
		
		PrintWriter writer = new PrintWriter(new FileWriter("doc/output/out_histogramm_K_neg.txt"));
		
		// BGR schreiben horizontal 2.1.	
    	for(int x = 0; x < bmp.image.getWidth(); x++) {
    		int y = 0;
//    		writer.printf("x:%3d, y:%3d, r:%5d, g:%5d, b:%5d\n", x, y, bmp.image.getRgbPixel(x, y).r, bmp.image.getRgbPixel(x, y).g, bmp.image.getRgbPixel(x, y).b);
    		//writer.println("X: " + x + ", y: " + y + ", R: " + bmp.image.getRgbPixel(x, y).r + " G: " + bmp.image.getRgbPixel(x, y).g + " B: " + bmp.image.getRgbPixel(x, y).b);
		}

		// BGR schreiben vertikal 2.1.	
    	for(int y = 0; y < bmp.image.getHeight(); y++) {
    		int x = 0;
//    		writer.printf("x:%3d, y:%3d, r:%5d, g:%5d, b:%5d\n", x, y, bmp.image.getRgbPixel(x, y).r, bmp.image.getRgbPixel(x, y).g, bmp.image.getRgbPixel(x, y).b);
    		//writer.println("X: " + x + ", y: " + y + ", R: " + bmp.image.getRgbPixel(x, y).r + " G: " + bmp.image.getRgbPixel(x, y).g + " B: " + bmp.image.getRgbPixel(x, y).b);
    		
    	}
    	
	    if (args.length == 1) 
			System.exit(0);

		outFilename = "doc/output/a4_flaeche_Y.bmp";//args[1];
		OutputStream out = new FileOutputStream(outFilename);
		
		// Graustufenbild, Cb und Cr
		int[] counter = new int[256]; 
		int yCounter = 0;
		double konCount = 0;
		for(int y = 0; y < bmp.image.getHeight(); y++) {
			for(int x = 0;x < bmp.image.getWidth(); x++) {
				int r = bmp.image.getRgbPixel(x, y).r;
				int g = bmp.image.getRgbPixel(x,y).g;
				int b = bmp.image.getRgbPixel(x,y).b;

				//Farbwerte Cb
				double cbr = -0.169*r;
				double cbg = -0.331*g;
				double cbb = 0.5*b;
				//Farbwerte Cr
				double crr = 0.5*r;
				double crg = -0.419*g;
				double crb = -0.081*b;

				int Y =(int) (0.299*r + 0.587*g + 0.114*b);
				double Cb = (int)(cbr + cbg + cbb)+128;
				double Cr = (int)(crr + crg + crb)+128;
				//Rekonstruktion

				double j = Y;	//Pixelintensit�t(Y)
				double k = -1;	//Kontrast�anderung
				j *= k;
				double h = 0.0;	//Helligkeits�nderung
				//j += h;
				double newR = j + 1.403 * (Cr-128);
				double newG = j - 0.344 * (Cb-128) - 0.714*(Cr-128);
				double newB = j + 1.773 * (Cb-128);
				double f = k*(j-128)+128+h;

				if(newR < 0){
					newR=0;
				}
				if(newG < 0){
					newG=0;
				}
				if(newB < 0){
					newB=0;
				}

				if(newR > 255){
					newR=255;
				}
				if(newG > 255){
					newG=255;
				}
				if(newB > 255){
					newB=255;
				}

				PixelColor newpx = new PixelColor((int)(Y),(int)(Y),(int)(Y));
				bmp.image.setRgbPixel(x, y, newpx);

				yCounter += j;
				for(int index = 0; index < 256; index++){
					if(j == index){
						counter[index]++;
					}
				}


			}
		}
//		double midY = yCounter/(bmp.image.getWidth()*bmp.image.getHeight());
//		for(int y = 0; y < bmp.image.getHeight(); y++) {
//			for(int x = 0;x < bmp.image.getWidth(); x++) {
//				int r = bmp.image.getRgbPixel(x, y).r;
//				int g = bmp.image.getRgbPixel(x,y).g;
//				int b = bmp.image.getRgbPixel(x,y).b;
//				int Y =(int) (0.299*r + 0.587*g + 0.114*b);
//
//				konCount += Math.pow(Y - midY, 2);
//			}
//		}
		
//		System.out.println(konCount);
//		f(j) = k*(j-128)+128+h

//		double konY = Math.sqrt(konCount/(bmp.image.getWidth()*bmp.image.getHeight()));
//		System.out.printf("mittlere Helligkeit: %f5 %n", midY);
//		System.out.printf("Kontrast: %f5 %n", konY);

		for(int c: counter){
			System.out.println(c);
		}

//		
//		// downsampling
//		int xpre1 = 0; //horizontales Downsampling
////		int ypre1 = 0; //vertikales Downsampling
//		for(int y = 0; y < bmp.image.getHeight(); y++) {
//			for(int x = 0; x < bmp.image.getWidth(); x++) {
//					if(x % 2 !=0){
//						xpre1 = x - 1;
////						ypre1 = y - 1;
//						PixelColor xnew = new PixelColor(bmp.image.getRgbPixel(xpre1, y).r, bmp.image.getRgbPixel(xpre1, y).g, bmp.image.getRgbPixel(xpre1, y).b);
//						bmp.image.setRgbPixel(x, y, xnew);	
//					}
//			}
//		}
//		
//		// bitreduzierung
//		int reduced_bits = 1;
//		int mask = (1<<reduced_bits); // 00001000
//		mask -= 1; // 00000111
//		mask = ~mask; // 11111000
//		for(int y = 0; y < bmp.image.getHeight(); y++) {
//			for (int x = 0; x < bmp.image.getWidth(); x++) {
//					int r = (int)((bmp.image.getRgbPixel(x, y).r)/reduced_bits)*reduced_bits;
//					int g = (int)((bmp.image.getRgbPixel(x, y).g)/reduced_bits)*reduced_bits;
//					int b = (int)((bmp.image.getRgbPixel(x, y).b)/reduced_bits)*reduced_bits;
//					PixelColor pRed = new PixelColor(r,g,b);
//					bmp.image.setRgbPixel(x, y, pRed);
//			}
//		}
//		
//		// bitreduzierung differenz
//		reduced_bits = 1;
//		int bitsPerColor = 8;
//		for(int y = 0; y < bmp.image.getHeight(); y++) {
//			for (int x = 0; x < bmp.image.getWidth(); x++) {
//
//				// ********* ToDo ***************
//				
//			}
//		}
		
		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}
