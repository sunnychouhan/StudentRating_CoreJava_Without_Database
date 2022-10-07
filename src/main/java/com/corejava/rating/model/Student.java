package com.corejava.rating.model;


public class Student {
    Subject subjects;
    String name;

    String serialNumber;

    public Student(Subject subjects, String name, String serialNumber) {
        this.subjects = subjects;
        this.name = name;
        this.serialNumber= serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject subjects) {
        this.subjects = subjects;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
