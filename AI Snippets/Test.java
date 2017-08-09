
import java.io.File;
import java.util.*;

import java.io.File;
import java.awt.Desktop;

public class Test
{
    public static void example() throws Exception
    {
        NeuralNetwork nn = new NeuralNetwork(2, 2, 2, 0.5);
        nn.commentary = true;
        
        nn.A = new Matrix(2,2).setData(0.1, 0.3, 0.2, 0.4);
        nn.B = new Matrix(2,2).setData(0.3, 0.2, 0.4, 0.1);

        nn.train( new DataPoint().setInput(0.3, 0.4).setTarget(0.7, 0.35) );

        nn.query("",0.1,0.2);
    }


    public static void testMNIST100() throws Exception
    {
        testMNIST("data/trainData.csv");
    }
    
    public static void testMNIST(String filename) throws Exception
    {
        

        Scanner scan = new Scanner( new File(filename) );
        ArrayList<DataPoint> dataPointList = new ArrayList<DataPoint>();
        double lineNum = 0;
        while ( scan.hasNextLine() )
        {
            String line = scan.nextLine();
            String[] dataStrings = line.split(",");
            double[] dataNumbers = new double[ dataStrings.length ];
            for (int n = 0; n < dataStrings.length; n++)
            {
                String s = dataStrings[n];
                double d = Double.parseDouble(s);
                dataNumbers[n] = d;
            }

            double targetNumber = dataNumbers[0];

            double[] inputNumbers = Arrays.copyOfRange( dataNumbers, 1, dataNumbers.length );
            
            // convert categorical data into array of boolean (0/1) data
            double[] targetNumbers = new double[10];
            for (int n = 0; n < 10; n++)
            {
                if (n == targetNumber)
                    targetNumbers[n] = 1.0;
                else
                    targetNumbers[n] = 0.0;
            }

            System.out.println("Adding training example: " + lineNum);
            lineNum++;

            DataPoint point = new DataPoint();

            double[] transfInputNumbers = linearTransform( inputNumbers, 0,255, 0.01,0.99 );
            Matrix inputMatrix = new Matrix(inputNumbers.length, 1).setData(transfInputNumbers);
            point.setInput(inputMatrix);

            double[] transfTargetNumbers = linearTransform( targetNumbers, 0,1, 0.01,0.99 );
            Matrix targetMatrix = new Matrix(targetNumbers.length, 1).setData(transfTargetNumbers);
            point.setTarget(targetMatrix);

            dataPointList.add(point);            
        }

        System.out.println("Begin training!");
        NeuralNetwork nn = new NeuralNetwork(784, 400, 10, 0.5);
        nn.commentary = false;

        lineNum = 0;
        for (DataPoint dp : dataPointList)
        {
            System.out.println("Training example " + lineNum);
            nn.train(dp);
            lineNum++;
        }

        System.out.println("Training done!");

        System.out.println("Beginning testing!");
        filename = "data/testData.csv";
        scan = new Scanner( new File(filename) );
        dataPointList = new ArrayList<DataPoint>();
        lineNum = 0;

        while ( scan.hasNextLine() )
        {
            String line = scan.nextLine();
            String[] dataStrings = line.split(",");
            double[] dataNumbers = new double[ dataStrings.length ];
            for (int n = 0; n < dataStrings.length; n++)
            {
                String s = dataStrings[n];
                double d = Double.parseDouble(s);
                dataNumbers[n] = d;
            }

            double targetNumber = dataNumbers[0];

            double[] inputNumbers = Arrays.copyOfRange( dataNumbers, 1, dataNumbers.length );

            // convert categorical data into arrays, binary data
            double[] targetNumbers = new double[10];
            for (int n = 0; n < 10; n++)
            {
                if (n == targetNumber)
                    targetNumbers[n] = 1.0;
                else
                    targetNumbers[n] = 0.0;
            }

            System.out.println("Testing example #" + lineNum);
            lineNum++;

            double[] transfData = linearTransform( inputNumbers, 0,255, 0.01,0.99 );
            nn.query(dataStrings[0],transfData);

            Utils.pressEnterToContinue();
            Utils.clearTerminalWindow();
        }
        System.out.println("Finished testing!"); 

    }

    /**
     *  Transform double[] data
     */
    public static double[] linearTransform( double[] oldData, double xMin, double xMax, double yMin, double yMax )
    {
        double[] newData = new double[oldData.length];

        double slope = (yMax - yMin) / (xMax - xMin);
        double intercept = yMin - slope * xMin;

        for (int n = 0; n < oldData.length; n++)
        {
            newData[n] = slope * oldData[n] + intercept;                
        }

        return newData;
    }

}