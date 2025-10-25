package javaassigmnet01;

public class CostCalculator {
    private static final double FUEL_PRICE = 310.0;  // LKR per liter
    private static final double MARKUP_RATE = 0.25;  // 25% markup
    
    // Basic calculation method - we'll improve this later
    public static double calculateBaseCost(int distance, int ratePerKm, int weight) {
        // Cost = D × R × (1 + W/10000)
        return distance * ratePerKm * (1 + (weight / 10000.0));
    }
    
    // First attempt at fuel cost calculation
    public static double calculateFuelCost(int distance, int fuelEfficiency) {
        // Calculate fuel used (distance/efficiency)
        double fuelUsed = distance / (double)fuelEfficiency;
        // Calculate fuel cost
        return fuelUsed * FUEL_PRICE;
    }
}
