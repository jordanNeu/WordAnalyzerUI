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
// Program that imports a .txt file and analyzes it for the individual words and then prints the duplicates in descending
// order, with a UI

public class HelloApplication extends Application {

    // HashMap container is created that will hold our commonly seen words
    private HashMap<String, Integer> wordCount = new HashMap<>();
    @Override
    public void start(Stage primaryStage) {

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
    public static void main(String[] args) {
        launch(args);
    }
    public class TxtDetector {
        public static boolean detectTxtFile(String fileName) {
            return fileName != null && fileName.toLowerCase().endsWith(".txt");
        }
    }
}