import java.math.*;
import java.util.ArrayList;
import java.util.Random;

public interface DNA
{
	
	default double cost_Func(double prediction, int actual)
	{
		double output = 0.0;
		output = Math.pow(Math.E, (prediction - actual));
		return output;
	}

	default double sigmoidFunction(double prediction)
	{
		return 1 / (Math.sqrt(1 + prediction));
	}

	default ArrayList<Double> selectDecisions(int divisor, double[] outputNodes, int finalsize)
	{
		int index = 0;
		ArrayList<Double> preDecisions = new ArrayList<Double>();
			ArrayList<double[]> organizedDecisions = new ArrayList<double[]>();
			ArrayList<Double> finaloutput = new ArrayList<Double>();


		//Getting outputs for nodes and setting size for organized list
		for(int x = 0; x < finalsize; x++)
		{

				preDecisions.add(outputNodes[x]);
				double[] nullDecision = {-1, -1};
		    organizedDecisions.add(nullDecision);
		}


       		 //Sorting node outputs from lowest to highest
		do
		{
			double[] currentDecision = new double[2];
			currentDecision[0] = Double.valueOf(index);
			currentDecision[1] = preDecisions.get(index);
			organizedDecisions.add(currentDecision);
			
			for(int y =0; y <organizedDecisions.size(); y++)
			{
				if(organizedDecisions.get(organizedDecisions.size() -1)[1] > organizedDecisions.get(y)[1])
				{
					double[] next = organizedDecisions.get(y);
					organizedDecisions.set(y, currentDecision);
					organizedDecisions.set(organizedDecisions.size() -1, next);
					break;
				}
			}	

			index++;
		}
		while(index <= finalsize -1); //post check statement
			
		//remove unchanged outputs
		for(int x =0; x < organizedDecisions.size(); x++)
		{
			if(organizedDecisions.get(x)[0] == -1)
			{
				organizedDecisions.remove(x);
				x-=1;
			}
		}

        //Sets lower half to outputs of zero
        for(int x = 0; x < (organizedDecisions.size())/2; x++)
        {
		preDecisions.set(((int)organizedDecisions.get(x)[0]), 0.0);
	}
		
		return preDecisions;
	}
	//end of selectDecisions method
	
	default double[] linearRegressionFunc (double bias, double weight, int numNuerons, double activation, double learningRate, int actual)
	{
		double[] output = new double[2];
		int epochs = 0;
		double error = 0;

		while(epochs < numNuerons)
		{
			error = cost_Func(actual, actual);
			output[0] = weight - learningRate * 2 * (error/numNuerons);
			output[1] = bias - learningRate * 2 * ((error*actual)/numNuerons);
			
		}
		
		return output;
	}
	//end of linearRegressionFunc method
	
	default void BackPropagateError(Node[] inputLayer, Node[] curLayer, Node[] nextLayer, double LearningRate, double Momentum) 
	{
		double WeightDiff = 0.0; 
		//Create array of layers
		ArrayList<Node[]> Layers = new ArrayList<Node[]>();
		Layers.add(inputLayer);
		Layers.add(curLayer);
		Layers.add(nextLayer);
		
		//Nested fors to set weights properly for back prop
		for(int i = Layers.size() - 1; i > 0; i--)
		{
			// Update Weights
			for (int j = 0; j < Layers.get(i).length; j++) 
			{
				
			// Calculate Bias weight difference to node j
				Layers.get(i)[j].setDeltabias(LearningRate * 
					Layers.get(i)[j].getSignalError() + 
					Momentum*Layers.get(i)[j].getDetlaBias());

				// Update Bias weight to node j
					curLayer[j].setBias(curLayer[j].getBias() + 
					Layers.get(i)[j].getDetlaBias());

				// Update Weights
				for (int k = 0; k < Layers.get(i)[j].getWeights().size(); k++) 
				{
					//System.out.println("The current lenght of the array is: " + Layers.get(i)[j].getWeights().size());
					//System.out.println("The number of nodes in the earlier array is: " + (Layers.get(i-1).length - 1));
					
				// Calculate weight difference between node j and k
					WeightDiff = 
						LearningRate * 
						Layers.get(i)[j].getSignalError()* sigmoidFunction(Layers.get(i-1)[k].getActivation()) + 
						Momentum * Layers.get(i)[j].getSignalError();
				
					System.out.println("The Weight difference is: " + WeightDiff);
				//Set the difference between weights
					Layers.get(i)[j].setWeightDiff(WeightDiff);					

					// Update weight between node j and k
					Layers.get(i)[j].setWeights(k, Layers.get(i)[j].getWeights(k) + WeightDiff);
				
				}
			}
		}
	}
	//end of backPropogateError method
	
	default void CalculateSignalErrors(Node[]hiddenLayer ,Node[] outputLayer, double[] ExpectedOutput) 
	{
		int i,j,k;
		double Sum = 0.0;

	       	// Calculate all output signal error
		for (i = 0; i < outputLayer.length; i++) 
		{
			Random r = new Random();
			double randomValue = 0.1 + (0.8 - 0.1) * r.nextDouble();

			outputLayer[i].setSignalError((ExpectedOutput[i] * randomValue) - 
			Math.pow(outputLayer[i].getActivation(), 2) * 
			(1-outputLayer[i].getActivation()));
		}

	       	// Calculate signal error for all nodes in the hidden layer
		// (back propagate the errors)
		for (i = 3-2; i > 0; i--) 
		{
			Sum = 0;
			for (j = 0; j < hiddenLayer.length; j++) 
			{
				
				for(k = 0; k < outputLayer.length; k++)
				{
					Sum = Sum + hiddenLayer[j].getWeights(k) * 
						hiddenLayer[j].getSignalError(); //one expression
					
					hiddenLayer[i].setSignalError(hiddenLayer[i].getActivation()*(1 - 
						hiddenLayer[i].getActivation())*Sum); //one expression
					
				}
			}
		}
	}

}


