import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public final class bmp_io_ue6_vStud {

	public static void main(String[] args) throws IOException {
		String inFilename = null;
		String inFilename2 = null;

		PixelColor pc = null;
		PixelColor pc_f = null;

		BmpImage bmp = null;
		BmpImage bmp_f = null;

		String outFilename = null;
		OutputStream out = null;

		if (args.length < 1) {
			System.out.println("At least three filename specified  (" + args.length + ")");
			// System.exit(0);
		}

		inFilename = "doc/a4_flaeche_Y.bmp"; // args[0];
		InputStream in = new FileInputStream(inFilename);
		bmp = BmpReader.read_bmp(in);

		inFilename2 = "doc/a4_flaeche_Y_fehler.bmp";// args[1];
		InputStream in2 = new FileInputStream(inFilename2);
		bmp_f = BmpReader.read_bmp(in2);

		outFilename = "doc/output/a4_flaeche_median.bmp";// args[2];
		out = new FileOutputStream(outFilename);

		int kernels = 3;
		int[] dat = new int[9];
		int k3 = 0;
		int k6 = 0;
		int k7 = 0;
		double k0 = 0.4;
		double k1 = 0.2;

		// filter
		for (int y = 1; y < bmp.image.getHeight() - 1; y++) {
			for (int x = 1; x < bmp.image.getWidth() - 1; x++) {
				// int sum=0;
				// for(int i=x-1; i<=x+1;i++){
				// for (int j=y-1;j<=y+1;j++){
				// if(i<1 || j < 1 || i >=bmp.image.getWidth() || j >=
				// bmp.image.getHeight()){
				// sum+=115;
				// }else {
				// sum += bmp.image.getRgbPixel(i, j).b;
				//
				// }
				// }
				// }
				// sum/= kernels*kernels;
				// pc_f= new PixelColor(sum,sum,sum);
				// bmp.image.setRgbPixel(x,y,pc_f);
//				int k = 3;
//				int Ypc = bmp.image.getRgbPixel(x, y).b;
//				int Ypc_f = bmp_f.image.getRgbPixel(x, y).b;
//				pc = new PixelColor(k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128);
//				bmp.image.setRgbPixel(x, y, pc);

				// ----- Aufgabe 2 --------------------------------------------------
//				int sum = 0;
//				for (int i = x - 1; i <= x + 1; i++) {
//					for (int j = y - 1; j <= y + 1; j++) {
//						if (i < 1 || j < 1 || i >= bmp.image.getWidth() || j >= bmp.image.getHeight()) {
//							sum += 115;
//						} else if(j == y && i == x) {
//							
//							sum += bmp.image.getRgbPixel(i, j).b*12;
//						} else if((j == y && (i == x-1 || i == x+1)) || (i == x && (j == y-1 || j == y+1))){
//							sum += bmp.image.getRgbPixel(i, j).b*(-2);
//						} else if(j != y && i != x){
//							sum += bmp.image.getRgbPixel(i, j).b*0;
//						}
//					}
//				}
//				sum /= 4;
//				if(sum > 255){
//					sum = 255;
//				} 
//				if(sum < 0){
//					sum = 0;
//				}
//				pc_f = new PixelColor(sum, sum, sum);
//				bmp.image.setRgbPixel(x, y, pc_f);
				
//				int k = 3;
//				int Ypc = bmp.image.getRgbPixel(x, y).b;
//				int Ypc_f = bmp_f.image.getRgbPixel(x, y).b;
//				pc = new PixelColor(k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128);
//				bmp.image.setRgbPixel(x, y, pc);
				// -------------------------------------------------------------------
				// Aufgabe 3
				if(x > 0 && x < bmp.image.getWidth()-1 && y > 0 && y < bmp.image.getHeight()-1){
					dat[0] = bmp_f.image.getRgbPixel(x-1, y-1).r;
					dat[1] = bmp_f.image.getRgbPixel(x-1, y).r;
					dat[2] = bmp_f.image.getRgbPixel(x-1, y+1).r;
					dat[3] = bmp_f.image.getRgbPixel(x, y+1).r;
					dat[4] = bmp_f.image.getRgbPixel(x+1, y+1).r;
					dat[5] = bmp_f.image.getRgbPixel(x+1, y).r;
					dat[6] = bmp_f.image.getRgbPixel(x+1, y-1).r;
					dat[7] = bmp_f.image.getRgbPixel(x, y-1).r;
					dat[8] = bmp_f.image.getRgbPixel(x, y).r;
					Arrays.sort(dat);
					
					k3 = dat[dat.length/2];
					
				}
				
				pc = new PixelColor(k3, k3, k3);
				bmp.image.setRgbPixel(x, y, pc);
				
				// ------------------------------------------------------------------
				// Aufgabe 4 Vertikal
				/*k6 =  ((
						+(2 * bmp_f.image.getRgbPixel(x-1, y).r)
						+(0 * bmp_f.image.getRgbPixel(x, y).r)
						+(-2 * bmp_f.image.getRgbPixel(x+1, y).r)
						+(1 * bmp_f.image.getRgbPixel(x-1, y-1).r)
						+(0 * bmp_f.image.getRgbPixel(x, y-1).r)
						+(-1 * bmp_f.image.getRgbPixel(x+1, y-1).r)
						+(1 * bmp_f.image.getRgbPixel(x-1, y+1).r)
						+(0 * bmp_f.image.getRgbPixel(x, y+1).r)
						+(-1 * bmp_f.image.getRgbPixel(x+1, y+1).r))/1);
				
				k6 = (int)(k0*(k6-128)+128);
				
				if(k6 > 255){
					k6 = 255;
				}
				if(k6 <0){
					k6 = 0;
				}
				pc = new PixelColor(k6, k6, k6);
				bmp.image.setRgbPixel(x, y, pc);*/
				
				//Aufgabe 4 horiz
				
				/*k7 =  ((
						+(0 * bmp_f.image.getRgbPixel(x-1, y).r)
						+(0 * bmp_f.image.getRgbPixel(x, y).r)
						+(0 * bmp_f.image.getRgbPixel(x+1, y).r)
						+(1 * bmp_f.image.getRgbPixel(x-1, y-1).r)
						+(2 * bmp_f.image.getRgbPixel(x, y-1).r)
						+(1 * bmp_f.image.getRgbPixel(x+1, y-1).r)
						+(-1 * bmp_f.image.getRgbPixel(x-1, y+1).r)
						+(-2 * bmp_f.image.getRgbPixel(x, y+1).r)
						+(-1 * bmp_f.image.getRgbPixel(x+1, y+1).r))/1);
				
				k7 = (int)(k1*(k7-128)+128);
				
				if(k7 > 255){
					k7 = 255;
				}
				if(k7 <0){
					k7 = 0;
				}
				pc = new PixelColor(k7, k7, k7);
				bmp.image.setRgbPixel(x, y, pc);*/
				
				
			}
		}

		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}