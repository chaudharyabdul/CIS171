/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import java.time.LocalDate;

// Base POJO for business logic
public abstract class WellnessEntry {
    private LocalDate dtmEntryDate;
    private String strJournalNote;

    // Constructor for WellnessEntry
    public WellnessEntry(LocalDate dtmEntryDate, String strJournalNote) {
        this.dtmEntryDate = dtmEntryDate;
        this.strJournalNote = strJournalNote;
    }
    
    // Abstract method to calculate wellness impact score
    public abstract double calculateImpactScore();
    
    // Getters
    public LocalDate getDtmEntryDate() {
        return dtmEntryDate;
    }

    public String getStrJournalNote() {
        return strJournalNote;
    }
    
    // CSV formatted string
    public abstract String toFileString();
}