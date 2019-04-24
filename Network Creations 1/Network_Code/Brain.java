package src.Network_Code;

import java.util.Scanner;

import Network_Code.ForgettingNetwork;
import Network_Code.InitialNetwork;
import Network_Code.SelectionNetwork;
import Network_Code.midiNote;
import portfolio.project.Piano;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Brain {
	// Note: need to write handeling logic for Exception
	public static void main(String[] args) throws IOException {

		String inputfile = "src/Network_Code/inputfile.dat";
		String outputfile = "src/Network_Code/outputfile.dat";
		String midioutput = "src/Network_Code/midioutput.dat";
		String expectedfile = "src/Network_Code/excpected.dat";
		String midiFile = "dflat5 csharp5 blfat4 gsharp4";

		double learningRate = 0.05;
		double Momentum = 0.25;

		int epochs = 100000;
		int dataSize = dataSize(inputfile);
		int noteLocation = 0;


		InitialNetwork initNet = new InitialNetwork(dataSize, dataSize - 1, learningRate);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(dataSize, dataSize - 1, learningRate);
		SelectionNetwork selectNet = new SelectionNetwork(dataSize, dataSize - 1, learningRate);
		midiNote midiNoteSelecter = new midiNote();
		Piano newPiano = new Piano();

	
	for (int e = 0; e <= epochs; e++) {
			//Determine location in midiFile
			if(noteLocation >= noteSize(midiFile) && noteLocation != 0)
			{
				noteLocation = 0;
				midiNoteSelecter.clearFile(midioutput);
			}
			else
				noteLocation++;

			System.out.println("Entering Input Network");
			initNet.settingInputs((setInputs(inputfile, 89)));
			initNet.writeOutputs();

			System.out.println("Entering Forgetting Network");
			forgettingNet.settingInputs((setInputs(outputfile, 89)));
			forgettingNet.writeOutputs();
			// clearing old outputs
			// pwOut.close();

			System.out.println("Entering Selection Network");
			selectNet.settingInputs((setInputs(outputfile, 89)));
			// clearing old outputs
			// pwOut.close();
			selectNet.writeOutputs();

			midiNoteSelecter.noteToNetConverter(nextNote(midiFile, noteLocation), noteSize(midiFile), expectedfile);
			midiNoteSelecter.netToNoteConvereter(outputfile, midioutput);
			
			System.out.print("Epoch: " + e);

			System.out.println();
			System.out.print("Initial Network Error: ");
			initNet.training_Nodes(getExpectedOutput(expectedfile, dataSize), learningRate, Momentum);

			System.out.println();
			System.out.print("Forgetting Network Error: ");
			forgettingNet.training_Nodes(getExpectedOutput(expectedfile, dataSize), learningRate, Momentum);

			System.out.println();
			System.out.print("Selection Network Error: ");
			selectNet.training_Nodes(getExpectedOutput(expectedfile, dataSize), learningRate, Momentum);

			inputfile = outputfile;

			System.out.println();
		}
	
		
	}

	private static double[] getExpectedOutput(String fileLocation, int dataSize) throws FileNotFoundException {
		int x = 0;

		double[] output = new double[dataSize];

		Scanner expectedDataScanner = new Scanner(new File(fileLocation));

		while (expectedDataScanner.hasNext()) {
			output[x] = Double.valueOf(expectedDataScanner.nextLine());
			x++;
		}

		return output;
	}

	// sets inputs of specific data size and at a specific location in the array
	private static double[] setInputs(String filelocation, int dataSize) throws FileNotFoundException {
		int x = 0;
		double[] output = new double[dataSize];

		Scanner inputDataScanner = new Scanner(new File(filelocation));

		while (inputDataScanner.hasNext()) {
			output[x] = Double.valueOf(inputDataScanner.nextLine());
			x++;
		}
		inputDataScanner.close();

		return output;
	}

	// gives size of data, makes cheeky NASA reference
	private static int dataSize(String fileLocation) throws FileNotFoundException {
		int output = 0;
		Scanner dataSizeScanner = new Scanner(new File(fileLocation));
		try {
			// System.out.println("First line in data " + inputDataReadScanner.nextLine());
			while (dataSizeScanner.hasNext()) {
				dataSizeScanner.nextLine();
				output += 1;
			}
			dataSizeScanner.close();

			return output;
		} catch (Exception e) {
			System.out.println("Houston we have a problem!");
			System.out.println("We have a failure due to " + e.getClass().getSimpleName());
			return 0;
		}
	}

	private static String nextNote(String fileLocation, int times) throws FileNotFoundException
	{
		String output = new String(" ");
		Scanner nextNoteScanner = new Scanner(fileLocation);
		int x = 0;
		nextNoteScanner.useDelimiter(" ");
		while(nextNoteScanner.hasNext())
		{
			x++;
			String cur = nextNoteScanner.next();
			if(x == times)
			{
				output = cur; 
				break;
			}

		}
		nextNoteScanner.close();
		return output;
	}

	private static int noteSize(String fileLocation) throws FileNotFoundException
	{
		String output = new String();
		Scanner noteSizeScanner = new Scanner(fileLocation);
		int x = 0;
		noteSizeScanner.useDelimiter(" ");
		while(noteSizeScanner.hasNext())
		{
			x++;
			noteSizeScanner.next();
		}
		noteSizeScanner.close();
		return x;
	}
	
}
