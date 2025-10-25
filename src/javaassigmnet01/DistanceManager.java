package javaassigmnet01;

import java.util.ArrayList;

/**
 * DistanceManager class handles the distances between cities.
 * This class is responsible for:
 * - Storing distances between cities
 * - Calculating routes
 * - Loading and saving distance data
 * 
 * @author Hansika
 */
public class DistanceManager {
    // 2D array to store distances between cities
    private final double[][] distances;
    private final CityManager cityManager;
    
    /**
     * Constructor for DistanceManager
     * Initializes the distance matrix and loads existing data
     * 
     * @param cityManager Reference to CityManager for city operations
     */
    public DistanceManager(CityManager cityManager) {
        this.cityManager = cityManager;
        this.distances = new double[30][30];  // Maximum 30 cities as per requirement
        loadDistances();
    }
    
    /**
     * Load distances from file
     */
    private void loadDistances() {
        ArrayList<String> loadedCities = new ArrayList<>();
        if (FileManager.loadRouteData(loadedCities, distances)) {
            System.out.println("Distance data loaded from file.");
        }
    }
    
    /**
     * Save current distances to file
     */
    public void saveDistances() {
        FileManager.saveRouteData(cityManager.getAllCities(), distances);
    }et01;

public class DistanceMa    /**
     * Load distances from file
     */
    private void loadDistances() {
        ArrayList<String> loadedCities = new ArrayList<>();
        if (FileManager.loadRouteData(loadedCities, distances)) {
            // Distances loaded successfully
            System.out.println("Distance data loaded from file.");
        }
    }
    
    /**
     * Save current distances to file
     */
    public void saveDistances() {
        FileManager.saveRouteData(cityManager.getAllCities(), distances);
    }
    
    public void displayDistanceTable() {
        var cities = cityManager.getAllCities();
        if (cities.isEmpty()) {
            System.out.println("No cities to display distances for.");
            return;
        }{
    private final int[][] distances;
    private final CityManager cityManager;
    
    public DistanceManager(CityManager cityManager) {
        this.cityManager = cityManager;
        this.distances = new int[cityManager.getMaxCities()][cityManager.getMaxCities()];
        loadDistances();
        // Initialize all distances to -1 (indicating not set)
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (i == j) {
                    distances[i][j] = 0;  // Distance to self is 0
                } else {
                    distances[i][j] = -1; // Not set
                }
            }
        }
    }
    
    public boolean setDistance(String city1, String city2, int distance) {
        int index1 = cityManager.getCityIndex(city1);
        int index2 = cityManager.getCityIndex(city2);
        
        if (index1 == -1 || index2 == -1 || index1 == index2) {
            return false;
        }
        
        if (distance < 0) {
            return false;
        }
        
        // Set distance symmetrically
        distances[index1][index2] = distance;
        distances[index2][index1] = distance;
        return true;
    }
    
    public int getDistance(String city1, String city2) {
        int index1 = cityManager.getCityIndex(city1);
        int index2 = cityManager.getCityIndex(city2);
        
        if (index1 == -1 || index2 == -1) {
            return -1;
        }
        
        return distances[index1][index2];
    }
    
    public void displayDistanceTable() {
        var cities = cityManager.getAllCities();
        if (cities.isEmpty()) {
            System.out.println("No cities available.");
            return;
        }
        
        // Print header
        System.out.print("\nDistance Table (km)\n    ");
        for (String city : cities) {
            System.out.printf("%-8s", city);
        }
        System.out.println();
        
        // Print separator
        for (int i = 0; i <= cities.size(); i++) {
            System.out.print("--------");
        }
        System.out.println();
        
        // Print distances
        for (int i = 0; i < cities.size(); i++) {
            System.out.printf("%-4s", cities.get(i));
            for (int j = 0; j < cities.size(); j++) {
                if (distances[i][j] == -1) {
                    System.out.printf("%-8s", "N/A");
                } else {
                    System.out.printf("%-8d", distances[i][j]);
                }
            }
            System.out.println();
        }
    }
}
