package com.example.Undoschool_Assessment.repository;

import com.example.Undoschool_Assessment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
