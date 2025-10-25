package javaassigmnet01;

import java.util.Scanner;

public class JavaAssigmnet01 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CityManager cityManager = new CityManager();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Logistics Management System ===");
            System.out.println("1. City Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageCities();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private static void manageCities() {
        int choice;
        do {
            System.out.println("\n--- City Management Menu ---");
            System.out.println("1. Add a new city");
            System.out.println("2. Rename a city");
            System.out.println("3. Remove a city");
            System.out.println("4. View all cities");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (cityManager.getCityCount() >= cityManager.getMaxCities()) {
                        System.out.println("Cannot add more cities (limit reached).");
                    } else {
                        System.out.print("Enter city name: ");
                        String newCity = scanner.nextLine().trim();
                        if (cityManager.addCity(newCity)) {
                            System.out.println(newCity + " added successfully.");
                        } else {
                            System.out.println("City already exists!");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter city name to rename: ");
                    String oldName = scanner.nextLine().trim();
                    System.out.print("Enter new city name: ");
                    String newName = scanner.nextLine().trim();
                    if (cityManager.renameCity(oldName, newName)) {
                        System.out.println("City renamed successfully.");
                    } else {
                        System.out.println("City not found or new name already exists!");
                    }
                    break;

                case 3:
                    System.out.print("Enter city name to remove: ");
                    String removeCity = scanner.nextLine().trim();
                    if (cityManager.removeCity(removeCity)) {
                        System.out.println(removeCity + " removed successfully.");
                    } else {
                        System.out.println("City not found!");
                    }
                    break;

                case 4:
                    System.out.println("\nList of cities:");
                    var cities = cityManager.getAllCities();
                    if (cities.isEmpty()) {
                        System.out.println("No cities added yet.");
                    } else {
                        for (int i = 0; i < cities.size(); i++) {
                            System.out.println((i + 1) + ". " + cities.get(i));
                        }
                    }
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }
}
