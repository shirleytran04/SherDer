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
import File.FileHandler;
import Swipe.Swipe;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuUI extends JFrame {
    private final Authentication auth;
    private final FileHandler fileHandler;
    private final Swipe swipe;

    public MainMenuUI(Authentication auth, FileHandler fileHandler) {
        this.auth = auth;
        this.fileHandler = fileHandler;
        this.swipe = new Swipe();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Main Menu");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 20, 20));

        JButton loginButton = createButton("Login", e -> new LoginUI(auth).setVisible(true));
        add(loginButton);

        JButton registerButton = createButton("Register", e -> new RegistrationUI(auth).setVisible(true));
        add(registerButton);

        JButton editProfileButton = createButton("Edit Profile", e -> {
            if (auth.getCurrentUser() != null) {
                new ProfileUI(auth).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(editProfileButton);

        JButton swipeButton = createButton("Swipe", e -> {
            if (auth.getCurrentUser() != null) {
                showSwipeGenderOptions();
            } else {
                JOptionPane.showMessageDialog(null, "Please login first.");
            }
        });
        add(swipeButton);

        JButton viewMatchesButton = createButton("View Matches", e -> {
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

        JButton exitButton = createButton("Exit", e -> dispose());
        add(exitButton);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(actionListener);
        return button;
    }

    private void showSwipeGenderOptions() {
        Object[] options = {"Males", "Females", "Both"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Which users would you like to swipe?",
            "Select Gender",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        switch (choice) {
            case 0:
                swipe.filterUsersByGender("male");
                new SwipeUI(swipe).setVisible(true);
                break;
            case 1:
                swipe.filterUsersByGender("female");
                new SwipeUI(swipe).setVisible(true);
                break;
            case 2:
                swipe.filterUsersByGender(List.of("male", "female"));
                new SwipeUI(swipe).setVisible(true);
                break;
            default:
                break;
        }
    }
}