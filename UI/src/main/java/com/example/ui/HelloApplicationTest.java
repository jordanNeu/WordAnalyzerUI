package com.example.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// A Test class meant to test if our File Chooser will accept anything BUT .txt files
class HelloApplicationTest {
    @Test
    public void testDetectTxt() {
        // Creates a control boolean that will always be true, setting a guideline
        boolean result = HelloApplication.TxtDetector.detectTxtFile("example.txt");
        assertTrue(result);
        // Tests for a filename with no extension, returning False
        result = HelloApplication.TxtDetector.detectTxtFile("example");
        assertFalse(result);
        // Tests for a filename with a .url extension, returing False
        result = HelloApplication.TxtDetector.detectTxtFile("example.url");
        assertFalse(result);
        // Tests for a null entry, returning false
        result = HelloApplication.TxtDetector.detectTxtFile(null);
        assertFalse(result);
    }


    }
