package com.corejava.rating;

import com.corejava.rating.dto.StudentDto;
import com.corejava.rating.dto.SubjectDto;
import com.corejava.rating.model.Assignment;
import com.corejava.rating.model.Student;
import com.corejava.rating.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComputeService {
    public static final String DEFAULT_POINTS = "100";
    public static final String DEFAULT_SUBMISSION_OCT_2022 = "6-Oct-2022";
    public static final String ONE_1 = "1";
    public static final String TWO_2 = "2";
    public static final String THREE_3 = "3";
    public static final String FOUR_4 = "4";
    public static final String TEST = "test";
    public static final String QUIZ = "quiz";
    public static final String LAB = "lab";
    public static final String PROJECT = "project";
    PrintService printService;
    DataLoader dataLoaderInstance;

    public ComputeService(PrintService printService) {
        this.printService = printService;
        dataLoaderInstance = DataLoader.getDataLoaderInstance();
    }

    void startProcess() {
        firstDisplay();
        List<Student> enrollmentData = dataLoaderInstance.getStudents();
        Boolean start = Boolean.TRUE;
        while (start) {
            printService.printRequiredOperations();
            switch (printService.getSelectedOperation()) {
                case ONE_1:
                    computeScoreByStudentName(enrollmentData, getAllStudents(enrollmentData));
                    break;
                case TWO_2:
                    computeScoreBySubjectName(enrollmentData, getAllSubjects(enrollmentData));
                    break;
                case THREE_3:
                    otherFeatures();
                    break;
                default:
                    invalidOperationExitSystem();
                    break;
            }

            printService.wantToTryAgain();
            String next = printService.getSelectedOperation();
            if (!next.equals("Y")) {
                start = false;
                printService.goodBye();
            }
        }
    }

    void computeScoreByStudentName(List<Student> studentList, List<String> allStudents) {
        printService.printStudentsToSelect(allStudents);
        String studentName = printService.getSelectedOperation();
        List<SubjectDto> subjectDtos = calculateScoreByStudentName(studentList, studentName);
        printService.printScoreByStudentName(studentName, subjectDtos);
    }

    void computeScoreBySubjectName(List<Student> studentList, List<String> allSubjects) {
        printService.printSubjectsToSelect(allSubjects);
        String subjectName = printService.getSelectedOperation();
        List<StudentDto> studentDtos = calculateScoreBySubjectName(studentList, subjectName);
        printService.printScoreBySubjectName(subjectName, studentDtos);
    }


    List<SubjectDto> calculateScoreByStudentName(List<Student> studentList, String studentName) {
        Map<String, List<Student>> bySubject = groupByStudentName(studentList, studentName);
        return getSubjectsByStudentName(bySubject);
    }

    List<StudentDto> calculateScoreBySubjectName(List<Student> studentList, String subjectName) {
        Map<String, List<Student>> byStudent = groupBySubjectName(studentList, subjectName);
        return getStudentsByStudentName(byStudent);
    }

    List<SubjectDto> getSubjectsByStudentName(Map<String, List<Student>> bySubject) {

        return bySubject.entrySet()
                .stream()
                .map(sujbectKey -> {
                    String subject = sujbectKey.getKey();
                    List<Student> students = sujbectKey.getValue();

                    ArrayList<Double> test = new ArrayList();
                    ArrayList<Double> quiz = new ArrayList();
                    ArrayList<Double> lab = new ArrayList();
                    ArrayList<Double> project = new ArrayList();
                    students.stream()
                            .forEach(student -> {
                                Assignment assignmentList = student.getSubjects().getAssignmentList();

                                if (assignmentList.getCategory().startsWith(TEST)) {
                                    test.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(QUIZ)) {
                                    quiz.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(LAB)) {
                                    lab.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(PROJECT)) {
                                    project.add(Double.valueOf(assignmentList.getPoints()));
                                }
                            });


                    Double testRatings = deriveRating(test, test.size(), 40.0);
                    Double quizRatings = deriveRating(quiz, quiz.size(), 20.0);
                    Double labRatings = deriveRating(lab, lab.size(), 10.0);
                    Double projectRatings = deriveRating(project, project.size(), 30.0);


                    SubjectDto dto = new SubjectDto();
                    dto.setSubject(subject);

                    dto.setLab(getIfApplicable(lab, labRatings));
                    dto.setQuiz(getIfApplicable(quiz, quizRatings));
                    dto.setTest(getIfApplicable(test, testRatings));
                    dto.setProject(getIfApplicable(project, projectRatings));

                    Double ovrallRatings = labRatings + quizRatings + testRatings + projectRatings;
                    dto.setOverall(String.valueOf(ovrallRatings));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    List<StudentDto> getStudentsByStudentName(Map<String, List<Student>> bySubject) {
        return bySubject.entrySet()
                .stream()
                .map(stringListEntry -> {

                    String students = stringListEntry.getKey();
                    List<Student> subjects = stringListEntry.getValue();

                    ArrayList<Double> test = new ArrayList();
                    ArrayList<Double> quiz = new ArrayList();
                    ArrayList<Double> lab = new ArrayList();
                    ArrayList<Double> project = new ArrayList();
                    subjects.stream()
                            .forEach(stdnt -> {
                                Assignment assignmentList = stdnt.getSubjects().getAssignmentList();

                                if (assignmentList.getCategory().startsWith(TEST)) {
                                    test.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(QUIZ)) {
                                    quiz.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(LAB)) {
                                    lab.add(Double.valueOf(assignmentList.getPoints()));

                                } else if (assignmentList.getCategory().startsWith(PROJECT)) {
                                    project.add(Double.valueOf(assignmentList.getPoints()));
                                }
                            });


                    Double testRatings = deriveRating(test, test.size(), 40.0);
                    Double quizRatings = deriveRating(quiz, quiz.size(), 20.0);
                    Double labRatings = deriveRating(lab, lab.size(), 10.0);
                    Double projectRatings = deriveRating(project, project.size(), 30.0);


                    StudentDto dto = new StudentDto();
                    dto.setName(students);

                    dto.setLab(getIfApplicable(lab, labRatings));
                    dto.setQuiz(getIfApplicable(quiz, quizRatings));
                    dto.setTest(getIfApplicable(test, testRatings));
                    dto.setProject(getIfApplicable(project, projectRatings));

                    Double ovrallRatings = labRatings + quizRatings + testRatings + projectRatings;
                    dto.setOverall(String.valueOf(ovrallRatings));
                    return dto;
                }).collect(Collectors.toList());
//        bySubject.forEach((students, subjects) -> {
//                    ArrayList<Double> test = new ArrayList();
//                    ArrayList<Double> quiz = new ArrayList();
//                    ArrayList<Double> lab = new ArrayList();
//                    ArrayList<Double> project = new ArrayList();
//                    subjects.stream()
//                            .forEach(stdnt -> {
//                                Assignment assignmentList = stdnt.getSubjects().getAssignmentList();
//
//                                if (assignmentList.getCategory().startsWith(TEST)) {
//                                    test.add(Double.valueOf(assignmentList.getPoints()));
//
//                                } else if (assignmentList.getCategory().startsWith(QUIZ)) {
//                                    quiz.add(Double.valueOf(assignmentList.getPoints()));
//
//                                } else if (assignmentList.getCategory().startsWith(LAB)) {
//                                    lab.add(Double.valueOf(assignmentList.getPoints()));
//
//                                } else if (assignmentList.getCategory().startsWith(PROJECT)) {
//                                    project.add(Double.valueOf(assignmentList.getPoints()));
//                                }
//                            });
//
//
//                    Double testRatings = deriveRating(test, test.size(), 40.0);
//                    Double quizRatings = deriveRating(quiz, quiz.size(), 20.0);
//                    Double labRatings = deriveRating(lab, lab.size(), 10.0);
//                    Double projectRatings = deriveRating(project, project.size(), 30.0);
//
//
//                    StudentDto dto = new StudentDto();
//                    dto.setName(students);
//
//                    dto.setLab(labRatings);
//                    dto.setQuiz(quizRatings);
//                    dto.setTest(testRatings);
//                    dto.setProject(projectRatings);
//
//                    dto.setOverall(labRatings + quizRatings + testRatings + projectRatings);
//                    studentDtos.add(dto);
//                });
    }

    private static String getIfApplicable(ArrayList<Double> lab, Double labRatings) {
        return lab.size() > 0 ? String.valueOf(labRatings) : "NA";
    }

    void invalidOperationExitSystem() {
        this.printService.invalidInputParam("Sorry ! Invalid Operation , Terminating  Bye Bye!");
    }


    List<String> getAllStudents(List<Student> studentList) {
        return studentList.stream()
                .map(student -> student.getName())
                .distinct()
                .collect(Collectors.toList());
    }

    List<String> getAllSubjects(List<Student> studentList) {
        return studentList.stream()
                .map(student -> student.getSubjects().getSubject())
                .distinct()
                .collect(Collectors.toList());
    }


    Map<String, List<Student>> groupByStudentName(List<Student> studentList, String studentName) {
        return studentList.stream()
                .filter(student -> student.getName().equals(studentName))
                .collect(Collectors.groupingBy(student -> student.getSubjects().getSubject()));
    }

    Map<String, List<Student>> groupBySubjectName(List<Student> studentList, String subjectName) {
        return studentList.stream()
                .filter(student -> student.getSubjects().getSubject().equals(subjectName))
                .collect(Collectors.groupingBy(student -> student.getName()));
    }


    Double deriveRating(ArrayList<Double> assignments, int numberOfAssignments, Double assigmentCategory) {
        return assignments.stream()
                .map(score -> calculatePercentage(numberOfAssignments, score, assigmentCategory))
                .reduce((double) 0, (a, b) -> a + b);
    }

    Double calculatePercentage(int numberOfAssignments, Double points, Double assignmentCategory) {
        return numberOfAssignments != 0 ? (((assignmentCategory / numberOfAssignments) * points) / 100) : 0;
    }

    void addRemoveCategories() {
        printService.printSelectedAddRemoveOperation();
        switch (printService.getSelectedOperation()) {
            case ONE_1:
                addCategories();
                break;
            case "2":
                removeCategories();
                break;
            default:
                invalidOperationExitSystem();
                break;
        }
    }

    void removeCategories() {
        HashMap<String, Double> category = this.dataLoaderInstance.getAssignmentCategory();
        printService.displayCategories(category);
        printService.printLine("Enter Category Type From Above list to Remove ");
        String categoryTypeEntered = printService.getSelectedOperation();
        category.remove(categoryTypeEntered);
        printService.displayCategories(category);
    }

    void addCategories() {
        HashMap<String, Double> category = this.dataLoaderInstance.getAssignmentCategory();
        printService.printLine("1) Enter Category Type ");
        String categoryTypeEntered = printService.getSelectedOperation();

        printService.printLine("2) Enter Category Weight ");
        String categoryWeightEntered = printService.getSelectedOperation();

        category.put(categoryTypeEntered, Double.valueOf(categoryWeightEntered));
        printService.displayCategories(category);
    }

    void validateNewEnrolmentRequest(String[] input) {
        if (input.length < 3) {
            printService.invalidInputParam("Sorry! Invalid Input not matching with example format." +
                    "Eg Davanth, Computing Techniques,test_1, " +
                    "\nTerminating  Bye Bye!");
        }

        String student = input[0];
        List<String> allStudents = getAllStudents(this.dataLoaderInstance.getStudents());
        if (!allStudents.contains(student)) {
            printService.invalidInputParam("Sorry! Invalid Student Name :" + student + " \nTerminating  Bye Bye!");
        }

        String subject = input[1];
        List<String> allSubjects = getAllSubjects(this.dataLoaderInstance.getStudents());
        if (!allSubjects.contains(subject)) {
            printService.invalidInputParam("Sorry! Invalid Subject Name :" + subject + " \nTerminating  Bye Bye!");
        }

        String asmtCategory = input[2];
        if (!asmtCategory.startsWith(TEST)
                && !asmtCategory.startsWith(QUIZ)
                && !asmtCategory.startsWith(PROJECT)
                && !asmtCategory.startsWith(LAB)
        ) {
            printService.invalidInputParam("Sorry! Invalid Assignment Category " + asmtCategory + "\nTerminating  Bye Bye!");
        }
    }


    int getNextSerialNumber(List<Student> students) {
        int serialNumber = students.size();
        serialNumber = serialNumber + 1;
        return serialNumber;
    }

    void addEnrolment() {
        printService.printAddEnrollment();
        String enrollment = printService.getSelectedOperation();
        String[] split = enrollment.split("[,]+");
        validateNewEnrolmentRequest(split);
        List<Student> students = this.dataLoaderInstance.getStudents();
        Assignment assignment = new Assignment(split[2], DEFAULT_POINTS, DEFAULT_SUBMISSION_OCT_2022);
        Subject subjects = new Subject(assignment, split[1]);
        int serialNumber = getNextSerialNumber(students);
        Student student = new Student(subjects, split[0], String.valueOf(serialNumber));
        students.add(student);
        printService.printLine("Successfully Enrolled Student = " + split[0]);
    }

    void showCategories() {
        HashMap<String, Double> category = this.dataLoaderInstance.getAssignmentCategory();
        printService.printCategories();
        printService.displayCategories(category);
    }

    void otherFeatures() {
        printService.printRequiredFeatures();
        switch (printService.getSelectedOperation()) {
            case ONE_1:
                addRemoveCategories();
                break;
            case TWO_2:
                showCategories();
                break;
            case THREE_3:
                crudOperations();
                break;
            default:
                invalidOperationExitSystem();
                break;
        }
    }

    private void crudOperations() {
        printService.printCRUDOperations();
        switch (printService.getSelectedOperation()) {
            case ONE_1:
                addEnrolment();
                break;
            case TWO_2:
                viewEnrolment();
                break;
            case THREE_3:
                updateEnrolment();
                break;
            case FOUR_4:
                deleteEnrolment();
                break;
            default:
                invalidOperationExitSystem();
                break;
        }
    }

    void deleteEnrolment() {
        printService.printLine("\nTo Delete an enrolment," +
                "\nPlease Note the Id by calling read enrolment Action First," +
                "Provide the Id of Student Enrolment to Delete");

        String deleteEnrolmentId = printService.getSelectedOperation();
        List<Student> students = this.dataLoaderInstance.getStudents();
        students.removeIf(student -> student.getSerialNumber().equals(deleteEnrolmentId));
        printService.printLine("Removed Student Enrolment  with Id= " + deleteEnrolmentId + "Successfully");
    }

    void updateEnrolment() {
        printService.printLine("\nTo Update students Enrolled Data," +
                "\nPlease Note the Id by calling read enrolment Action," +
                "\nThe input should be provide in the below format" +
                " Id,StudentName,Subject,Category,SubmissionDate,Points" +
                "\nEg 20,Chaya,Electro Field,test_1,21-Jul-2016,80");
        String updateEnrolment = printService.getSelectedOperation();
        String[] split = updateEnrolment.split("[,]+");
        List<Student> students = this.dataLoaderInstance.getStudents();
        students.forEach(student -> {
            if (student.getSerialNumber().equals(split[0])) {
                student.setName(split[1]);
                Subject subjects = student.getSubjects();
                subjects.setSubject(split[2]);
                subjects.setAssignmentList(new Assignment(split[3], split[5], split[4]));
            }
        });

        printService.printLine("Updated Successfully Result can verified by call read Api Action.");
    }

    void viewEnrolment() {
        printService.printViewEnrolments();
        String viewAllEnrolment = printService.getSelectedOperation();
        List<Student> students = dataLoaderInstance.getStudents();
        List<Student> enrolledStudents;
        if (viewAllEnrolment.equals("Y")) {
            enrolledStudents = students.stream()
                    .collect(Collectors.toList());
        } else {
            enrolledStudents = viewEnrolmentByStudentName(this.dataLoaderInstance, students);
        }
        printService.printLine("id|studentName|subject|category|submissionDate|points ");
        enrolledStudents.forEach(student -> {
            String detail =
                    student.getSerialNumber() + "|  " +
                            student.getName() + "|" +
                            student.getSubjects().getSubject() + "|" +
                            student.getSubjects().getAssignmentList().getCategory() + "|" +
                            student.getSubjects().getAssignmentList().getSubmissionDate() + "|" +
                            student.getSubjects().getAssignmentList().getPoints();
            printService.printLine(detail);
        });
    }

    void firstDisplay() {
        printService.printLine(PrintService.STAR_LINE);
        List<Student> students = dataLoaderInstance.getStudents();
        List<Student> enrolledStudents = students.stream()
                .collect(Collectors.toList());
        printService.printLine("id|studentName|subject|category|submissionDate|points ");
        enrolledStudents.forEach(student -> {
            String detail =
                    student.getSerialNumber() + "|  " +
                            student.getName() + "|" +
                            student.getSubjects().getSubject() + "|" +
                            student.getSubjects().getAssignmentList().getCategory() + "|" +
                            student.getSubjects().getAssignmentList().getSubmissionDate() + "|" +
                            student.getSubjects().getAssignmentList().getPoints();
            printService.printLine(detail);
        });
    }

    List<Student> viewEnrolmentByStudentName(DataLoader dataLoaderInstance, List<Student> students) {
        List<Student> collect;
        printService.printEnrolmentByStudentName();
        String viewEnrolment = printService.getSelectedOperation();
        List<String> allStudents = getAllStudents(dataLoaderInstance.getStudents());
        if (!allStudents.contains(viewEnrolment)) {
            printService.invalidInputParam("Sorry! Invalid Student Name :" + viewEnrolment + " \nTerminating  Bye Bye!");
        }
        collect = students.stream()
                .filter(student -> student.getName().equals(viewEnrolment))
                .collect(Collectors.toList());
        return collect;
    }

}
