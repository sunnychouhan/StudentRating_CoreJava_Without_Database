package com.corejava.rating;

import com.corejava.rating.dto.StudentDto;
import com.corejava.rating.dto.SubjectDto;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PrintService {

    public static final String STAR_LINE = "\n*************************************************";

    void printRequiredOperations() {
        printLine(STAR_LINE);
        printLine("\n Select your Operation from below :");
        printLine("1) Compute Score by Student Name Press 1");
        printLine("2) Compute Score by Subject Name Press 2");
        printLine("3) Basic Features Press 3");
    }

    void printRequiredFeatures() {
        printLine(STAR_LINE);
        printLine("\n Select Features from below :");
        printLine("1) Add/Remove Assignments with weights from existing list ");
        printLine("2) Display all Assignments with weights");
        printLine("3) CRUD Operation On Student Data Press 3");
    }

    void printLine(String starLine) {
        System.out.println(starLine);
    }

    void printStudentsToSelect(List<String> allStudents) {
        printLine(PrintService.STAR_LINE);
        printLine("Select Student Name from below list -\n");
        allStudents.forEach(s -> printLine(" " + s));
    }

    void printSubjectsToSelect(List<String> allSubjects) {
        printLine(PrintService.STAR_LINE);
        System.out.print("Select Subject from below list - \n");
        allSubjects.forEach(s -> System.out.println(" " + s));
    }

    void printAddEnrollment() {
        printLine("\nEnter Comma separated Student Name, Subject and Category." +
                "Eg Davanth,Computing Techniques,test_1");
    }

    void printCRUDOperations() {
        printLine(STAR_LINE);
        printLine("\n Select CRUD Operation for student enrolment to subjects with Assignment Category :");
        printLine("1) Enrolment Student to Subject with Category ");
        printLine("2) Read Student Enrolment to Subjects");
        printLine("3) Update Enrolment for Student to Subject");
        printLine("4) Delete Student to Subject");
    }

    void printSelectedAddRemoveOperation() {
        printLine(STAR_LINE);
        printLine("\n Select Operations :");
        printLine("1) Add Assignments ");
        printLine("2) Remove Assignments");
    }

    void printCategories() {
        printLine(STAR_LINE);
        printLine("Category | Weight");
    }

    void printEnrolmentByStudentName() {
        printLine("\nEnter Student Name to view its Enrolment detail" +
                "\n [Davanth,Ananth,Chaya,Esharath,Bhagath]");
    }

    void printViewEnrolments() {
        printLine("Do you want to View all Enrollments then Press Y");
    }

    String getSelectedOperation() {
        Scanner requiredOperation = new Scanner(System.in).useDelimiter("\n");
        return requiredOperation.next();
    }

    void wantToTryAgain() {
        printLine(STAR_LINE);
        printLine("Do want to try again ? Press Y");
    }

    void goodBye() {
        printLine("Good Bye !!");
    }

    void printScoreByStudentName(String studentName, List<SubjectDto> subjectDto) {
        StudentDto studentDto = new StudentDto(subjectDto, studentName);
        printLine("\nstudentName = " + studentName);
        printLine("    subject          |Test|Quiz|Lab|Project|Overall(%)");

        studentDto.getSubjects().stream()
                .forEach(subject ->
                        System.out.println(
                                subject.getSubject() + " | "
                                        + subject.getTest() + " | "
                                        + subject.getQuiz() + " | "
                                        + subject.getLab() + " | "
                                        + subject.getProject() + " | "
                                        + subject.getOverall()
                        ));
    }


    void printScoreBySubjectName(String subjectName, List<StudentDto> studentDtos) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setStudentDtos(studentDtos);
        subjectDto.setSubject(subjectName);
        printLine("\nsubjectName = " + subjectName);
        printLine("Student |Test|Quiz|Lab|Project|Overall(%)");

        subjectDto.getStudentDtos().stream()
                .forEach(student ->
                        System.out.println(
                                student.getName() + " | "
                                        + student.getTest() + " | "
                                        + student.getQuiz() + " | "
                                        + student.getLab() + " | "
                                        + student.getProject() + " | "
                                        + student.getOverall()
                        ));
    }

    void invalidInputParam(String s) {
        printLine(STAR_LINE);
        printLine(s);
        System.exit(0);
    }

    void displayCategories(HashMap<String, Double> assignmentCategory) {
        assignmentCategory.forEach((s, aDouble) -> System.out.println(" " + s + " | " + aDouble));
    }
}
