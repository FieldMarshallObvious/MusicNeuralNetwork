import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;



public class ForgettingNetwork implements DNA
{
    private Node[] inputNodes;
    private Node[] hiddenNodes;
    private Node[] outputNodes;

    private double[] decisions;

    private int numOfInputs;
    private int numOfOutputs;  
    private double learningRate;  

    private String finaloutput;

    public ForgettingNetwork()
    {
        //set default values
        numOfInputs = 0;
        numOfOutputs = 0;
        learningRate = 0.0;    

        finaloutput = new String(" ");

        makingRays(0, 0);
    }

    public ForgettingNetwork(int newInputs, int newOutputs, double inputLearningRate)
    {
        this();
        learningRate = inputLearningRate;

        //creating rays with input values
        System.out.println("The number of inputs is: " + newInputs);
        makingRays(newInputs, newOutputs);
        System.out.println("The number nodes in the input layer: " + inputNodes.length);

        //assigning nodes for ray objects
        assigningNodes(inputNodes);
        assigningNodes(hiddenNodes);
        assigningNodes(outputNodes);


        //creating weights
        //creating hidden layer weights 
        creatingWeights(inputNodes.length, hiddenNodes);
        //creating output layer weights
        creatingWeights(hiddenNodes.length, outputNodes);
    }

   
    public double[] calcOutput()
    {
        //structures to hold values for calculations
        double[] activations = new double[hiddenNodes.length];
        double[] oldactivations = activations;

        //Creates list of input node activations
        for(int x = 0; x < inputNodes.length; x++)
        {
            activations[x] = sigmoidFunction(inputNodes[x].getActivation());
             oldactivations[x] = activations[x];    
        }
    
        //Creates list of hidden node activations
        for(int x = 0; x < inputNodes.length; x ++)
        {
            double curactivation =  sigmoidFunction(hiddenNodes[x].activationFunc(oldactivations));
            activations[x] = curactivation;
        }
    
        //Sets input node activations to hidden node activations;
        oldactivations = activations;
        activations = new double[outputNodes.length];

        //Creates list of output nodes activations
        for(int x = 0; x < outputNodes.length; x++)
        {
            double curavtivation = sigmoidFunction(outputNodes[x].activationFunc(oldactivations));
            activations[x] = curavtivation;
        }

        return activations;
    }
    public void settingInputs(double[] inputs)
    {
        for(int x = 0; x < inputNodes.length; x++)
        {
            inputNodes[x].setActivation(inputs[x]);
        }
    }
    
    //writes combined calculations to a place in memory with reference "finaloutput"
    public void writeOutputs() throws IOException
    {
        ArrayList<Double> netDecisions = this.selectDecisions(2, this.calcOutput(), outputNodes.length);

        finaloutput = netDecisions.stream().map(Object::toString).collect(Collectors.joining("\n"));

        usingBufferedWritter();
    }
    public void training_Nodes(double[] ExpectedOutput, double learningRate, double Momentum)
    {
        //REALLY IN NEED OF FIXING
        this.CalculateSignalErrors(hiddenNodes, outputNodes, ExpectedOutput);
        this.BackPropagateError(inputNodes,hiddenNodes, outputNodes, learningRate, Momentum);
    }

    private void makingRays(int newInputs, int newOutputs)
    {
        //creating input Nodes ray
        inputNodes = new Node[newInputs];
        outputNodes = new Node[newOutputs];

        //determining hidden Nodes based on num of outputs and inputs
        hiddenNodes = new Node[((newInputs + newOutputs)/2) + ((newInputs + newOutputs)/2)];


        //creating final deicisions ray
        decisions = new double[outputNodes.length];
    }

    private void assigningNodes(Node[] curLayer)
    {
        for(int x = 0; x < curLayer.length; x++)
        {
            Node curNode = new Node();
            curLayer[x] = curNode;
        }
    }

    private void creatingWeights(int previousLayerSize, Node[] curLayer)
    {
        for(Node curNode : curLayer)
        {
            curNode.setWeights(previousLayerSize);
        }
    }

    private void usingBufferedWritter() throws IOException
    {
        String fileContent = finaloutput;
     
        BufferedWriter writer = new BufferedWriter(new FileWriter("outputfile.dat"));
        writer.write(fileContent);
        writer.close();
    }
    
}
