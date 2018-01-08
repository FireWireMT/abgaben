import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

		inFilename2 = "doc/output/a6_flaeche_2a.bmp";// args[1];
		InputStream in2 = new FileInputStream(inFilename2);
		bmp_f = BmpReader.read_bmp(in2);

		outFilename = "doc/output/a6_flaeche_2b.bmp";// args[2];
		out = new FileOutputStream(outFilename);

		int kernels = 3;

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
				
				int k = 3;
				int Ypc = bmp.image.getRgbPixel(x, y).b;
				int Ypc_f = bmp_f.image.getRgbPixel(x, y).b;
				pc = new PixelColor(k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128, k * (Ypc - Ypc_f) + 128);
				bmp.image.setRgbPixel(x, y, pc);
				// -------------------------------------------------------------------
			}
		}

		try {
			BmpWriter.write_bmp(out, bmp);
		} finally {
			out.close();
		}
	}
}