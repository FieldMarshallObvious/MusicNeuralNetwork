import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class SelectionNetwork implements DNA
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

    public SelectionNetwork()
    {
        numOfInputs = 0;
        numOfOutputs = 0;
        learningRate = 0.0;    

        finaloutput = new String(" ");

        makingRays(0, 0);
    }

    public SelectionNetwork(int newInputs, int newOutputs, double inputLearningRate)
    {
        this();
        learningRate = inputLearningRate;

        //creating rays
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

    public void settingInputs(double[] inputs)
    {
        for(int x = 0; x < inputNodes.length; x++)
        {
            inputNodes[x].setActivation(inputs[x]);
        }
    }

    public double[] calcOutput()
    {
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

    public void writeOutputs() throws IOException
    {
        Double max = 0.0;

        ArrayList<Double> netDecisions = this.selectDecisions(2, this.calcOutput(), outputNodes.length);

        max = Collections.max(netDecisions);
        for(int x = 0; x < netDecisions.size(); x++)
        {
            if(! netDecisions.get(x).equals(max))
                netDecisions.set(x, 0.0);
        }
        finaloutput = netDecisions.stream().map(Object::toString).collect(Collectors.joining("\n"));

        usingBufferedWritter();
    }
    public void training_Nodes(double[] ExpectedOutput, double learningRate, double Momentum)
    {
        //REALLY IN NEED OF FIXING
        double sigSum = this.CalculateSignalErrors(layers, outputNodes, ExpectedOutput);
        this.BackPropagateError(inputNodes,hiddenNodes, outputNodes, learningRate, Momentum);
        System.out.print(sigSum);
    }

    private void makingRays(int newnewInputs, int newOutputs)
    {
        //creating input Nodes ray
        inputNodes = new Node[newnewInputs];
        outputNodes = new Node[newOutputs];

        //determining hidden Nodes based on num of outputs and inputs
        hiddenNodes = new Node[((newnewInputs + newOutputs)/2) + ((newnewInputs + newOutputs)/2)];


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
        writer.write(finaloutput);
        writer.close();
    }
}