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
        
        public DeliveryRequest(String sourceCity, String destCity, int weight, int vehicleType) {
            this.sourceCity = sourceCity;
            this.destCity = destCity;
            this.weight = weight;
            this.vehicleType = vehicleType;
        }
    }
}
