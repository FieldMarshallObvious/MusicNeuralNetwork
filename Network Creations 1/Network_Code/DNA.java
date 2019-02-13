
public interface DNA
{
	
	default double cost_Func(double prediction, int actual)
	{
		double output = 0.0;
		output = pow((prediction - actual), 2);
			return output;
	}

	default double sigmoidFunction(double prediction)
	{
		double raised = pow(10, (-1 * prediction);
		double output = (1)/(1 + raised));
		return output;
	}


	default double[] linearRegressionFunc (double Bias, double weight, int numNuerons, double activation, double learningRate, int actual)
	{
		double[] output = new double[2];
		int epochs = 0;
		double error = 0;

		while(epochs < numNuerons)
		{
			error = cost_Func(prediction, actual);
			output[0] = weight - learningRate * 2 * (error/numNuerons);
			output[1] = bias - learningRate * 2 * ((error*actual)/NumNuerons);
			
		}
		
		return output;
	}

	 // Calculate the node activations
	 default void FeedForward(Node[] Layers)
	 {

		// Since no weights contribute to the output 
		// vector from the input layer,
		// assign the input vector from the input layer 
		// to all the node in the first hidden layer
           	for (int i = 0; i < Layers[0].Node.length; i++)
				Layer[0].Node[i].Output = Layer[0].Input[i];

		Layer[1].Input = Layer[0].Input;
		for (int i = 1; i < NumberOfLayers; i++) 
		{
			Layer[i].FeedForward();

			// Unless we have reached the last layer, assign the layer i's output vector
			// to the (i+1) layer's input vector
			if (i != NumberOfLayers-1)
				Layer[i+1].Input = Layer[i].OutputVector();
		}

	} 

	default void BackPropagateError(Node[] curLayer, Node[] nextLayer) 
	{
		double WeightDiff;

		// Update Weights
		
		for (i = NumberOfLayers-1; i > 0; i--) 
		{
		
			for (j = 0; j < curLayer.length; j++) {
				/*
				// Calculate Bias weight difference to node j
				curLayer[j].ThresholdDiff 
					= LearningRate * 
					nextLayer[j].SignalError + 
					Momentum*Node[j].ThresholdDiff;

				// Update Bias weight to node j
				curLayer[j].Threshold = 
					curLayer[j].Threshold + 
					curLayer[j].ThresholdDiff;*/

				// Update Weights
				for (k = 0; k < nextLayer.length; k++) {
					// Calculate weight difference between node j and k
					WeightDiff = 
						LearningRate * 
						curLayer[j].SignalError*Node[k].Output +
						(curLayer[j].getWeight() - nextLayer[j].getWeight);

					// Update weight between node j and k
					curLayer[j].setWeights();

					nextLayer.setWeights();
				}
			}
		}
	}

	default void CalculateSignalErrors() 
	{
		int i,j,k,OutputLayer;
		double Sum;

		OutputLayer = NumberOfLayers-1;

	       	// Calculate all output signal error
		for (i = 0; i < Layer[OutputLayer].Node.length; i++) 
			Layer[OutputLayer].Node[i].SignalError 
				= (ExpectedOutput[SampleNumber][i] - 
					Layer[OutputLayer].Node[i].Output) * 
					Layer[OutputLayer].Node[i].Output * 
					(1-Layer[OutputLayer].Node[i].Output);

	       	// Calculate signal error for all nodes in the hidden layer
		// (back propagate the errors)
		for (i = NumberOfLayers-2; i > 0; i--) {
			for (j = 0; j < Layer[i].Node.length; j++) {
				Sum = 0;

				for (k = 0; k < Layer[i+1].Node.length; k++)
					Sum = Sum + Layer[i+1].Node[k].Weight[j] * 
						Layer[i+1].Node[k].SignalError;

				Layer[i].Node[j].SignalError 
					= Layer[i].Node[j].Output*(1 - 
						Layer[i].Node[j].Output)*Sum;
			}
		}

	}

}


