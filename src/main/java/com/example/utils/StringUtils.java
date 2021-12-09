package com.example.utils;

import aquality.selenium.core.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static com.example.utils.Const.*;

public class StringUtils {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;
    private static final int MAX_LENGTH = 5;

    public static String getVariant(String str) {
        return str.substring(str.indexOf(" ") + 1);
    }

    public static String getIdFromLink(String str) {
        return str.substring(str.indexOf("=") + 1);
    }

    public static String getProjectName() {
        String projectName = System.getProperty(USER_DIR);
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
            Logger.getInstance().info(FILE_ERROR + e);
            throw new IllegalArgumentException(FILE_ERROR, e);
        }
        return String.valueOf(logText);
    }

    public static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey(COMPUTER_NAME)) {
            return env.get(COMPUTER_NAME);
        } else {
            return env.getOrDefault(HOSTNAME, UNKNOWN_COMPUTER);
        }
    }
}