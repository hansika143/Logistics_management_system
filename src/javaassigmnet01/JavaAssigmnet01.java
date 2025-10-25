/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaassigmnet01;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hansika
 */
public class JavaAssigmnet01 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> cities = new ArrayList<>();
        int maxCities = 30;
        int choice;

        do {
            System.out.println("\n--- City Management Menu ---");
            System.out.println("1. Add a new city");
            System.out.println("2. Rename a city");
            System.out.println("3. Remove a city");
            System.out.println("4. View all cities");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    if (cities.size() >= maxCities) {
                        System.out.println("Cannot add more cities (limit reached).");
                    } else {
                        System.out.print("Enter city name: ");
                        String newCity = input.nextLine().trim();
                        if (cities.contains(newCity)) {
                            System.out.println("City already exists!");
                        } else {
                            cities.add(newCity);
                            System.out.println(newCity + " added successfully.");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter city name to rename: ");
                    String oldName = input.nextLine().trim();
                    if (cities.contains(oldName)) {
                        System.out.print("Enter new city name: ");
                        String newName = input.nextLine().trim();
                        if (cities.contains(newName)) {
                            System.out.println("City with this name already exists!");
                        } else {
                            cities.set(cities.indexOf(oldName), newName);
                            System.out.println("City renamed successfully.");
                        }
                    } else {
                        System.out.println("City not found!");
                    }
                    break;

                case 3:
                    System.out.print("Enter city name to remove: ");
                    String removeCity = input.nextLine().trim();
                    if (cities.remove(removeCity)) {
                        System.out.println(removeCity + " removed successfully.");
                    } else {
                        System.out.println("City not found!");
                    }
                    break;

                case 4:
                    System.out.println("\nList of cities:");
                    if (cities.isEmpty()) {
                        System.out.println("No cities added yet.");
                    } else {
                        for (String city : cities) {
                            System.out.println("- " + city);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);

    }
        final int MAX_CITIES = 5;
        String[] cities = {"Colombo", "Kandy", "Galle", "Jaffna", "Matara"};
        int[][] distance = new int[MAX_CITIES][MAX_CITIES];

        {    
        for (int i = 0; i < MAX_CITIES; i++) {
            for (int j = 0; j < MAX_CITIES; j++) {
                if (i == j)
                    distance[i][j] = 0;
            }
        }
    
        int choice;
        do {
            System.out.println("\n--- Distance Management Menu ---");
            System.out.println("1. Input or edit distance");
            System.out.println("2. Display distance table");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Show city list
                    System.out.println("\nAvailable Cities:");
                {
                    int MAX_CITIES = 0;
                    for (int i = 0; i < MAX_CITIES; i++) {
                        String[] cities = null;
                        System.out.println(i + " - " + cities[i]);
                    }
                }

                    // Input two city indexes
                    System.out.print("Enter first city number: ");
                    int c1 = input.nextInt();
                    System.out.print("Enter second city number: ");
                    int c2 = input.nextInt();

                    if (c1 == c2) {
                        System.out.println("Distance from a city to itself must be 0!");
                    } else if (c1 >= 0 && c1 < MAX_CITIES && c2 >= 0 && c2 < MAX_CITIES) {
                        System.out.print("Enter distance between " + cities[c1] + " and " + cities[c2] + ": ");
                        int d = input.nextInt();
                        distance[c1][c2] = d;
                        distance[c2][c1] = d; 
                        System.out.println("Distance updated successfully!");
                    } else {
                        System.out.println("Invalid city number!");
                    }
                    break;


                case 2:
                    // Display table
                    System.out.println("\nDistance Table (in km):");
                    System.out.print("\t");
                    for (int i = 0; i < MAX_CITIES; i++) {
                        System.out.print(cities[i] + "\t");
                    }
                    System.out.println();

                    for (int i = 0; i < MAX_CITIES; i++) {
                        System.out.print(cities[i] + "\t");
                        for (int j = 0; j < MAX_CITIES; j++) {
                            System.out.print(distance[i][j] + "\t");
                        }
                        System.out.println();
                    }
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
    
            }
        
        