
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

}


