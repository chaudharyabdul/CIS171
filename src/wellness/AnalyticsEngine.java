/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import java.util.ArrayList;
import java.util.List;

// Utility class for wellness analytics calculations
public class AnalyticsEngine {

    // Calculates standard deviation of impact scores
    public static double calculateStandardDeviation(ArrayList<WellnessEntry> entries) {
        if (entries.isEmpty()) {
            return 0.0;
        }
        
        // Calculate mean
        double sum = 0.0;
        for (WellnessEntry entry : entries) {
            sum += entry.calculateImpactScore();
        }
        double mean = sum / entries.size();
        
        // Calculate variance
        double variance = 0.0;
        for (WellnessEntry entry : entries) {
            variance += Math.pow(entry.calculateImpactScore() - mean, 2);
        }
        variance /= entries.size();
        
        // Return standard deviation
        return Math.sqrt(variance);
    }
    
    // Analyzes sentiment of journal note
    public static String analyzeSentiment(String journalNote) {
        if (journalNote == null || journalNote.isEmpty()) {
            return "Neutral";
        }
        
        String[] positiveWords = {"good", "happy", "great", "positive", "relaxed"};
        String[] negativeWords = {"bad", "sad", "stressed", "negative", "tired"};
        
        String lowerNote = journalNote.toLowerCase();
        int positiveCount = 0, negativeCount = 0;
        
        for (String word : positiveWords) {
            if (lowerNote.contains(word)) positiveCount++;
        }
        for (String word : negativeWords) {
            if (lowerNote.contains(word)) negativeCount++;
        }
        
        if (positiveCount > negativeCount) return "Positive";
        if (negativeCount > positiveCount) return "Negative";
        return "Neutral";
    }
    
    // Generates a personalized tip based on recent wellness score trends
    public static String generatePersonalizedTip(ArrayList<WellnessEntry> entries) {
        // Require at least 5 entries to establish a meaningful trend
        if (entries.size() < 5) {
            return "Add more entries to receive personalized tips!";
        }

        // Get the last 5 entries from the list
        List<WellnessEntry> recentEntries = entries.subList(entries.size() - 5, entries.size());
        
        double firstHalfScore = 0;
        // Average of the first 2 of the last 5 entries
        for (int i = 0; i < 2; i++) {
            firstHalfScore += recentEntries.get(i).calculateImpactScore();
        }
        firstHalfScore /= 2.0;

        double secondHalfScore = 0;
        // Average of the last 3 of the last 5 entries
        for (int i = 2; i < 5; i++) {
            secondHalfScore += recentEntries.get(i).calculateImpactScore();
        }
        secondHalfScore /= 3.0;

        // Compare recent trend to older trend
        if (secondHalfScore > firstHalfScore + 0.5) {
            return "Your wellness scores are trending up recently. Keep up the great work!";
        } else if (secondHalfScore < firstHalfScore - 0.5) {
            return "Your recent scores have dipped. Try to focus on consistency in your daily routines.";
        } else {
            return "You are maintaining a consistent wellness level. Stay focused!";
        }
    }
    
    // Validates input range
    public static boolean isValidRange(double value, double min, double max) {
        return value >= min && value <= max;
    }
}