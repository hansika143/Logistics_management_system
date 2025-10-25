package javaassigmnet01;

import java.util.ArrayList;

public class DeliveryManager {
    private ArrayList<DeliveryRequest> requests;
    private final int MAX_DELIVERIES = 50;  // Maximum deliveries as per requirement
    private final CityManager cityManager;
    private final VehicleManager vehicleManager;
    
    public DeliveryManager(CityManager cityManager, VehicleManager vehicleManager) {
        this.requests = new ArrayList<>();
        this.cityManager = cityManager;
        this.vehicleManager = vehicleManager;
    }
    
    public boolean validateDeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
        // Check if cities exist
        if (cityManager.getCityIndex(sourceCity) == -1 || cityManager.getCityIndex(destCity) == -1) {
            return false;
        }
        
        // Check if source and destination are different
        if (sourceCity.equals(destCity)) {
            return false;
        }
        
        // Check if vehicle type is valid
        if (vehicleType < 0 || vehicleType >= vehicleManager.getVehicleCount()) {
            return false;
        }
        
        // Check if weight is valid for vehicle
        if (!vehicleManager.canHandleWeight(vehicleType, weight)) {
            return false;
        }
        
        return true;
    }
    
    public boolean addDeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
        if (requests.size() >= MAX_DELIVERIES) {
            return false;
        }
        
        if (!validateDeliveryRequest(sourceCity, destCity, weight, vehicleType)) {
            return false;
        }
        
        requests.add(new DeliveryRequest(sourceCity, destCity, weight, vehicleType));
        return true;
    }
    
    // Basic delivery request class - we'll expand this later
    class DeliveryRequest {
        String sourceCity;
        String destCity;
        int weight;
        int vehicleType;
        DeliveryCostEstimate costEstimate;
        
        public DeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
            this.sourceCity = sourceCity;
            this.destCity = destCity;
            this.weight = weight;
            this.vehicleType = vehicleType;
            
            // Calculate cost estimate
            int distance = distanceManager.getDistance(sourceCity, destCity);
            if (distance > 0) {  // Only if we have a valid distance
                this.costEstimate = new DeliveryCostEstimate(
                    sourceCity, 
                    destCity,
                    distance,
                    vehicleManager.getVehicleType(vehicleType),
                    weight,
                    vehicleManager.getRatePerKm(vehicleType),
                    vehicleManager.getAvgSpeed(vehicleType),
                    vehicleManager.getFuelEfficiency(vehicleType)
                );
            }
        }
        
        @Override
        public String toString() {
            return String.format("From: %-10s To: %-10s Weight: %-5dkg Vehicle: %s", 
                sourceCity, destCity, weight, vehicleManager.getVehicleType(vehicleType));
        }
        
        public void displayCostEstimate() {
            if (costEstimate != null) {
                costEstimate.displayEstimate();
            } else {
                System.out.println("Cannot calculate cost - distance not set between cities.");
            }
        }
    }
    
    public void displayDeliveries() {
        if (requests.isEmpty()) {
            System.out.println("No deliveries in the system.");
            return;
        }
        
        System.out.println("\nDelivery Requests:");
        System.out.println("----------------------------------------");
        for (int i = 0; i < requests.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, requests.get(i));
        }
        System.out.println("----------------------------------------");
    }
    
    public void displayDeliveryDetails(int index) {
        if (index >= 0 && index < requests.size()) {
            requests.get(index).displayCostEstimate();
        } else {
            System.out.println("Invalid delivery number!");
        }
    }
    
    public int getDeliveryCount() {
        return requests.size();
    }
    
    public int getRemainingCapacity() {
        return MAX_DELIVERIES - requests.size();
    }
}
