import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class Brain
{
	//Note: need to write handeling logic for Exception  
	public static void main(String[] args) throws IOException
	{
		
		String inputfile = "/Users/noahdelangel/Desktop/School/Portfolio Project/MusicNeuralNetwork/Network Creations 1/Network_Code/inputfile.dat";
		String outputfile = "/Users/noahdelangel/Desktop/School/Portfolio Project/MusicNeuralNetwork/Network Creations 1/Network_Code/outputfile.dat";
		String expectedfile = "/Users/noahdelangel/Desktop/School/Portfolio Project/MusicNeuralNetwork/Network Creations 1/Network_Code/excpected.dat";

		double learningRate = 0.3;
		double Momentum = 0.5;

		int epochs = 1000;
		int dataSize = dataSize(inputfile);

		InitialNetwork initNet = new InitialNetwork(dataSize, dataSize-1, learningRate);
		ForgettingNetwork forgettingNet = new ForgettingNetwork(dataSize, dataSize - 1, learningRate);
		SelectionNetwork selectNet = new SelectionNetwork(dataSize, dataSize - 1, learningRate);
		
		
		for(int e = 0; e <= 1; e++)
		{
			System.out.println("Entering Input Network");
			initNet.settingInputs((setInputs(inputfile, 89)));
			initNet.writeOutputs();
			
			System.out.println("Entering Forgetting Network");
			forgettingNet.settingInputs((setInputs(outputfile, 89)));
			forgettingNet.writeOutputs();
			//clearing old outputs
			//pwOut.close();


			System.out.println("Entering Selection Network");
			selectNet.settingInputs((setInputs(outputfile, 89)));
			//clearing old outputs
			//pwOut.close();
			selectNet.calcOutput();
			selectNet.writeOutputs();


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

			//inputfile = outputfile;

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
	
	//sets inputs of specific data size and at a specific location in the array
	private static double[] setInputs(String filelocation, int dataSize) throws FileNotFoundException
	{
		int x = 0;
		double[] output = new double[dataSize];

		Scanner inputDataScanner = new Scanner(new File(filelocation));

		while(inputDataScanner.hasNext())
		{
			output[x] = Double.valueOf(inputDataScanner.nextLine());
			x++;
		}
		inputDataScanner.close();

		return output;
	}
	
	//gives size of data, makes cheeky NASA reference
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
