
public class DNA_Class
{
	public double cost_Func(double prediction, int actual)
	{
		double output = 0.0;
		output = pow((prediction - actual), 2);
			return output;
	}

	public double sigmoidFunction(double prediction)
	{
		double raised = pow(10, (-1 * prediction);
		double output = (1)/(1 + raised));
		return output;
	}


	public double[] linearRegressionFunc (double Bias, double weight, int numNuerons, double activation, double learningRate, int actual)
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
	 public void FeedForward(Node[] Layers)
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

	private void BackPropagateError(Node[] curLayer, Node[] nextLayer) 
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


}


