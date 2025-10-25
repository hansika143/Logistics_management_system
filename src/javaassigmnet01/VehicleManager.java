package javaassigmnet01;

/**
 * VehicleManager class manages different types of vehicles and their properties.
 * This class stores information about:
 * - Vehicle types (Van, Truck, Lorry)
 * - Vehicle capacities in kg
 * - Cost rates per kilometer
 * - Average speeds
 * - Fuel efficiency (km/l)
 * 
 * @author Hansika
 */
public class VehicleManager {
    // Array to store different types of vehicles
    private final String[] vehicleTypes = {"Van", "Truck", "Lorry"};
    
    // Array to store maximum weight capacity for each vehicle type (in kilograms)
    private final int[] capacities = {1000, 5000, 10000};  // in kg
    
    // Array to store cost per kilometer for each vehicle type (in Sri Lankan Rupees)
    private final int[] ratesPerKm = {30, 40, 80};        // in LKR
    
    // Array to store average speed for each vehicle type (in kilometers per hour)
    private final int[] avgSpeeds = {60, 50, 45};         // in km/h
    
    // Array to store fuel efficiency for each vehicle type (in kilometers per liter)
    private final int[] fuelEfficiencies = {12, 6, 4};    // in km/l
    
    public String getVehicleType(int index) {
        if(index >= 0 && index < vehicleTypes.length) {
            return vehicleTypes[index];
        }
        return null;
    }
    
    public int getCapacity(int index) {
        if(index >= 0 && index < capacities.length) {
            return capacities[index];
        }
        return -1;
    }
    
    public int getRatePerKm(int index) {
        if(index >= 0 && index < ratesPerKm.length) {
            return ratesPerKm[index];
        }
        return -1;
    }
    
    public int getAvgSpeed(int index) {
        if(index >= 0 && index < avgSpeeds.length) {
            return avgSpeeds[index];
        }
        return -1;
    }
    
    public int getFuelEfficiency(int index) {
        if(index >= 0 && index < fuelEfficiencies.length) {
            return fuelEfficiencies[index];
        }
        return -1;
    }
    
    public int getVehicleCount() {
        return vehicleTypes.length;
    }
    
    // Check if a vehicle can handle the given weight
    public boolean canHandleWeight(int vehicleIndex, int weight) {
        if(vehicleIndex >= 0 && vehicleIndex < capacities.length) {
            return weight <= capacities[vehicleIndex];
        }
        return false;
    }
    
    // Display available vehicles and their specifications
    public void displayVehicles() {
        System.out.println("\nAvailable Vehicles:");
        System.out.println("----------------------------------------------------------");
        System.out.printf("%-6s %-8s %-15s %-15s %-15s %-15s%n", 
                "Index", "Type", "Capacity(kg)", "Rate/km(LKR)", "Speed(km/h)", "Fuel(km/l)");
        System.out.println("----------------------------------------------------------");
        
        for(int i = 0; i < vehicleTypes.length; i++) {
            System.out.printf("%-6d %-8s %-15d %-15d %-15d %-15d%n", 
                    i + 1,
                    vehicleTypes[i], 
                    capacities[i], 
                    ratesPerKm[i], 
                    avgSpeeds[i], 
                    fuelEfficiencies[i]);
        }
        System.out.println("----------------------------------------------------------");
    }
}
