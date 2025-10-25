package javaassigmnet01;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<String> path;
    public int totalDistance;

    public Route(List<String> path, int totalDistance) {
        this.path = new ArrayList<>(path);  // Create a copy of the path
        this.totalDistance = totalDistance;
    }

    @Override
    public String toString() {
        return String.join(" -> ", path) + " (" + totalDistance + " km)";
    }

    public List<String> getPath() {
        return new ArrayList<>(path);  // Return a copy to prevent modification
    }
}
