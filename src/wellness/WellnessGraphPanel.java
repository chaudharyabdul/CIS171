/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Custom panel for drawing wellness data visualization
public class WellnessGraphPanel extends JPanel {
    private ArrayList<WellnessEntry> entries;
    
    // Constructor for graph panel
    public WellnessGraphPanel(ArrayList<WellnessEntry> entries) {
        this.entries = entries;
        setPreferredSize(new Dimension(400, 200));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Wellness Score Trend"));
    }
    
    // Updates the entries for graph
    public void updateEntries(ArrayList<WellnessEntry> entries) {
        this.entries = entries;
        repaint();
    }
    
    // Custom paint method for drawing graph
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (entries == null || entries.isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString("No data to display", 150, 100);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth() - 40;
        int height = getHeight() - 40;
        int xStart = 20, yStart = 20;
        
        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(xStart, yStart + height, xStart + width, yStart + height); // X-axis
        g2d.drawLine(xStart, yStart, xStart, yStart + height); // Y-axis
        
        // Draw data points and lines
        if (entries.size() > 1) {
            int xStep = width / (entries.size() - 1);
            int prevX = xStart;
            int prevY = yStart + height - (int)((entries.get(0).calculateImpactScore() / 10.0) * height);
            
            g2d.setColor(Color.BLUE);
            g2d.fillOval(prevX - 3, prevY - 3, 6, 6);
            
            for (int i = 1; i < entries.size(); i++) {
                int x = xStart + (i * xStep);
                int y = yStart + height - (int)((entries.get(i).calculateImpactScore() / 10.0) * height);
                
                // Draw line
                g2d.setColor(Color.RED);
                g2d.drawLine(prevX, prevY, x, y);
                
                // Draw point
                g2d.setColor(Color.BLUE);
                g2d.fillOval(x - 3, y - 3, 6, 6);
                
                prevX = x;
                prevY = y;
            }
        }
        
        // Draw labels
        g2d.setColor(Color.BLACK);
        g2d.drawString("Entries", width / 2, yStart + height + 15);
        g2d.rotate(-Math.PI / 2);
        g2d.drawString("Score", -height / 2 - 20, 15);
    }
}