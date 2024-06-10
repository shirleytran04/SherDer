package UI;

/**
 * @author imshi
 */

import User.User;
import Auth.Authentication;
import File.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MatchUI extends JFrame {
    private final List<User> matchedUsers;
    private final Authentication auth;
    private final FileHandler fileHandler;

    public MatchUI(List<User> matchedUsers, Authentication auth, FileHandler fileHandler) {
        this.matchedUsers = matchedUsers;
        this.auth = auth;
        this.fileHandler = fileHandler;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Matched Users");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(textArea);

        StringBuilder builder = new StringBuilder();
        for (User user : matchedUsers) {
            builder.append(formatUser(user)).append("\n\n");
        }
        textArea.setText(builder.toString());

        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Home");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setPreferredSize(new Dimension(1100, 80));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeUI(auth, fileHandler).setVisible(true);
            }
        });

        add(backButton, BorderLayout.SOUTH);
    }

    private String formatUser(User user) {
        return user.getUsername() + ": " + user.getProfile().getAge() + ", " +
                user.getProfile().getLocation() + ", " + user.getProfile().getBio() + ", " +
                String.join(", ", user.getProfile().getInterests()) + ", " + user.getProfile().getGender();
    }
}