package javaassigmnet01;

import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
    private static final String DELIVERY_FILE = "delivery_records.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
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
