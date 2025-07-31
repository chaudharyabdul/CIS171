/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import java.time.LocalDate;

// POJO class for physical wellness entries
public class PhysicalEntry extends WellnessEntry {
    private double dblExerciseMinutes;
    private double dblSleepHours;

    // Constructor for physical entry
    public PhysicalEntry(LocalDate dtmEntryDate, String strJournalNote, 
                        double dblExerciseMinutes, double dblSleepHours) {
        super(dtmEntryDate, strJournalNote);
        this.dblExerciseMinutes = dblExerciseMinutes;
        this.dblSleepHours = dblSleepHours;
    }

    // Calculates impact score based on exercise and sleep
    @Override
    public double calculateImpactScore() {
        double score = (dblExerciseMinutes / 60.0) + dblSleepHours;
        return Math.min(score, 10.0); // Normalize to 0-10 scale
    }
    
    @Override
    public String toFileString() {
        return "Physical," + getDtmEntryDate() + "," + getStrJournalNote() + "," 
               + dblExerciseMinutes + "," + dblSleepHours;
    }
    
    // Getters
    public double getDblExerciseMinutes() {
        return dblExerciseMinutes;
    }

    public double getDblSleepHours() {
        return dblSleepHours;
    }
}