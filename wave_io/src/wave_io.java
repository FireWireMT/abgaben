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
	
		inFilename= "doc/sine_hi01.wav"; //args[0];
		outFilename="doc/out_file/out_file_sine_hi01.wav"; //args[1];
		
		
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
			PrintWriter writer = new PrintWriter(new FileWriter("doc/out_file/out_file_sine_hi01.txt"));
			
			
		
			//long data[][] = new long[numChannels][(int)numFrames];

			//readWavFile.readFrames(data, (int)numFrames);
			
			// samples schreiben 2.1.	
			for (int i=0; i < samples;i++) {
				writer.printf("%5d  %8d\n", i, readWavFile.sound[i]);
				// ********* ToDo *************** 	
				
			}
		    
		    if (args.length == 1) 
				System.exit(0);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WavFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		try {

			// 2.4 Downsampling
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
			int reduced_bits = 3;
			int mask = (1<<reduced_bits); // 00001000
			mask -= 1; // 00000111
			mask = ~mask; // 11111000
			for (int i=0; i < samples;i++) {
				readWavFile.sound[i] &= mask;
				
			}
			
 			// 3.4 Bitreduzierung
			reduced_bits = 1;
			for (int i=0; i < samples;i++) {
			
				// ********* ToDo *************** 	
				
			}
			
			WavFile.write_wav(outFilename, numChannels, numFrames, validBits, sampleRate, readWavFile.sound);
		}			
		catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
