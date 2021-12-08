package com.example.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class StringUtils {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;
    private static final int MAX_LENGTH = 5;
    private static final String START_STR_OF_LOGGER = "Got browser profile options from settings file:";

    public static String getVariant(String str) {
        return str.substring(str.indexOf(" ") + 1);
    }

    public static String getId(String str) {
        return str.substring(str.indexOf("=") + 1);
    }

    public static String getProjectName() {
        String projectName = System.getProperty("user.dir");
        return projectName.substring(projectName.lastIndexOf('\\') + 1, projectName.length());
    }

    public static String generateRandomText() {
        StringBuilder randomText = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(MAX_LENGTH) + 1; i++) {
            if (RANDOM.nextBoolean()) {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a'));
            } else {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'A'));
            }
        }
        return randomText.toString();
    }

    public static String getLogOfTest(String logFile) {
        StringBuilder logText = new StringBuilder();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    logText.append(line).append("\n");
                    if (line.contains(START_STR_OF_LOGGER)) {
                        logText.setLength(0);
                        logText.append(line).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(logText);
    }
}
