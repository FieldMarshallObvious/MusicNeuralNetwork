import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import DryWetMidi;


//currently not quite sure how to work with midi files in this so this is essentially just the psuedo code

public class Brain
{
	//Note: need to write handeling logic for Exception  
	public static void main(String[] args) throws IOException
	{ 
		double learningRate = 0.3;
		double Momentum = 0.5;

		int epochs = 1000;
		int dataSize = dataSize("<input file>");

		IntialNetwork initNet = new IntialNetwork(88, 88, learningRate);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(44, 44, learningRate);
		SelectionNetwork selectNet = new SelectionNetwork(22, 22, learningRate);


		for(int e = 0; e <= epochs; e++)
		{
			initNet.calcOutput();
			initNet.writeOutputs();

			forgettingNet.calcOutput();
			forgettingNet.writeOutputs();

			selectNet.calcOutput();
			selectNet.writeOutputs();
		}

		initNet.training_Nodes(getExpectedOutput("<input file>", dataSize), learningRate, Momentum);

	}

	private static double[] getExpectedOutput(String fileLocation, int dataSize) throws FileNotFoundException
	{
		int x = 0;

		double[] output = new double[dataSize];

		Scanner expectedDataReaderScanner = new Scanner(new File(fileLocation));
		
		while(expectedDataReaderScanner.hasNext())
		{
			x++;
			output[x] = Double.valueOf(expectedDataReaderScanner.nextLine());
		}

		return output;
	}

	private static int dataSize(String fileLocation) throws FileNotFoundException
	{
		int output = 0;
		Scanner inputDataReadeScanner = new Scanner(new File(fileLocation));
		while(inputDataReadeScanner.hasNext())
		{
			inputDataReadeScanner.nextLine();
			output+=1;
		}
		return output;
	}
	/*
	public static void ConvertMidiToText(string midiFilePath, string textFilePath)
	{
 		   var midiFile = MidiFile.Read(midiFilePath);

  		  File.WriteAllLines(textFilePath, midiFile.GetNotes()
            .Select(n => $"{n.NoteNumber} {n.Time} {n.Length}"));
	}

	public String readMidiText(string textFilePath)
	{

	}*/

}
