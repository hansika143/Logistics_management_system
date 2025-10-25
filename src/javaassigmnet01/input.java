package javaassigmnet01;

import java.util.Scanner;

public class input {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public static int nextInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
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
