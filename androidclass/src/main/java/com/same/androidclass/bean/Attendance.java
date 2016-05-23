package com.same.androidclass.bean;

import java.io.Serializable;

/**
 * 考勤实体类
 * Created by alic on 16-4-30.
 */
public class Attendance implements Serializable{
    private String classCode;//课程代码
    private String className;//课程名称
    private String classStatus;//考勤状态
    private String absenceNum;//学期的旷课节数
    private String studentId;//学生学号
    private String term;//学期

    //construct -- start
    public Attendance() {
    }

    public Attendance(String classCode, String className, String classStatus, String absenceNum, String studentId, String term) {
        this.classCode = classCode;
        this.className = className;
        this.classStatus = classStatus;
        this.absenceNum = absenceNum;
        this.studentId = studentId;
        this.term = term;
    }

//construct -- end


    //set and get -- start
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getAbsenceNum() {
        return absenceNum;
    }

    public void setAbsenceNum(String absenceNum) {
        this.absenceNum = absenceNum;
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
    //set and get -- end
}
