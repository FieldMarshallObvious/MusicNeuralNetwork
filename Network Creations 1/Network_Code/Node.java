import java.lang.Math;
import java.util.ArrayList;

class Node
{
    private ArrayList<Double> weights;
    private ArrayList<Double> Δweights;    

    private double activation;
    private double bias;
    private double Δbias;
    
    private double signalError;

    //Constructor Methods
    public Node()
    {
        weights = new ArrayList<Double>();
        activation = 0.0;
        bias = 0.0;
    }

    public Node(double inputBias)
    {
        this();
        bias  = inputBias;
    }

    //Functions
    public void setWeights(int numneurons)
    {
        for(int x = 0; x < numneurons; x++)
        {
            double randnum = Math.random() * (100 - 5);
            //System.out.println("This weight is " + randnum);
            weights.add(randnum);
        }
    }

    public void setWeights(int index, double newWeight)
    {
        Δweights.set(index, weights.get(index) - newWeight);
        weights.set(index, newWeight);
    }

    public void setActivation(double newActivation)
    {
        activation = newActivation;
    }

    public void setBias(double newBias)
    {
        Δbias = bias - newBias;
        bias = newBias;
    }

    public void setSignalError(double newSignalError)
    {
        signalError = newSignalError;
    }

        //this method should read in note values, calculate the difference between them
    // and then return that value as a double for further use
    public double deltaMidiCompare(double noteOne, double noteTwo)
    {
        double delta = (noteOne - noteTwo);
        return delta;
    }

    public double activationFunc(double[] activations)
    {
            for(int x = 0; x < weights.size(); x++)
            {
                activation += weights.get(x)*activations[x] + bias;
                System.out.println("The input activation is " +activations[x]);
                System.out.println("The Weight of node [x] is " + weights.get(x));
                System.out.println("Activation of cur Node: " + activation);
            }
            return activation;
    }

     //Return methods 
     public double getWeights(int index){return weights.get(index);}
 
     public double getBias(){return bias;}
 
     public double getActivation(){ return activation;}
 
     public double getSignalError(){ return signalError;}


     public String toString()
     {
        String nodeInfo = "";
        
        nodeInfo = "Node Info: \n" + "Biases: " + bias + "\nCurrent Weights: ";
        nodeInfo += "[";
        for(int x = 0; x < weights.size(); x++)
        {
            nodeInfo += weights.get(x);
            if(x < weights.size() - 1)
                nodeInfo += ", ";
        }
        nodeInfo += "]";

        return nodeInfo;
     }
}
