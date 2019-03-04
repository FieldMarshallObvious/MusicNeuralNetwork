import java.math.*;
public interface DNA
{
	
	default double cost_Func(double prediction, int actual)
	{
		double output = 0.0;
		output = Math.pow((prediction - actual), 2);
			return output;
	}

	default double sigmoidFunction(double prediction)
	{
		double raised = Math.pow(10, (-1 * prediction));
		double output = (1)/(1 + raised);
		return output;
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

	default void BackPropagateError(Node[] inputLayer, Node[] curLayer, double signalErrorCur, Node[] nextLayer, double signalErrorNext,double LearningRate, double Momentum) 
	{
		//NOTE: Consider passing array of all layers
		double WeightDiff = 0.0; 

		// Update Weights
		
		for (int i = 2-1; i > 0; i--) 
		{
		
			for (int j = 0; j < curLayer.length; j++) 
			{
				
				// Calculate Bias weight difference to node j
				double ThresholdDiff = LearningRate * 
					signalErrorNext + 
					Momentum*nextLayer[j].getBias();

				// Update Bias weight to node j
				curLayer[j].setBias( 
					curLayer[j].getBias() + 
					ThresholdDiff);

				// Update Weights
				for (int k = 0; k < nextLayer.length; k++) 
				{
					// Calculate weight difference between node j and k
					WeightDiff = 
						LearningRate * 
						signalErrorCur*curLayer[k].getActivation() + //NOTE: Logic here needs to be fixed
						(curLayer[j].getWeights(k) - nextLayer[k].getWeights(k));

					// Update weight between node j and k
					curLayer[k].setWeights(k, signalErrorCur*inputLayer[k].getActivation() + WeightDiff);

					nextLayer[k].setWeights(k, curLayer[k].getWeights(k) + WeightDiff);
				}
			}
		}
	}

	default double CalculateSignalErrors(Node[]inputLayer, Node[] outputLayer, double[][] ExpectedOutput, int SampleNumber) 
	{
		int i,j,k,OutputLayer;
		
		double Sum;
		double signalErrorCur;


	       	// Calculate all output signal error
		for (i = 0; i < outputLayer.length; i++) 
			outputLayer[i].SignalError 
				= (ExpectedOutput[SampleNumber][i] - 
					outputLayer(i).getActivation() * 
					outputLayer(i).getActivation() * 
					(1-outputLayer(i).getActivation()));

	       	// Calculate signal error for all nodes in the hidden layer
		// (back propagate the errors)

		for (j = 0; j < inputLayer.length; j++) 
		{
			Sum = 0;

			for (k = 0; k < layers[i+1].Node.length; k++)
			{
				Sum = Sum + layers[i+1].Node[k].Weight[j] * 
					layers[i+1].Node[k].SignalError;

				layers[i].get(i).SignalError 
				= layers[i].get(j).getActivation()*(1 - 
					layers[i].get(j).getActivation())*Sum;
			}
		

		return sum;
		}
	}

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


