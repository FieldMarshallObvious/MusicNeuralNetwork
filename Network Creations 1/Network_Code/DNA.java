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

	default ArrayList<Double> selectDecisions(int divisor, Node[] outputNodes)
	{
		System.out.println("outputting decisions");
        int x = 0;
        ArrayList<Double> preDecisions = new ArrayList<Double>();
        ArrayList<double[]> organizedDecisions = new ArrayList<double[]>();
        
        //Getting outputs for nodes
        for(Node cur: outputNodes)
        {
            x++;
            double curOutput = this.sigmoidFunction(cur.getActivation());
            preDecisions.add(curOutput);
        }

        x = 0;

        
        //Giving organized list size
        for(int y = 0; y < preDecisions.size(); y++)
        {
            double[] nullDecision = {-1, -1};
            organizedDecisions.add(nullDecision);
        }
        

        //Sorting node outputs from lowest to highest
        for(double cur: preDecisions)
        {
            x++;
            double[] currentDecision = new double[2];
            currentDecision[0] = Double.valueOf(x);
            currentDecision[1] = cur;

            for(int y = 0; y < organizedDecisions.size(); y++)
            {
              
                if(cur < organizedDecisions.get(y)[1] || y == organizedDecisions.size() - 1)
                {
                    System.out.println("Added a Node");

                    double[] reciever = organizedDecisions.get(y);
                    organizedDecisions.set(y, currentDecision);
                    organizedDecisions.set(y-1, reciever);

                    break;
                }

            }
        }

        for(double[] cur : organizedDecisions)
        {
            System.out.println("The current in the organized list is: " + cur[0]);
            if(cur[0] == 2 || cur[0] == 3 )
                System.out.println("Found key: " + cur[0]);
        }

        //Sets lower half to outputs to zero
        for(int y = 0; y < (organizedDecisions.size())/2; y++)
        {
            System.out.println("The curret key is: " + organizedDecisions.get(y)[0] + "\n" + "The current value is: " + organizedDecisions.get(y)[1]);
            preDecisions.set(((int)organizedDecisions.get(y)[0]) - 1, 0.0);
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

		// Update Weights
		
		for (int j = 0; j < curLayer.length; j++) 
		{
				
			// Calculate Bias weight difference to node j
			double ThresholdDiff = LearningRate * 
					nextLayer[j].getSignalError() + 
					Momentum*nextLayer[j].getBias();

				// Update Bias weight to node j				curLayer[j].setBias( 
					curLayer[j].setActivation(curLayer[j].getBias() + 
					ThresholdDiff);

				// Update Weights
				for (int k = 0; k < nextLayer.length; k++) 
				{
					// Calculate weight difference between node j and k
					WeightDiff = 
						LearningRate * 
						curLayer[k].getActivation()*curLayer[k].getActivation() + //NOTE: Logic here needs to be fixed
						(curLayer[k].getWeights(k) - nextLayer[k].getWeights(k));

					// Update weight between node j and k
					curLayer[k].setWeights(k, curLayer[k].getActivation()*inputLayer[k].getActivation() + WeightDiff);

					nextLayer[k].setWeights(k, curLayer[k].getWeights(k) + WeightDiff);
				}
			}
	}

	default double CalculateSignalErrors(Node[]inputLayer ,Node[] outputLayer, double[] ExpectedOutput) 
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
			for (j = 0; j < inputLayer.length; j++) 
			{
				
				for(k = 0; k < outputLayer.length; k++)
				{
					Sum = Sum + inputLayer[j].getWeights(k) * 
						inputLayer[j].getSignalError();

					inputLayer[i].setSignalError(inputLayer[i].getActivation()*(1 - 
						inputLayer[i].getActivation())*Sum);
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


