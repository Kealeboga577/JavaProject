package com.mycompany.quickchat.mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * QuickChat - User Registration and Login System
 */
class Login {

    public final Scanner scanner = new Scanner(System.in);

    // User registration fields
    public String userName;
    public String userPassword;
    public String countryCode;
    public String userPhoneNumber;
    public String firstName;
    public String lastName;

    // Login fields
    public String loginUserName;
    public String loginUserPassword;

    // Map of country name -> {country code, expected local digit length}
    // Local digit length = total digits AFTER the country code
    public static final Map<String, String[]> COUNTRY_CODE_MAP = new HashMap<>();
    static {
        COUNTRY_CODE_MAP.put("South Africa",     new String[]{"+27",  "9"});
        COUNTRY_CODE_MAP.put("United States",    new String[]{"+1",   "10"});
        COUNTRY_CODE_MAP.put("United Kingdom",   new String[]{"+44",  "10"});
        COUNTRY_CODE_MAP.put("Nigeria",          new String[]{"+234", "10"});
        COUNTRY_CODE_MAP.put("Kenya",            new String[]{"+254", "9"});
        COUNTRY_CODE_MAP.put("Zimbabwe",         new String[]{"+263", "9"});
        COUNTRY_CODE_MAP.put("India",            new String[]{"+91",  "10"});
        COUNTRY_CODE_MAP.put("Australia",        new String[]{"+61",  "9"});
        COUNTRY_CODE_MAP.put("Germany",          new String[]{"+49",  "10"});
        COUNTRY_CODE_MAP.put("France",           new String[]{"+33",  "9"});
        COUNTRY_CODE_MAP.put("Brazil",           new String[]{"+55",  "11"});
        COUNTRY_CODE_MAP.put("China",            new String[]{"+86",  "11"});
        COUNTRY_CODE_MAP.put("Canada",           new String[]{"+1",   "10"});
        COUNTRY_CODE_MAP.put("UAE",              new String[]{"+971", "9"});
        COUNTRY_CODE_MAP.put("Saudi Arabia",     new String[]{"+966", "9"});
    }

    // -------------------------
    // Validation Methods
    // -------------------------

    /**
     * Username must contain an underscore and be 5 characters or fewer.
     */
    boolean checkUserName(String username) {
        if (username.contains("_") && username.length() <= 5) {
            return true;
        } else {
            System.out.println("Username is not correctly formed. "
                    + "Please ensure your username contains an underscore and is no more than 5 characters.");
            return false;
        }
    }

    /**
     * Password must be at least 8 characters and include:
     * an uppercase letter, a lowercase letter, a digit, and a special character.
     */
    boolean checkPasswordComplexity(String password) {
        if (password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*")
                && password.matches(".*[!@#$%^&*()].*")) {
            return true;
        } else {
            System.out.println("Password is not correctly formatted. "
                    + "Please ensure your password has at least 8 characters, "
                    + "a capital letter, a lowercase letter, a number, and a special character.");
            return false;
        }
    }

    /**
     * Validates the cell phone number against the entered country code.
     *
     * Checks:
     * 1. The country code entered matches a known country in our list.
     * 2. The phone number actually starts with that country code.
     * 3. The local part (digits after the country code) contains only digits.
     * 4. The local part has the correct length for that country.
     */
    boolean checkCellPhoneNumber() {

        // Step 1: Look up the entered country code in the map
        String matchedCountry = null;
        int expectedLocalLength = 0;

        for (Map.Entry<String, String[]> entry : COUNTRY_CODE_MAP.entrySet()) {
            String[] details = entry.getValue(); // details[0] = code, details[1] = local digit length
            if (details[0].equals(countryCode)) {
                matchedCountry = entry.getKey();
                expectedLocalLength = Integer.parseInt(details[1]);
                break;
            }
        }

        if (matchedCountry == null) {
            System.out.println("Unrecognised country code \"" + countryCode + "\".");
            System.out.println("Supported codes: +27 (SA), +1 (US/CA), +44 (UK), +234 (NG), +254 (KE), "
                    + "+263 (ZW), +91 (IN), +61 (AU), +49 (DE), +33 (FR), "
                    + "+55 (BR), +86 (CN), +971 (UAE), +966 (SA).");
            return false;
        }

        // Step 2: Check that the phone number starts with the country code
        if (!userPhoneNumber.startsWith(countryCode)) {
            System.out.println("Phone number does not start with your country code (" + countryCode + ").");
            return false;
        }

        // Step 3: Extract the local part (everything after the country code)
        String localPart = userPhoneNumber.substring(countryCode.length());

        // Step 4: Local part must contain digits only
        if (!localPart.matches("\\d+")) {
            System.out.println("Phone number contains invalid characters. Only digits are allowed after the country code.");
            return false;
        }

        // Step 5: Local part must have the correct length for the country
        if (localPart.length() != expectedLocalLength) {
            System.out.println("Phone number is the wrong length for " + matchedCountry + ".");
            System.out.println("Expected " + expectedLocalLength + " digits after " + countryCode
                    + ", but got " + localPart.length() + ".");
            return false;
        }

        System.out.println("Phone number validated for " + matchedCountry + ": " + userPhoneNumber);
        return true;
    }

