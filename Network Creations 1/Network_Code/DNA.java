import java.math.*;
import java.util.ArrayList;

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
		}while(index <= finalsize -1);
			
		//remove unchanged outputs
		for(int x =0; x < organizedDecisions.size(); x++)
		{
			if(organizedDecisions.get(x)[0] == -1)
			{
				organizedDecisions.remove(x);
				x-=1;
			}
		}

        //Sets lower half to outputs to zero
        for(int x = 0; x < (organizedDecisions.size())/2; x++)
        {
			preDecisions.set(((int)organizedDecisions.get(x)[0]), 0.0);
		}
		
		return preDecisions;
	}
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
	
	default void BackPropagateError(Node[] inputLayer, Node[] curLayer, Node[] nextLayer, double LearningRate, double Momentum) 
	{
		//NOTE: Consider passing array of all layers
		double WeightDiff = 0.0; 
		ArrayList<Node[]> Layers = new ArrayList<Node[]>();
		Layers.add(inputLayer);
		Layers.add(curLayer);
		Layers.add(nextLayer);

		for(int i = Layers.size() - 1; i > 0; i--)
		{
			// Update Weights
			for (int j = 0; j < Layers.get(i).length; j++) 
			{
				
			// Calculate Bias weight difference to node j
			double BiasDiff = LearningRate * 
					Layers.get(i)[j].getSignalError() + 
					Momentum*nextLayer[j].getBias();

				// Update Bias weight to node j
					curLayer[j].setBias(curLayer[j].getBias() + 
					BiasDiff);

				// Update Weights
				for (int k = 0; k < Layers.get(i)[j].getWeights().size(); k++) 
				{
					// Calculate weight difference between node j and k
					WeightDiff = 
						LearningRate * 
						Layers.get(i)[j].getSignalError()*Layers.get(i-1)[k].getActivation() + 
						Momentum * Layers.get(i)[k].getSignalError();

					System.out.println("The Weight difference is: " + WeightDiff);

					Layers.get(i)[k].setWeightDiff(WeightDiff);					

					// Update weight between node j and k
					curLayer[k].setWeights(k, Layers.get(i)[j].getWeights(k) + WeightDiff);
				}
			}
		}
	}

	default double CalculateSignalErrors(Node[]hiddenLayer ,Node[] outputLayer, double[] ExpectedOutput) 
	{
		int i,j,k,OutputLayer;
		OutputLayer = 2;
		double Sum = 0.0;

	       	// Calculate all output signal error
		for (i = 0; i < outputLayer.length; i++) 
		{
			outputLayer[i].setSignalError(ExpectedOutput[i] - 
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
						hiddenLayer[j].getSignalError();

					hiddenLayer[i].setSignalError(hiddenLayer[i].getActivation()*(1 - 
						hiddenLayer[i].getActivation())*Sum);
				}
			}
		}

		return Sum;
	}

	//Consider removing
	/*
	 // Calculate the node activations
	 default void FeedForward(Node[] Layers)
	 {

		// Since no weights contribute to the output 
		// vector from the input layer,
		// assign the input vector from the input layer 
		// to all the node in the first hidden layer
		for (int i = 0; i < Layers[0].getNumNeurons; i++)
		{
			Layers[0].Node[i].Output = Layers[0].Input[i];
		}

		Layers[1].Input = Layers[0].Input;
		for (int i = 1; i < NumberOfLayers; i++) 
		{
			Layers[i].FeedForward();

			// Unless we have reached the last layer, assign the layer i's output vector
			// to the (i+1) layer's input vector
			if (i != NumberOfLayers-1)
				Layers[i+1].Input = Layers[i].OutputVector();
		}

	} */


	/*
	default double CalculateOverallError(Node[][] layersNodes ,double[][] actual) 
	{

		int i,j;

		double OverallError = 0;
       	
		for (i = 0; i < NumberOfSamples; i++)
		{
			for (j = 0; j < layersNodes[NumberOfLayers-1].Node.length; j++) {
           			OverallError = 
					OverallError + 
					0.5*( Math.pow(layersNodes[i][j].getActivation() - 
						ActualOutput[i][j],2) );
		}

		return OverallError;
	}

	}
*/



}


