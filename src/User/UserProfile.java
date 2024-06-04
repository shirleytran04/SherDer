/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 * Manages user profile data, including age, location, bio, and interests, 
 * and includes methods for converting these details to and from a string format
 * 
 * @author imshi
 */

import java.util.Arrays;
import java.util.List;

public class UserProfile {
    private int age;
    private String location;
    private String bio;
    private List<String> interests;
    private String gender; 

    public UserProfile(int age, String location, String bio, List<String> interests, String gender) {
        this.age = age;
        this.location = location;
        this.bio = bio;
        this.interests = interests;
        this.gender = gender;  
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getInterests() {
        return interests;
    }

    public String getGender() {
        return gender;  
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public void setGender(String gender) {  
        this.gender = gender;
    }

    public static List<String> interestsFromString(String interestsString) {
        return Arrays.asList(interestsString.split(","));
    }

    @Override
    public String toString() {
        return age + "," + location + "," + bio + "," + String.join(",", interests) + "," + gender;
    }

    public static UserProfile fromString(String content) {
        String[] parts = content.split(",", 5);  
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid profile data format: " + content);
        }

        try {
            int age = Integer.parseInt(parts[0]);
            String location = parts[1];
            String bio = parts[2];
            List<String> interests = interestsFromString(parts[3]);
            String gender = parts[4]; 

            return new UserProfile(age, location, bio, interests, gender);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid profile data format: " + content, e);
        }
    }
}