/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author imshi
 */

import Swipe.Swipe;
import User.User;
import File.FileHandler;
import Auth.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private List<String> currentUserImages;
    private Random random = new Random();
    private final Authentication auth;
    private final FileHandler fileHandler;

    public SwipeUI(Swipe swipe, Authentication auth, FileHandler fileHandler) {
        this.swipe = swipe;
        this.auth = auth;
        this.fileHandler = fileHandler;
        initializeUI();
        startImageTransition();
    }


    private void initializeUI() {
        setTitle("Swipe Users");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 182, 193));
        setLayout(new BorderLayout(10, 10));

        JPanel contentPanel = new JPanel(new BorderLayout(30, 30));
        contentPanel.setOpaque(false);

        userImageLabel = new JLabel();
        userImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userImageLabel.setVerticalAlignment(SwingConstants.TOP);
        userImageLabel.setSize(new Dimension(600, 800));
        contentPanel.add(userImageLabel, BorderLayout.WEST);

        JPanel infoPanel = new JPanel(new BorderLayout(10, 10));
        infoPanel.setOpaque(false);

        userProfileTextPane = new JTextPane();
        userProfileTextPane.setContentType("text/html");
        userProfileTextPane.setEditable(false);
        userProfileTextPane.setFocusable(false);
        userProfileTextPane.setSize(new Dimension(600, 500));
        userProfileTextPane.setBackground(new Color(255, 182, 193));
        infoPanel.add(userProfileTextPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 120));
        buttonPanel.setOpaque(false);
        yesButton = createButton("Yes", 'z');
        noButton = createButton("No", 'x');
        stopButton = createButton("Stop Swiping", 'c');
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(stopButton);
        infoPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(infoPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        displayCurrentUser();
        addKeyBindings();
    }

    private JButton createButton(String text, char key) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(243, 58, 106));
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(255, 105, 180));
                } else {
                    g.setColor(getBackground());
                }
                g.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(getBackground());
                g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
        try {
            InputStream heyamFontInputStream = getClass().getResourceAsStream("/resources/heyam.ttf");
            Font heyamFont = Font.createFont(Font.TRUETYPE_FONT, heyamFontInputStream).deriveFont(28f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(heyamFont);
            button.setFont(heyamFont);
        } catch (Exception e) {
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            e.printStackTrace();
        }
        button.setPreferredSize(new Dimension(200, 60));
        button.setBackground(new Color(255, 105, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Yes")) {
                    swipe.selectUser(true);
                } else if (text.equals("No")) {
                    swipe.selectUser(false);
                } else if (text.equals("Stop Swiping")) {
                    swipe.saveSelectedUsers();
                    dispose();
                    new HomeUI(auth, fileHandler).setVisible(true); 
                }
                displayCurrentUser();
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(243, 58, 106));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 105, 180));
                button.repaint();
            }
        });

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == key) {
                    button.doClick();
                }
            }
        });

        return button;
    }

    private void addKeyBindings() {
        Action yesAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesButton.doClick();
            }
        };
        Action noAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noButton.doClick();
            }
        };
        Action stopAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.doClick();
            }
        };

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('z'), "yesAction");
        getRootPane().getActionMap().put("yesAction", yesAction);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('x'), "noAction");
        getRootPane().getActionMap().put("noAction", noAction);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('s'), "stopAction");
        getRootPane().getActionMap().put("stopAction", stopAction);
    }

    private void displayCurrentUser() {
        User currentUser = swipe.getCurrentUser();
        if (currentUser != null) {
            userProfileTextPane.setText(formatUserProfile(currentUser));
            currentUserImages = generateRandomImagePaths();
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
        StringBuilder interests = new StringBuilder();
        for (String interest : user.getProfile().getInterests()) {
            interests.append("<li>").append(interest).append("</li>");
        }
        return "<html><body style='font-size:16px; text-align: left;'>"
                + "<div style='text-align: center; font-weight: bold; font-size: 28px;'>" + user.getUsername() + "</div>"
                + "<br><b>Age:</b> " + user.getProfile().getAge() + "<br><br>"
                + "<b>Gender:</b> " + user.getProfile().getGender() + "<br><br>"
                + "<b>Location:</b> " + user.getProfile().getLocation() + "<br><br>"
                + "<b>Bio:</b> " + user.getProfile().getBio() + "<br><br>"
                + "<b>Interests:</b><ul>" + interests.toString() + "</ul>"
                + "</body></html>";
    }

    private List<String> generateRandomImagePaths() {
        List<String> imagePaths = new ArrayList<>();
        int totalSets = 22; 
        List<Integer> availableSets = new ArrayList<>();
        for (int i = 0; i < totalSets; i++) {
            availableSets.add(i * 3 + 1);
        }

        while (!availableSets.isEmpty() && imagePaths.size() < 3) {
            int setIndex = availableSets.remove(random.nextInt(availableSets.size())); 
            for (int i = 0; i < 3; i++) {
                imagePaths.add("/resources/" + (setIndex + i) + ".jpg");
            }
        }
        return imagePaths;
    }

    private void updateImage() {
        if (currentUserImages != null && !currentUserImages.isEmpty()) {
            String imagePath = currentUserImages.get(imageIndex);
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(600, 800, Image.SCALE_SMOOTH);
                userImageLabel.setIcon(new ImageIcon(scaledImage));
                userImageLabel.setText("");
            } else {
                userImageLabel.setText("Image not found");
                userImageLabel.setIcon(null);
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
        }, 1000, 1000); 
    }

    private void stopImageTransition() {
        if (timer != null) {
            timer.cancel();
        }
    }
}

