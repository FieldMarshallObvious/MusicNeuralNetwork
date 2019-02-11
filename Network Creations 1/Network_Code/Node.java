import java.lang.Math;
import java.util.ArrayList;

class Node
{
    ArrayList<Double> weights;
    ArrayList<Double> Δweights;    

    double activation;
    double bias;
    double Δbias;
    
    public Node()
    {
        Weights = new ArrayList<Double>();
        activation = 0.0;
        bias = 0.0;
    }

    public Node(double inputBias)
    {
        this();
        bias  = inputBias;
    }

    public void setWeights(int numneurons)
    {
        for(int x = 0; x < numneurons; x++)
        {
            double randnum = Math.random();
            weights.add(randnum);
        }
    }
    
    public void setWeights(int index, double newWeight)
    {
        Δweights.set(index, weights.get(index) - newWeight);
        weight.set(index, newWeight);
    }

    public void setActivation(double newActivation)
    {
        Activation = newActivation;
    }

    public double getWeights(int index)
    {
        return 0.0;
    }



public void setBias(double newBias)
{
    Δbias = bias - newBias;
    bias = newBias;
}

public double activationFunc(double[]activations)
    {
        for(int x = 0; x < weights.length; x++)
        {
            activation += weights.get(x)*acivations[x] + bias;
        }
        return activation;
}
}
