package javaassigmnet01;

import java.util.ArrayList;

/**
 * DeliveryManager class handles all delivery-related operations.
 * This class is responsible for:
 * - Managing delivery requests
 * - Calculating delivery costs
 * - Tracking delivery status
 * - Maintaining delivery history
 * 
 * The class works with CityManager, VehicleManager, and DistanceManager
 * to handle all aspects of the delivery system.
 * 
 * @author Hansika
 */
public class DeliveryManager {
    // List to store all delivery requests
    private ArrayList<DeliveryRequest> requests;
    
    // Maximum number of deliveries that can be handled (as per requirement)
    private final int MAX_DELIVERIES = 50;
    
    // References to other managers needed for delivery operations
    private final CityManager cityManager;
    private final VehicleManager vehicleManager;
    private final DistanceManager distanceManager;
    
    // Counter to generate unique delivery IDs
    private static long nextDeliveryId = 1;
    
    /**
     * Generates a new unique delivery ID.
     * Handles potential overflow by resetting if necessary.
     * @return A unique delivery ID
     */
    private synchronized static int generateDeliveryId() {
        if (nextDeliveryId >= Integer.MAX_VALUE) {
            // Reset ID counter if it's about to overflow
            nextDeliveryId = 1;
        }
        return (int) nextDeliveryId++;
    }
    
    /**
     * Constructor for DeliveryManager
     * Initializes the delivery system with required managers
     * 
     * @param cityManager Manages city operations
     * @param vehicleManager Manages vehicle operations
     * @param distanceManager Manages distance calculations
     */
    public DeliveryManager(CityManager cityManager, VehicleManager vehicleManager, DistanceManager distanceManager) {
        this.requests = new ArrayList<>();
        this.cityManager = cityManager;
        this.vehicleManager = vehicleManager;
        this.distanceManager = distanceManager;
        
        // Load existing delivery history from file
        loadDeliveryHistory();
    }
    
    /**
     * Validates a delivery request before it can be added to the system.
     * Checks:
     * - If both cities exist in the system
     * - If source and destination cities are different
     * - If there's a valid distance between cities
     * - If the vehicle type is valid
     * - If the vehicle can handle the given weight
     *
     * @param sourceCity The city where the delivery starts
     * @param destCity The destination city
     * @param weight The weight of the delivery in kg
     * @param vehicleType The type of vehicle to use
     * @return true if the request is valid, false otherwise
     * @throws IllegalArgumentException if any parameter is null or invalid
     */
    public boolean validateDeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
        // Validate input parameters
        if (sourceCity == null || destCity == null || sourceCity.trim().isEmpty() || destCity.trim().isEmpty()) {
            throw new IllegalArgumentException("City names cannot be null or empty");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }
        if (vehicleType < 0) {
            throw new IllegalArgumentException("Vehicle type cannot be negative");
        }
        
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
    
    /**
     * Inner class to represent a delivery request.
     * Encapsulates all information about a single delivery.
     */
    public class DeliveryRequest {
    private final String sourceCity;
    private final String destCity;
    private final int weight;
    private final int vehicleType;
    private final DeliveryCostEstimate costEstimate;
        
    // Tracking information
    private String status;  // "Pending", "In Progress", "Completed"
    private String deliveryDate;  // When the delivery was completed
    private final int deliveryId;  // Unique ID for each delivery

    public String getSourceCity() { return sourceCity; }
    public String getDestCity() { return destCity; }
    public int getWeight() { return weight; }
    public int getVehicleType() { return vehicleType; }
    public DeliveryCostEstimate getCostEstimate() { return costEstimate; }
        
        public DeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
            this.sourceCity = sourceCity;
            this.destCity = destCity;
            this.weight = weight;
            this.vehicleType = vehicleType;
            this.status = "Pending";
            this.deliveryId = generateDeliveryId();
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
            } else {
                this.costEstimate = null;
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
    
    /**
     * Displays all delivery requests in the system.
     * Shows a formatted table with delivery details.
     */
    public void displayDeliveries() {
        if (requests == null || requests.isEmpty()) {
            System.out.println("No deliveries in the system.");
            return;
        }
        
        System.out.println("\nDelivery Requests:");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-5s %-12s %-12s %-8s %-10s %-12s %-10s%n", 
            "No.", "From", "To", "Weight", "Vehicle", "Status", "Date");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < requests.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, requests.get(i));
        }
        System.out.println("--------------------------------------------------------------------------------");
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
    
    /**
     * Loads delivery history from file.
     * Handles file reading errors and data validation.
     */
    private void loadDeliveryHistory() {
        try {
            ArrayList<String> records = FileManager.readDeliveryRecords();
            if (records == null) {
                System.out.println("Warning: No delivery history found or file could not be read");
                return;
            }

            for (String record : records) {
                try {
                    String[] parts = record.split("\\|");
                    if (parts.length < 7) {
                        System.out.println("Warning: Skipping invalid record format: " + record);
                        continue;
                    }

                    String timestamp = parts[0].trim();
                    String sourceCity = parts[1].trim();
                    String destCity = parts[2].trim();
                    int weight = Integer.parseInt(parts[3].trim());
                    String vehicleType = parts[4].trim();
                    double cost = Double.parseDouble(parts[5].trim());
                    String status = parts[6].trim();
                
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
                } catch (Exception e) {
                    System.out.println("Warning: Error parsing record: " + record);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading delivery history: " + e.getMessage());
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
