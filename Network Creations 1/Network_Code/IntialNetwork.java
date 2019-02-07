import MidiConvert;
import Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IntialNetwork
{
    Node[] inputNodes;
    Node[] hiddenNodes;
    Node[] outputNodes;
    Node[] decisions;
    int numOfInputs = 0;
    int numOfOutputs = 0;

     public void Initial_Network()
    {
        //creating input Nodes ray
        inputNodes = new Node[numOfInputs];
        outputNodes = new Node[numOfOutputs];

        //determining hidden Nodes based on num of outputs and inputs
        hiddenNodesNodes = new Node[((numOfInputs + numOfOutputs)/2) + ((numOfInputs + numOfOutputs)/2);


        //creating final deicisions ray
        Decisions = new Node[outputNodes.size];
    }

    public void Initial_Network(int newInputs, int newOutputs)
    {
        numOfInputs = newInputs;
        numOfOutputs = Outputs;
    
        //creating rays
        this();

        //assigning nodes for ray objects
        assigningNodes(curLayer);


        //creating weights
            //creating hidden layer weights 
            creatingWeights(inputNodes.size(), hiddenNodes);
            //creating output layer weights
            creatingWeights(hiddenNodes.size(), outputNodes);

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
        activations[x] = inputNodes.get(x.getActivation);
        oldactivations[x] = activations[x];
    }
    
    for(int x = 0; x < inputNodes.size; x ++)
    {
        double curactivation = activations[x]; 
activations[x] =  hiddenNodes.get(x.activationFunc(oldactivations));
    }
    
    oldactivations = activations;

    for(int x = 0; x < hiddenNodes.size; x++)
    {
        double curavtivation = activations[x];
        activations[x] = outputNodes.get(x.activationFunc(oldactivations));
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
    public void training_Nodes()
    {
        
    }

}