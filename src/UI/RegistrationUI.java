/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 * Handles the registration process for new users, 
 * collecting essential information 
 * and verifying conditions before creating an account 
 * through the Authentication class
 * 
 * @author imshi
 */

import Auth.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JTextField locationField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private Authentication auth;

    public RegistrationUI(Authentication auth) {
        this.auth = auth;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel genderLabel = new JLabel("Gender:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();
        locationField = new JTextField();

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(genderLabel);
        panel.add(maleRadioButton);
        panel.add(new JLabel());
        panel.add(femaleRadioButton);
        panel.add(new JLabel()); 
        panel.add(registerButton);

        add(panel);
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        int age = 0;
        boolean validAge = false;

        try {
            age = Integer.parseInt(ageField.getText().trim());
            if (age < 18) {
                JOptionPane.showMessageDialog(this, "You are not old enough to register.");
            } else {
                validAge = true;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid age.");
        }

        String location = locationField.getText().trim();
        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Location cannot be empty.");
        }

        if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select your gender.");
        }

        if (validAge && !location.isEmpty() && (maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
            String gender = maleRadioButton.isSelected() ? "male" : "female";
            if (auth.register(username, password, age, location, gender)) {
                JOptionPane.showMessageDialog(this, "Registration successful.");
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username might be taken.");
            }
        }
    }
}