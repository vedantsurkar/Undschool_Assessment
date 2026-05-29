package com.example.Undoschool_Assessment.entity;
import jakarta.persistence.*;
@Entity
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Teacher teacher;

    private String teacherTimezone; // e.g. "America/New_York"

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public String getTeacherTimezone() { return teacherTimezone; }
    public void setTeacherTimezone(String teacherTimezone) { this.teacherTimezone = teacherTimezone; }
}
