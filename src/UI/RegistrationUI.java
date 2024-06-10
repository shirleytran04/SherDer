/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
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
    private final Authentication auth;

    public RegistrationUI(Authentication auth) {
        this.auth = auth;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register");
        setSize(650, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel genderLabel = new JLabel("Gender:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        ageField = new JTextField(20);
        locationField = new JTextField(20);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(ageLabel, gbc);

        gbc.gridx = 1;
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(locationLabel, gbc);

        gbc.gridx = 1;
        panel.add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(genderLabel, gbc);

        gbc.gridx = 1;
        panel.add(maleRadioButton, gbc);

        gbc.gridy++;
        panel.add(femaleRadioButton, gbc);

        gbc.gridy++;
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void handleRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        int age;
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
            return;
        }

        String location = locationField.getText().trim();
        if (location.isEmpty() || !location.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Location cannot be empty and must contain only letters.");
            return;
        }

        if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select your gender.");
            return;
        }

        if (validAge && !location.isEmpty() && !location.matches(".*\\d.*") && (maleRadioButton.isSelected() || femaleRadioButton.isSelected())) {
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            if (auth.register(username, password, age, location, gender)) {
                JOptionPane.showMessageDialog(this, "Registration successful.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username might be taken.");
            }
        }
    }
}