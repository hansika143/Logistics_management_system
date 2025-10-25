package javaassigmnet01;

import java.util.ArrayList;

public class DeliveryManager {
    private ArrayList<DeliveryRequest> requests;
    private final int MAX_DELIVERIES = 50;  // Maximum deliveries as per requirement
    private final CityManager cityManager;
    private final VehicleManager vehicleManager;
    private final DistanceManager distanceManager;
    
    public DeliveryManager(CityManager cityManager, VehicleManager vehicleManager, DistanceManager distanceManager) {
        this.requests = new ArrayList<>();
        this.cityManager = cityManager;
        this.vehicleManager = vehicleManager;
        this.distanceManager = distanceManager;
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
        
        // Check if distance is set between cities
        if (distanceManager.getDistance(sourceCity, destCity) <= 0) {
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
        
        // Adding tracking information
        private String status;  // "Pending", "In Progress", "Completed"
        private String deliveryDate;  // When the delivery was completed
        private int deliveryId;  // Unique ID for each delivery
        private static int nextDeliveryId = 1;  // To generate unique IDs
        
        public DeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
            this.sourceCity = sourceCity;
            this.destCity = destCity;
            this.weight = weight;
            this.vehicleType = vehicleType;
            this.status = "Pending";
            this.deliveryId = nextDeliveryId++;
            this.deliveryDate = "-";
            
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
            return String.format("ID: %-3d | From: %-10s To: %-10s | Weight: %-5dkg | Vehicle: %-8s | Status: %-10s | Date: %s", 
                deliveryId, sourceCity, destCity, weight, vehicleManager.getVehicleType(vehicleType),
                status, deliveryDate);
        }
        
        public void displayCostEstimate() {
            if (costEstimate != null) {
                costEstimate.displayEstimate();
            } else {
                System.out.println("Cannot calculate cost - distance not set between cities.");
            }
        }
        
        public void updateStatus(String newStatus) {
            this.status = newStatus;
            if (newStatus.equals("Completed")) {
                // Get current date in simple format using just string for now
                this.deliveryDate = java.time.LocalDate.now().toString();
            }
        }
        
        public String getStatus() {
            return status;
        }
        
        public int getDeliveryId() {
            return deliveryId;
        }
        
        public String getDeliveryDate() {
            return deliveryDate;
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
    
    public void updateDeliveryStatus(int deliveryId, String newStatus) {
        for (DeliveryRequest request : requests) {
            if (request.getDeliveryId() == deliveryId) {
                request.updateStatus(newStatus);
                return;
            }
        }
        System.out.println("Delivery ID not found!");
    }
    
    public int getCompletedDeliveries() {
        int count = 0;
        for (DeliveryRequest request : requests) {
            if (request.getStatus().equals("Completed")) {
                count++;
            }
        }
        return count;
    }
    
    public int getPendingDeliveries() {
        int count = 0;
        for (DeliveryRequest request : requests) {
            if (request.getStatus().equals("Pending")) {
                count++;
            }
        }
        return count;
    }
    
    public void displayDeliveryStats() {
        System.out.println("\n=== Delivery Statistics ===");
        System.out.println("Total Deliveries: " + requests.size());
        System.out.println("Completed Deliveries: " + getCompletedDeliveries());
        System.out.println("Pending Deliveries: " + getPendingDeliveries());
        System.out.println("Remaining Capacity: " + getRemainingCapacity());
        System.out.println("=========================");
    }
}
