package javaassigmnet01;

import java.util.Scanner;

/**
 * Main class for the Logistics Management System
 * Created by: Hansika
 * Date: October 25, 2025
 * 
 * This is my implementation of the logistics management system.
 * The system helps manage deliveries between cities using different types of vehicles.
 */
public class JavaAssigmnet01 {
    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);
    
    // Create managers for different parts of the system
    private static final CityManager cityManager = new CityManager();
    private static final DistanceManager distanceManager = new DistanceManager(cityManager);
    private static final VehicleManager vehicleManager = new VehicleManager();
    private static final DeliveryManager deliveryManager = new DeliveryManager(cityManager, vehicleManager, distanceManager);
    private static final PerformanceReports performanceReports = new PerformanceReports(deliveryManager, distanceManager);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Logistics Management System ===");
            System.out.println("1. City Management");
            System.out.println("2. Distance Management");
            System.out.println("3. Vehicle Management");
            System.out.println("4. Delivery Management");
            System.out.println("5. Route Optimization");
            System.out.println("6. Performance Reports");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageCities();
                    break;
                case 2:
                    manageDistances();
                    break;
                case 3:
                    manageVehicles();
                    break;
                case 4:
                    manageDeliveries();
                    break;
                case 5:
                    optimizeRoute();
                    break;
                case 6:
                    generatePerformanceReport();
                    break;
                case 0:
                    System.out.println("Saving data...");
                    saveAllData();
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

    private static void manageDistances() {
        int choice;
        do {
            System.out.println("\n--- Distance Management Menu ---");
            System.out.println("1. Set distance between cities");
            System.out.println("2. View distance table");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable cities:");
                    var cities = cityManager.getAllCities();
                    if (cities.isEmpty()) {
                        System.out.println("No cities available. Please add cities first.");
                        break;
                    }
                    
                    for (int i = 0; i < cities.size(); i++) {
                        System.out.println((i + 1) + ". " + cities.get(i));
                    }

                    System.out.print("\nEnter first city name: ");
                    String city1 = scanner.nextLine().trim();
                    System.out.print("Enter second city name: ");
                    String city2 = scanner.nextLine().trim();
                    
                    if (city1.equalsIgnoreCase(city2)) {
                        System.out.println("Cannot set distance from a city to itself!");
                        break;
                    }

                    System.out.print("Enter distance (km): ");
                    int distance = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (distance < 0) {
                        System.out.println("Distance cannot be negative!");
                        break;
                    }

                    if (distanceManager.setDistance(city1, city2, distance)) {
                        System.out.println("Distance updated successfully!");
                    } else {
                        System.out.println("Invalid city names or distance!");
                    }
                    break;

                case 2:
                    distanceManager.displayDistanceTable();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }

    private static void manageVehicles() {
        int choice;
        do {
            System.out.println("\n--- Vehicle Management Menu ---");
            System.out.println("1. View available vehicles");
            System.out.println("2. Check vehicle capacity");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    vehicleManager.displayVehicles();
                    break;

                case 2:
                    vehicleManager.displayVehicles();
                    System.out.print("\nEnter vehicle number (1-" + vehicleManager.getVehicleCount() + "): ");
                    int vehicleIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline

                    if (vehicleIndex < 0 || vehicleIndex >= vehicleManager.getVehicleCount()) {
                        System.out.println("Invalid vehicle number!");
                        break;
                    }

                    System.out.print("Enter cargo weight (kg): ");
                    int weight = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (weight <= 0) {
                        System.out.println("Weight must be positive!");
                        break;
                    }

                    String vehicleType = vehicleManager.getVehicleType(vehicleIndex);
                    if (vehicleManager.canHandleWeight(vehicleIndex, weight)) {
                        System.out.println(vehicleType + " can handle " + weight + " kg.");
                    } else {
                        System.out.println(vehicleType + " cannot handle " + weight + " kg.");
                        System.out.println("Maximum capacity: " + vehicleManager.getCapacity(vehicleIndex) + " kg");
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

    private static void manageDeliveries() {
        int choice;
        do {
            System.out.println("\n--- Delivery Management Menu ---");
            System.out.println("1. Create new delivery request");
            System.out.println("2. View all deliveries");
            System.out.println("3. Update delivery status");
            System.out.println("4. View delivery statistics");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Check delivery capacity
                    if (deliveryManager.getRemainingCapacity() <= 0) {
                        System.out.println("Cannot add more deliveries. Maximum limit reached!");
                        break;
                    }

                    // Show available cities
                    System.out.println("\nAvailable cities:");
                    var cities = cityManager.getAllCities();
                    if (cities.isEmpty()) {
                        System.out.println("No cities available. Please add cities first.");
                        break;
                    }
                    for (int i = 0; i < cities.size(); i++) {
                        System.out.println((i + 1) + ". " + cities.get(i));
                    }

                    // Get source and destination
                    System.out.print("\nEnter source city name: ");
                    String sourceCity = scanner.nextLine().trim();
                    System.out.print("Enter destination city name: ");
                    String destCity = scanner.nextLine().trim();

                    if (sourceCity.equals(destCity)) {
                        System.out.println("Source and destination cannot be the same!");
                        break;
                    }

                    // Show available vehicles
                    vehicleManager.displayVehicles();

                    // Get vehicle and weight
                    int vehicleType;
                    int weight;
                    
                    try {
                        System.out.print("\nSelect vehicle (1-" + vehicleManager.getVehicleCount() + "): ");
                        vehicleType = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        
                        if (vehicleType < 0 || vehicleType >= vehicleManager.getVehicleCount()) {
                            System.out.println("Invalid vehicle number!");
                            continue;
                        }

                        System.out.print("Enter cargo weight (kg): ");
                        weight = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        if (weight <= 0) {
                            System.out.println("Weight must be positive!");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Please enter valid numbers.");
                        scanner.nextLine(); // Clear the invalid input
                        continue;
                    }

                    // Try to add the delivery request
                    if (deliveryManager.addDeliveryRequest(sourceCity, destCity, weight, vehicleType)) {
                        System.out.println("Delivery request added successfully!");
                    } else {
                        System.out.println("Failed to add delivery request. Please check your inputs.");
                    }
                    break;

                case 2:
                    deliveryManager.displayDeliveries();
                    break;

                case 3:
                    deliveryManager.displayDeliveries();
                    if (deliveryManager.getDeliveryCount() > 0) {
                        System.out.print("\nEnter delivery ID to update: ");
                        int id;
                        try {
                            id = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.nextLine(); // Clear the invalid input
                            continue;
                        }
                        
                        System.out.println("\nChoose new status:");
                        System.out.println("1. Pending");
                        System.out.println("2. In Progress");
                        System.out.println("3. Completed");
                        System.out.print("Enter choice: ");
                        int statusChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        String newStatus;
                        switch(statusChoice) {
                            case 1:
                                newStatus = "Pending";
                                break;
                            case 2:
                                newStatus = "In Progress";
                                break;
                            case 3:
                                newStatus = "Completed";
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                continue;
                        }
                        
                        if (deliveryManager.updateDeliveryStatus(id, newStatus)) {
                            System.out.println("Delivery status updated!");
                        } else {
                            System.out.println("Failed to update delivery status.");
                        }
                    }
                    break;

                case 4:
                    deliveryManager.displayDeliveryStats();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }
    
    private static void optimizeRoute() {
        System.out.println("\n--- Route Optimization ---");
        
        // Show available cities
        System.out.println("\nAvailable cities:");
        var cities = cityManager.getAllCities();
        if (cities.isEmpty()) {
            System.out.println("No cities available. Please add cities first.");
            return;
        }
        
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i));
        }
        
        // Get source and destination cities
        System.out.print("\nEnter source city name: ");
        String sourceCity = scanner.nextLine().trim();
        System.out.print("Enter destination city name: ");
        String destCity = scanner.nextLine().trim();
        
        if (sourceCity.equals(destCity)) {
            System.out.println("Source and destination cannot be the same!");
            return;
        }
        
        // Validate source city
        if (cityManager.getCityIndex(sourceCity) == -1) {
            System.out.println("Source city '" + sourceCity + "' not found!");
            return;
        }
        
        // Validate destination city
        if (cityManager.getCityIndex(destCity) == -1) {
            System.out.println("Destination city '" + destCity + "' not found!");
            return;
        }
        
        // Find the best route
        RouteOptimizer optimizer = new RouteOptimizer(cityManager, distanceManager);
        Route bestRoute = optimizer.findBestRoute(sourceCity, destCity);
        
        if (bestRoute != null) {
            System.out.println("\nBest route found:");
            System.out.println(bestRoute);
            System.out.println("Total distance: " + bestRoute.totalDistance + " km");
            
            // Show delivery cost estimates for this route
            System.out.println("\nDelivery cost estimates for this route:");
            System.out.println("----------------------------------------");
            
            // Get cargo weight for cost estimation
            System.out.print("Enter cargo weight for cost estimation (kg): ");
            int estimationWeight;
            try {
                estimationWeight = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (estimationWeight <= 0) {
                    System.out.println("Weight must be positive! Using default weight of 1000kg.");
                    estimationWeight = 1000;
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Using default weight of 1000kg.");
                scanner.nextLine(); // Clear the invalid input
                estimationWeight = 1000;
            }
            
            // Show estimates for each vehicle type
            for (int i = 0; i < vehicleManager.getVehicleCount(); i++) {
                String vehicleType = vehicleManager.getVehicleType(i);
                double ratePerKm = vehicleManager.getRatePerKm(i);
                double avgSpeed = vehicleManager.getAvgSpeed(i);
                double fuelEfficiency = vehicleManager.getFuelEfficiency(i);
                
                // Skip vehicles that can't handle the weight
                if (!vehicleManager.canHandleWeight(i, estimationWeight)) {
                    System.out.println("\n" + vehicleType + " cannot handle " + estimationWeight + "kg");
                    continue;
                }
                
                DeliveryCostEstimate estimate = new DeliveryCostEstimate(
                    sourceCity,
                    destCity,
                    bestRoute.totalDistance,
                    vehicleType,
                    estimationWeight,
                    ratePerKm,
                    avgSpeed,
                    fuelEfficiency
                );
                System.out.println("\nFor " + vehicleType + ":");
                estimate.displayEstimate();
            }
            System.out.println("----------------------------------------");
        } else {
            System.out.println("No valid route found between " + sourceCity + 
                    " and " + destCity);
        }
    }
    
    /**
     * Generate and display performance reports
     */
    private static void generatePerformanceReport() {
        if (deliveryManager.getDeliveryCount() == 0) {
            System.out.println("\nNo deliveries to generate report from.");
            return;
        }
        performanceReports.generateReport();
    }
    
    /**
     * Save all data before exiting
     */
    private static void saveAllData() {
        cityManager.saveCities();
        distanceManager.saveDistances();
    }
}
