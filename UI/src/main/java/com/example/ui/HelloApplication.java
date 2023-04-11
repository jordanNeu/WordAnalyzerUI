/**
 * 'HelloApplication' is a JavaFX program that is built upon a previous Java application
 * It's purpose is to import a .txt file and analyze it for the amount of duplicate
 * words found within the document.
 * The program prints the words along with their count in descending order, all
 * within a GUI
 *  * @author Jordan Neumann
 *  * @version 1.3
 *  * @since 2023-01-01
 */
package com.example.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Scanner;

import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
public class HelloApplication extends Application {
    private HashMap<String, Integer> wordCount = new HashMap<>();
    /**
     * The 'start' method prompts the UI to open allowing us to import a .txt file
     * and allowing us to interact with the application
     * @param primaryStage
     *
     * This class holds the majority of our program, it launches the GUI that allows us to
     * interact with the program
     */
    @Override
    public void start(Stage primaryStage) {
        /**
         * @param wordCount A HashMap that holds the amount of words counted by our program
         * @param fileLabel The Label for our file browser source
         * @param textArea This is the window that will display our text once a .txt file is selected
         * @param browseButton This is a button that will open a file-browser that accepts .txt files
         * @param analyzeButton This button will call the method to loop through our selected .txt
         * @param resultLabel This area begins the listing of our found-words
         * @param wordCountsArea This text area lists the count for each word found
         * @return null
         */
        wordCount = new HashMap<>();
        // Elements of our UI, including Buttons and Text Areas
        Label fileLabel = new Label("No File Selected");
        TextArea textArea = new TextArea();
        Button browseButton = new Button("Browse");
        Button analyzeButton = new Button("Analyze");
        Label resultLabel = new Label();
        VBox wordCountsArea = new VBox();
        // Action performed by clicking the browse button, file explorer will open allowing .txt files to be imported
        browseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*txt"));
            // Constraint that allows only .txt files to be chosen
            // As long as there is a file selected our program will run
            if (selectedFile != null) {
                fileLabel.setText(selectedFile.getName());
                try {
                    // While our scanner has a line to read it will, appending it to the text area we've created
                    Scanner scan = new Scanner(selectedFile);
                    while (scan.hasNextLine()) {
                        String word = scan.nextLine();
                        textArea.appendText(word + "\n");
                    }
                    scan.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // The Analyze button is a continuation on our Module 2 project, it initiated the splitting of our .txt file
        // into individual words that will be analyzed and checked for duplicates, adding these up as it iterates
        // through the document
        analyzeButton.setOnAction(e -> {
            wordCount.clear();
            String[] words = textArea.getText().split("\\W+");
            for (String word : words) {
                if (!wordCount.containsKey(word)) {
                    wordCount.put(word, 1);
                    // If it's the first time seeing a word it will log it.
                } else {
                    wordCount.put(word, wordCount.get(word) + 1);
                    // Otherwise if it's a duplicate it will count it towards existing logs
                }
            }
            resultLabel.setText("Total Words: " + words.length + ", unique words: " + wordCount.size());

            wordCountsArea.getChildren().clear();
            List<String> repeatedWords = new ArrayList<>();
            for (String word : wordCount.keySet()) {
                int count = wordCount.get(word);
                if (count > 1) {
                    repeatedWords.add(word);
                }
            }
            // Compares current word to previous word to see if they're duplicates and if their value is equal, higher.
            // or lower
            Collections.sort(repeatedWords, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int count1 = wordCount.get(o1);
                    int count2 = wordCount.get(o2);
                    return count2 - count1;
                }
            });
            // Prints word's with amount of times repeated
            for (String word : repeatedWords) {
                int count = wordCount.get(word);
                Label label = new Label(word + ": " + count);
                wordCountsArea.getChildren().add(label);
            }
        });
        // UI construction, appends the labels and buttons we've created to the UI
        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.getChildren().addAll(fileLabel, textArea, browseButton, analyzeButton, resultLabel, wordCountsArea);
        // Dictates the dimensions of the window that will appear once program is run
        Scene scene = new Scene(box, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Word Analyzer");
        primaryStage.show();
    }
    /**
     * This is the main method that will launch our GUI and allow us to use our application
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * 'TxtDetector' is connected to our test method and only checks to see if a .txt file
     * is present in our browseButton browser
     * @return fileName
     */
    public class TxtDetector {
        public static boolean detectTxtFile(String fileName) {
            return fileName != null && fileName.toLowerCase().endsWith(".txt");
        }
    }
}