package com.same.androidclass.bean;

import java.io.Serializable;

/**
 *课程实体
 * Created by alic on 16-5-7.
 */

public class Course implements Serializable {
    private String courseName;//课程名称
    private String courseCode;//课程代码
    private String courseTeacher;//负责老师
    private String courseRange;//上课时间范围
    private String courseClassroom;//课室
    private String courseWeek;//第几周
    private String courseTime;//时间
    private String courseTerm;//学期
    private String studentId;//学生学号

    public Course() {
    }


    public Course(String courseName, String courseCode, String courseTeacher, String courseRange, String courseClassroom, String courseWeek, String courseTime, String courseTerm, String studentId) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseTeacher = courseTeacher;
        this.courseRange = courseRange;
        this.courseClassroom = courseClassroom;
        this.courseWeek = courseWeek;
        this.courseTime = courseTime;
        this.courseTerm = courseTerm;

        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTeacher() {
        return courseTeacher;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseRange() {
        return courseRange;
    }

    public void setCourseRange(String courseRange) {
        this.courseRange = courseRange;
    }

    public String getCourseClassroom() {
        return courseClassroom;
    }

    public void setCourseClassroom(String courseClassroom) {
        this.courseClassroom = courseClassroom;
    }

    public String getCourseWeek() {
        return courseWeek;
    }

    public void setCourseWeek(String courseWeek) {
        this.courseWeek = courseWeek;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
