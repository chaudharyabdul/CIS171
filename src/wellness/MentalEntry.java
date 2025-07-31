/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import java.time.LocalDate;

// POJO class for mental wellness entries
public class MentalEntry extends WellnessEntry {
    private int intMoodRating; // 1-10 scale
    private int intStressLevel; // 1-10 scale

    // Constructor for mental entry
    public MentalEntry(LocalDate dtmEntryDate, String strJournalNote, 
                      int intMoodRating, int intStressLevel) {
        super(dtmEntryDate, strJournalNote);
        this.intMoodRating = intMoodRating;
        this.intStressLevel = intStressLevel;
    }

    // Calculates impact score based on mood and stress
    @Override
    public double calculateImpactScore() {
        double score = intMoodRating - intStressLevel + 5;
        return Math.max(0, Math.min(score, 10.0));
    }
    
    @Override
    public String toFileString() {
        return "Mental," + getDtmEntryDate() + "," + getStrJournalNote() + "," 
               + intMoodRating + "," + intStressLevel;
    }
    
    // Getters
    public int getIntMoodRating() {
        return intMoodRating;
    }

    public int getIntStressLevel() {
        return intStressLevel;
    }
}