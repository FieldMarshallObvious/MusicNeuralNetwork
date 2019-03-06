import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class ForgettingNetwork implements DNA
{
    private Node[] layers = new Node[3];
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
        numOfInputs = 0;
        numOfOutputs = 0;
        learningRate = 0.0;    

        finaloutput = new String(" ");

        makingRays(0, 0);
    }

    public ForgettingNetwork(int newInputs, int newOutputs)
    {
        this();

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

    private void makingRays(int newnewInputs, int newOutputs)
    {
        //creating input Nodes ray
        inputNodes = new Node[numOfInputs];
        outputNodes = new Node[numOfOutputs];

        //determining hidden Nodes based on num of outputs and inputs
        hiddenNodes = new Node[((numOfInputs + numOfOutputs)/2) + ((numOfInputs + numOfOutputs)/2)];


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

private double[] calcOutput()
{
    double[] activations = new double[inputNodes.length];
    double[] oldactivations = activations;


    for(int x = 0; x < inputNodes.length; x++)
    {
        activations[x] = inputNodes[x].getActivation();
        oldactivations[x] = activations[x];
    }
    
    for(int x = 0; x < inputNodes.length; x ++)
    {
        double curactivation =  hiddenNodes[x].activationFunc(oldactivations); 
        activations[x] = curactivation;
    }
    
    oldactivations = activations;

    for(int x = 0; x < hiddenNodes.length; x++)
    {
        double curavtivation = outputNodes[x].activationFunc(oldactivations);
        activations[x] = curavtivation;
    }

    return activations;
}

    private static void usingBufferedWritter() throws IOException
    {
        String fileContent = "<output text>";
     
        BufferedWriter writer = new BufferedWriter(new FileWriter("<output file>"));
        writer.write(fileContent);
        writer.close();
    }
    
    public void writeOutputs() throws IOException
    {
        usingBufferedWritter();
    }
    public void training_Nodes(double[] ExpectedOutput)
    {
        //REALLY IN NEED OF FIXING
        this.CalculateSignalErrors(layers, outputNodes, ExpectedOutput);
        //this.BackPropagateError(hiddenNodes, outputNodes);
    }
}