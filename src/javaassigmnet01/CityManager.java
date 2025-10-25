package javaassigmnet01;

import java.util.ArrayList;
import java.util.Scanner;

public class CityManager {
    private ArrayList<String> cities;
    private final int MAX_CITIES = 30;
    
    public CityManager() {
        cities = new ArrayList<>();
        // Adding some initial cities for testing
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
}
