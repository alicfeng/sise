package com.same.androidclass.bean;

import java.io.Serializable;

/**
 * 学生实体类
 * Created by alic on 16-4-28.
 */
public class Student implements Serializable {
    private String studentId;//学号
    private String username;//姓名
    private String gradeYear;//年级
    private String profession;//专业
    private String idCard;//身份证
    private String email;//邮箱
    private String gradeAdmin;//行政班
    private String assistant;//辅导员
    private String headTeacher;//班主任

    //construct
    public Student(){

    }
    public Student(String studentId, String username, String gradeYear, String profession, String idCard, String email, String gradeAdmin, String assistant, String headTeacher) {
        this.studentId = studentId;
        this.username = username;
        this.gradeYear = gradeYear;
        this.profession = profession;
        this.idCard = idCard;
        this.email = email;
        this.gradeAdmin = gradeAdmin;
        this.assistant = assistant;
        this.headTeacher = headTeacher;
    }

    //set and get function start
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGradeYear() {
        return gradeYear;
    }

    public void setGradeYear(String gradeYear) {
        this.gradeYear = gradeYear;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGradeAdmin() {
        return gradeAdmin;
    }

    public void setGradeAdmin(String gradeAdmin) {
        this.gradeAdmin = gradeAdmin;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getHeadTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(String headTeacher) {
        this.headTeacher = headTeacher;
    }
    //set and get function end
}

