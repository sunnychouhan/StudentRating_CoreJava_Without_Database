package com.corejava.rating;

import com.corejava.rating.dto.StudentDto;
import com.corejava.rating.dto.SubjectDto;
import com.corejava.rating.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComputeServiceTest {
    @Mock
    PrintService printService;

    ComputeService computeService;

    @BeforeEach
    public void setUp() {
        computeService = new ComputeService(printService);
    }

    @Test
    @DisplayName("Compute average score per assignment category &overall  rating for assigned subject")
    public void testRequirement_1() {

        //given
        List<Student> students = computeService.dataLoaderInstance.getStudents();

        //when
        List<SubjectDto> subjectDtos = computeService.calculateScoreByStudentName(students, "Ananth");

        //then
        Assertions.assertEquals(2, subjectDtos.size());
        subjectDtos.forEach(subjectDto -> {
            if (subjectDto.getSubject().equals("Electro Fields")) {
                Assertions.assertEquals(subjectDto.getTest(), "40.0");
                Assertions.assertEquals(subjectDto.getQuiz(), "20.0");
                Assertions.assertEquals(subjectDto.getLab(), "10.0");
                Assertions.assertEquals(subjectDto.getProject(), "30.0");
                Assertions.assertEquals(subjectDto.getOverall(), "100.0");
            }

            if (subjectDto.getSubject().equals("Computing Techniques")) {
                Assertions.assertEquals(subjectDto.getTest(), "34.4");
                Assertions.assertEquals(subjectDto.getQuiz(), "NA");
                Assertions.assertEquals(subjectDto.getLab(), "NA");
                Assertions.assertEquals(subjectDto.getProject(), "NA");
                Assertions.assertEquals(subjectDto.getOverall(), "34.4");
            }
        });
    }

    @Test
    @DisplayName("Compute subject average score per assignment category &overall  rating for assigned subject")
    public void testRequirement_2() {

        //given
        List<Student> students = computeService.dataLoaderInstance.getStudents();

        //when

        List<StudentDto> studentDtos =
                computeService.calculateScoreBySubjectName(students, "Computing Techniques");

        //then
        Assertions.assertEquals(3, studentDtos.size());
        studentDtos.forEach(subjectDto -> {
            if (subjectDto.getName().equals("Bhagath")) {
                Assertions.assertEquals(subjectDto.getTest(), "NA");
                Assertions.assertEquals(subjectDto.getQuiz(), "NA");
                Assertions.assertEquals(subjectDto.getLab(), "NA");
                Assertions.assertEquals(subjectDto.getProject(), "30.0");
                Assertions.assertEquals(subjectDto.getOverall(), "30.0");
            }

            if (subjectDto.getName().equals("Chaya")) {
                Assertions.assertEquals(subjectDto.getTest(), "NA");
                Assertions.assertEquals(subjectDto.getQuiz(), "4.0");
                Assertions.assertEquals(subjectDto.getLab(), "NA");
                Assertions.assertEquals(subjectDto.getProject(), "NA");
                Assertions.assertEquals(subjectDto.getOverall(), "4.0");

            }


            if (subjectDto.getName().equals("Ananth")) {
                Assertions.assertEquals(subjectDto.getTest(), "34.4");
                Assertions.assertEquals(subjectDto.getQuiz(), "NA");
                Assertions.assertEquals(subjectDto.getLab(), "NA");
                Assertions.assertEquals(subjectDto.getProject(), "NA");
                Assertions.assertEquals(subjectDto.getOverall(), "34.4");

            }

        });

    }

    @Test
    @DisplayName("Should add new Categories")
    public void testAddCategories() {
        //when
        when(printService.getSelectedOperation()).thenReturn("Games").thenReturn("100");
        computeService.addCategories();

        //then
        HashMap<String, Double> assignmentCategory = computeService.dataLoaderInstance.getAssignmentCategory();
        Assertions.assertEquals(5, assignmentCategory.size());
    }

    @Test
    @DisplayName("Should remove Categories")
    public void testRemoveCategories() {
        //when
        when(printService.getSelectedOperation()).thenReturn("TEST");
        computeService.removeCategories();

        //then
        HashMap<String, Double> assignmentCategory = computeService.dataLoaderInstance.getAssignmentCategory();
        Assertions.assertEquals(3, assignmentCategory.size());
    }

    @Test
    @DisplayName("Should showCategories")
    public void testShowCategories() {
        //when
        computeService.showCategories();

        //then
        HashMap<String, Double> assignmentCategory = computeService.dataLoaderInstance.getAssignmentCategory();
        Assertions.assertEquals(4, assignmentCategory.size());
    }

    @Test
    @DisplayName("Should All Enrolments")
    public void testShowAllEnrolments() {
        //given
        when(printService.getSelectedOperation()).thenReturn("Y");

        //when
        computeService.viewEnrolment();

        //then
        List<Student> students = computeService.dataLoaderInstance.getStudents();
        Assertions.assertEquals(20, students.size());
    }
}