// Dylan Michalak
// CS 3310-03
// Project 2: Matrix Multiplication
// Due 5/18/2023

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Project2Main {
    public static void main(String[] args) throws IOException {
        boolean keepRunning = true;

        Scanner input = new Scanner(System.in);

        // MAIN MENU LOOP
        while(keepRunning) {
            System.out.print("(1) Select test cases to display\n(2) Generate 2^k length random integer arrays"
            + "\n(3) Test Algorithm Speeds\n(0) Exit\n");
            int inpChoice = input.nextInt();

            if (inpChoice == 1) {
                //define a List to store all the entries in testCases.txt
                boolean keepTesting = true;
                List<int[]> caseList = new ArrayList<>();

                /*
                 * read testCases.txt file within this program's directory, store entires as arrays 
                 * of integers in the arrayList, caseList
                 * CATCH: a fileNotFoundException, if testCases.txt does not exist
                */
                Scanner fileReader = new Scanner(new File("./testCases.txt"));

                /*
                * TAKE TEST CASE FILE AND PARSE EACH CASE INTO AN ArrayList ENTRY
                */
                while(fileReader.hasNext()) {
                    caseList.add(getArray(fileReader.nextLine()));
                }
                fileReader.close();


                /*
                * USER MENU FOR VIEWING INDIVIDUAL TEST CASES
                */
                while(keepTesting) {

                    /*
                     * Ask the user what test case they want to display
                     * input: the test case #
                     */
                    System.out.print("\n(1-10)Select a test case\n(0)Exit");
                    int caseChoice = input.nextInt();
                    if (caseChoice > 0 && caseChoice < 11) {

                        //process user input to correctly align with ArrayList indexes
                        caseChoice--;

                        //runs each algorithm with the chosen test case
                        int[] chosenCase = caseList.get(caseChoice);

                        printTestCase(Arrays.copyOfRange(chosenCase, 1, chosenCase.length), chosenCase[0]);
                    } else
                        keepTesting = false;
                }

            } else if (inpChoice ==2) {

                boolean keepGenerating = true;

                /*
                * create BufferedWriter, to write new arrays and index values to arrays.txt
                * OVERWRITES list of problems previously stored in arrays.txt
                */ 
                BufferedWriter output = new BufferedWriter(new FileWriter("./arrays.txt"));

                while(keepGenerating) {

                    System.out.print("\nEnter \"0\" to exit, or enter an integer value \"k\" for a 2^k length integer array\n" + 
                    "to be generated and stored, containing random integer values, along with a selection index: ");
                    int generateChoice = input.nextInt();

                    if (generateChoice == 0)
                        keepGenerating = false;

                    /*
                     * Code block for randomly generating, and saving an integer array along with an index value on a new
                     * line in arrays.txt
                     */
                    else if (generateChoice >= 1) {

                        String newArray = generateArray(generateChoice);
                        output.append(newArray);
                        output.newLine();

                        System.out.print("\nDone generating a " + Math.pow(2, generateChoice) + " length integer array.");

                    } else 
                        System.out.println("Error: Invalid input.");
                }

                output.close();

            } else if (inpChoice == 3) {

                // more efficient way of reading large chunks of data than scanner
                try (BufferedReader br = Files.newBufferedReader(Paths.get("arrays.txt"), StandardCharsets.UTF_8)) {
                    
                    // for loop to repeat the timing & averaging process for every entry
                    for (String line = null; (line = br.readLine()) != null;) {

                        int[] inputInt = getArray(line);

                        // allocates 10x3 array for the execution times
                        double[][] timeTable = new double[10][3];

                        // runs each algorithm 10 times and store execution time in a different column of the timeTable 2d array
                        for (int i = 0; i < 10; i++) {
                            timeTable[i] = testAlgos(Arrays.copyOfRange(inputInt, 1, inputInt.length), inputInt[0]);
                        }

                        // display average execution time for classical multiplication
                        System.out.printf("\nResults for a %d length array:\n", inputInt.length-1);
                        System.out.print("Merge Sort Selection: ");
                        
                        double average = 0;
                        double[] mergeTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            mergeTimes[i] = timeTable[i][0];
                        }

                        Arrays.sort(mergeTimes);
                        mergeTimes[0] = 0;
                        mergeTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += mergeTimes[i];
                        }

                        average = average / 8;

                        System.out.printf("%.9f seconds", average);

                        // display average execution time for classical multiplication
                        System.out.print("\nQuick Selection: ");
                        
                        average = 0;
                        double[] quickTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            quickTimes[i] = timeTable[i][1];
                        }

                        Arrays.sort(quickTimes);
                        quickTimes[0] = 0;
                        quickTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += quickTimes[i];
                        }

                        average = average / 8;

                        System.out.printf("%.9f seconds", average);

                        // display average execution time for classical multiplication
                        System.out.print("\nQuick Selection w/ MoM pivot: ");
                        
                        average = 0;
                        double[] medTimes = new double[10];

                        for (int i = 0; i < 10; i++) {
                            medTimes[i] = timeTable[i][2];
                        }

                        Arrays.sort(medTimes);
                        medTimes[0] = 0;
                        medTimes[9] = 9;

                        for (int i = 1; i < 9; i++) {
                            average += medTimes[i];
                        }

                        average = average / 8;

                        System.out.printf("%.9f seconds", average);
                    }
                }

            } else if (inpChoice == 0) {
                keepRunning = false;
            } else 
                System.out.println("Error: Invalid input.");
            
        }

        input.close();
    }

    private static void printArray(int[] array) {
        // loop through the array, printing each element between brackets
        System.out.print("[ ");
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.print("]");
    }

    private static void printTestCase(int[] array, int k) {
        System.out.printf("Element %d of the following array, once sorted : \n", k);
        printArray(array);

        int a, b, c;
        a = SelectionAlgorithms.mergeSortSelect(array, k);
        b = SelectionAlgorithms.quickSelect(array, k);
        c = SelectionAlgorithms.quickSelectMM(array, k);
        
        System.out.printf("\nMerge Sort: %d\nQuick Select: %d\nMedian of Medians: %d\n", a, b, c);
    }


    private static String generateArray(int k) {
        int length = (int)Math.pow(2, k);
        int[] newArray = new int[length];
        Random rand = new Random();

        for (int i = 1; i < length; i++) {
            newArray[i] = rand.nextInt(-99, 99);
        }

        int index = rand.nextInt(1, length);


        StringBuilder arrayValues = new StringBuilder();
        arrayValues.append(index);
        for (int i = 0; i < length; i++) {
            arrayValues.append(":");
            arrayValues.append(newArray[i]);
        }
        
        return arrayValues.toString();
    }

    private static int[] getArray(String input) {
        String[] arrayRaw =  input.split(":");
        int[] intArray = new int[arrayRaw.length];

            for (int i = 0; i < arrayRaw.length; i++) {
                intArray[i] = Integer.parseInt(arrayRaw[i]);
            }
        return intArray;
    }

    private static double[] testAlgos(int[] array, int k) {
        // allocate ram for array of times to return & result of multiplication
        double[] returnTimes = new double[3];


        final double mergeStartTime = System.nanoTime();
        SelectionAlgorithms.mergeSortSelect(array, k);
        final double mergeEndTime = System.nanoTime();
        returnTimes[0] = (mergeEndTime - mergeStartTime) / 1000000000;


        final double quickStartTime = System.nanoTime();
        SelectionAlgorithms.quickSelect(array, k);
        final double quickEndTime = System.nanoTime();
        returnTimes[1] = (quickEndTime - quickStartTime) / 1000000000;


        final double medStartTime = System.nanoTime();
        SelectionAlgorithms.quickSelectMM(array, k);
        final double medEndTime = System.nanoTime();
        returnTimes[2] = (medEndTime - medStartTime) / 1000000000;


        return returnTimes;
    }
}
