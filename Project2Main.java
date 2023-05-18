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
            System.out.print("\n(1) Select test cases to display\n(2) Generate 2^k length random integer arrays"
            + "\n(3) Test Algorithm Speeds\n(0) Exit");
            int inpChoice = input.nextInt();

            if (inpChoice == 1) {

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

    }

    private static void printTestCase(int[] array, int k) {

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

    private static double[] testAlgos(int[] array, int k) {
        // initialize and define array of execution times
        double[] returnTimes = new double[3];

        System.out.printf("\nSelection times for %dth element in array of length %d.", k, array.length);
    }
}
