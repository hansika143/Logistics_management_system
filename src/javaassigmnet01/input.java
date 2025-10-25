package javaassigmnet01;

import java.util.Scanner;

/**
 * Input utility class to handle user input operations.
 * This class provides methods to:
 * - Get string input from user
 * - Get integer input from user
 * - Get double input from user
 * - Validate user input
 * 
 * @author Hansika
 */
public class input {
    // Scanner object to read user input
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Get a string input from the user
     * @param prompt The message to display to the user
     * @return The string entered by the user, with whitespace trimmed
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Get an integer input from the user
     * Keeps asking until a valid integer is entered
     * @return The integer entered by the user
     */
    public static int nextInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline character
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    public static double nextDouble() {
        while (true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    public static int getIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            int value = nextInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("Please enter a number between %d and %d.\n", min, max);
        }
    }
}
