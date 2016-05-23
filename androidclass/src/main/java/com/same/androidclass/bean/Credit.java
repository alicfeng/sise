package com.same.androidclass.bean;

import java.io.Serializable;

/**
 * 课程信息实体
 * Created by alic on 16-5-14.
 */
public class Credit implements Serializable {
    private String studentId;
    private float obligatoryGradeAll;//必修总学分
    private float obligatoryGraded;//必修已获得学分
    private float optionGradeAll;//选修总学分
    private float optionGraded;//选修已获得学分
    private float graded;//已修学分
    private float grading;//在读课程学分
    private float optionCourseGrade;//已选课程学分
    private float expectedGrade;//预期获得学分
    private float averagePoint;//平均学分绩点
    private float gradeAll;//本专业本年级毕业需修满学分


    public Credit() {
    }

    public Credit(String studentId, float obligatoryGradeAll, float obligatoryGraded, float optionGradeAll, float optionGraded, float graded, float grading, float optionCourseGrade, float expectedGrade, float averagePoint, float gradeAll) {
        this.studentId = studentId;
        this.obligatoryGradeAll = obligatoryGradeAll;
        this.obligatoryGraded = obligatoryGraded;
        this.optionGradeAll = optionGradeAll;
        this.optionGraded = optionGraded;
        this.graded = graded;
        this.grading = grading;
        this.optionCourseGrade = optionCourseGrade;
        this.expectedGrade = expectedGrade;
        this.averagePoint = averagePoint;
        this.gradeAll = gradeAll;
    }


    public float getObligatoryGradeAll() {
        return obligatoryGradeAll;
    }

    public void setObligatoryGradeAll(float obligatoryGradeAll) {
        this.obligatoryGradeAll = obligatoryGradeAll;
    }

    public float getObligatoryGraded() {
        return obligatoryGraded;
    }

    public float getGradeAll() {
        return gradeAll;
    }

    public void setGradeAll(float gradeAll) {
        this.gradeAll = gradeAll;
    }

    public void setObligatoryGraded(float obligatoryGraded) {
        this.obligatoryGraded = obligatoryGraded;
    }

    public float getOptionGradeAll() {
        return optionGradeAll;
    }

    public void setOptionGradeAll(float optionGradeAll) {
        this.optionGradeAll = optionGradeAll;
    }

    public float getOptionGraded() {
        return optionGraded;
    }

    public void setOptionGraded(float optionGraded) {
        this.optionGraded = optionGraded;
    }

    public float getGraded() {
        return graded;
    }

    public void setGraded(float graded) {
        this.graded = graded;
    }

    public float getGrading() {
        return grading;
    }

    public void setGrading(float grading) {
        this.grading = grading;
    }

    public float getOptionCourseGrade() {
        return optionCourseGrade;
    }

    public void setOptionCourseGrade(float optionCourseGrade) {
        this.optionCourseGrade = optionCourseGrade;
    }

    public float getExpectedGrade() {
        return expectedGrade;
    }

    public void setExpectedGrade(float expectedGrade) {
        this.expectedGrade = expectedGrade;
    }

    public float getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(float averagePoint) {
        this.averagePoint = averagePoint;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
