package com.same.androidclass.bean;

import java.io.Serializable;

/**
 * 考试信息实体
 * Created by alic on 16-5-13.
 */
public class Exam implements Serializable {
    private String studentId;//学生学号
    private String courseCode;//课程代码
    private String courseName;//课程名称
    private String examDate;//考试日期
    private String examTime;//考试时间
    private String placeCode;//考场代码
    private String placeName;//考场名称
    private int examSeat;//考试座位
    private String examStatus;//考试状态

    public Exam() {
    }

    public Exam(String studentId, String courseCode, String courseName, String examDate, String examTime, String placeCode, String placeName, int examSeat, String examStatus) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.examDate = examDate;
        this.examTime = examTime;
        this.placeCode = placeCode;
        this.placeName = placeName;
        this.examSeat = examSeat;
        this.examStatus = examStatus;
    }

    public String getStuentId() {
        return studentId;
    }

    public void setStuentId(String stuentId) {
        this.studentId = stuentId;
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

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getExamSeat() {
        return examSeat;
    }

    public void setExamSeat(int examSeat) {
        this.examSeat = examSeat;
    }

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }
}
