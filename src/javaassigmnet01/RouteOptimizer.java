package javaassigmnet01;

import java.util.ArrayList;
import java.util.List;

public class RouteOptimizer {
    private final CityManager cityManager;
    private final DistanceManager distanceManager;
    
    // Route class is now a standalone class
    
    public RouteOptimizer(CityManager cityManager, DistanceManager distanceManager) {
        this.cityManager = cityManager;
        this.distanceManager = distanceManager;
    }
    
    // Helper method to calculate total distance of a route
    private int calculateRouteDistance(List<String> cities) {
        int totalDistance = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            int distance = distanceManager.getDistance(cities.get(i), cities.get(i + 1));
            if (distance <= 0) {
                return -1; // Invalid route
            }
            totalDistance += distance;
        }
        return totalDistance;
    }
    
    // Find all possible routes (limited to 4 cities)
    private List<Route> findAllRoutes(String source, String dest, int maxStops) {
        List<Route> routes = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        currentPath.add(source);
        
        findRoutes(source, dest, currentPath, routes, maxStops);
        return routes;
    }
    
    // Recursive helper to find routes
    private void findRoutes(String current, String dest, List<String> path, 
            List<Route> routes, int maxStops) {
        
        if (path.size() > maxStops + 1) {  // +1 because path includes source
            return;
        }
        
        if (current.equals(dest) && path.size() > 1) {
            int distance = calculateRouteDistance(path);
            if (distance > 0) {
                routes.add(new Route(new ArrayList<>(path), distance));
            }
            return;
        }
        
        var allCities = cityManager.getAllCities();
        for (String nextCity : allCities) {
            if (!path.contains(nextCity)) {  // Avoid cycles
                int distance = distanceManager.getDistance(current, nextCity);
                if (distance > 0) {  // If there's a valid connection
                    path.add(nextCity);
                    findRoutes(nextCity, dest, path, routes, maxStops);
                    path.remove(path.size() - 1);  // Backtrack
                }
            }
        }
    }
    
    // Find the shortest route between two cities
    public Route findBestRoute(String source, String dest) {
        // First try direct route
        int directDistance = distanceManager.getDistance(source, dest);
        if (directDistance > 0) {
            var directPath = new ArrayList<String>();
            directPath.add(source);
            directPath.add(dest);
            Route directRoute = new Route(directPath, directDistance);
            
            // Try to find better routes with up to 2 intermediate stops
            var allRoutes = findAllRoutes(source, dest, 3);
            
            // Add direct route to all routes
            allRoutes.add(directRoute);
            
            // Find shortest route
            Route bestRoute = null;
            int minDistance = Integer.MAX_VALUE;
            
            for (Route route : allRoutes) {
                if (route.totalDistance < minDistance) {
                    minDistance = route.totalDistance;
                    bestRoute = route;
                }
            }
            
            return bestRoute;
        }
        
        // If no direct route, try routes with intermediate stops
        List<Route> routes = findAllRoutes(source, dest, 3);
        if (!routes.isEmpty()) {
            Route bestRoute = routes.get(0);
            for (Route route : routes) {
                if (route.totalDistance < bestRoute.totalDistance) {
                    bestRoute = route;
                }
            }
            return bestRoute;
        }
        
        return null;  // No route found
    }
}
