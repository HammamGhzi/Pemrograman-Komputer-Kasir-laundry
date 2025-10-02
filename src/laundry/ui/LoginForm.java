/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package laundry.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private Timer clockTimer;
    
    public LoginForm() {
        initComponents();
        setupClock();
        setLocationRelativeTo(null);
        
        // Cek koneksi database saat aplikasi dimulai
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                checkDatabaseConnection();
            }
        });
    }
    
    private void initComponents() {
        setTitle("Ashvara Laundry - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setResizable(false);
        
        // Set background gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(74, 144, 226), 
                                                         0, getHeight(), new Color(143, 148, 251));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBounds(0, 30, 450, 100);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        // Title
        JLabel titleLabel = new JLabel("ASHVARA LAUNDRY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Management Login");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(220, 220, 220));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        
        // Time and Date Panel
        JPanel timePanel = new JPanel();
        timePanel.setOpaque(false);
        timePanel.setBounds(50, 150, 350, 60);
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(220, 220, 220));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        timePanel.add(timeLabel);
        timePanel.add(dateLabel);
        
        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(255, 255, 255, 230));
        loginPanel.setBounds(50, 230, 350, 280);
        loginPanel.setLayout(null);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactoryHelper.createRoundedBorder(15),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(new Color(60, 60, 60));
        userLabel.setBounds(30, 30, 100, 25);
        
        usernameField = new JTextField();
        usernameField.setBounds(30, 55, 290, 35);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setForeground(new Color(60, 60, 60));
        passLabel.setBounds(30, 110, 100, 25);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 135, 290, 35);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Login Button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(30, 190, 290, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(74, 144, 226));
        loginButton.setBorder(BorderFactoryHelper.createRoundedBorder(8));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect for login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(54, 124, 206));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(74, 144, 226));
            }
        });
        
        // Login action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Enter key listener for password field
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        
        // Add components to login panel
        loginPanel.add(userLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        
        // Footer
        JLabel footerLabel = new JLabel("© 2025 Ashvara Laundry Management System");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(220, 220, 220));
        footerLabel.setBounds(0, 540, 450, 20);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Add all components to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(timePanel);
        mainPanel.add(loginPanel);
        mainPanel.add(footerLabel);
        
        add(mainPanel);
    }
    
    private void setupClock() {
        // Update time every second
        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        clockTimer.start();
        updateTime(); // Initial update
    }
    
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date now = new Date();
        
        timeLabel.setText(timeFormat.format(now));
        dateLabel.setText(dateFormat.format(now));
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate credentials against database
        if (DatabaseConfig.validateLogin(username, password)) {
            showMessage("Login berhasil! Selamat datang, " + username + "!", 
                       "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Close login form and open main application
            clockTimer.stop();
            dispose();
            
            // TODO: Open main application window here
            // new MainForm(username).setVisible(true);
            
        } else {
            showMessage("Username atau password salah!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
    
    private void checkDatabaseConnection() {
        // Cek koneksi database saat aplikasi dimulai
        if (!DatabaseConfig.testConnection()) {
            showMessage("Tidak dapat terhubung ke database!\n\n" +
                       "Pastikan:\n" +
                       "1. XAMPP sudah berjalan\n" +
                       "2. MySQL service aktif\n" +
                       "3. Database 'laundry_db' sudah dibuat\n" +
                       "4. MySQL Connector sudah ditambahkan ke project", 
                       "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        } else if (!DatabaseConfig.isDatabaseReady()) {
            showMessage("Database belum siap!\n\n" +
                       "Silakan jalankan script SQL di phpMyAdmin terlebih dahulu.", 
                       "Database Setup Required", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
