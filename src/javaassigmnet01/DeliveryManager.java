package javaassigmnet01;

import java.util.ArrayList;

public class DeliveryManager {
    private ArrayList<DeliveryRequest> requests;
    private final int MAX_DELIVERIES = 50;  // Maximum deliveries as per requirement
    
    public DeliveryManager() {
        requests = new ArrayList<>();
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
