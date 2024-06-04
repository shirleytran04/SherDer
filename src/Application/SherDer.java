/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

/**
 * This is the entry point to run the application.
 * 
 * @author imshi
 */

import Auth.Authentication;
import File.FileHandler;
import UI.WelcomeMessageUI;

import javax.swing.SwingUtilities;

public class SherDer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileHandler fileHandler = new FileHandler("users.txt", "matched_users.txt");
            Authentication auth = new Authentication(fileHandler);
            new WelcomeMessageUI(auth, fileHandler).setVisible(true);
        });
    }
}