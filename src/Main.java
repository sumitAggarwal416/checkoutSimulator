import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Sumit Aggarwal 000793607; code not copied; not let anyone else copy
 */

public class Main {

    public static void main(String[] args) {
        // write your code here

        int x = 0;
        int xLanes = 0;
        int nLanes = 0;

        int[] express; //stores time taken by each customer in express lane
        int[] normal; // stores time taken by each customer in normal lane

        LinkedQueue<Customer>[] expressLine;
        LinkedQueue<Customer>[] normalLine;

        final String filename = "src/CustomerData_Example.txt";
        final String file = "src/CustomerData.txt";

        // ============= Part A ==============

        List results = checkout(filename);
        normal = (int[]) results.get(2);
        express = (int[]) results.get(0);

        expressLine = (LinkedQueue<Customer>[]) results.get(1);
        normalLine = (LinkedQueue<Customer>[]) results.get(3);

        xLanes = ((int[]) results.get(4))[0];
        nLanes = ((int[]) results.get(4))[1];

        int maxTimeLane = normal[0];
        int totalCheckoutLanes = 1; //counts amount of total checkout lines for display

        //==== EXPRESS CHECKOUT LINES ====//
        for (int i = 0; i < xLanes; i++) {

            System.out.println("CheckOut(Express) # " + (totalCheckoutLanes) + " (Est Time = " + express[i] + " s) =" + expressLine[i]);
            totalCheckoutLanes++;

            if (express[i] > maxTimeLane) {
                maxTimeLane = express[i];
            }
        }

        //==== NORMAL CHECKOUT LINES ====//
        for (int i = 0; i < nLanes; i++) {
            System.out.println("CheckOut (Normal) # " + (totalCheckoutLanes) + " (Est Time = " + normal[i] + " s) =" + normalLine[i]);
            totalCheckoutLanes++;
            if (normal[i] > maxTimeLane) {
                maxTimeLane = normal[i];
            }
        }

        System.out.printf("Time to serve all customers = %d s\n\n", maxTimeLane);


        try {


            // ============= Part B ==============

            Scanner fin = new Scanner(new File(file));

            results = checkout(file);

            express = new int[xLanes];
            normal = new int[nLanes];

            normal = (int[]) results.get(2);
            express = (int[]) results.get(0);

            expressLine = (LinkedQueue<Customer>[]) results.get(1);
            normalLine = (LinkedQueue<Customer>[]) results.get(3);

            xLanes = ((int[]) results.get(4))[0];
            nLanes = ((int[]) results.get(4))[1];
            maxTimeLane = normal[0];

            for(int i = 0; i < xLanes; i++){
                if(express[i] > maxTimeLane)
                    maxTimeLane = express[i];
            }
            for(int i = 0; i < nLanes; i++)
                if(normal[i] > maxTimeLane)
                    maxTimeLane = normal[i];

            int SIMULATION_STEP = 30;

            System.out.printf("t(s)");
            for (int i = 0; i < xLanes + nLanes; i++) {
                System.out.printf("    Line " + (i + 1));
            }
            System.out.println("");

            for (int i = 0; i < expressLine.length; i++)
                express[i] = expressLine[i].peek().calculateTime();


            for (int i = 0; i < normalLine.length; i++)
                normal[i] = normalLine[i].peek().calculateTime();

            for (int i = 0; i <= maxTimeLane; i++) {
                for (int j = 0; j < express.length; j++) {
                    if (express[j] == 0) {
                        if (!expressLine[j].isEmpty())
                            expressLine[j].dequeue();
                        if (!expressLine[j].isEmpty())
                            express[j] = ((expressLine[j]).peek()).calculateTime();
                    }
                }

                for (int j = 0; j < normal.length; j++) {
                    if (normal[j] == 0) {
                        if (!normalLine[j].isEmpty())
                            normalLine[j].dequeue();
                        if (!normalLine[j].isEmpty())
                            normal[j] = ((normalLine[j]).peek()).calculateTime();
                    }
                }

                if ((i % SIMULATION_STEP == 0 && i > 0) || i == maxTimeLane) {
                    System.out.printf("%3d", i);
                    for (int j = 0; j < express.length; j++) {
                        System.out.printf("%10d", expressLine[j].size());
                    }
                    for (int j = 0; j < normal.length; j++)
                        System.out.printf("%10d", normalLine[j].size());
                    System.out.println("");
                    System.out.println("========================================= JUST FOLLOWING SOCIAL DISTANCING RULES ==========================================");
                }

                for (int j = 0; j < express.length; j++) {
                    if (express[j] > 0)
                        express[j]--;
                }
                for (int j = 0; j < normal.length; j++)
                    if (normal[j] > 0)
                        normal[j]--;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error loading file. " + ex.getMessage());
        }

    }

    public static List<Object> checkout(String fileName) {

        int x = 0;
        int xLanes = 0;
        int nLanes = 0;

        int[] express = null; //stores time taken by each customer in express lane
        int[] normal = null; // stores time taken by each customer in normal lane

        LinkedQueue<Customer>[] expressLine = null;
        LinkedQueue<Customer>[] normalLine = null;

        try {
            Scanner fin = new Scanner(new File(fileName));

            xLanes = fin.nextInt();
            nLanes = fin.nextInt();

            express = new int[xLanes];
            normal = new int[nLanes];

            x = fin.nextInt();

            int numberOfCustomers = fin.nextInt();

            expressLine = new LinkedQueue[xLanes];
            normalLine = new LinkedQueue[nLanes];

            //initializing the arrays to avoid getting the NullPointerException
            for (int i = 0; i < xLanes; i++) {
                expressLine[i] = new LinkedQueue<Customer>();
                express[i] = 0;
            }

            for (int i = 0; i < nLanes; i++) {
                normalLine[i] = new LinkedQueue<Customer>();
                normal[i] = 0;

            }

            while (fin.hasNext()) {

                Customer c = new Customer(fin.nextInt());

                int fastestInExpress = -1;
                int positionInExpress = -1; // the express lane number (for example, Express Lane #3)

                if (expressLine != null && (c.getNi() <= x)) {

                    fastestInExpress = express[0];
                    positionInExpress = 0;

                    //update the fastest express lane
                    for (int i = 1; i < expressLine.length; i++) {
                        if (express[i] < fastestInExpress) {
                            fastestInExpress = express[i];
                            positionInExpress = i;
                        }
                    }
                }

                int fastestInNormal = normal[0];
                int positionInNormal = 0; // the normal lane number (for example, Normal Lane #2)

                //update the fastest normal lane
                for (int i = 1; i < normalLine.length; i++) {
                    if (normal[i] < fastestInNormal) {
                        fastestInNormal = normal[i];
                        positionInNormal = i;
                    }
                }

                if (positionInExpress > -1 && c.getNi() <= x) {
                    if (fastestInNormal >= fastestInExpress) {
                        //choose express lane if time taken in the normal lane is greater than or equal to time taken in
                        //express lane
                        expressLine[positionInExpress].enqueue(c);
                        express[positionInExpress] = express[positionInExpress] + c.calculateTime();
                    } else {
                        normalLine[positionInNormal].enqueue(c);
                        normal[positionInNormal] = normal[positionInNormal] + c.calculateTime();
                    }
                } else {
                    normalLine[positionInNormal].enqueue(c);
                    normal[positionInNormal] = normal[positionInNormal] + c.calculateTime();
                }
            }
        } catch (FileNotFoundException EX) {
            System.out.println("Error Loading File. " + EX.getMessage());
        }

        return Arrays.asList(express, expressLine, normal, normalLine, new int[]{xLanes, nLanes});
                //(The Arrays.asList() was found on the internet and thus I had to use it because I could not think of
                //anything else. I tried to make it generic by using a Comparable[], but it seemed a bit too messy to me
                // so I went with it.
    }
}
