package UI;

/**
 * @author imshi
 */

import Auth.SessionManager;
import User.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MatchUI extends JFrame {
    private JTextArea matchTextArea;

    public MatchUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Match Viewer");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        matchTextArea = new JTextArea();
        matchTextArea.setEditable(false);
        matchTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(matchTextArea);

        JButton doneButton = new JButton("Done");
        doneButton.setFont(new Font("Arial", Font.BOLD, 24));
        doneButton.setPreferredSize(new Dimension(200, 50));
        doneButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(doneButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void displayMatches(List<User> matchedUsers) {
        if (matchedUsers == null || matchedUsers.isEmpty()) {
            matchTextArea.setText("No matches to display.");
            return;
        }

        StringBuilder matchesBuilder = new StringBuilder();
        for (int i = 1; i <= matchedUsers.size(); i++) {
            matchesBuilder.append("Lover ").append(i).append("\n");
            matchesBuilder.append(matchedUsers.get(i - 1).toString()).append("\n\n");
        }
        matchTextArea.setText(matchesBuilder.toString());
    }

    public void displayViewMatches(List<User> matchedUsers) {
        if (SessionManager.isLoggedIn()) {
            displayMatches(matchedUsers);
            setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Please login first.");
        }
    }
}