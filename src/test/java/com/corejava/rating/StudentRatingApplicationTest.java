//package com.corejava.rating;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Spy;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintStream;
//import java.util.Scanner;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//class StudentRatingApplicationTest {
//    private static final String EOL =
//            System.getProperty("line.separator");
//    private PrintStream console;
//    private ByteArrayOutputStream bytes;
//
//    private InputStream sysInBackup;
//
//
//    @BeforeEach
//    public void setUp() {
//        bytes = new ByteArrayOutputStream();
//        console = System.out;
//        System.setOut(new PrintStream(bytes));
//
//        sysInBackup = System.in; // backup System.in to restore it later
//
//    }
//
//    @AfterEach
//    public void tearDown() {
//        System.setOut(console);
//        System.setIn(sysInBackup);
//    }
//
//
//    @Test
//    @DisplayName("Compute & display student average score per assignment category &overall  rating for assigned subject")
//    public void testRequirement_1() {
//        ByteArrayInputStream computeByStudent = new ByteArrayInputStream("1".getBytes());
//        System.setIn(computeByStudent);
//
////        ByteArrayInputStream studentName = new ByteArrayInputStream("Chaya".getBytes());
////        System.setIn(studentName);
//        StudentRatingApplication.main();
//
//        String expectedOutput = "*************************************************" + EOL +
//                "" + EOL +
//                " Select your Operation from below :" + EOL +
//                "1) Compute Score by Student Name Press 1" + EOL +
//                "2) Compute Score by Subject Name Press 2" + EOL +
//                "3) Basic Features Press 3" + EOL +
//                "*************************************************" + EOL +
//                "Select Student Name from below list -" + EOL +
//                "" + EOL +
//                "studentName = Chaya" + EOL +
//                "    subject          |Test|Quiz|Lab|Project|Overall(%)" + EOL +
//                "Computing Techniques | 0.0 | 4.0 | 0.0 | 0.0 | 4.0" + EOL +
//                "Electro Fields | 32.0 | 0.0 | 1.0 | 30.0 | 63.0" + EOL +
//                "" + EOL +
//                "*************************************************" + EOL +
//                "Do want to try again ? Press Y" + EOL +
//                "Good Bye !!" + EOL;
//
////        String expectedResult = (expectedOutput + EOL).replaceAll(System.lineSeparator(), "").trim();
////        String actualResult = bytes.toString().replaceAll(System.lineSeparator(), "").trim();
////        assertTrue(expectedResult.equals(actualResult));
//        assertEquals(expectedOutput, bytes.toString());
//
//    }
//
//}