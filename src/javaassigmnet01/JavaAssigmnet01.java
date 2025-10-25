package javaassigmnet01;

import java.util.Scanner;

public class JavaAssigmnet01 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CityManager cityManager = new CityManager();
    private static final DistanceManager distanceManager = new DistanceManager(cityManager);
    private static final VehicleManager vehicleManager = new VehicleManager();
    private static final DeliveryManager deliveryManager = new DeliveryManager(cityManager, vehicleManager, distanceManager);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Logistics Management System ===");
            System.out.println("1. City Management");
            System.out.println("2. Distance Management");
            System.out.println("3. Vehicle Management");
            System.out.println("4. Delivery Management");
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
                    System.out.print("\nSelect vehicle (1-" + vehicleManager.getVehicleCount() + "): ");
                    int vehicleType = scanner.nextInt() - 1;
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter cargo weight (kg): ");
                    int weight = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

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
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        System.out.println("\nChoose new status:");
                        System.out.println("1. In Progress");
                        System.out.println("2. Completed");
                        System.out.print("Enter choice: ");
                        int statusChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                        String newStatus = statusChoice == 1 ? "In Progress" : "Completed";
                        deliveryManager.updateDeliveryStatus(id, newStatus);
                        System.out.println("Delivery status updated!");
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
}