    /** Returns the username of the currently logged-in user, for use by Chat. */
    String getLoggedInUserName() {
        return loginUserName;
    }

    // -------------------------
    // Register Method
    // -------------------------

    boolean registerUser() {
        System.out.println("=====REGISTER=====");

        System.out.print("Please enter a username: ");
        userName = scanner.nextLine().trim();

        System.out.print("Please enter a password: ");
        userPassword = scanner.nextLine().trim();

        System.out.print("Please enter your country code (e.g. +27): ");
        countryCode = scanner.nextLine().trim();

        System.out.print("Please enter your cell phone number (including country code): ");
        userPhoneNumber = scanner.nextLine().trim();

        if (checkUserName(userName)
                && checkPasswordComplexity(userPassword)
                && checkCellPhoneNumber()) {

            System.out.print("Please enter your first name: ");
            firstName = scanner.nextLine().trim();

            System.out.print("Please enter your last name: ");
            lastName = scanner.nextLine().trim();

            System.out.println("Registration successful! Welcome, " + firstName + " " + lastName + ".");
            return true;
        } else {
            System.out.println("Registration failed. Please try again.");
            return false;
        }
    }

    // -------------------------
    // Login Method
    // -------------------------

    boolean login() {
        System.out.println("\n=====LOGIN=====");

        System.out.print("Please enter your username: ");
        loginUserName = scanner.nextLine().trim();

        System.out.print("Please enter your password: ");
        loginUserPassword = scanner.nextLine().trim();

        // Check credentials against registered user
        if (loginUserName.equals(userName) && loginUserPassword.equals(userPassword)) {
            System.out.println("Login successful! Welcome back, " + firstName + ".");
            return true;
        } else {
            System.out.println("Login failed. Incorrect username or password.");
            return false;
        }
    }
}

// -------------------------
// Message Class
// -------------------------

/**
 * Represents a single message sent from one user to another.
 */
class Message {

    public final String sender;
    public final String recipient;
    public final String messageText;
    public final String timestamp;
    public final int messageID;

    Message(int messageID, String sender, String recipient, String messageText) {
        this.messageID   = messageID;
        this.sender      = sender;
        this.recipient   = recipient;
        this.messageText = messageText;
        this.timestamp   = new java.util.Date().toString();
    }

    /** Returns a formatted summary of this message. */
    String getDetails() {
        return "[#" + messageID + "] " + sender + " -> " + recipient
                + " | " + timestamp + "\n   \"" + messageText + "\"";
    }

    String getSender()      { return sender; }
    String getRecipient()   { return recipient; }
    String getMessageText() { return messageText; }
    int    getMessageID()   { return messageID; }
}

// -------------------------
// Chat Class
// -------------------------

/**
 * Handles all messaging after a user has logged in.
 * Allows sending messages, viewing inbox, sent messages, searching, and deleting.
 */
class Chat {

    public final Scanner scanner = new Scanner(System.in);
    public final String currentUser;
    public final List<Message> allMessages = new ArrayList<>();
    public int nextMessageID = 1;

    Chat(String currentUser) {
        this.currentUser = currentUser;
    }

