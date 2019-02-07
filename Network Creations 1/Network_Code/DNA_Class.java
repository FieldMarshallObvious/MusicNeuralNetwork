import MidiConvert

class DNA_Class()
{
	var midi = MidiConvert.create()

	var fs = require(‘fs’)
	double freq = 0
	//same thing as in the brain, don't know how to deal with this
	/*
	public midi_Interept void (midiNote curNote)
	{	
		midi.track()
		
		.patch();
				.Note(cureNote)
				
		fs.writeFileSync(“output.mid”, midi.encode(), “binary”)
	}
	*/

	public void create_Note(midiNote curNote)
	{
	// midi.track()
	// .patch(<instrument number>)

	// .note(curNote.getNote, curNote.getTime, midiNum.getDuration)
	}

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
		return = output;
	}


	public linearRegressionFunc double[] (double Bias, double weight, int numNuerons, double activation, double learningRate, int actual)
	{
		double[] output = new double[2];
		int epochs = 0;
		double error = 0;

	while(epochs < numNuerons)
	{
			error = cost_Func(prediction, actual);
			double[0] = weight - learningRate * 2 * (error/numNuerons);
			double[1] = bias - learningRate * 2 * ((error*actual)/NumNuerons);
			
		}
		
		return output;
	}

}


