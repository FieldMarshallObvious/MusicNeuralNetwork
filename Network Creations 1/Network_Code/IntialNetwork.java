import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    public IntialNetwork(int newInputs, int newOutputs, double inputLearningRate)
    {
        this();
        learningRate = inputLearningRate;

        //creating rays
        makingRays(newInputs, newOutputs);

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
        double[] activations = new double[hiddenNodes.length];
        double[] oldactivations = activations;

        for(int x = 0; x < inputNodes.length; x++)
        {
            activations[x] = sigmoidFunction(inputNodes[x].getActivation());
            System.out.println("The input activation for input node " + x + " is: " + activations[x]);
            oldactivations[x] = activations[x];
        }
    
        for(int x = 0; x < inputNodes.length; x ++)
        {
            double curactivation =  sigmoidFunction(hiddenNodes[x].activationFunc(oldactivations));
            System.out.println("The activation for hidden node " + x + " is: " + activations[x]); 
            activations[x] = curactivation;
        }
    
        oldactivations = activations;

        for(int x = 0; x < outputNodes.length; x++)
        {
            double curavtivation = sigmoidFunction(outputNodes[x].activationFunc(oldactivations));
            System.out.println("The activition for output node " + x + " is: " + activations[x]);
            activations[x] = curavtivation;
            System.out.println(outputNodes[x].getActivation());
        }

        return activations;
    }  

    
    public void writeOutputs() throws IOException
    {
        int x = 0;
        ArrayList<Double> preDecisions = new ArrayList<Double>();
        
        System.out.println("Out of the loop" + preDecisions.get(x));

        for(Node cur: outputNodes)
        {
            x++;
            double curOutput = this.sigmoidFunction(cur.getActivation());
            preDecisions.add(curOutput);
            System.out.println("In the for loop " + preDecisions.get(x));
        }
        double max = Collections.max(preDecisions);

        for(double cur: preDecisions)
        {
            if(cur >= max/2)
            {
                finaloutput+= cur;
                finaloutput+= "\n";
                x++;
            }
        }
        
        usingBufferedWritter();
    }

    public void training_Nodes(double[] ExpectedOutput, double learningRate, double Momentum)
    {
        //REALLY IN NEED OF FIXING
        double sigSum = this.CalculateSignalErrors(layers, outputNodes, ExpectedOutput);
        this.BackPropagateError(inputNodes,hiddenNodes, outputNodes, learningRate, Momentum);
        System.out.print(sigSum);
    }

    public void settingInputs(double[] inputs)
    {
        for(int x = 0; x < inputNodes.length; x++)
        {
            inputNodes[x].setActivation(inputs[x]);
        }
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
     
        BufferedWriter writer = new BufferedWriter(new FileWriter("<output file>"));
        writer.write(fileContent);
        writer.close();
    }
}