
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class wave_io {
	public static void main(String[] args) {
		int samples = 0;
		int validBits = 0;
		long sampleRate = 0;
		long numFrames = 0;
		int numChannels = 0;

		String inFilename = null;
		String outFilename = null;

		if (args.length < 1) {
			try {
				throw new WavFileException("At least one filename specified  (" + args.length + ")");
			} catch (WavFileException e1) {
				e1.printStackTrace();
			}
		}

		inFilename = "../audio/Musik_FireWire.wav"; // args[0];
		outFilename = "../audio/Musik_FireWireFilterNeg.wav"; // args[1];

		// read wave data, sample contained in array readWavFile.sound
		WavFile readWavFile = null;
		try {
			readWavFile = WavFile.read_wav(inFilename);

			// local copy of header data
			numFrames = readWavFile.getNumFrames();
			numChannels = readWavFile.getNumChannels();
			samples = (int) numFrames * numChannels;
			validBits = readWavFile.getValidBits();
			sampleRate = readWavFile.getSampleRate();
			// PrintWriter writer = new PrintWriter(new FileWriter(""));

			// long data[][] = new long[numChannels][(int)numFrames];
			//
			// readWavFile.readFrames(data, (int)numFrames);

			// samples schreiben 2.1.
			
			
			// aufgabe 5.1 clipping
			// System.out.println(samples);
			int db = 12;
			float a = (float) Math.pow(10, (db / (float) 20)); // Umrechnung aus
																// Db nach
																// linerarer
																// Faktor
			float fx = 0;
			// short x = 0;
			short[] xArray = new short[samples];

			 for (int i=0; i < samples;i++) {
			 xArray[i] = readWavFile.sound[i];
			 fx = xArray[i];
			 fx = fx * a;
			 xArray[i] = (short) fx; //Fall 1
			 if(fx > 32767){
			 xArray[i] = 32767; //Fall 2
			 }
			 if(fx < -32768){
			 xArray[i] = -32768; //Fall 3
			 }
			// writer.printf("%5d %8d\n", i, readWavFile.sound[i]);
			 readWavFile.sound[i] = xArray[i];
			 }

			// aufgabe 5.2 Echo
			short[] y = new short[samples];
			float aEcho = (float) 0.6;

//			float echo = 10f;
//			float t = (echo / 1000f) * 44100f;
//			System.out.println("t: " + t);
//			for (int k = 0; k < samples; k++) {
//				if (k > t) {
//					readWavFile.sound[k] = (short) (readWavFile.sound[k] + aEcho * (readWavFile.sound[(int) (k - t)]));
//				}
//			}
			
			//5.3 einfacher Filter
//			short[] copy = readWavFile.sound;
//			for(int k = 0; k < samples; k++){
//				if(k == 0){
//					y[k] = (short) (0.5 * copy[k] - 0.45 * copy[k]);
//				} else {
//					y[k] = (short) (0.5 * copy[k] - 0.45 * copy[k-1]);
//				}
//				readWavFile.sound[k] = y [k];
//			}

			if (args.length == 1)
				System.exit(0);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (WavFileException e1) {
			e1.printStackTrace();
		}

		try {

			//// // 2.4 Downsampling
			// PrintWriter writerDown = new PrintWriter(new
			//// FileWriter("doc/out_file/out_file_downsampled_sine_lo01.txt"));
			//
			// for (int i=0; i < samples/2;i++) {
			//
			// readWavFile.sound[i] = readWavFile.sound[i*2];
			//
			// }
			//
			// sampleRate /= 2;
			// numFrames /= 2;
			//
			// for (int i=0; i < samples;i++) {
			// writerDown.printf("%5d %8d\n", i, readWavFile.sound[i]);
			//
			//
			// }

			// 3.2 Bitreduzierung
			/*
			 * int reduced_bits = 6; int mask = (1<<reduced_bits); // 00001000
			 * mask -= 1; // 00000111 mask = ~mask; // 11111000 for (int i=0; i
			 * < samples;i++) { readWavFile.sound[i] &= mask;
			 * 
			 * }
			 */

			//// // 3.4 Bitreduzierung
			// int reduced_bits = 16;
			// for (int i=0; i < samples;i++) {
			// short bitredu = readWavFile.sound[i];
			// readWavFile.sound[i] = (short)(readWavFile.sound[i]/Math.pow(2,
			//// reduced_bits));
			// readWavFile.sound[i] = (short) (readWavFile.sound[i]*Math.pow(2,
			//// reduced_bits));
			//
			// readWavFile.sound[i] -= bitredu;
			//
			// readWavFile.sound[i] *= (short) (Math.pow(2, 16-reduced_bits-1));
			// }

			// float a = 24/20; //Umrechnung aus Db nach linerarer Faktor
			// float fx = 0;
			// short x = 0;
			// short[] xArray = new short[samples];
			//

			// double eff1
			// double eff2
			// double effComp =
			// double k = eff1/eff2;
			// double y = 0;

			// for(int i = 0; i < samples; i++){
			// y = a * (xArray[i]);
			// fx = xArray[i];
			// fx *= a;
			// xArray[i] = (short) fx; //Fall 1
			// if(fx > 32767){
			// xArray[i] = 32767; //Fall 2
			// }
			// if(fx < -32768){
			// xArray[i] = -32768; //Fall 3
			// }
			// }
			//
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
