package wave_io_vollständig;
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
	
		inFilename= "audio/Musik_FireWire.wav"; //args[0];
		outFilename="audio/Musik_FireWirebitredu16.wav"; //args[1];
		
		
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
			PrintWriter writer = new PrintWriter(new FileWriter(""));
			
			
		
			//long data[][] = new long[numChannels][(int)numFrames];

			//readWavFile.readFrames(data, (int)numFrames);
			
			// samples schreiben 2.1.	
			for (int i=0; i < samples;i++) {
				writer.printf("%5d  %8d\n", i, readWavFile.sound[i]);
				
			}
		    
		    if (args.length == 1) 
				System.exit(0);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (WavFileException e1) {
			e1.printStackTrace();
		}
			
		try {

//			// 2.4 Downsampling
			PrintWriter writerDown = new PrintWriter(new FileWriter("doc/out_file/out_file_downsampled_sine_lo01.txt"));

			for (int i=0; i < samples/2;i++) {

				readWavFile.sound[i] = readWavFile.sound[i*2];

			}

			sampleRate /= 2;
			numFrames /= 2;

			for (int i=0; i < samples;i++) {
				writerDown.printf("%5d  %8d\n", i, readWavFile.sound[i]);


			}
			
 			// 3.2 Bitreduzierung
		/*	int reduced_bits = 6;
			int mask = (1<<reduced_bits); // 00001000
			mask -= 1; // 00000111
			mask = ~mask; // 11111000
			for (int i=0; i < samples;i++) {
				readWavFile.sound[i] &= mask;
				
			}*/
			
// 			// 3.4 Bitreduzierung
			int reduced_bits = 16;
			for (int i=0; i < samples;i++) {
				short bitredu = readWavFile.sound[i];
				readWavFile.sound[i] = (short)(readWavFile.sound[i]/Math.pow(2, reduced_bits));
				readWavFile.sound[i] = (short) (readWavFile.sound[i]*Math.pow(2, reduced_bits));

				readWavFile.sound[i] -= bitredu;

				readWavFile.sound[i] *= (short) (Math.pow(2, 16-reduced_bits-1));
			}
//			
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		}			
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
