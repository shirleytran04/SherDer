/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auth;

/**
 * Saves user login and profile into a file 
 * and reuses when run the app
 * 
 * @author imshi
 */

import File.FileHandler;
import User.User;
import User.UserProfile;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Authentication {
    private final FileHandler fileHandler;
    private final HashMap<String, User> users = new HashMap<>();    
    private User currentUser;

    public Authentication(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        loadUsers(fileHandler.readUsersFile());
    }

    private void loadUsers(List<String> fileLines) {
        if (fileLines.isEmpty()) {
            return;
        }
        
        for (String line : fileLines) {
            if (line.trim().isEmpty()) {
                continue; 
            }
            try {
                User user = User.fromString(line);
                users.put(user.getUsername(), user);
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid user data: " + line); 
            }
        }
    }

    public void saveUsers() {
        StringBuilder userLines = new StringBuilder();
        for (User user : users.values()) {
            userLines.append(user.toString()).append("\n");
        }
        fileHandler.writeUsersFile(userLines.toString());
    }

    public boolean register(String username, String password, int age, String location, String gender) {
        if (!users.containsKey(username)) {
            UserProfile profile = new UserProfile(age, location, "", new ArrayList<>(), gender);
            User newUser = new User(username, password, profile);
            users.put(username, newUser);
            saveUsers();  
            return true;
        }
        return false;
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}