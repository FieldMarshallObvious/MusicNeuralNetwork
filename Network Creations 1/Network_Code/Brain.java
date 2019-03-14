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
		int dataSize = dataSize("inputfile.dat");

		IntialNetwork initNet = new IntialNetwork(dataSize, dataSize-1, learningRate);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(dataSize/2, (dataSize/2) - 1, learningRate);
		SelectionNetwork selectNet = new SelectionNetwork(dataSize/4, (dataSize/2) - 1, learningRate);

		


		for(int e = 0; e <= epochs; e++)
		{
			initNet.settingInputs((setInputs("inputfile.dat", 89)));
			initNet.calcOutput();
			initNet.writeOutputs();
			
			forgettingNet.settingInputs((setInputs("outputfile.dat", 44)));
			forgettingNet.calcOutput();
			//clearing old outputs
			//pwOut.close();

			forgettingNet.writeOutputs();

			selectNet.settingInputs((setInputs("outputfile.dat", 22)));
			//clearing old outputs
			//pwOut.close();
			selectNet.calcOutput();
			selectNet.writeOutputs();


			System.out.print("Epoch: " + e);

			System.out.println();
			System.out.print("Initial Network Error: ");
			initNet.training_Nodes(getExpectedOutput("input file.txt", dataSize), learningRate, Momentum);

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
		inputDataScanner.close();

		return output;
	}
	private static int dataSize(String fileLocation) throws FileNotFoundException
	{
		int output = 0;
		Scanner inputDataReadScanner = new Scanner(new File(fileLocation));
		try
		{
			//System.out.println("First line in data " + inputDataReadScanner.nextLine());
			while(inputDataReadScanner.hasNext())
			{
				inputDataReadScanner.nextLine();
				output+=1;
			}
			inputDataReadScanner.close();
		
			return output;
		}
		catch(Exception e)
		{
			System.out.println("Houston we have a problem!");
			System.out.println("We have a failure due to " + e.getClass().getSimpleName());
			return 0;
		}
	}
}
