package com.corejava.rating.model;

public class Subject {
    Assignment assignmentList;
    String subject;

    public Subject(Assignment assignmentList, String subject) {
        this.assignmentList = assignmentList;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public Assignment getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(Assignment assignmentList) {
        this.assignmentList = assignmentList;
    }
}
