package javaassigmnet01;

public class DeliveryCostEstimate {
    private final String sourceCity;
    private final String destCity;
    private final int distance;
    private final String vehicleType;
    private final int weight;
    private final double baseCost;
    private final double fuelUsed;
    private final double fuelCost;
    private final double operationalCost;
    private final double profit;
    private final double customerCharge;
    private final double estimatedTime;
    
    public DeliveryCostEstimate(String sourceCity, String destCity, int distance,
            String vehicleType, int weight, int ratePerKm, int avgSpeed, int fuelEfficiency) {
        
        this.sourceCity = sourceCity;
        this.destCity = destCity;
        this.distance = distance;
        this.vehicleType = vehicleType;
        this.weight = weight;
        
        // Calculate all costs
        this.baseCost = CostCalculator.calculateBaseCost(distance, ratePerKm, weight);
        this.fuelUsed = CostCalculator.calculateFuelUsed(distance, fuelEfficiency);
        this.fuelCost = CostCalculator.calculateFuelCost(fuelUsed);
        this.operationalCost = CostCalculator.calculateOperationalCost(baseCost, fuelCost);
        this.profit = CostCalculator.calculateProfit(baseCost);
        this.customerCharge = CostCalculator.calculateFinalCharge(operationalCost, profit);
        this.estimatedTime = CostCalculator.calculateDeliveryTime(distance, avgSpeed);
    }
    
    public void displayEstimate() {
        System.out.println("\n======================================================");
        System.out.println("DELIVERY COST ESTIMATION");
        System.out.println("------------------------------------------------------");
        System.out.printf("From: %s%n", sourceCity);
        System.out.printf("To: %s%n", destCity);
        System.out.printf("Distance: %d km%n", distance);
        System.out.printf("Vehicle: %s%n", vehicleType);
        System.out.printf("Weight: %d kg%n", weight);
        System.out.println("------------------------------------------------------");
        System.out.printf("Base Cost: %.2f LKR%n", baseCost);
        System.out.printf("Fuel Used: %.2f L%n", fuelUsed);
        System.out.printf("Fuel Cost: %.2f LKR%n", fuelCost);
        System.out.printf("Operational Cost: %.2f LKR%n", operationalCost);
        System.out.printf("Profit: %.2f LKR%n", profit);
        System.out.printf("Customer Charge: %.2f LKR%n", customerCharge);
        System.out.printf("Estimated Time: %.2f hours%n", estimatedTime);
        System.out.println("======================================================");
    }
}
