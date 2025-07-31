/**
* Abdul Chaudhary
* CIS171
* July 27, 2025
* Final Project: Personal Wellness Tracker
*/

package wellness;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

// Main class with main method
public class WellnessManager extends JFrame implements ActionListener {
    
    // GUI Components
    private User currentUser;
    private JLabel lblStatus;
    private JTextArea txtAnalytics;
    private WellnessGraphPanel graphPanel;
    private JTextField txtName, txtAge, txtExercise, txtSleep, txtMood, txtStress, txtNote;
    private JButton btnCreateUser, btnLoadUser, btnSaveUser, btnAddPhysical, btnAddMental, btnRefresh;
    
    private static final String DATA_FILE = "data/wellness_data.txt";
    
    // Constructor to setup GUI
    public WellnessManager() {
        initializeGUI();
        tryLoadUser();
    }
    
    // Initialize GUI components and layout
    private void initializeGUI() {
        setTitle("Personal Wellness Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create panels
        JPanel topPanel = createUserPanel();
        JPanel centerPanel = createEntryPanel();
        JPanel bottomPanel = createAnalyticsPanel();
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // pack(); // Was squishing the User name
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Creates user management panel
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("User Management"));
        
        panel.add(new JLabel("Name:"));
        txtName = new JTextField(10);
        panel.add(txtName);
        
        panel.add(new JLabel("Age:"));
        txtAge = new JTextField(5);
        panel.add(txtAge);
        
        btnCreateUser = new JButton("Create User");
        btnCreateUser.addActionListener(this);
        panel.add(btnCreateUser);
        
        btnLoadUser = new JButton("Load User");
        btnLoadUser.addActionListener(this);
        panel.add(btnLoadUser);
        
        btnSaveUser = new JButton("Save User");
        btnSaveUser.addActionListener(this);
        panel.add(btnSaveUser);
        
        lblStatus = new JLabel("No user loaded");
        panel.add(lblStatus);
        
        return panel;
    }
    
