
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
		double raised = pow(10, (-1 * prediction));
		double output = (1)/(1 + raised);
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
		//NOTE: Consider passing array of all layers
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
						curLayer[j].SignalError*Node[k].Output + //NOTE: Logic here needs to be fixed
						(curLayer[j].getWeight() - nextLayer[j].getWeight);

					// Update weight between node j and k
					curLayer[k].setWeights(curLayer.get(j).SignalError*Layer[i-1].get(k).getActivation + curLayer.get[j].WeightDiff[k]);

					nextLayer[k].setWeights(curLayer[k].indexOf(), curLayer[k].getWeights(k) + curLayer[k].WeightDiff(k));
				}
			}
		}
	}

	default double CalculateSignalErrors(Node[] Layers, double[][] ExpectedOutput) 
	{
		int i,j,k,OutputLayer;
		OutputLayer = 2;
		double Sum;

		OutputLayer = NumberOfLayers-1;

	       	// Calculate all output signal error
		for (i = 0; i < Layers[OutputLayer].length; i++) 
			Layers[OutputLayer].get(i).SignalError 
				= (ExpectedOutput[SampleNumber][i] - 
					Layers[OutputLayer].get(i).getActivation()) * 
					Layers[OutputLayer].get(i).getActivation() * 
					(1-Layers[OutputLayer].get(i).getActivation());

	       	// Calculate signal error for all nodes in the hidden layer
		// (back propagate the errors)
		for (i = NumberOfLayers-2; i > 0; i--) 
		{
			for (j = 0; j < Layers[i].length; j++) {
				Sum = 0;

				for (k = 0; k < Layers[i+1].Node.length; k++)
					Sum = Sum + Layers[i+1].Node[k].Weight[j] * 
						Layers[i+1].Node[k].SignalError;

					Layers[i].get(i).SignalError 
					= Layers[i].get(j).getActivation()*(1 - 
						Layers[i].get(j).getActivation())*Sum;
			}
		}

		return sum;
	}

	default double CalculateOverallError(Node[][] layersNodes ,double[][] actual) 
	{

		int i,j;

		double OverallError = 0;
       	
		for (i = 0; i < NumberOfSamples; i++)
			for (j = 0; j < layersNodes[NumberOfLayers-1].Node.length; j++) {
           			OverallError = 
					OverallError + 
					0.5*( Math.pow(layersNodes[i][j].getActivation() - 
						ActualOutput[i][j],2) );
		}

		return OverallError;
	}

	public  BackPropagation(int NumberOfNodes[], double InputSamples[][], double OutputSamples[][], double LearnRate, double Moment, double MinError, long MaxIter) 
	{

		int i,j;

		// Initiate variables
		NumberOfSamples = InputSamples.length;
		MinimumError = MinError;
		LearningRate = LearnRate;
		Momentum = Moment;
		NumberOfLayers = NumberOfNodes.length;
		MaximumNumberOfIterations = MaxIter;

		// Create network layers
		Layer = new LAYER[NumberOfLayers];

		// Assign the number of node to the input layer
		Layer[0] = new LAYER(NumberOfNodes[0],NumberOfNodes[0]);

		// Assign number of nodes to each layer
		for (i = 1; i < NumberOfLayers; i++) 
			Layer[i] = new LAYER(NumberOfNodes[i],NumberOfNodes[i-1]);

			Input = new double[NumberOfSamples][Layer[0].Node.length];
			ExpectedOutput = new double[NumberOfSamples][Layer[NumberOfLayers-1].Node.length];
			ActualOutput = new double[NumberOfSamples][Layer[NumberOfLayers-1].Node.length];

		// Assign input set
		for (i = 0; i < NumberOfSamples; i++)
			for (j = 0; j < Layer[0].Node.length; j++)
				Input[i][j] = InputSamples[i][j];

		// Assign output set
		for (i = 0; i < NumberOfSamples; i++)
			for (j = 0; j < Layer[NumberOfLayers-1].Node.length; j++)
				ExpectedOutput[i][j] = OutputSamples[i][j];
}



}


