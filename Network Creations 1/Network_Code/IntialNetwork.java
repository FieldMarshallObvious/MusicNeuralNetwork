import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IntialNetwork implements DNA
{
    Node[] layers = new Node[3];
    Node[] inputNodes;
    Node[] hiddenNodes;
    Node[] outputNodes;

    double[] decisions;

    int numOfInputs;
    int numOfOutputs;  
    double learningRate;  

    String finaloutput;

public IntialNetwork()
    {
        numOfInputs = 0;
        numOfOutputs = 0;
        learningRate = 0.0;    

        finaloutput = new String(" ");

        makingRays(0, 0);
    }

    public IntialNetwork(int newInputs, int newOutputs)
    {
        this();

        //assigning nodes for ray objects
        Node.assigningNodes(curLayer);
    
    
        //creating weights
        //creating hidden layer weights 
        Node.creatingWeights(inputNodes.size, hiddenNodes);
        //creating output layer weights
        Node.creatingWeights(hiddenNodes.size, outputNodes);
    }

    private void makingRays(int newnewInputs, int newOutputs)
    {
        //creating input Nodes ray
        inputNodes = new Node[numOfInputs];
        outputNodes = new Node[numOfOutputs];

        //determining hidden Nodes based on num of outputs and inputs
        hiddenNodesNodes = new Node[((numOfInputs + numOfOutputs)/2) + ((numOfInputs + numOfOutputs)/2)];


        //creating final deicisions ray
        decisions = new double[outputNodes.size];
    }
    private void assigningNodes(Node[] curLayer)
    {
        for(int x = 0; x < curLayer.size; x++)
        {
            Node curNode = new Node();
            Node[x] = curNode;
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
    double[] activations = new double[inputNodes.size];
    double[] oldactivations = activations;


    for(int x = 0; x < inputNodes.size; x++)
    {
        activations[x] = inputNodes[x].getActivation(x);
        oldactivations[x] = activations[x];
    }
    
    for(int x = 0; x < inputNodes.size; x ++)
    {
        double curactivation = activations[x]; 
        activations[x] =  hiddenNodes[x].get(inputNodes[x].activationFunc(oldactivations));
    }
    
    oldactivations = activations;

    for(int x = 0; x < hiddenNodes.size; x++)
    {
        double curavtivation = activations[x];
        activations[x] = outputNodes[x].get(hiddenNodes[x].activationFunc(oldactivations));
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
    
    public void writeOutputs()
    {
        usingBufferedWritter();
    }
    public void training_Nodes(double[] ExpectedOutput)
    {
        this.CalculateSignalErrors(Layers, ExpectedOutput);
        this.BackPropagateError(hiddenNodes, outputNodes);
    }

}