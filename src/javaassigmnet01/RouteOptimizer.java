package javaassigmnet01;

import java.util.ArrayList;
import java.util.List;

public class RouteOptimizer {
    private final CityManager cityManager;
    private final DistanceManager distanceManager;
    
    // Simple class to store route information
    class Route {
        List<String> cities;
        int totalDistance;
        
        public Route() {
            cities = new ArrayList<>();
            totalDistance = 0;
        }
        
        public Route(List<String> cities, int totalDistance) {
            this.cities = new ArrayList<>(cities);
            this.totalDistance = totalDistance;
        }
        
        @Override
        public String toString() {
            StringBuilder path = new StringBuilder();
            for (int i = 0; i < cities.size(); i++) {
                path.append(cities.get(i));
                if (i < cities.size() - 1) {
                    path.append(" → ");
                }
            }
            return String.format("Route: %s (Total Distance: %d km)", path.toString(), totalDistance);
        }
    }
    
    public RouteOptimizer(CityManager cityManager, DistanceManager distanceManager) {
        this.cityManager = cityManager;
        this.distanceManager = distanceManager;
    }
    
    // First try - just find direct route between cities
    public int findDirectDistance(String source, String dest) {
        return distanceManager.getDistance(source, dest);
    }
}
