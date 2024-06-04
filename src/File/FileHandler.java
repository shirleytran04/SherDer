/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

/**
 *
 * @author imshi
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private final String usersFile;
    private final String matchesFile;

    public FileHandler(String usersFile, String matchesFile) {
        this.usersFile = usersFile;
        this.matchesFile = matchesFile;
    }

    public List<String> readUsersFile() {
        return readFile(usersFile);
    }

    public void writeUsersFile(String content) {
        writeFile(usersFile, content);
    }

    public List<String> readMatchesFile() {
        return readFile(matchesFile);
    }

    public void writeMatchesFile(String content) {
        writeFile(matchesFile, content);
    }

    private List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void writeFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}