/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker - JUnit Tests
*/

package wellness;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;

// JUnit test suite for wellness tracker business logic
public class WellnessTrackerTests {
    
    private PhysicalEntry physicalEntry;
    private MentalEntry mentalEntry;
    private ArrayList<WellnessEntry> entries;
    
    // Set up test data before each test
    @BeforeEach
    void setUp() {
        physicalEntry = new PhysicalEntry(LocalDate.now(), "Test note", 60.0, 8.0);
        mentalEntry = new MentalEntry(LocalDate.now(), "Happy note", 8, 3);
        entries = new ArrayList<>();
        entries.add(physicalEntry);
        entries.add(mentalEntry);
    }
    
    // Tests inheritance and polymorphism
    @Test
    void testCalculateImpactScore() {
        // Test physical entry calculation
        double physicalScore = physicalEntry.calculateImpactScore();
        assertEquals(9.0, physicalScore, 0.001, "Physical score should be 9.0 (1hr + 8hrs sleep)");
        assertTrue(physicalScore >= 0, "Physical score should be non-negative");
        assertTrue(physicalScore <= 10, "Physical score should be normalized to max 10");
        
        // Test mental entry calculation  
        double mentalScore = mentalEntry.calculateImpactScore();
        assertEquals(10.0, mentalScore, 0.001, "Mental score should be 10.0 (8-3+5=10)");
        assertTrue(mentalScore >= 0, "Mental score should be non-negative");
        assertTrue(mentalScore <= 10, "Mental score should be normalized to max 10");
        
        // Test polymorphic behavior
        assertNotEquals(physicalScore, mentalScore, "Different entry types should calculate differently");
    }
    
    // Tests mathematical calculations and edge cases
    @Test
    void testCalculateStandardDeviation() {
        // Test with populated list
        double stdDev = AnalyticsEngine.calculateStandardDeviation(entries);
        assertTrue(stdDev >= 0, "Standard deviation should be non-negative");
        assertEquals(0.5, stdDev, 0.001, "Standard deviation should be 0.5 for scores 9.0 and 10.0");
        
        // Test with empty list
        ArrayList<WellnessEntry> emptyList = new ArrayList<>();
        double emptyStdDev = AnalyticsEngine.calculateStandardDeviation(emptyList);
        assertEquals(0.0, emptyStdDev, "Empty list should return 0.0 standard deviation");
        
        // Test with single entry
        ArrayList<WellnessEntry> singleEntry = new ArrayList<>();
        singleEntry.add(physicalEntry);
        double singleStdDev = AnalyticsEngine.calculateStandardDeviation(singleEntry);
        assertEquals(0.0, singleStdDev, "Single entry should return 0.0 standard deviation");
        
        // Test with identical entries
        ArrayList<WellnessEntry> identicalEntries = new ArrayList<>();
        PhysicalEntry entry1 = new PhysicalEntry(LocalDate.now(), "Test", 60.0, 8.0);
        PhysicalEntry entry2 = new PhysicalEntry(LocalDate.now(), "Test", 60.0, 8.0);
        identicalEntries.add(entry1);
        identicalEntries.add(entry2);
        double identicalStdDev = AnalyticsEngine.calculateStandardDeviation(identicalEntries);
        assertEquals(0.0, identicalStdDev, "Identical scores should return 0.0 standard deviation");
    }
}