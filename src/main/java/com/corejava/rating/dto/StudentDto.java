package com.corejava.rating.dto;


import java.util.List;

public class StudentDto  extends CommonDto {
    List<SubjectDto> subjects;
    String name;

    public StudentDto(List<SubjectDto> subjects, String name) {
        this.subjects = subjects;
        this.name = name;
    }

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentDto() {
    }
}