    /** Prompts the user to send a message. Validates recipient and message length. */
    void sendMessage() {
        System.out.println("\n=====SEND MESSAGE=====");

        System.out.print("Enter recipient username: ");
        String recipient = scanner.nextLine().trim();

        if (recipient.isEmpty()) {
            System.out.println("Recipient cannot be empty.");
            return;
        }
        if (recipient.equals(currentUser)) {
            System.out.println("You cannot send a message to yourself.");
            return;
        }

        System.out.print("Enter your message (max 250 characters): ");
        String text = scanner.nextLine();

        if (text.isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }
        if (text.length() > 250) {
            System.out.println("Message too long (" + text.length() + "/250 characters). Please shorten it.");
            return;
        }

        Message msg = new Message(nextMessageID++, currentUser, recipient, text);
        allMessages.add(msg);
        System.out.println("Message sent to " + recipient + "! (ID: #" + msg.getMessageID() + ")");
    }

    /** Displays all messages received by the current user. */
    void viewInbox() {
        System.out.println("\n=====INBOX=====");
        boolean found = false;
        for (Message msg : allMessages) {
            if (msg.getRecipient().equals(currentUser)) {
                System.out.println(msg.getDetails());
                found = true;
            }
        }
        if (!found) System.out.println("Your inbox is empty.");
    }

    /** Displays all messages sent by the current user. */
    void viewSentMessages() {
        System.out.println("\n=====SENT MESSAGES=====");
        boolean found = false;
        for (Message msg : allMessages) {
            if (msg.getSender().equals(currentUser)) {
                System.out.println(msg.getDetails());
                found = true;
            }
        }
        if (!found) System.out.println("You have not sent any messages.");
    }

    /** Searches all messages the current user is involved in for a keyword. */
    void searchMessages() {
        System.out.println("\n=====SEARCH MESSAGES=====");
        System.out.print("Enter a keyword to search: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        boolean found = false;
        for (Message msg : allMessages) {
            boolean involved = msg.getSender().equals(currentUser) || msg.getRecipient().equals(currentUser);
            if (involved && msg.getMessageText().toLowerCase().contains(keyword)) {
                System.out.println(msg.getDetails());
                found = true;
            }
        }
        if (!found) System.out.println("No messages found containing \"" + keyword + "\".");
    }

    /** Deletes one of the current user's sent messages by ID. */
    void deleteMessage() {
        System.out.println("\n=====DELETE MESSAGE=====");
        System.out.print("Enter the message ID to delete: ");
        String input = scanner.nextLine().trim();
        int id;

        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
            return;
        }

        Message toDelete = null;
        for (Message msg : allMessages) {
            if (msg.getMessageID() == id && msg.getSender().equals(currentUser)) {
                toDelete = msg;
                break;
            }
        }

        if (toDelete != null) {
            allMessages.remove(toDelete);
            System.out.println("Message #" + id + " deleted successfully.");
        } else {
            System.out.println("Message not found or you do not have permission to delete it.");
        }
    }

    /** Main chat menu loop — runs until the user quits. */
    void start() {
        System.out.println("\nWELCOME TO QUICKCHAT, " + currentUser.toUpperCase() + "!");

        boolean running = true;
        while (running) {
            System.out.println("\n=====QUICKCHAT MENU=====");
            System.out.println("1. Send a message");
            System.out.println("2. View inbox");
            System.out.println("3. View sent messages");
            System.out.println("4. Search messages");
            System.out.println("5. Delete a message");
            System.out.println("0. Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": sendMessage();      break;
                case "2": viewInbox();        break;
                case "3": viewSentMessages(); break;
                case "4": searchMessages();   break;
                case "5": deleteMessage();    break;
                case "0":
                    System.out.println("Goodbye, " + currentUser + "! See you next time.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 0-5.");
            }
        }
    }
}

// -------------------------
// Main Class
// -------------------------

public class QuickChat {

    public static void main(String[] args) {
        Login c = new Login();

        if (c.registerUser()) {
            if (c.login()) {
                Chat chat = new Chat(c.getLoggedInUserName());
                chat.start();
            } else {
                System.out.println("Login failed. Please restart and try again.");
            }
        } else {
            System.out.println("Registration failed. Login aborted.");
        }
    }
}