package com.example.assignment_login.Model;

public class CourseModel {

    private String courseName;
    private String courseImg;
    private String courseMode;
    private String courseTrack;

    public CourseModel(String courseName, String courseImg, String courseMode, String courseTrack) {
        this.courseName = courseName;
        this.courseImg = courseImg;
        this.courseMode = courseMode;
        this.courseTrack = courseTrack;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getCourseMode() {
        return courseMode;
    }

    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }

    public String getCourseTrack() {
        return courseTrack;
    }

    public void setCourseTrack(String courseTrack) {
        this.courseTrack = courseTrack;
    }
}
