import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.source.tree.BinaryTree;

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
            oldactivations[x] = activations[x];
        }
    
        for(int x = 0; x < inputNodes.length; x ++)
        {
            double curactivation =  sigmoidFunction(hiddenNodes[x].activationFunc(oldactivations));
            activations[x] = curactivation;
        }
    
        oldactivations = activations;

        for(int x = 0; x < outputNodes.length; x++)
        {
            double curavtivation = sigmoidFunction(outputNodes[x].activationFunc(oldactivations));
            activations[x] = curavtivation;
        }

        return activations;
    }  

    
    public void writeOutputs() throws IOException
    {
        System.out.println("outputting decisions");
        int x = 0;
        ArrayList<Double> preDecisions = new ArrayList<Double>();
        ArrayList<double[]> organizedDecisions = new ArrayList<double[]>();
        
        //Getting outputs for nodes
        for(Node cur: outputNodes)
        {
            x++;
            double curOutput = this.sigmoidFunction(cur.getActivation());
            preDecisions.add(curOutput);
        }

        x = 0;

        
        //Giving organized list size
        for(int y = 0; y < preDecisions.size(); y++)
        {
            double[] nullDecision = {-1, -1};
            organizedDecisions.add(nullDecision);
        }
        

        //Sorting node outputs from lowest to highest
        for(double cur: preDecisions)
        {
            x++;
            double[] currentDecision = new double[2];
            currentDecision[0] = Double.valueOf(x);
            currentDecision[1] = cur;

            for(int y = 0; y < organizedDecisions.size(); y++)
            {
              
                if(cur < organizedDecisions.get(y)[1] || y == organizedDecisions.size() - 1)
                {
                    System.out.println("Added a Node");

                    double[] reciever = organizedDecisions.get(y);
                    organizedDecisions.set(y, currentDecision);
                    organizedDecisions.set(y-1, reciever);

                    break;
                }

            }
        }

        for(double[] cur : organizedDecisions)
        {
            System.out.println("The current in the organized list is: " + cur[0]);
            if(cur[0] == 2 || cur[0] == 3 )
                System.out.println("Found key: " + cur[0]);
        }

        //Sets lower half to outputs to zero
        for(int y = 0; y < (organizedDecisions.size())/2; y++)
        {
            System.out.println("The curret key is: " + organizedDecisions.get(y)[0] + "\n" + "The current value is: " + organizedDecisions.get(y)[1]);
            preDecisions.set(((int)organizedDecisions.get(y)[0]) - 1, 0.0);
        }
        
        //Converts output to String, to allow it to outputed by buffer
        finaloutput = preDecisions.stream().map(Object::toString).collect(Collectors.joining("\n"));
        System.out.println("Final output of intital network \n" + finaloutput);

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
     
        BufferedWriter writer = new BufferedWriter(new FileWriter("outputfile.dat"));
        writer.write(fileContent);
        writer.close();
    }
}