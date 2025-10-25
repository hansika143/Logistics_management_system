package javaassigmnet01;

public class CostCalculator {
    private static final double FUEL_PRICE = 310.0;  // LKR per liter
    private static final double MARKUP_RATE = 0.25;  // 25% markup
    
    public static double calculateBaseCost(int distance, double ratePerKm, int weight) {
        // Cost = D × R × (1 + W/10000)
        return distance * ratePerKm * (1 + (weight / 10000.0));
    }
    
    public static double calculateFuelUsed(int distance, double fuelEfficiency) {
        // FuelUsed = D/E
        return distance / fuelEfficiency;
    }
    
    public static double calculateFuelCost(double fuelUsed) {
        // FuelCost = FuelUsed × F
        return fuelUsed * FUEL_PRICE;
    }
    
    public static double calculateDeliveryTime(int distance, double avgSpeed) {
        // Time = D/S
        return distance / avgSpeed;
    }
    
    public static double calculateOperationalCost(double deliveryCost, double fuelCost) {
        // TotalCost = DeliveryCost + FuelCost
        return deliveryCost + fuelCost;
    }
    
    public static double calculateProfit(double baseCost) {
        // Profit = Cost × 0.25
        return baseCost * MARKUP_RATE;
    }
    
    public static double calculateFinalCharge(double operationalCost, double profit) {
        // CustomerCharge = TotalCost + Profit
        return operationalCost + profit;
    }
}
