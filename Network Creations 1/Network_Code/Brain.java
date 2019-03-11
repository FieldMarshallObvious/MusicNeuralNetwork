import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import DryWetMidi;
import java.io.PrintWriter;


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

		IntialNetwork initNet = new IntialNetwork(89, 88, learningRate);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(44, 44, learningRate);
		SelectionNetwork selectNet = new SelectionNetwork(22, 22, learningRate);

		PrintWriter pwIn = new PrintWriter("<input file>");
		PrintWriter pwOut = new PrintWriter("<output file>");


		for(int e = 0; e <= epochs; e++)
		{
			initNet.settingInputs((setInputs("<input file>", 89)));
			initNet.calcOutput();
			initNet.writeOutputs();
			
			forgettingNet.settingInputs((setInputs("<output file>", 44)));
			forgettingNet.calcOutput();
			//clearing old outputs
			pwOut.close();

			forgettingNet.writeOutputs();

			selectNet.settingInputs((setInputs("<output file>", 22)));
			//clearing old outputs
			pwOut.close();
			selectNet.calcOutput();
			selectNet.writeOutputs();
			

			System.out.print("Epoch: " + e);

			System.out.println();
			System.out.print("Initial Network Error: ");
			initNet.training_Nodes(getExpectedOutput("<input file>", dataSize), learningRate, Momentum);

			System.out.println();
			System.out.print("Forgetting Network Error: ");
			forgettingNet.training_Nodes(getExpectedOutput("<input file>", dataSize), learningRate, Momentum);

			System.out.println();
			System.out.print("Selection Network Error: ");
			selectNet.training_Nodes(getExpectedOutput("<input file>", dataSize), learningRate, Momentum);

			System.out.println();
		}

		//Once reachers final itteration implement piano use


	}

	private static double[] getExpectedOutput(String fileLocation, int dataSize) throws FileNotFoundException
	{
		int x = 0;

		double[] output = new double[dataSize];

		Scanner expectedDataScanner = new Scanner(new File(fileLocation));
		
		while(expectedDataScanner.hasNext())
		{
			x++;
			output[x] = Double.valueOf(expectedDataScanner.nextLine());
		}

		return output;
	}

	private static double[] setInputs(String filelocation, int dataSize) throws FileNotFoundException
	{
		int x = 0;
		double[] output = new double[dataSize];

		Scanner inputDataScanner = new Scanner(new File(filelocation));

		while(inputDataScanner.hasNext())
		{
			x++;
			output[x] = Double.valueOf(inputDataScanner.nextLine());
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
