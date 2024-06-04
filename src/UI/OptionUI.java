/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 * Provides options for users to choose from, 
 * offering both instantly displayed 
 * and delayed output to enhance user experience
 * 
 * @author imshi
 */

import Auth.Authentication;
import File.FileHandler;
import Swipe.Swipe;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class OptionUI extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JButton editProfileButton;
    private JButton swipeMaleButton;
    private JButton swipeFemaleButton;
    private JButton swipeBothButton;
    private JButton viewMatchesButton;
    private JButton exitButton;
    private final Authentication auth;
    private final FileHandler fileHandler;
    private final Swipe swipe;

    public OptionUI(Authentication auth, FileHandler fileHandler) {
        this.auth = auth;
        this.fileHandler = fileHandler;
        this.swipe = new Swipe();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Options");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10));

        loginButton = createButton("Login", e -> new LoginUI(auth).setVisible(true));
        add(loginButton);

        registerButton = createButton("Register", e -> new RegistrationUI(auth).setVisible(true));
        add(registerButton);

        editProfileButton = createButton("Edit Profile", e -> {
            if (auth.getCurrentUser() != null) {
                new ProfileUI(auth).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(editProfileButton);

        swipeMaleButton = createButton("Swipe Males", e -> {
            if (auth.getCurrentUser() != null) {
                swipe.filterUsersByGender("male");
                new SwipeUI(swipe).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(swipeMaleButton);

        swipeFemaleButton = createButton("Swipe Females", e -> {
            if (auth.getCurrentUser() != null) {
                swipe.filterUsersByGender("female");
                new SwipeUI(swipe).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(swipeFemaleButton);

        swipeBothButton = createButton("Swipe Both", e -> {
            if (auth.getCurrentUser() != null) {
                swipe.filterUsersByGender(List.of("male", "female"));
                new SwipeUI(swipe).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(swipeBothButton);

        viewMatchesButton = createButton("View Matches", e -> {
            if (auth.getCurrentUser() != null) {
                List<User> matchedUsers = fileHandler.readMatchesFile().stream()
                    .map(User::fromString)
                    .collect(Collectors.toList());
                new MatchUI().displayViewMatches(matchedUsers);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(viewMatchesButton);

        exitButton = createButton("Exit", e -> dispose());
        add(exitButton);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(actionListener);
        return button;
    }
}