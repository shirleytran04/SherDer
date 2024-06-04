/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 * Represents a user in the SherDer application
 * each user has a username, password, and profile information
 * provides methods for displaying user information 
 * 
 * @author imshi
 */

import java.util.List;

public class User {
    private final String username;
    private final String password;
    private final UserProfile profile;

    public User(String username, String password, UserProfile profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void printUser() {
        System.out.println("----------------------------");
        System.out.println("User name: " + username);
        System.out.println("Age: " + profile.getAge());
        System.out.println("Location: " + profile.getLocation());
        System.out.println("Bio: " + profile.getBio());
        
        System.out.println("Interests: ");
        List<String> interests = profile.getInterests();
        for (String interest : interests) {
            System.out.println("- " + interest);
        }
        
        System.out.println("----------------------------");
    }

    @Override
    public String toString() {
        return username + "-" + password + "-" + profile.toString();
    }

    public static User fromString(String content) {
        String[] parts = content.split("-", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid user data format: " + content);
        }

        String username = parts[0];
        String password = parts[1];
        UserProfile profile = UserProfile.fromString(parts[2]);

        return new User(username, password, profile);
    }
}