    // Creates entry input panel
    private JPanel createEntryPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Physical entry panel
        JPanel physicalPanel = new JPanel(new GridBagLayout());
        physicalPanel.setBorder(BorderFactory.createTitledBorder("Add Physical Entry"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0;
        physicalPanel.add(new JLabel("Exercise (min):"), gbc);
        gbc.gridx = 1;
        txtExercise = new JTextField(10);
        physicalPanel.add(txtExercise, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        physicalPanel.add(new JLabel("Sleep (hrs):"), gbc);
        gbc.gridx = 1;
        txtSleep = new JTextField(10);
        physicalPanel.add(txtSleep, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnAddPhysical = new JButton("Add Physical Entry");
        btnAddPhysical.addActionListener(this);
        physicalPanel.add(btnAddPhysical, gbc);
        
        // Mental entry panel
        JPanel mentalPanel = new JPanel(new GridBagLayout());
        mentalPanel.setBorder(BorderFactory.createTitledBorder("Add Mental Entry"));
        gbc = new GridBagConstraints();
        
        gbc.gridx = 0; gbc.gridy = 0;
        mentalPanel.add(new JLabel("Mood (1-10):"), gbc);
        gbc.gridx = 1;
        txtMood = new JTextField(10);
        mentalPanel.add(txtMood, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mentalPanel.add(new JLabel("Stress (1-10):"), gbc);
        gbc.gridx = 1;
        txtStress = new JTextField(10);
        mentalPanel.add(txtStress, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnAddMental = new JButton("Add Mental Entry");
        btnAddMental.addActionListener(this);
        mentalPanel.add(btnAddMental, gbc);
        
        // Note panel
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setBorder(BorderFactory.createTitledBorder("Journal Note"));
        txtNote = new JTextField();
        notePanel.add(txtNote, BorderLayout.CENTER);
        
        mainPanel.add(physicalPanel);
        mainPanel.add(mentalPanel);
        
        JPanel entryContainer = new JPanel(new BorderLayout());
        entryContainer.add(mainPanel, BorderLayout.CENTER);
        entryContainer.add(notePanel, BorderLayout.SOUTH);
        
        return entryContainer;
    }
    
    // Creates analytics and graph panel
    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        txtAnalytics = new JTextArea(3, 50);
        txtAnalytics.setEditable(false);
        txtAnalytics.setBorder(BorderFactory.createTitledBorder("Analytics"));
        
        graphPanel = new WellnessGraphPanel(null);
        
        btnRefresh = new JButton("Refresh Analytics");
        btnRefresh.addActionListener(this);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRefresh);
        
        panel.add(txtAnalytics, BorderLayout.NORTH);
        panel.add(graphPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Handles all button click events
    // Implements ActionListener interface requirement
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnCreateUser) {
                handleCreateUser();
            } else if (e.getSource() == btnLoadUser) {
                handleLoadUser();
            } else if (e.getSource() == btnSaveUser) {
                handleSaveUser();
            } else if (e.getSource() == btnAddPhysical) {
                handleAddPhysical();
            } else if (e.getSource() == btnAddMental) {
                handleAddMental();
            } else if (e.getSource() == btnRefresh) {
                refreshAnalytics();
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
        }
    }
    
    // Handles user creation with input validation
    private void handleCreateUser() {
        String name = txtName.getText().trim();
        String ageText = txtAge.getText().trim();
        
        // Input validation
        if (name.isEmpty()) {
            showError("Name cannot be empty");
            return;
        }
        
        try {
            int age = Integer.parseInt(ageText);
            if (!AnalyticsEngine.isValidRange(age, 1, 150)) {
                showError("Age must be between 1 and 150");
                return;
            }
            
            currentUser = new User(name, age);
            lblStatus.setText("User created: " + name);
            refreshAnalytics();
            
        } catch (NumberFormatException e) {
            showError("Invalid age format");
        }
    }
    
    // Handles user loading from file
    private void handleLoadUser() {
        User loaded = User.loadFromFile(DATA_FILE);
        if (loaded != null) {
            currentUser = loaded;
            txtName.setText(loaded.getStrName());
            txtAge.setText(String.valueOf(loaded.getIntAge()));
            lblStatus.setText("User loaded: " + loaded.getStrName());
            refreshAnalytics();
        } else {
            showError("Could not load user data");
        }
    }
    
    // Handles user saving to file
    private void handleSaveUser() {
        if (currentUser == null) {
            showError("No user to save");
            return;
        }
        
        try {
            // Ensure data directory exists
            new java.io.File("data").mkdirs();
            currentUser.saveToFile(DATA_FILE);
            lblStatus.setText("User saved successfully");
        } catch (Exception e) {
            showError("Could not save user: " + e.getMessage());
        }
    }
    
    // Handles adding physical entry with validation
    private void handleAddPhysical() {
        if (currentUser == null) {
            showError("Create or load a user first");
            return;
        }
        
        try {
            double exercise = Double.parseDouble(txtExercise.getText());
            double sleep = Double.parseDouble(txtSleep.getText());
            String note = txtNote.getText().trim();
            
            // Validation
            if (!AnalyticsEngine.isValidRange(exercise, 0, 1440)) {
                showError("Exercise must be between 0-1440 minutes");
                return;
            }
            if (!AnalyticsEngine.isValidRange(sleep, 0, 24)) {
                showError("Sleep must be between 0-24 hours");
                return;
            }
            
            PhysicalEntry entry = new PhysicalEntry(LocalDate.now(), note, exercise, sleep);
            currentUser.addWellnessEntry(entry);
            
            // Clear fields
            txtExercise.setText("");
            txtSleep.setText("");
            txtNote.setText("");
            
            refreshAnalytics();
            
        } catch (NumberFormatException e) {
            showError("Invalid number format");
        }
    }
    
    // Handles adding mental entry with validation
    private void handleAddMental() {
        if (currentUser == null) {
            showError("Create or load a user first");
            return;
        }
        
        try {
            int mood = Integer.parseInt(txtMood.getText());
            int stress = Integer.parseInt(txtStress.getText());
            String note = txtNote.getText().trim();
            
            // Validation
            if (!AnalyticsEngine.isValidRange(mood, 1, 10)) {
                showError("Mood must be between 1-10");
                return;
            }
            if (!AnalyticsEngine.isValidRange(stress, 1, 10)) {
                showError("Stress must be between 1-10");
                return;
            }
            
            MentalEntry entry = new MentalEntry(LocalDate.now(), note, mood, stress);
            currentUser.addWellnessEntry(entry);
            
            // Clear fields
            txtMood.setText("");
            txtStress.setText("");
            txtNote.setText("");
            
            refreshAnalytics();
            
        } catch (NumberFormatException e) {
            showError("Invalid number format");
        }
    }
    
    // To refresh analytics display and graph 
    private void refreshAnalytics() {
        if (currentUser != null) {
            txtAnalytics.setText(currentUser.getAnalyticsSummary());
            graphPanel.updateEntries(currentUser.getWellnessEntries());
        } else {
            txtAnalytics.setText("No user data available");
            graphPanel.updateEntries(null);
        }
    }
    
    // Try to load user on startup
    private void tryLoadUser() {
        User loaded = User.loadFromFile(DATA_FILE);
        if (loaded != null) {
            currentUser = loaded;
            txtName.setText(loaded.getStrName());
            txtAge.setText(String.valueOf(loaded.getIntAge()));
            lblStatus.setText("User auto-loaded: " + loaded.getStrName());
            refreshAnalytics();
        }
    }
    
    // Error dialog
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default look and feel
            }
            new WellnessManager();
        });
    }
}