/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// POJO class representing a user with wellness entries
public class User {
    private String strName;
    private int intAge;
    private ArrayList<WellnessEntry> wellnessEntries; // Composition relationship

    // Constructor for User
    public User(String strName, int intAge) {
        this.strName = strName;
        this.intAge = intAge;
        this.wellnessEntries = new ArrayList<>();
    }

    // Adds wellness entry to user's collection
    public void addWellnessEntry(WellnessEntry entry) {
        this.wellnessEntries.add(entry);
    }

    // Gets analytics summary for user
    public String getAnalyticsSummary() {
        if (wellnessEntries.isEmpty()) {
            return "No entries yet. Add some to see analytics!";
        }
        
        // Sort entries by date to ensure consistency for trend analysis
        Collections.sort(wellnessEntries, Comparator.comparing(WellnessEntry::getDtmEntryDate));
        
        // Calculate base metrics
        double totalImpact = 0.0;
        for (WellnessEntry entry : wellnessEntries) {
            totalImpact += entry.calculateImpactScore();
        }
        double averageImpact = totalImpact / wellnessEntries.size();
        double stdDev = AnalyticsEngine.calculateStandardDeviation(wellnessEntries);
        
        // Analyze sentiment of the most recent journal note
        WellnessEntry latestEntry = wellnessEntries.get(wellnessEntries.size() - 1);
        String sentiment = AnalyticsEngine.analyzeSentiment(latestEntry.getStrJournalNote());
        
        String baseAnalytics = String.format("Entries: %d | Avg Score: %.2f | Std Dev: %.2f | Latest Note Sentiment: %s", 
                           wellnessEntries.size(), averageImpact, stdDev, sentiment);
        
        // Generate and append the personalized tip
        String personalizedTip = AnalyticsEngine.generatePersonalizedTip(wellnessEntries);
        
        return baseAnalytics + "\nTip: " + personalizedTip;
    }
    
    // Saves user data to file
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("USER," + strName + "," + intAge);
            writer.newLine();
            for (WellnessEntry entry : wellnessEntries) {
                writer.write(entry.toFileString());
                writer.newLine();
            }
        }
    }
    
    // Loads user data from file
    public static User loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line == null || !line.startsWith("USER,")) {
                return null;
            }
            
            String[] parts = line.split(",");
            User user = new User(parts[1], Integer.parseInt(parts[2]));
            
            while ((line = reader.readLine()) != null) {
                parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[1]);
                String note = parts[2];
                
                if (parts[0].equals("Physical")) {
                    user.addWellnessEntry(new PhysicalEntry(date, note, 
                        Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
                } else if (parts[0].equals("Mental")) {
                    user.addWellnessEntry(new MentalEntry(date, note, 
                        Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
                }
            }
            return user;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    // Getters
    public String getStrName() { return strName; }
    public int getIntAge() { return intAge; }
    public ArrayList<WellnessEntry> getWellnessEntries() { return wellnessEntries; }
}