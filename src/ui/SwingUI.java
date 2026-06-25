package src.ui;

import src.model.Login;
import src.model.Passenger;
import src.service.AuthenticationService;
import src.service.PassengerService;
import src.service.AdminService;
import src.exception.*;
import src.util.EmailValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SwingUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    private AuthenticationService authService;
    private PassengerService passengerService;
    private AdminService adminService;
    private Login currentUser;
    
    // Login components
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    
    // Registration components
    private JTextField regNameField, regAgeField, regOriginField, regDestField;
    private JTextField regGenderField, regTicketField, regTrainField, regEmailField;
    private JPasswordField regPasswordField;
    
    // Admin components
    private JTable passengerTable;
    private DefaultTableModel tableModel;
    private JLabel statsLabel; // ✅ Store stats label reference
    
    // Passenger details
    private JLabel pnrLabel = new JLabel("-");
    private JLabel nameLabel = new JLabel("-");
    private JLabel ageLabel = new JLabel("-");
    private JLabel originLabel = new JLabel("-");
    private JLabel destLabel = new JLabel("-");
    private JLabel genderLabel = new JLabel("-");
    private JLabel priceLabel = new JLabel("-");
    private JLabel trainLabel = new JLabel("-");
    private JLabel emailLabel = new JLabel("-");
    
    public SwingUI() {
        authService = AuthenticationService.getInstance();
        passengerService = PassengerService.getInstance();
        adminService = AdminService.getInstance();
        
        adminService.initializeAdmin();
        createAndShowGUI();
    }
    
    private void createAndShowGUI() {
        frame = new JFrame("Passenger Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegistrationPanel(), "register");
        mainPanel.add(createAdminPanel(), "admin");
        mainPanel.add(createPassengerPanel(), "passenger");
        
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    
    // ==================== LOGIN PANEL ====================
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("PASSENGER MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Login to Your Account");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);
        
        loginEmailField = new JTextField(20);
        loginEmailField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(loginEmailField, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        
        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(loginPasswordField, gbc);
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 102, 204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setPreferredSize(new Dimension(100, 35));
        loginBtn.addActionListener(e -> handleLogin());
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);
        
        JLabel registerLink = new JLabel("Don't have an account? Register here");
        registerLink.setForeground(new Color(0, 102, 204));
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.setFont(new Font("Arial", Font.PLAIN, 14));
        registerLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clearRegistrationFields();
                cardLayout.show(mainPanel, "register");
            }
        });
        gbc.gridy = 5;
        panel.add(registerLink, gbc);
        
        JLabel adminInfo = new JLabel("Admin: admin@pms.com / admin123");
        adminInfo.setForeground(Color.GRAY);
        adminInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 6;
        panel.add(adminInfo, gbc);
        
        return panel;
    }
    
    // ==================== REGISTRATION PANEL ====================
    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 255, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel titleLabel = new JLabel("Passenger Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 153, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        String[] labels = {"Name:", "Age:", "Origin:", "Destination:", 
                          "Gender (M/F):", "Ticket Price (₹):", "Train Number:", 
                          "Email:", "Password:"};
        
        regNameField = new JTextField(25);
        regAgeField = new JTextField(25);
        regOriginField = new JTextField(25);
        regDestField = new JTextField(25);
        regGenderField = new JTextField(25);
        regTicketField = new JTextField(25);
        regTrainField = new JTextField(25);
        regEmailField = new JTextField(25);
        regPasswordField = new JPasswordField(25);
        
        JTextField[] fields = {
            regNameField, regAgeField, regOriginField, regDestField,
            regGenderField, regTicketField, regTrainField, regEmailField,
            regPasswordField
        };
        
        gbc.gridwidth = 1;
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(label, gbc);
            gbc.gridx = 1;
            fields[i].setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(fields[i], gbc);
        }
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(0, 153, 0));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 14));
        registerBtn.setPreferredSize(new Dimension(120, 35));
        registerBtn.addActionListener(e -> handleRegistration());
        
        JButton backBtn = new JButton("Back to Login");
        backBtn.setBackground(new Color(204, 204, 204));
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setPreferredSize(new Dimension(150, 35));
        backBtn.addActionListener(e -> {
            clearRegistrationFields();
            cardLayout.show(mainPanel, "login");
        });
        
        btnPanel.add(registerBtn);
        btnPanel.add(backBtn);
        
        gbc.gridy = labels.length + 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);
        
        return panel;
    }
    
    // ==================== ADMIN PANEL ====================
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 248, 240));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 248, 240));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(204, 102, 0));
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel userLabel = new JLabel("Welcome, Admin!");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.GRAY);
        topPanel.add(userLabel, BorderLayout.CENTER);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(204, 0, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "login");
        });
        topPanel.add(logoutBtn, BorderLayout.EAST);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        btnPanel.setBackground(new Color(255, 248, 240));
        
        JButton registerBtn = new JButton("Register Passenger");
        registerBtn.setBackground(new Color(0, 153, 0));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Arial", Font.BOLD, 13));
        registerBtn.addActionListener(e -> {
            clearRegistrationFields();
            cardLayout.show(mainPanel, "register");
        });
        
        JButton refreshBtn = new JButton("Refresh List");
        refreshBtn.setBackground(new Color(0, 102, 204));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Arial", Font.BOLD, 13));
        refreshBtn.addActionListener(e -> loadPassengers());
        
        // ✅ Store stats label reference
        statsLabel = new JLabel("Total Passengers: 0");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statsLabel.setForeground(Color.GRAY);
        
        btnPanel.add(registerBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(statsLabel);
        
        String[] columns = {"PNR", "Name", "Age", "Origin", "Destination", "Gender", "Price", "Train", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        passengerTable = new JTable(tableModel);
        passengerTable.setFont(new Font("Arial", Font.PLAIN, 12));
        passengerTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        passengerTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(passengerTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        loadPassengers();
        return panel;
    }
    
    // ==================== PASSENGER PANEL ====================
    private JPanel createPassengerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Passenger Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        topPanel.add(titleLabel, BorderLayout.WEST);
        
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.GRAY);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(204, 0, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "login");
        });
        topPanel.add(logoutBtn, BorderLayout.EAST);
        
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        
        pnrLabel = new JLabel("-");
        nameLabel = new JLabel("-");
        ageLabel = new JLabel("-");
        originLabel = new JLabel("-");
        destLabel = new JLabel("-");
        genderLabel = new JLabel("-");
        priceLabel = new JLabel("-");
        trainLabel = new JLabel("-");
        emailLabel = new JLabel("-");
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font valueFont = new Font("Arial", Font.PLAIN, 14);
        Color labelColor = new Color(80, 80, 80);
        Color valueColor = new Color(0, 0, 0);
        
        String[] fieldLabels = {"PNR Number:", "Name:", "Age:", "Origin:", 
                               "Destination:", "Gender:", "Ticket Price:", 
                               "Train Number:", "Email:"};
        JLabel[] valueLabels = {pnrLabel, nameLabel, ageLabel, originLabel, 
                               destLabel, genderLabel, priceLabel, trainLabel, emailLabel};
        
        for (int i = 0; i < fieldLabels.length; i++) {
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(fieldLabels[i]);
            label.setFont(labelFont);
            label.setForeground(labelColor);
            detailsPanel.add(label, gbc);
            
            gbc.gridx = 1;
            gbc.gridwidth = 2;
            valueLabels[i].setFont(valueFont);
            valueLabels[i].setForeground(valueColor);
            detailsPanel.add(valueLabels[i], gbc);
        }
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(detailsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ==================== HANDLERS ====================
    
    private void handleLogin() {
        String email = loginEmailField.getText().trim();
        String password = new String(loginPasswordField.getPassword()).trim();
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter email and password!");
            return;
        }
        
        try {
            Login login = authService.validateLogin(email, password);
            currentUser = login;
            
            if (login.getUserType().equals("Admin")) {
                cardLayout.show(mainPanel, "admin");
                loadPassengers();
                loginEmailField.setText("");
                loginPasswordField.setText("");
                JOptionPane.showMessageDialog(frame, "Welcome Admin!");
            } else {
                Passenger p = passengerService.findPassengerByEmail(email);
                if (p != null) {
                    updatePassengerDetails(p);
                    cardLayout.show(mainPanel, "passenger");
                    loginEmailField.setText("");
                    loginPasswordField.setText("");
                    JOptionPane.showMessageDialog(frame, "Welcome " + p.getPassengerName() + "!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Passenger details not found!");
                }
            }
            
        } catch (InvalidEmailException e) {
            JOptionPane.showMessageDialog(frame, "Invalid email format!\n" + EmailValidator.getEmailHelp());
        } catch (InvalidCredentialsException e) {
            JOptionPane.showMessageDialog(frame, "Invalid credentials!\nAdmin: admin@pms.com / admin123");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
    
    private void updatePassengerDetails(Passenger p) {
        if (p == null) return;
        
        pnrLabel.setText(p.getPnrNumber() != null ? p.getPnrNumber() : "-");
        nameLabel.setText(p.getPassengerName() != null ? p.getPassengerName() : "-");
        ageLabel.setText(p.getAge() > 0 ? String.valueOf(p.getAge()) : "-");
        originLabel.setText(p.getOrigin() != null ? p.getOrigin() : "-");
        destLabel.setText(p.getDestination() != null ? p.getDestination() : "-");
        genderLabel.setText(p.getGender() != null ? p.getGender() : "-");
        priceLabel.setText(p.getTicketPrice() > 0 ? "₹" + p.getTicketPrice() : "-");
        trainLabel.setText(p.getTrainNumber() != null ? p.getTrainNumber() : "-");
        emailLabel.setText(p.getEmail() != null ? p.getEmail() : "-");
        
        // Update welcome label
        try {
            Component[] components = ((JPanel) mainPanel.getComponent(3)).getComponents();
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    for (Component child : panel.getComponents()) {
                        if (child instanceof JLabel && ((JLabel) child).getText().equals("Welcome!")) {
                            ((JLabel) child).setText("Welcome, " + p.getPassengerName() + "!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }
    }
    
    private void handleRegistration() {
        try {
            String name = regNameField.getText().trim();
            String ageStr = regAgeField.getText().trim();
            String origin = regOriginField.getText().trim();
            String destination = regDestField.getText().trim();
            String gender = regGenderField.getText().trim();
            String ticketStr = regTicketField.getText().trim();
            String train = regTrainField.getText().trim();
            String email = regEmailField.getText().trim();
            String password = new String(regPasswordField.getPassword()).trim();
            
            if (name.isEmpty() || ageStr.isEmpty() || origin.isEmpty() || 
                destination.isEmpty() || gender.isEmpty() || ticketStr.isEmpty() || 
                train.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }
            
            int age = Integer.parseInt(ageStr);
            double ticket = Double.parseDouble(ticketStr);
            
            if (age < 1 || age > 150) {
                JOptionPane.showMessageDialog(frame, "Age must be between 1 and 150!");
                return;
            }
            
            if (ticket < 0) {
                JOptionPane.showMessageDialog(frame, "Ticket price cannot be negative!");
                return;
            }
            
            if (password.length() < 3) {
                JOptionPane.showMessageDialog(frame, "Password must be at least 3 characters!");
                return;
            }
            
            if (!EmailValidator.isValidEmail(email)) {
                JOptionPane.showMessageDialog(frame, "Invalid email format!\n" + EmailValidator.getEmailHelp());
                return;
            }
            
            if (authService.isEmailExists(email)) {
                JOptionPane.showMessageDialog(frame, "Email already registered!\nPlease use a different email.");
                return;
            }
            
            Passenger passenger = passengerService.registerPassenger(
                name, age, origin, destination, gender, ticket, train, email, password
            );
            
            JOptionPane.showMessageDialog(frame, 
                "Registration successful!\n" +
                "PNR: " + passenger.getPnrNumber() + "\n" +
                "Name: " + passenger.getPassengerName() + "\n" +
                "Email: " + passenger.getEmail()
            );
            
            clearRegistrationFields();
            
            if (currentUser != null && currentUser.getUserType().equals("Admin")) {
                cardLayout.show(mainPanel, "admin");
                loadPassengers();
            } else {
                cardLayout.show(mainPanel, "login");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Age and Ticket Price must be numbers!");
        } catch (InvalidEmailException e) {
            JOptionPane.showMessageDialog(frame, "Invalid email format!\n" + EmailValidator.getEmailHelp());
        } catch (DuplicateEmailException e) {
            JOptionPane.showMessageDialog(frame, "Email already registered!");
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
        }
    }
    
    // ✅ FIXED: Safe loadPassengers method
    private void loadPassengers() {
        tableModel.setRowCount(0);
        List<Passenger> passengers = passengerService.getAllPassengers();
        for (Passenger p : passengers) {
            tableModel.addRow(new Object[]{
                p.getPnrNumber(),
                p.getPassengerName(),
                p.getAge(),
                p.getOrigin(),
                p.getDestination(),
                p.getGender(),
                "₹" + p.getTicketPrice(),
                p.getTrainNumber(),
                p.getEmail()
            });
        }
        
        // ✅ Update stats label safely
        if (statsLabel != null) {
            statsLabel.setText("Total Passengers: " + passengers.size());
        }
    }
    
    private void clearRegistrationFields() {
        regNameField.setText("");
        regAgeField.setText("");
        regOriginField.setText("");
        regDestField.setText("");
        regGenderField.setText("");
        regTicketField.setText("");
        regTrainField.setText("");
        regEmailField.setText("");
        regPasswordField.setText("");
    }
}