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

                    String[] testCaseRaw =  fileReader.nextLine().split(":");
                    int[] testCaseInt = new int[testCaseRaw.length];

                    for (int i = 0; i < testCaseRaw.length; i++) {
                        testCaseInt[i] = Integer.parseInt(testCaseRaw[i]);
                    }

                    caseList.add(testCaseInt);
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
                    System.out.println("(1-10)Select a test case\n(0)Exit");
                    int caseChoice = input.nextInt();
                    if (caseChoice > 0 && caseChoice < 11) {

                        //process user input to correctly align with ArrayList indexes
                        caseChoice--;

                        //declare 3 square matrices to use for the multiplicands and their product, as well as set variables for organizing the data in 2d arrays
                        int[] chosenCase = caseList.get(caseChoice);

                        printTestCase(Arrays.copyOfRange(chosenCase, 1, chosenCase.length), chosenCase[0]);
                    } else
                        keepTesting = false;
                }

            } else if (inpChoice ==2) {

            } else if (inpChoice == 3) {

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

    private static void printTimeResults(int[] array) {

    }

    private static String generateArray(int k) {
        return "\0";
    }

    private static int[] getArray(String input) {
        int temp[] = {0};
        return temp;
    }

    private static void testAlgos(int[] array, int k) {
        // initialize and define array of execution times
        double[] returnTimes = new double[3];

        System.out.printf("\nSelection times for %dth element in array of length %d.", k, array.length);
    }
}
