package javaassigmnet01;

import java.util.ArrayList;
import java.util.List;

public class RouteOptimizer {
    private final CityManager cityManager;
    private final DistanceManager distanceManager;
    
    public RouteOptimizer(CityManager cityManager, DistanceManager distanceManager) {
        this.cityManager = cityManager;
        this.distanceManager = distanceManager;
    }
    
    // First try - just find direct route between cities
    public int findDirectDistance(String source, String dest) {
        return distanceManager.getDistance(source, dest);
    }
}
