package javaassigmnet01;

import java.util.ArrayList;

public class DeliveryManager {
    private ArrayList<DeliveryRequest> requests;
    private final int MAX_DELIVERIES = 50;  // Maximum deliveries as per requirement
    private final CityManager cityManager;
    private final VehicleManager vehicleManager;
    private final DistanceManager distanceManager;
    private static int nextDeliveryId = 1;  // To generate unique IDs
    
    public DeliveryManager(CityManager cityManager, VehicleManager vehicleManager, DistanceManager distanceManager) {
        this.requests = new ArrayList<>();
        this.cityManager = cityManager;
        this.vehicleManager = vehicleManager;
        this.distanceManager = distanceManager;
        
        // Load delivery history from file
        loadDeliveryHistory();
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
        
        DeliveryRequest request = new DeliveryRequest(sourceCity, destCity, weight, vehicleType);
        requests.add(request);
        
        // Save to file if cost estimate is available
        if (request.costEstimate != null) {
            FileManager.saveDeliveryRecord(
                sourceCity, 
                destCity, 
                weight, 
                vehicleManager.getVehicleType(vehicleType),
                request.costEstimate.getCustomerCharge(),
                "Pending"
            );
        }
        
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
        
        public DeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
            this.sourceCity = sourceCity;
            this.destCity = destCity;
            this.weight = weight;
            this.vehicleType = vehicleType;
            this.status = "Pending";
            this.deliveryId = DeliveryManager.nextDeliveryId++;
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
                // Format date as dd/MM/yyyy
                this.deliveryDate = java.time.LocalDate.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );
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
    
    public boolean updateDeliveryStatus(int deliveryId, String newStatus) {
        // Validate status
        if (!newStatus.equals("Pending") && !newStatus.equals("In Progress") && !newStatus.equals("Completed")) {
            System.out.println("Invalid status! Use: Pending, In Progress, or Completed");
            return false;
        }
        
        for (DeliveryRequest request : requests) {
            if (request.getDeliveryId() == deliveryId) {
                request.updateStatus(newStatus);
                
                // Save updated status to file
                if (request.costEstimate != null) {
                    FileManager.saveDeliveryRecord(
                        request.sourceCity,
                        request.destCity,
                        request.weight,
                        vehicleManager.getVehicleType(request.vehicleType),
                        request.costEstimate.getCustomerCharge(),
                        newStatus
                    );
                }
                
                return true;
            }
        }
        System.out.println("Delivery ID not found!");
        return false;
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
    
    public int getInProgressDeliveries() {
        int count = 0;
        for (DeliveryRequest request : requests) {
            if (request.getStatus().equals("In Progress")) {
                count++;
            }
        }
        return count;
    }
    
    private void loadDeliveryHistory() {
        ArrayList<String> records = FileManager.readDeliveryRecords();
        for (String record : records) {
            String[] parts = record.split("\\|");
            if (parts.length >= 7) {
                String timestamp = parts[0];
                String sourceCity = parts[1];
                String destCity = parts[2];
                int weight = Integer.parseInt(parts[3]);
                String vehicleType = parts[4];
                double cost = Double.parseDouble(parts[5]);
                String status = parts[6];
                
                // Find matching vehicle type index
                int vehicleIndex = -1;
                for (int i = 0; i < vehicleManager.getVehicleCount(); i++) {
                    if (vehicleManager.getVehicleType(i).equals(vehicleType)) {
                        vehicleIndex = i;
                        break;
                    }
                }
                
                if (vehicleIndex != -1) {
                    DeliveryRequest request = new DeliveryRequest(sourceCity, destCity, weight, vehicleIndex);
                    request.updateStatus(status);
                    requests.add(request);
                }
            }
        }
    }
    
    public void displayDeliveryStats() {
        int total = requests.size();
        int completed = getCompletedDeliveries();
        int inProgress = getInProgressDeliveries();
        int pending = getPendingDeliveries();
        int remaining = getRemainingCapacity();

        System.out.println("\n=== Delivery Statistics ===");
        System.out.println("Total Deliveries: " + total);
        System.out.println("Completed Deliveries: " + completed);
        System.out.println("In Progress Deliveries: " + inProgress);
        System.out.println("Pending Deliveries: " + pending);
        System.out.println("Remaining Capacity: " + remaining);
        System.out.println("=========================");
        
        // Export statistics to file
        FileManager.exportDeliveryStats(total, completed, inProgress, pending, remaining);
    }
    
    /**
     * Get all delivery requests for reporting purposes
     * @return ArrayList of all delivery requests
     */
    public ArrayList<DeliveryRequest> getAllDeliveries() {
        return new ArrayList<>(requests);  // Return a copy to prevent external modification
    }
}
