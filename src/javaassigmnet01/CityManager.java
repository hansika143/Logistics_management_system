package javaassigmnet01;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CityManager class handles all operations related to cities in the system.
 * This class is responsible for:
 * - Storing city names
 * - Adding new cities
 * - Removing cities
 * - Checking if cities exist
 * 
 * @author Hansika
 */
public class CityManager {
    // ArrayList to store city names
    private ArrayList<String> cities;
    // Maximum number of cities that can be stored
    private final int MAX_CITIES = 30;
    
    /**
     * Constructor for CityManager
     * Initializes the cities list and adds some sample cities
     */
    public CityManager() {
        cities = new ArrayList<>();
        loadCities();
        // Adding initial cities for testing
        addCity("Colombo");
        addCity("Kandy");
        addCity("Galle");
        addCity("Jaffna");
        addCity("Matara");
    }
    
    public boolean addCity(String cityName) {
        cityName = cityName.trim();
        if (cities.size() >= MAX_CITIES) {
            return false;
        }
        if (cities.contains(cityName)) {
            return false;
        }
        cities.add(cityName);
        return true;
    }
    
    public boolean renameCity(String oldName, String newName) {
        oldName = oldName.trim();
        newName = newName.trim();
        if (cities.contains(newName)) {
            return false;
        }
        int index = cities.indexOf(oldName);
        if (index == -1) {
            return false;
        }
        cities.set(index, newName);
        return true;
    }
    
    public boolean removeCity(String cityName) {
        return cities.remove(cityName.trim());
    }
    
    public boolean hasCity(String cityName) {
        return cities.contains(cityName.trim());
    }
    
    public ArrayList<String> getAllCities() {
        return new ArrayList<>(cities);
    }
    
    public int getCityCount() {
        return cities.size();
    }
    
    public int getMaxCities() {
        return MAX_CITIES;
    }
    
    public int getCityIndex(String cityName) {
        return cities.indexOf(cityName.trim());
    }
    
    public String getCityName(int index) {
        if (index >= 0 && index < cities.size()) {
            return cities.get(index);
        }
        return null;
    }
    
    /**
     * Load cities from file when starting
     */
    private void loadCities() {
        // First try to load from file
        ArrayList<String> loadedCities = new ArrayList<>();
        int[][] dummyDistances = new int[MAX_CITIES][MAX_CITIES];
        
        if (!FileManager.loadRouteData(loadedCities, dummyDistances)) {
            // If file doesn't exist, add default cities
            addDefaultCities();
        } else {
            cities.addAll(loadedCities);
        }
    }
    
    /**
     * Add some default cities if no file exists
     */
    private void addDefaultCities() {
        addCity("Colombo");
        addCity("Kandy");
        addCity("Galle");
        addCity("Jaffna");
        addCity("Matara");
    }
    
    /**
     * Save current city list to file
     */
    public void saveCities() {
        int[][] currentDistances = new int[MAX_CITIES][MAX_CITIES];
        // Get current distances from DistanceManager if needed
        FileManager.saveRouteData(cities, currentDistances);
    }
}
