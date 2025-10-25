package javaassigmnet01;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles all file operations for the logistics system.
 * Created by: [Your Name]
 * Date: October 25, 2025
 */
public class FileManager {
    private static final String ROUTES_FILE = "routes.txt";
    private static final String DELIVERY_FILE = "delivery_records.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Save city list and distance matrix to file
     */
    public static void saveRouteData(ArrayList<String> cities, int[][] distances) {
        try (PrintWriter out = new PrintWriter(new FileWriter(ROUTES_FILE))) {
            // Save city list
            out.println("# Cities");
            for (String city : cities) {
                out.println(city);
            }
            
            // Save distance matrix
            out.println("# Distances");
            for (int i = 0; i < distances.length; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < distances[i].length; j++) {
                    line.append(distances[i][j]);
                    if (j < distances[i].length - 1) {
                        line.append(",");
                    }
                }
                out.println(line.toString());
            }
            
            System.out.println("Route data saved successfully.");
            
        } catch (IOException e) {
            System.out.println("Error saving route data: " + e.getMessage());
        }
    }
    
    /**
     * Load city list and distance matrix from file
     */
    public static boolean loadRouteData(ArrayList<String> cities, int[][] distances) {
        try (BufferedReader br = new BufferedReader(new FileReader(ROUTES_FILE))) {
            String line;
            boolean readingCities = false;
            boolean readingDistances = false;
            int row = 0;
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                
                if (line.equals("# Cities")) {
                    readingCities = true;
                    readingDistances = false;
                    continue;
                } else if (line.equals("# Distances")) {
                    readingCities = false;
                    readingDistances = true;
                    continue;
                }
                
                if (line.isEmpty()) continue;
                
                if (readingCities) {
                    cities.add(line);
                } else if (readingDistances) {
                    String[] values = line.split(",");
                    for (int col = 0; col < values.length; col++) {
                        distances[row][col] = Integer.parseInt(values[col]);
                    }
                    row++;
                }
            }
            
            System.out.println("Route data loaded successfully.");
            return true;
            
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - this is normal for first run
            return false;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading route data: " + e.getMessage());
            return false;
        }
    }
    
    // Save delivery records to file
    public static void saveDeliveryRecord(String sourceCity, String destCity, 
            int weight, String vehicleType, double cost, String status) {
        try (FileWriter fw = new FileWriter(DELIVERY_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String timestamp = dateFormat.format(new Date());
            String record = String.format("%s|%s|%s|%d|%s|%.2f|%s",
                    timestamp, sourceCity, destCity, weight, vehicleType, cost, status);
            out.println(record);
            
        } catch (IOException e) {
            System.out.println("Error saving delivery record: " + e.getMessage());
        }
    }
    
    // Read all delivery records
    public static ArrayList<String> readDeliveryRecords() {
        ArrayList<String> records = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(DELIVERY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet - this is normal for first run
            return records;
        } catch (IOException e) {
            System.out.println("Error reading delivery records: " + e.getMessage());
        }
        
        return records;
    }
    
    // Export delivery statistics to a report file
    public static void exportDeliveryStats(int total, int completed, 
            int inProgress, int pending, int remaining) {
        String filename = "delivery_stats_" + 
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("=== Delivery Statistics Report ===");
            out.println("Generated on: " + dateFormat.format(new Date()));
            out.println("--------------------------------");
            out.println("Total Deliveries: " + total);
            out.println("Completed Deliveries: " + completed);
            out.println("In Progress Deliveries: " + inProgress);
            out.println("Pending Deliveries: " + pending);
            out.println("Remaining Capacity: " + remaining);
            out.println("--------------------------------");
            
            System.out.println("Statistics exported to: " + filename);
            
        } catch (IOException e) {
            System.out.println("Error exporting statistics: " + e.getMessage());
        }
    }
}
