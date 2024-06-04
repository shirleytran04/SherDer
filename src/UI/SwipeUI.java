/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 * User information copy from ChatGPT
 * Images took from internet and Pinterest
 * 
 * @author imshi
 */

import Swipe.Swipe;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SwipeUI extends JFrame {
    private final Swipe swipe;
    private JLabel userImageLabel;
    private JTextPane userProfileTextPane;
    private JButton yesButton;
    private JButton noButton;
    private JButton stopButton;
    private Timer timer;
    private int imageIndex = 0;

    public SwipeUI(Swipe swipe) {
        this.swipe = swipe;
        initializeUI();
        startImageTransition();
    }

    private void initializeUI() {
        setTitle("Swipe Users");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        yesButton = createButton("Yes");
        noButton = createButton("No");
        stopButton = createButton("Stop Swiping");
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(stopButton);

        userImageLabel = new JLabel();
        userImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userImageLabel.setPreferredSize(new Dimension(600, 400));

        userProfileTextPane = new JTextPane();
        userProfileTextPane.setEditable(false);
        userProfileTextPane.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(userProfileTextPane);

        add(buttonPanel, BorderLayout.NORTH);
        add(userImageLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        displayCurrentUser();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(e -> {
            if (text.equals("Yes")) {
                swipe.selectUser(true);
            } else if (text.equals("No")) {
                swipe.selectUser(false);
            } else {
                swipe.saveSelectedUsers();
                dispose();
            }
            displayCurrentUser();
        });
        return button;
    }

    private void displayCurrentUser() {
        User currentUser = swipe.getCurrentUser();
        if (currentUser != null) {
            userProfileTextPane.setText(formatUserProfile(currentUser));
            imageIndex = 0;  
            updateImage();
        } else {
            userProfileTextPane.setText("No more users to display.");
            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            stopImageTransition();
        }
    }

    private String formatUserProfile(User user) {
        return "<html><body style='font-size:18px;'>" +
                "<b>Name:</b> " + user.getUsername() + "<br><br>" +
                "<b>Age:</b> " + user.getProfile().getAge() + "<br><br>" +
                "<b>Location:</b> " + user.getProfile().getLocation() + "<br><br>" +
                "<b>Bio:</b> " + user.getProfile().getBio() + "<br><br>" +
                "<b>Interests:</b> " + String.join(", ", user.getProfile().getInterests()) + "<br><br>" +
                "<b>Gender:</b> " + user.getProfile().getGender() +
                "</body></html>";
    }

    private void updateImage() {
        User currentUser = swipe.getCurrentUser();
        if (currentUser != null) {
            List<String> images = swipe.getCurrentUserImages();
            if (images != null && !images.isEmpty()) {
                URL imageUrl = getClass().getClassLoader().getResource(images.get(imageIndex));
                if (imageUrl != null) {
                    userImageLabel.setIcon(new ImageIcon(imageUrl));
                    userImageLabel.setText("");
                } else {
                    userImageLabel.setText("Image not found");
                    userImageLabel.setIcon(null);
                }
            }
        }
    }

    private void startImageTransition() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                imageIndex = (imageIndex + 1) % 3;  
                SwingUtilities.invokeLater(() -> updateImage());
            }
        }, swipe.getImageTransitionDelay(), swipe.getImageTransitionDelay());
    }

    private void stopImageTransition() {
        if (timer != null) {
            timer.cancel();
        }
    }
}