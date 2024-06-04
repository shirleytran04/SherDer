/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 * Manages user interactions for editing their profile, 
 * displaying the current profile, and updating it based on user input, 
 * with changes saved through the Authentication class
 * 
 * @author imshi
 */

import Auth.Authentication;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileUI extends JFrame {
    private final Authentication auth;
    private JEditorPane currentProfileArea;
    private JTextField bioField;
    private JTextField interestsField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;

    public ProfileUI(Authentication auth) {
        this.auth = auth;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Edit Profile");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel currentProfileLabel = new JLabel("Current Profile:");
        currentProfileLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(currentProfileLabel, gbc);

        currentProfileArea = new JEditorPane();
        currentProfileArea.setContentType("text/html");
        currentProfileArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(currentProfileArea);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        JLabel bioLabel = new JLabel("Update your bio:");
        bioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(bioLabel, gbc);

        bioField = new JTextField(20);
        bioField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(bioField, gbc);

        JLabel interestsLabel = new JLabel("Enter your interests:");
        interestsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(interestsLabel, gbc);

        interestsField = new JTextField(20);
        interestsField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(interestsField, gbc);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(genderLabel, gbc);

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(maleRadioButton, gbc);
        gbc.gridx = 3;
        add(femaleRadioButton, gbc);

        JButton updateButton = new JButton("Update Profile");
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        updateButton.addActionListener(e -> updateProfile());
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(updateButton, gbc);

        displayCurrentProfile();
    }

    private void displayCurrentProfile() {
        User currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            StringBuilder profileBuilder = new StringBuilder();
            profileBuilder.append("<html>");
            profileBuilder.append("<b>Age:</b> ").append(currentUser.getProfile().getAge()).append("<br>");
            profileBuilder.append("<b>Location:</b> ").append(currentUser.getProfile().getLocation()).append("<br>");
            profileBuilder.append("<b>Bio:</b> ").append(currentUser.getProfile().getBio()).append("<br>");
            profileBuilder.append("<b>Gender:</b> ").append(currentUser.getProfile().getGender()).append("<br>");
            profileBuilder.append("<b>Interests:</b><br>");
            for (String interest : currentUser.getProfile().getInterests()) {
                profileBuilder.append("- ").append(interest).append("<br>");
            }
            profileBuilder.append("</html>");

            currentProfileArea.setText(profileBuilder.toString());

            if (currentUser.getProfile().getGender().equalsIgnoreCase("male")) {
                maleRadioButton.setSelected(true);
            } else {
                femaleRadioButton.setSelected(true);
            }
        }
    }

    private void updateProfile() {
        User currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String bio = bioField.getText().trim();
            String interestsInput = interestsField.getText().trim();

            if (!bio.isEmpty()) {
                currentUser.getProfile().setBio(bio);
            }

            if (!interestsInput.isEmpty()) {
                String[] interestsArray = interestsInput.split(",");
                List<String> interests = new ArrayList<>();
                for (String interest : interestsArray) {
                    interests.add(interest.trim());
                }
                currentUser.getProfile().setInterests(interests);
            }

            if (maleRadioButton.isSelected()) {
                currentUser.getProfile().setGender("male");
            } else if (femaleRadioButton.isSelected()) {
                currentUser.getProfile().setGender("female");
            }

            auth.saveUsers();

            JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            displayCurrentProfile();
        }
    }
}