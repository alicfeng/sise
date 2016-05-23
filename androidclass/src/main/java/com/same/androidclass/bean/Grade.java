package com.same.androidclass.bean;

import java.io.Serializable;

/**
 * 成绩实体
 * Created by alic on 16-5-14.
 */
public class Grade implements Serializable{
    private String studentId;//学生学号
    private String term;//学期
    private String courseCode;//课程代码
    private String courseName;//课程名称
    private float credit;//学分
    private String method;//考核方式
    private String schoolYear;//修读学年学期
    private String grade;//成绩
    private float credited;//已得学分
    private int genre;//类型 必修 选修

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public Grade() {
    }

    public Grade(String studentId, String term, String courseCode, String courseName, float credit, String method, String schoolYear, String grade, float credited, int genre) {
        this.studentId = studentId;
        this.term = term;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.method = method;
        this.schoolYear = schoolYear;
        this.grade = grade;
        this.credited = credited;
        this.genre = genre;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getCredited() {
        return credited;
    }

    public void setCredited(float credited) {
        this.credited = credited;
    }
}
