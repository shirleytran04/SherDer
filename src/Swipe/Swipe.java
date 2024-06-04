/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swipe;

/**
 *
 * @author imshi
 */

import User.User;
import User.UserProfile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Swipe {
    private final List<User> randomUsers;
    private final List<User> selectedUsers;
    private final Random random = new Random();
    private int currentUserIndex;
    private int imageTransitionDelay = 1500;

    public Swipe() {
        randomUsers = new ArrayList<>();
        selectedUsers = new ArrayList<>();
        generateUserProfiles();
    }

    public void generateUserProfiles() {
        List<String> maleNames = List.of(
            "Levi Ackerman", "Gojo Satoru", "Ethan Kim", "Lucas Rodriguez", "James White",
            "Benjamin Clark", "Jack King", "Logan Green", "Henry Scott", "Sebastian Evans",
            "Alexander Moore", "William Turner", "Daniel Harris", "Matthew Adams", "Joseph Baker",
            "David Carter", "Michael Foster", "Christopher Allen", "Thomas Cook", "Andrew Brooks"
        );

        List<String> femaleNames = List.of(
            "Shirley Tran", "Taylor Tran", "Bellist Tran", "PhucPhuc Truong", "Mia Gonzalez",
            "Charlotte Yang", "Amelia Harris", "Harper Hall", "Evelyn Hill", "Abigail Scott",
            "Ella Wright", "Sofia Lewis", "Aria Robinson", "Grace Young", "Chloe Lee",
            "Scarlett Walker", "Zoey Hernandez", "Lily King", "Ellie Lopez", "Layla Green"
        );

        List<String> bios = List.of(
            "Specialist in digital marketing with a keen interest in the trends of social media and eco-friendly brand initiatives.",
            "Analyst in finance with a penchant for analyzing financial data and seeking out new opportunities in investments.",
            "Illustrator and graphic designer committed to transforming narratives into visual art.",
            "Scientist focused on environmental issues, specifically strategies to combat climate change.",
            "Dedicated dancer and teacher of tango, embracing dance as a powerful medium of expression.",
            "Innovator in the tech industry, concentrating on artificial intelligence applications in the health sector.",
            "Student of journalism with a focus on covering political events and the ethics of media.",
            "Author of historical novels, dedicated to uncovering and retelling neglected historical narratives.",
            "Architect with expertise in the conservation and restoration of ancient structures.",
            "Restaurateur and chef specializing in the fusion of Mediterranean cuisines, with a flair for culinary innovation.",
            "Biology student with a specialization in marine ecosystems, committed to the preservation of marine environments.",
            "Photographer with a focus on wildlife, dedicated to conservation efforts.",
            "Developer of educational apps aimed at enhancing learning for children through digital solutions.",
            "Entrepreneur leading a startup that develops sustainable energy technologies.",
            "Architect designing landscapes that are both aesthetic and environmentally sustainable.",
            "Researcher and academic lecturer specializing in the study of Celtic history and culture.",
            "Fitness coach inspiring healthy lifestyles.",
            "Fashion photographer capturing style trends.",
            "Bird watching, Wildlife tracking, Habitat restoration",
            "Cinematography, Societal activism, Ocean surfing",
            "Social networking, Environmental advocacy, Fashion design"
        );

        List<String> interests = List.of(
            "Social networking,Environmental advocacy,Fashion design",
            "Investing,Global travel,Biking",
            "Sketching,Japanese animation,Gaming",
            "Outdoor activities,Ecological documentaries,Botanical hobbies",
            "Performing arts,Musical enjoyment,Physical well-being",
            "High-tech developments,Artificial intelligence,Winter sports",
            "Literature,Audio shows,Community service",
            "Creative writing,Historical research,Board games",
            "Building design,Historical exploration,Artistic painting",
            "Gastronomy,Exploring new cultures,Oenology",
            "Underwater exploration,Aquatic life study,Photographic arts",
            "Nature photography,Animal conservation,Exploring new places",
            "Tech innovations,Educational theory,Literary enjoyment",
            "Technological advancements,Eco-friendly initiatives,Playing golf",
            "Horticulture,Structural design,Mindful yoga",
            "Historical studies,Literary pursuits,Outdoor trekking",
            "Apparel design,Business ventures,Digital networking",
            "Cinematography,Societal activism,Ocean surfing",
            "Innovative marketing strategies,Artistic creation",
            "Photographic journeys,Global exploration,Artistic expression"
        );

        for (int i = 0; i < 20; i++) {
            String name = maleNames.get(i);
            int age = random.nextInt(18, 60);
            String location = getRandomLocation();
            String bio = bios.get(random.nextInt(bios.size()));
            List<String> interestList = UserProfile.interestsFromString(interests.get(random.nextInt(interests.size())));

            User user = new User(name, "", new UserProfile(age, location, bio, interestList, "male"));
            randomUsers.add(user);
        }

        for (int i = 0; i < 20; i++) {
            String name = femaleNames.get(i);
            int age = random.nextInt(18, 60);
            String location = getRandomLocation();
            String bio = bios.get(random.nextInt(bios.size()));
            List<String> interestList = UserProfile.interestsFromString(interests.get(random.nextInt(interests.size())));

            User user = new User(name, "", new UserProfile(age, location, bio, interestList, "female"));
            randomUsers.add(user);
        }
    }

    private String getRandomLocation() {
        List<String> locations = List.of(
            "USA", "UK", "South Korea", "Australia", "Argentina", "Canada", "Ireland",
            "Italy", "Spain", "Mexico", "South Africa", "China", "New Zealand",
            "Scotland", "Vietnam", "Japan", "France", "Germany", "Brazil", "India"
        );
        return locations.get(random.nextInt(locations.size()));
    }

    public void filterUsersByGender(String gender) {
        currentUserIndex = 0;
        randomUsers.clear();
        generateUserProfiles();
        randomUsers.removeIf(user -> !user.getProfile().getGender().equalsIgnoreCase(gender));
    }

    public void filterUsersByGender(List<String> genders) {
        currentUserIndex = 0;
        randomUsers.clear();
        generateUserProfiles();
        randomUsers.removeIf(user -> !genders.contains(user.getProfile().getGender().toLowerCase()));
    }

    public User getCurrentUser() {
        if (currentUserIndex < randomUsers.size()) {
            return randomUsers.get(currentUserIndex);
        }
        return null;
    }

    public List<String> getCurrentUserImages() {
        int imageNumber = getCurrentImageNumber();
        List<String> images = new ArrayList<>();
        images.add("resources/" + imageNumber + ".jpg");
        images.add("resources/" + (imageNumber + 1) + ".jpg");
        images.add("resources/" + (imageNumber + 2) + ".jpg");
        return images;
    }

    public int getCurrentImageNumber() {
        return currentUserIndex * 3 + 1; 
    }

    public void selectUser(boolean isYes) {
        if (isYes) {
            selectedUsers.add(randomUsers.get(currentUserIndex));
        }
        currentUserIndex++;
    }

    public void saveSelectedUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("matched_users.txt"))) {
            for (User user : selectedUsers) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getImageTransitionDelay() {
        return imageTransitionDelay;
    }

    public void setImageTransitionDelay(int delay) {
        this.imageTransitionDelay = delay;
    }
}