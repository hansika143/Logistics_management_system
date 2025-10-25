package javaassigmnet01;

/**
 * Handles generation of performance reports and statistics for the delivery system.
 * Created by: [Your Name]
 * Date: October 25, 2025
 */
public class PerformanceReports {
    private final DeliveryManager deliveryManager;
    private final DistanceManager distanceManager;
    
    public PerformanceReports(DeliveryManager deliveryManager, DistanceManager distanceManager) {
        this.deliveryManager = deliveryManager;
        this.distanceManager = distanceManager;
    }
    
    /**
     * Generates and displays a complete performance report
     */
    public void generateReport() {
        System.out.println("\n=== Performance Report ===");
        System.out.println("Generated on: " + java.time.LocalDate.now().toString());
        System.out.println("------------------------");
        
        // Delivery Statistics
        System.out.println("Delivery Statistics:");
        System.out.printf("Total Deliveries: %d%n", deliveryManager.getDeliveryCount());
        System.out.printf("Completed Deliveries: %d%n", deliveryManager.getCompletedDeliveries());
        System.out.printf("In Progress: %d%n", deliveryManager.getInProgressDeliveries());
        System.out.printf("Pending: %d%n", deliveryManager.getPendingDeliveries());
        
        // Calculate route statistics
        double totalDistance = calculateTotalDistance();
        System.out.printf("%nRoute Statistics:%n");
        System.out.printf("Total Distance Covered: %.1f km%n", totalDistance);
        
        // Calculate time and efficiency metrics
        if (deliveryManager.getCompletedDeliveries() > 0) {
            System.out.printf("Average Delivery Time: %.1f hours%n", calculateAverageDeliveryTime());
            System.out.printf("Average Distance per Delivery: %.1f km%n", 
                    totalDistance / deliveryManager.getCompletedDeliveries());
        }
        
        // Financial metrics
        calculateFinancialMetrics();
        
        // Route analysis
        analyzeRoutes();
        
        System.out.println("------------------------");
    }
    
    private double calculateTotalDistance() {
        double totalDistance = 0;
        var deliveries = deliveryManager.getAllDeliveries();
        
        for (DeliveryManager.DeliveryRequest delivery : deliveries) {
            if (delivery.getStatus().equals("Completed")) {
                totalDistance += distanceManager.getDistance(delivery.getSourceCity(), delivery.getDestCity());
            }
        }
        
        return totalDistance;
    }
    
    private double calculateAverageDeliveryTime() {
        double totalTime = 0;
        int completedCount = 0;
        var deliveries = deliveryManager.getAllDeliveries();
        
        for (DeliveryManager.DeliveryRequest delivery : deliveries) {
            if (delivery.getStatus().equals("Completed") && delivery.getCostEstimate() != null) {
                // Get delivery time from cost estimate
                totalTime += delivery.getCostEstimate().getEstimatedTime();
                completedCount++;
            }
        }
        
        return completedCount > 0 ? totalTime / completedCount : 0;
    }
    
    private void calculateFinancialMetrics() {
        double totalRevenue = 0;
        double totalProfit = 0;
        var deliveries = deliveryManager.getAllDeliveries();
        
        for (DeliveryManager.DeliveryRequest delivery : deliveries) {
            if (delivery.getStatus().equals("Completed") && delivery.getCostEstimate() != null) {
                totalRevenue += delivery.getCostEstimate().getCustomerCharge();
                totalProfit += delivery.getCostEstimate().getProfit();
            }
        }
        
        System.out.printf("%nFinancial Summary:%n");
        System.out.printf("Total Revenue: LKR %.2f%n", totalRevenue);
        System.out.printf("Total Profit: LKR %.2f%n", totalProfit);
        if (totalRevenue > 0) {
            System.out.printf("Profit Margin: %.1f%%%n", (totalProfit / totalRevenue) * 100);
        }
    }
    
    private void analyzeRoutes() {
        int shortestDistance = Integer.MAX_VALUE;
        int longestDistance = 0;
        String shortestRoute = "";
        String longestRoute = "";
        var deliveries = deliveryManager.getAllDeliveries();
        
        for (DeliveryManager.DeliveryRequest delivery : deliveries) {
            if (delivery.getStatus().equals("Completed")) {
                int distance = distanceManager.getDistance(delivery.getSourceCity(), delivery.getDestCity());
                
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    shortestRoute = delivery.getSourceCity() + " → " + delivery.getDestCity();
                }
                
                if (distance > longestDistance) {
                    longestDistance = distance;
                    longestRoute = delivery.getSourceCity() + " → " + delivery.getDestCity();
                }
            }
        }
        
        if (shortestDistance != Integer.MAX_VALUE) {
            System.out.printf("%nRoute Analysis:%n");
            System.out.printf("Shortest Route: %s (%d km)%n", shortestRoute, shortestDistance);
            System.out.printf("Longest Route: %s (%d km)%n", longestRoute, longestDistance);
        }
    }
}
