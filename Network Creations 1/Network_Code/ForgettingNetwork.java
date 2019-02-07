import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



public class ForgettingNet
{
    Node[] inputNodes;
    Node[] hiddenNodes;
    Node[] outputNodes;
    double[] decisions;

int numOfInputs = 0;
Int numOfOutputs = 0;    

String finaloutput = new String(" ");

public void ForgettingNet()
{
    //creating input Nodes ray
inputNodes = new Node[numOfInputs];
outputNodes = new Node[numOfOutputs];

//determining hidden Nodes based on num of outputs and inputs
hiddenNodesNodes = new Node[((numOfInputs + numOfOutputs)/2) + ((numOfInputs + numOfOutputs)/2)];


//creating final deicisions ray
decisions = new double[outputNodes.size];
}

public void ForgettingNet(int newInputs, int newOutputs)
{
//setting size of num of Nodes
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
        activations[x] = inputNodes.getActivation(x);
        oldactivations[x] = activations[x];
    }
    
    for(int x = 0; x < inputNodes.size; x ++)
    {
        activations[x] =  hiddenNodes.get(inputNodes[x].activationFunc(oldactivations));
    }
    
    oldactivations = activations;

    for(int x = 0; x < hiddenNodes.size; x++)
    {
        activations[x] = outputNodes.get(hiddenNodes[x].activationFunc(oldactivations));
    }

    return activations;
}

private static void usingBufferedWritter() throws IOException
{
    String fileContent = "<output character>";
     
    BufferedWriter writer = new BufferedWriter(new FileWriter("<output file>"));
    writer.write(fileContent);
    writer.close();
}

public void Decision()
{
for(int x = 0; x < outputNodes.size; x++)
{
    Node cur = outputNodes[x];
if(cur.getActivation <= 0.65)
decisions[x] = cur;
else
    decisions[x] = 0.0;
    
}
for(double cur : decisions)
{
    
}
}



public void Learn()
{

}

public void setInputs()
{
    
}



}
