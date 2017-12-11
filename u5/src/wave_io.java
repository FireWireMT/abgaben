
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class wave_io 
{
	public static void main(String[] args) 
	{
		int samples=0;
		int validBits=0;
		long sampleRate=0;
		long numFrames=0;
		int numChannels=0;

		String inFilename = null;
		String outFilename = null;
		
		
		
		if (args.length < 1) {
			try { throw new WavFileException("At least one filename specified  (" + args.length + ")"); }
			catch (WavFileException e1) { e1.printStackTrace(); }
		}
	
		inFilename= "../audio/Sprache_FireWire.wav"; //args[0];
		outFilename="../audio/Sprache_FireWireEcho100.wav"; //args[1];
		
		
		//read wave data, sample contained in array readWavFile.sound
		WavFile readWavFile = null;
		try {
			readWavFile = WavFile.read_wav(inFilename);
			
			//local copy of header data
			numFrames = readWavFile.getNumFrames(); 
			numChannels = readWavFile.getNumChannels();
			samples = (int)numFrames*numChannels;
			validBits = readWavFile.getValidBits();
			sampleRate = readWavFile.getSampleRate();
//			PrintWriter writer = new PrintWriter(new FileWriter(""));
			
			
		
//			long data[][] = new long[numChannels][(int)numFrames];
//
//			readWavFile.readFrames(data, (int)numFrames);
			
			// samples schreiben 2.1.	
			// aufgabe 5.1 clipping
//			System.out.println(samples);
			int db = 12;
			float a = (float) Math.pow(10, (db/(float) 20));	//Umrechnung aus Db nach linerarer Faktor
			float fx = 0;
//			short x = 0;
			short[] xArray = new short[samples];
			
//			for (int i=0; i < samples;i++) {
//				xArray[i] = readWavFile.sound[i];
//				fx = xArray[i];
//				fx = fx * a;
//				xArray[i] = (short) fx; //Fall 1 
//				if(fx > 32767){
//				xArray[i] = 32767;		//Fall 2
//				}
//				if(fx < -32768){
//					xArray[i] = -32768;	//Fall 3
//				}
////				writer.printf("%5d  %8d\n", i, readWavFile.sound[i]);
//				readWavFile.sound[i] = xArray[i];
//			}
			
			//aufgabe 5.2 Echo
			short[] y = new short[samples];
			float aEcho = (float) 0.6;
			float fEcho = 0;
			int echofaktor = 100;
//			for (int i=0; i < samples;i++) {
				for(int k = 0; k < samples; k++){
					xArray[k] = readWavFile.sound[k];
					if(k >= echofaktor){
						y[k] = (short) (xArray[k] + aEcho * xArray[k - echofaktor]);
					} else {
						y[k] = xArray[k];
					}
					readWavFile.sound[k] = y[k];
//				}
//				fx = xArray[i];
//				fx = fx + aEcho * fEcho;
//				xArray[i] = (short) fx; //Fall 1 
//				if(fx > 32767){
//				xArray[i] = 32767;		//Fall 2
//				}
//				if(fx < -32768){
//					xArray[i] = -32768;	//Fall 3
//				}
//				writer.printf("%5d  %8d\n", i, readWavFile.sound[i]);
				
			}
		    
		    if (args.length == 1) 
				System.exit(0);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (WavFileException e1) {
			e1.printStackTrace();
		}
			
		try {

////			// 2.4 Downsampling
//			PrintWriter writerDown = new PrintWriter(new FileWriter("doc/out_file/out_file_downsampled_sine_lo01.txt"));
//
//			for (int i=0; i < samples/2;i++) {
//
//				readWavFile.sound[i] = readWavFile.sound[i*2];
//
//			}
//
//			sampleRate /= 2;
//			numFrames /= 2;
//
//			for (int i=0; i < samples;i++) {
//				writerDown.printf("%5d  %8d\n", i, readWavFile.sound[i]);
//
//
//			}
			
 			// 3.2 Bitreduzierung
		/*	int reduced_bits = 6;
			int mask = (1<<reduced_bits); // 00001000
			mask -= 1; // 00000111
			mask = ~mask; // 11111000
			for (int i=0; i < samples;i++) {
				readWavFile.sound[i] &= mask;
				
			}*/
			
//// 			// 3.4 Bitreduzierung
//			int reduced_bits = 16;
//			for (int i=0; i < samples;i++) {
//				short bitredu = readWavFile.sound[i];
//				readWavFile.sound[i] = (short)(readWavFile.sound[i]/Math.pow(2, reduced_bits));
//				readWavFile.sound[i] = (short) (readWavFile.sound[i]*Math.pow(2, reduced_bits));
//
//				readWavFile.sound[i] -= bitredu;
//
//				readWavFile.sound[i] *= (short) (Math.pow(2, 16-reduced_bits-1));
//			}
			
//			float a = 24/20;		//Umrechnung aus Db nach linerarer Faktor
//			float fx = 0;
//			short x = 0;
//			short[] xArray = new  short[samples];
//			
			
//			double eff1
//			double eff2
//			double effComp = 
//			double k = eff1/eff2;
//			double y = 0;
			
			
			
//			for(int i = 0; i < samples; i++){
//				y = a * (xArray[i]);
//				fx = xArray[i];
//				fx *= a;
//				xArray[i] = (short) fx; //Fall 1 
//				if(fx > 32767){
//				xArray[i] = 32767;		//Fall 2
//				}
//				if(fx < -32768){
//					xArray[i] = -32768;	//Fall 3
//				}
//			}
//			
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		}			
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
