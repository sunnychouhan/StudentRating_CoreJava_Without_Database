package com.corejava.rating;

import com.corejava.rating.model.Assignment;
import com.corejava.rating.model.Student;
import com.corejava.rating.model.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DataLoader {
    private static final DataLoader INSTANCE = null;
    private HashMap<String, Double> assignmentCategory = new HashMap<>();
    private List<Student> students;

    private DataLoader() {
        //Initialize Category
        assignmentCategory.put("TEST", 40.0);
        assignmentCategory.put("PROJECT", 30.0);
        assignmentCategory.put("LAB", 10.0);
        assignmentCategory.put("QUIZ", 20.0);

        //Load Student data from file
        students = loadStudentData("data.txt");
    }

    public static final DataLoader getDataLoaderInstance() {
        if (INSTANCE == null)
            return new DataLoader();
        else
            return INSTANCE;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }


    public HashMap<String, Double> getAssignmentCategory() {
        return assignmentCategory;
    }

    public void setAssignmentCategory(HashMap<String, Double> assignmentCategory) {
        this.assignmentCategory = assignmentCategory;
    }

    private List<Student> loadStudentData(String fileName) {
        return getDataList(fileName)
                .stream()
                .map(this::getStudentDetails)
                .collect(Collectors.toList());
    }

    private Student getStudentDetails(String s) {
        String[] result = s.split("[|]+");
        String serialNumber = result[0];
        String name = result[1];
        String subject = result[2];
        String category = result[3];
        String submissionDate = result[4];
        String points = result[5];

        Assignment assignment1 = new Assignment(category, points, submissionDate);
        Subject subject1 = new Subject(assignment1, subject);

        Student student = new Student(subject1, name, serialNumber);
        return student;
    }

//    public StringBuilder loadDataFromFile(String fileName) {
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader br = new BufferedReader((new InputStreamReader((resourceAsStream))))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                stringBuilder.append(line).append(("\n"));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stringBuilder;
//    }

    public List<String> getDataList(String file) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(file);
        List<String> records = new ArrayList();
        try (BufferedReader br = new BufferedReader((new InputStreamReader((resourceAsStream))))) {
            String line;
            while ((line = br.readLine()) != null) {
//                stringBuilder.append(line).append(("\n"));
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
