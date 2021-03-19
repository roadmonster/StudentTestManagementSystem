package com.lihao.StudentManagement.bean;

public class Student {
    private int flowId;
    private int type;
    private String id;
    private String examId;
    private String studentName;
    private String location;
    private int grade;

    public Student() {
    }

    public Student(int flowId, int type, String id,
                   String examId, String studentName,
                   String location, int grade) {
        this.flowId = flowId;
        this.type = type;
        this.id = id;
        this.examId = examId;
        this.studentName = studentName;
        this.location = location;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "flowId=" + flowId +
                ", type=" + type +
                ", id='" + id + '\'' +
                ", examId='" + examId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", location='" + location + '\'' +
                ", grade=" + grade +
                '}';
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
